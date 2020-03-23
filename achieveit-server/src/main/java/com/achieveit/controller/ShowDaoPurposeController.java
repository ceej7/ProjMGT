package com.achieveit.controller;

import com.achieveit.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "为了在SwaggerUI中显示所有Dao", value="不要调用")
public class ShowDaoPurposeController {
    @ResponseBody
    @GetMapping("/showDao/getActivity")
    @ApiOperation("为了显示Activity")
    public Activity getActivity(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getClient")
    @ApiOperation("为了显示Client")
    public Client getClient(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getDefect")
    @ApiOperation("为了显示Defect")
    public Defect getDefect(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getEmployee")
    @ApiOperation("为了显示Employee")
    public Employee getEmployee(){
        return null;
    }


    @ResponseBody
    @GetMapping("/showDao/getEmployeeProject")
    @ApiOperation("为了显示EmployeeProject")
    public EmployeeProject getEmployeeProject(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getEmployeeRoleProject")
    @ApiOperation("为了显示EmployeeRoleProject")
    public EmployeeRoleProject getEmployeeRoleProject(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getManhour")
    @ApiOperation("为了显示Manhour")
    public Manhour getManhour(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getMilestone")
    @ApiOperation("为了显示Milestone")
    public Milestone getMilestone(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getProject")
    @ApiOperation("为了显示Project")
    public Project getProject(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getProjectWorkflow")
    @ApiOperation("为了显示ProjectWorkflow")
    public ProjectWorkflow getProjectWorkflow(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getProperty")
    @ApiOperation("为了显示Property")
    public Property getProperty(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getPropertyOccupy")
    @ApiOperation("为了显示PropertyOccupy")
    public PropertyOccupy getPropertyOccupy(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getRisk")
    @ApiOperation("为了显示Risk")
    public Risk getRisk(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getRiskRelation")
    @ApiOperation("为了显示RiskRelation")
    public RiskRelation getRiskRelation(){
        return null;
    }

    @ResponseBody
    @GetMapping("/showDao/getWorkflow")
    @ApiOperation("为了显示Workflow")
    public Workflow getWorkflow(){
        return null;
    }
}
