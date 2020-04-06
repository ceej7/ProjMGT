package com.achieveit.service;

import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import net.bytebuddy.implementation.bytecode.assign.primitive.PrimitiveUnboxingDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ProjectServiceTest {
    ProjectMapper projectMapper;
    WorkflowMapper workflowMapper;
    EmployeeMapper employeeMapper;
    EmployeeProjectMapper employeeProjectMapper;
    ProjectService projectService;


    @BeforeEach
    void setup(){
        projectMapper = mock(ProjectMapper.class);
        workflowMapper = mock(WorkflowMapper.class);
        employeeMapper = mock(EmployeeMapper.class);
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        projectService = new ProjectService(projectMapper, workflowMapper, employeeMapper,employeeProjectMapper);
    }

    //////////////getById()//////////////
    @Test
    void happy_path_getById() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        when(projectMapper.getByPidCascade("20200001O01")).thenReturn(w);
        ResponseMsg msg = projectService.getById("20200001O01");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals("20200001O01", ((Project)msg.getResponseMap().get("Project")).getPid());
        verify(projectMapper).getByPidCascade("20200001O01");
    }

    @Test
    void exception_when_getById() {
        when(projectMapper.getByPidCascade(any())).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getById("20200001O01");
        assertEquals(404, msg.getStatus());
        verify(projectMapper).getByPidCascade(any());
    }

    //////////////getProjectToManage()//////////////
    @Test
    void happy_path_getProjectToManage() {
        Project project = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> projects = new ArrayList<Project>();
        projects.add(project);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);

        when(employeeMapper.getByEid(1)).thenReturn(employee);
        when(projectMapper.getBySupEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByPmEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByQaManagerEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByEpgLeaderEidCascade(1)).thenReturn(projects);
        when(projectMapper.getByConfigurerEidCascade(1)).thenReturn(projects);

        String[] titles = {"pm_manager", "configurer", "pm", "epg_leader", "qa_manager"};
        int i = 0;
        while (i < 5) {
            String title = titles[i++];
            employee.setTitle(title);
            ResponseMsg msg = projectService.getProjectToManage(1);
            assertEquals(200, msg.getStatus());
            assertNotNull(msg.getResponseMap().get("Project"));
        }
    }

    @Test
    void exception_when_getProjectToManage(){
        when(employeeMapper.getByEid(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getProjectToManage(1);
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(employeeMapper).getByEid(1);
    }

    @Test
    void wrong_title_when_getProjectToManage(){
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "member", null);
        when(employeeMapper.getByEid(1)).thenReturn(employee);
        ResponseMsg msg = projectService.getProjectToManage(1);
        assertEquals(208, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(employeeMapper).getByEid(1);
    }

    //////////////getPagedProjectByEid()//////////////
    @Test
    void happy_path_getPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getByEidCascade(1)).thenReturn(pws);
        ResponseMsg msg = projectService.getPagedProjectByEid(1,2,10);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getByEidCascade(1);
    }

    @Test
    void exception_when_getPagedProjectByEid() {
        when(projectMapper.getByEidCascade(1)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getPagedProjectByEid(1,2,10);
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(projectMapper).getByEidCascade(1);
    }

    //////////////getFilteredPagedProjectByEid()//////////////
    @Test
    void happy_path_getFilteredPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(1,"",256,2047)).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","doing");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getNamedStatusByEidCascade(1,"",256,2047);
    }

    @Test
    void status_applying_getFilteredPagedProjectByEid() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt())).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","applying");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        verify(projectMapper).getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt());
    }

    @Test
    void exception_when_getFilteredPagedProjectByEid() {
        when(projectMapper.getNamedStatusByEidCascade(1,"",256,2047)).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"","doing");
        assertEquals(404, msg.getStatus());
        assertNull(msg.getResponseMap().get("Project"));
        verify(projectMapper).getNamedStatusByEidCascade(1,"",256,2047);
    }

    @Test
    void happy_path_getFilteredPagedProjectByEid_wo_status() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt())).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,"",null);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(10, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper).getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt());
    }

    @Test
    void happy_path_getFilteredPagedProjectByEid_wo_name() {
        Project w = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        List<Project> pws = new ArrayList<Project>();
        for (int i = 0; i < 100; i++) {
            pws.add(w);
        }
        when(projectMapper.getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt())).thenReturn(pws);
        ResponseMsg msg = projectService.getFilteredPagedProjectByEid(1,2,10,null,"done");
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("Project"));
        assertEquals(10, ((List<Project>)msg.getResponseMap().get("Project")).size());
        assertEquals(20, (int)msg.getResponseMap().get("page_length"));
        verify(projectMapper, times(2)).getNamedStatusByEidCascade(anyInt(),anyString(),anyInt(),anyInt());
    }

    //////////////newProject()//////////////
    @Test
    void happy_path_with_newProject()throws Exception{
        when(projectMapper.add(any())).thenReturn(1);
        when(workflowMapper.addWorkflow(any())).thenReturn(1);
        when(projectMapper.updateWorkflow(anyString(),anyInt())).thenReturn(1);
        when(workflowMapper.addTimeline(anyInt(),anyString(),anyInt())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeProject(any())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        Project project=new Project("proj1",null,null,null,null,null,null,null,null);
        ArrayList<Project>projects=new ArrayList<Project>();
        projects.add(project);
        projects.add(project);
        projects.add(project);
        when(projectMapper.getAllProjectIds()).thenReturn(projects);
        when(projectMapper.getByPidCascade(anyString())).thenReturn(project);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "pm", null);
        when(employeeMapper.getByEid(anyInt())).thenReturn(employee);
        ResponseMsg msg = projectService.newProject("proj1",null,null,null,null,1,1,1,1,1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("project"));
    }

    @Test
    void contain_header_when_newProject()throws Exception{

        when(projectMapper.add(any())).thenReturn(1);
        when(workflowMapper.addWorkflow(any())).thenReturn(1);
        when(projectMapper.updateWorkflow(anyString(),anyInt())).thenReturn(1);
        when(workflowMapper.addTimeline(anyInt(),anyString(),anyInt())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeProject(any())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        when(projectMapper.getByPidCascade(anyString())).thenReturn(new Project("proj1",null,null,null,null,null,null,null,null));
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "pm", null);
        when(employeeMapper.getByEid(anyInt())).thenReturn(employee);
        ResponseMsg msg = projectService.newProject("proj1",null,null,null,null,1,1,1,1,1);
        assertEquals(200, msg.getStatus());
        assertNotNull(msg.getResponseMap().get("project"));
    }

    @Test
    void faied_to_newProject()throws Exception{
        when(projectMapper.add(any())).thenReturn(0);
        when(workflowMapper.addWorkflow(any())).thenReturn(1);
        when(projectMapper.updateWorkflow(anyString(),anyInt())).thenReturn(1);
        when(workflowMapper.addTimeline(anyInt(),anyString(),anyInt())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeProject(any())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        when(projectMapper.getByPidCascade(anyString())).thenReturn(new Project("proj1",null,null,null,null,null,null,null,null));
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "pm", null);
        when(employeeMapper.getByEid(anyInt())).thenReturn(employee);
        ResponseMsg msg = projectService.newProject("proj1",null,null,null,null,1,1,1,1,1);
        assertEquals(214, msg.getStatus());
        assertNull(msg.getResponseMap().get("project"));
    }

    @Test
    void not_pm_when_newProject()throws Exception{
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, "member", null);
        when(employeeMapper.getByEid(anyInt())).thenReturn(employee);
        ResponseMsg msg = projectService.newProject("proj1",null,null,null,null,1,1,1,1,1);
        assertEquals(216, msg.getStatus());
        verify(employeeMapper).getByEid(anyInt());
    }

    @Test
    void exception_when_newProject()throws Exception{
        when(employeeMapper.getByEid(anyInt())).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.newProject("proj1",null,null,null,null,1,1,1,1,1);
        assertEquals(404, msg.getStatus());
        verify(employeeMapper).getByEid(anyInt());
    }

    //////////////removeEmployeeProject()//////////////
    @Test
    void happy_path_with_removeEmployeeProject()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, null, null, 1);
        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(employeeProject);
        when(employeeProjectMapper.delete(anyInt())).thenReturn(1);
        ArrayList<EmployeeRoleProject> roles=new ArrayList<EmployeeRoleProject>();
        EmployeeRoleProject employeeRoleProject=new EmployeeRoleProject();
        employeeRoleProject.setRole("member");
        roles.add(employeeRoleProject);
        employeeProject.setRoles(roles);
        ResponseMsg msg = projectService.removeEmployeeProject(1);
        assertEquals(200, msg.getStatus());
    }

    @Test
    void alternate_path_with_removeEmployeeProject()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, null, null, 1);
        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(employeeProject);
        when(employeeProjectMapper.delete(anyInt())).thenReturn(1);
        ArrayList<EmployeeRoleProject> roles=new ArrayList<EmployeeRoleProject>();
        EmployeeRoleProject employeeRoleProject=new EmployeeRoleProject();
        employeeRoleProject.setRole("pm");
        roles.add(employeeRoleProject);
        employeeProject.setRoles(roles);
        ResponseMsg msg = projectService.removeEmployeeProject(1);
        assertEquals(210, msg.getStatus());
    }

    @Test
    void no_such_member_when_removeEmployeeProject()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, null, null, 1);
        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(employeeProject);
        when(employeeProjectMapper.delete(anyInt())).thenReturn(0);
        ResponseMsg msg = projectService.removeEmployeeProject(1);
        assertEquals(212, msg.getStatus());
    }

