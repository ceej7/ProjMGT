package com.achieveit.service;

import com.achieveit.entity.*;
import com.achieveit.mapper.EmployeeMapper;
import com.achieveit.mapper.EmployeeProjectMapper;
import com.achieveit.mapper.ProjectMapper;
import com.achieveit.mapper.WorkflowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class WorkflowServiceTest {

    ProjectMapper projectMapper;
    EmployeeProjectMapper employeeProjectMapper;
    WorkflowMapper workflowMapper;
    EmployeeMapper employeeMapper;
    WorkflowService workflowService;

    @BeforeEach
    void setup(){
        employeeProjectMapper = mock(EmployeeProjectMapper.class);
        projectMapper = mock(ProjectMapper.class);
        workflowMapper=mock(WorkflowMapper.class);
        employeeMapper = mock(EmployeeMapper.class);
        workflowService = new WorkflowService(workflowMapper, employeeMapper,projectMapper,employeeProjectMapper);
    }

//    @Test
//    void happy_path_with_getById_ret200() {
//        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
//        when(workflowMapper.getByWidCascade(1)).thenReturn(w);
//        ResponseMsg msg = workflowService.getById(1);
//        assertEquals(200, msg.getStatus());
//        assertNotNull(msg.getResponseMap().get("workflow"));
//        assertEquals(1, ((Workflow)msg.getResponseMap().get("workflow")).getWid());
//        verify(workflowMapper).getByWidCascade(1);
//    }
//    @Test
//    void error_path_with_getById_ret210() {
//        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
//        when(workflowMapper.getByWidCascade(1)).thenReturn(null);
//        ResponseMsg msg = workflowService.getById(1);
//        assertEquals(210, msg.getStatus());
//    }
//    @Test
//    void exception_path_with_getById_ret404() {
//        Workflow w = new Workflow(1, 1, 1, 1, 1, 1, 1, "1", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
//        when(workflowMapper.getByWidCascade(1)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.getById(1);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_getTimelineByWid_ret200(){
//        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
//        ArrayList<Timeline> timelines = new ArrayList<>();
//        timelines.add(timeline);
//        when(workflowMapper.getTimelineByWid(anyInt())).thenReturn(timelines);
//        ResponseMsg  msg = workflowService.getTimelineByWid(1);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_getTimelineByWid_ret210(){
//        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
//        ArrayList<Timeline> timelines = new ArrayList<>();
//        timelines.add(timeline);
//        when(workflowMapper.getTimelineByWid(anyInt())).thenReturn(null);
//        ResponseMsg  msg = workflowService.getTimelineByWid(1);
//        assertEquals(210, msg.getStatus());
//    }
//
//    @Test
//    void exception_path_getTimelineByWid_ret404(){
//        Timeline timeline=new Timeline("", new Timestamp(0), 1, 1);
//        ArrayList<Timeline> timelines = new ArrayList<>();
//        timelines.add(timeline);
//        when(workflowMapper.getTimelineByWid(anyInt())).thenThrow(new RuntimeException());
//        ResponseMsg  msg = workflowService.getTimelineByWid(1);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_sup_agree_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(1);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.sup_agree(0, 0);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_sup_agree_ret214(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.sup_agree(0, 0);
//        assertEquals(214, msg.getStatus());
//    }
//
//    @Test
//    void error_path_sup_agree_ret404(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.sup_agree(0, 0);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_sup_disagree_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(1);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.sup_disagree(0, 0);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_sup_disagree_ret214(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.sup_disagree(0, 0);
//        assertEquals(214, msg.getStatus());
//    }
//
//    @Test
//    void error_path_sup_disagree_ret212(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setSup_eid(1);
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.sup_disagree(0, 0);
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void error_path_sup_disagree_ret210(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        ResponseMsg msg = workflowService.sup_disagree(0, 0);
//        assertEquals(210, msg.getStatus());
//    }
//
//    @Test
//    void error_path_sup_disagree_ret404(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(0);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.sup_disagree(0, 0);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_configurer_config_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.configurer_config(0,0, "", "", "");
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_configurer_config_ret212(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.configurer_config(1,0, "", "", "");
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void error_path_configurer_config_ret404(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.configurer_config(1,0, "", "", "");
//        assertEquals(404, msg.getStatus());
//    }
//    @Test
//    void error_path_epg_config_ret404(){
//        ResponseMsg msg = workflowService.epg_config(0, 0, null);
//        assertEquals(404, msg.getStatus());
//    }
//    @Test
//    void error_path_epg_config_ret212(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.epg_config(1,0,new ArrayList<Integer>());
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void happy_path_epg_config_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenReturn(projects);
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.epg_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(employeeProjects);
//        assertEquals(200, msg.getStatus());
//        msg = workflowService.epg_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(new ArrayList<EmployeeProject>());
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_epg_config_ret404_Exception(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenThrow(new RuntimeException());
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.epg_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenThrow(new RuntimeException());
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_qa_config_ret404(){
//        ResponseMsg msg = workflowService.qa_config(0, 0, null);
//        assertEquals(404, msg.getStatus());
//    }
//    @Test
//    void error_path_qa_config_ret212(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.qa_config(1,0,new ArrayList<Integer>());
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void happy_path_qa_config_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenReturn(projects);
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.qa_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(employeeProjects);
//        assertEquals(200, msg.getStatus());
//        msg = workflowService.qa_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(new ArrayList<EmployeeProject>());
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_qa_config_ret404_Exception(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenThrow(new RuntimeException());
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.qa_config(0,0, epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenThrow(new RuntimeException());
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_member_config_ret404(){
//        ResponseMsg msg = workflowService.member_config(0, 0, 0,null,null);
//        assertEquals(404, msg.getStatus());
//    }
//    @Test
//    void error_path_member_config_ret212(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(3);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.member_config(1,0,0,new ArrayList<Integer>(),new ArrayList<Integer>());
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_member_config_ret200(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(31);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenReturn(projects);
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.member_config(0,0, 0, epgs,epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(employeeProjects);
//        assertEquals(200, msg.getStatus());
//        msg = workflowService.member_config(0,0,0, epgs,epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenReturn(new ArrayList<EmployeeProject>());
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_member_config_ret404_Exception(){
//        when(workflowMapper.getByWidCascade(0)).thenReturn(null);
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(31);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ArrayList<Project> projects = new ArrayList<>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(0)).thenThrow(new RuntimeException());
//        ArrayList<EmployeeProject> employeeProjects=new ArrayList<EmployeeProject>();
//        employeeProjects.add(new EmployeeProject());
//        when(employeeProjectMapper.getEmployeeProjectByRole(anyString(),anyString())).thenReturn(employeeProjects);
//        ArrayList<Integer> epgs = new ArrayList<Integer>();
//        epgs.add(0);
//        ResponseMsg msg = workflowService.member_config(0,0, 0,epgs,epgs);
//        when(employeeProjectMapper.getEmployeeProject(anyString(), anyInt())).thenThrow(new RuntimeException());
//        assertEquals(404, msg.getStatus());
//    }
//
//
//    @Test
//    void error_path_pm_authority_ret212(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(63);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.pm_authority(1,0);
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void happy_path_pm_authority_ret200(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(63);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.pm_authority(0,0);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_authority_ret404_Exception(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(63);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.pm_authority(0,0);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_function_ret212(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(127);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.pm_function(1,0,null);
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void happy_path_pm_function_ret200(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(127);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        List<Project> projects = new ArrayList<Project>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(anyInt())).thenReturn(projects);
//        ResponseMsg msg = workflowService.pm_function(0,0,null);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_function_ret404_Exception(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(127);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.pm_function(0,0,null);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_common_doing_flow_ret212(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(255);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.pm_common_doing_flow(1,0,8);
//        assertEquals(212, msg.getStatus());
//    }
//    @Test
//    void happy_path_pm_common_doing_flow_ret200(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(255);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        List<Project> projects = new ArrayList<Project>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(anyInt())).thenReturn(projects);
//        ResponseMsg msg = workflowService.pm_common_doing_flow(0,0,8);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_common_doing_flow_ret404_Exception(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(255);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.pm_common_doing_flow(0,0,8);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_pm_common_archive_ret212(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits((int)Math.pow(2,11)-1);
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.pm_common_archive(1,0,0,"");
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_pm_common_archive_ret200(){
//        for (int i = 0; i < 17; i++) {
//            Workflow workflow = new Workflow();
//            workflow.setFlowbits((int)Math.pow(2,i+11)-1);
//            when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//            when(workflowMapper.getByWid(anyInt())).thenReturn(workflow);
//            ResponseMsg msg = workflowService.pm_common_archive(0,0,i,"");
//            assertEquals(200, msg.getStatus());
//        }
//
//    }
//
//    @Test
//    void error_path_pm_common_archive_ret404_Exception(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(255);
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.pm_common_archive(0,0,0,"");
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void error_path_configurer_after_archive_ret212(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(Integer.valueOf(0xfffffff));
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        ResponseMsg msg = workflowService.configurer_after_archive(1,0);
//        assertEquals(212, msg.getStatus());
//    }
//
//    @Test
//    void happy_path_configurer_after_archive_ret200(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(Integer.valueOf(0xfffffff));
//        when(workflowMapper.getByWidCascade(0)).thenReturn(workflow);
//        List<Project> projects = new ArrayList<Project>();
//        projects.add(new Project());
//        when(projectMapper.getByWid(anyInt())).thenReturn(projects);
//        ResponseMsg msg = workflowService.configurer_after_archive(0,0);
//        assertEquals(200, msg.getStatus());
//    }
//
//    @Test
//    void error_path_configurer_after_archive_ret404_Exception(){
//        Workflow workflow = new Workflow();
//        workflow.setFlowbits(Integer.valueOf(0xfffffff));
//        when(workflowMapper.getByWidCascade(0)).thenThrow(new RuntimeException());
//        ResponseMsg msg = workflowService.configurer_after_archive(0,0);
//        assertEquals(404, msg.getStatus());
//    }
//
//    @Test
//    void alternate_path() throws Exception {
//        workflowService.commonCheckImpl(0, new ResponseMsg(), 1, "", 1);
//        workflowService.memberAttend_and_RoleAssign_commonImpl("pm",1,"", 1);
//        workflowService.memberAttend_and_RoleAssign_commonImpl("qa_leader",1,"", 1);
//    }
}