//    @Test
//    void no_enough_authority_when_removeEmployeeProject()throws Exception{
//        byte[] authority={0};
//        EmployeeProject employeeProject = new EmployeeProject(1,authority, null, null, 1);
//        when(employeeProjectMapper.getByEpid(anyInt())).thenReturn(employeeProject);
//        ResponseMsg msg = projectService.removeEmployeeProject(1);
//        assertEquals(210, msg.getStatus());
//    }

    @Test
    void exception_when_removeEmployeeProject()throws Exception{
        when(employeeProjectMapper.getByEpid(anyInt())).thenThrow(new RuntimeException());
        ResponseMsg msg = projectService.removeEmployeeProject(1);
        assertEquals(404, msg.getStatus());
    }

    //////////////updateEmployeeProjectAndRole()//////////////
    @Test
    void happy_path_with_updateEmployeeProjectAndRole()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, 2, null, 1);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        String[] roles={"epg","rd","qa"};
        int i=0;
        while(i<3)
        {
            String role=roles[i++];
            ArrayList<String> t=new ArrayList<String>();
            t.add(role);
            ResponseMsg msg = projectService.updateEmployeeProjectAndRole(t,1,"1");
            assertNotNull(msg.getResponseMap().get("employeeProject"));
            assertEquals(200, msg.getStatus());
        }
    }

    @Test
    void happy_path_with_updateEmployeeProjectAndRole_02()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, 2, null, 1);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        ArrayList<Employee> employees = new ArrayList<Employee>();

        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(null).thenReturn(employeeProjects);
        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);

        ArrayList<String> t=new ArrayList<String>();
        t.add("qa");

        ResponseMsg msg = projectService.updateEmployeeProjectAndRole(t,1,"1");
        assertEquals(200, msg.getStatus());
    }

    @Test
    void alternate_path_with_updateEmployeeProjectAndRole_ret212()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, 2, null, 1);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        ArrayList<String> t=new ArrayList<String>();
        ResponseMsg msg =  projectService.updateEmployeeProjectAndRole(t,1,"1");
        assertEquals(212, msg.getStatus());
    }

    @Test
    void alternate_path_with_updateEmployeeProjectAndRole_ret200()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, 2, null, 1);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenReturn(employeeProjects);
        when(employeeProjectMapper.addEmployeeProject(any())).thenReturn(1);
        when(employeeProjectMapper.addEmployeeRoleProject(any())).thenReturn(1);
        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
        ArrayList<String> t=new ArrayList<String>();
        t.add("epg");
        ResponseMsg msg =  projectService.updateEmployeeProjectAndRole(t,1,"1");
        assertEquals(200, msg.getStatus());
    }

    @Test
    void error_path_with_updateEmployeeProjectAndRole()throws Exception{
        byte[] pm_authority={3};
        EmployeeProject employeeProject = new EmployeeProject(1,pm_authority, 2, null, 1);
        Employee employee = new Employee(1, "Alias", null, null, null, null, "123456", null, null, null);
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(employee);
        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
        employeeProjects.add(employeeProject);
        when(employeeProjectMapper.getEmployeeProject(anyString(),anyInt())).thenThrow(new RuntimeException());
        ArrayList<String> t=new ArrayList<String>();
        t.add("epg");
        ResponseMsg msg =  projectService.updateEmployeeProjectAndRole(t,1,"1");
        assertEquals(404, msg.getStatus());
    }

    //////////////updateProjectInfo()//////////////
    @Test
    void happy_path_updateProjectInfo()throws Exception{
        Project project = new Project("20200001O01", "", new Timestamp((long)1),new Timestamp((long)1) ,"" , "", "", 1, 1);
        when(projectMapper.getByPid("20200001O01")).thenReturn(project);
        Map<String,String> param=new HashMap<String, String>();
        param.put("name","Proj1");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("technique","no");
        param.put("domain","no");

        when(projectMapper.updateProject(any())).thenReturn(1);
        ResponseMsg msg=projectService.updateProjectInfo("20200001O01",param);
        assertEquals(200, msg.getStatus());
    }

    @Test
    void exception_when_updateProjectInfo()throws Exception{
        when(projectMapper.getByPid(anyString())).thenThrow(new RuntimeException());
        Map<String,String> param=new HashMap<String, String>();
        param.put("name","Proj1");
        param.put("starttime","2020-04-09T16:00:00.000Z");
        param.put("endtime","2020-05-09T16:00:00.000Z");
        param.put("technique","no");
        param.put("domain","no");
        param.put("function","");
        param.put("","{\"000000\":\"0-1\"}");

        when(projectMapper.updateProject(any())).thenReturn(1);
        ResponseMsg msg=projectService.updateProjectInfo("20200001O01",param);
        assertEquals(404, msg.getStatus());

    }
}