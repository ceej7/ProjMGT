package com.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.BitSet;

@ApiModel("Workflow Entity: 工作流信息")
public class Workflow {
    public Workflow(Integer wid, Integer flowbits, Integer pm_eid, Integer sup_eid, Integer configurer_eid, Integer epgleader_eid, Integer qamanager_eid, String git_repo, String server_root, String mail_list, String archive00, String archive01, String archive02, String archive03, String archive04, String archive05, String archive06, String archive07, String archive08, String archive09, String archive10, String archive11, String archive12, String archive13, String archive14, String archive15, String archive16) {
        this.wid = wid;
        this.flowbits = flowbits;
        this.pm_eid = pm_eid;
        this.sup_eid = sup_eid;
        this.configurer_eid = configurer_eid;
        this.epgleader_eid = epgleader_eid;
        this.qamanager_eid = qamanager_eid;
        this.git_repo = git_repo;
        this.server_root = server_root;
        this.mail_list = mail_list;
        this.archive00 = archive00;
        this.archive01 = archive01;
        this.archive02 = archive02;
        this.archive03 = archive03;
        this.archive04 = archive04;
        this.archive05 = archive05;
        this.archive06 = archive06;
        this.archive07 = archive07;
        this.archive08 = archive08;
        this.archive09 = archive09;
        this.archive10 = archive10;
        this.archive11 = archive11;
        this.archive12 = archive12;
        this.archive13 = archive13;
        this.archive14 = archive14;
        this.archive15 = archive15;
        this.archive16 = archive16;
        flowBits2Status();
    }

//    public Workflow(){
//        this.wid = 0;
//        this.flowbits = 0;
//        this.pm_eid = 0;
//        this.sup_eid = 0;
//        this.configurer_eid = 0;
//        this.epgleader_eid = 0;
//        this.qamanager_eid = 0;
//        this.git_repo = "";
//        this.server_root = "";
//        this.mail_list = "";
//        this.archive00 = "";
//        this.archive01 = "";
//        this.archive02 = "";
//        this.archive03 = "";
//        this.archive04 = "";
//        this.archive05 = "";
//        this.archive06 = "";
//        this.archive07 = "";
//        this.archive08 = "";
//        this.archive09 = "";
//        this.archive10 = "";
//        this.archive11 = "";
//        this.archive12 = "";
//        this.archive13 = "";
//        this.archive14 = "";
//        this.archive15 = "";
//        this.archive16 = "";
//        pm=new Employee();
//        sup=new Employee();
//        configurer=new Employee();
//        epgleader=new Employee();
//        qamanager=new Employee();
//        flowBits2Status();
//    }

    void flowBits2Status(){
        BitSet bitSets=new BitSet(32);
        bitSets.clear();
        for (int i = 0; i < 32; i++) {
            bitSets.set(i,  ((this.flowbits>>i)%2)==1);
        }
        if(bitSets.get(28)){
            status = "achieved";
        }else if(bitSets.get(10)){
            status = "submitted";
        }else if(bitSets.get(9)){
            status = "delivering";
        }else if(bitSets.get(8)){
            status = "started";
        }else if(bitSets.get(1)){
            status = "approved";
        }else if(bitSets.get(0)){
            status = "applying";
        }else{
            status = "rejected";
        }
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getFlowbits() {
        return flowbits;
    }

    public void setFlowbits(Integer flowbits) {
        this.flowbits = flowbits;
//        @ApiModelProperty("项目的状态，分别由\n" +
//                "rejected(立项拒绝)\n" +
//                "applying(申请中)\n" +
//                "approved(立项已批准，Config、EPG、QA、PM未进行项目配置)\n" +
//                "started(项目启动)\n" +
//                "delivering(项目正在交付)\n" +
//                "submitted(项目已交付)\n" +
//                "achieved(项目已归档)\n" +
//                "构成")
        flowBits2Status();
    }

    public Integer getPm_eid() {
        return pm_eid;
    }

    public void setPm_eid(Integer pm_eid) {
        this.pm_eid = pm_eid;
    }

    public Integer getSup_eid() {
        return sup_eid;
    }

    public void setSup_eid(Integer sup_eid) {
        this.sup_eid = sup_eid;
    }

    public Integer getConfigurer_eid() {
        return configurer_eid;
    }

    public void setConfigurer_eid(Integer configurer_eid) {
        this.configurer_eid = configurer_eid;
    }

    public Integer getEpgleader_eid() {
        return epgleader_eid;
    }

    public void setEpgleader_eid(Integer epgleader_eid) {
        this.epgleader_eid = epgleader_eid;
    }

    public Integer getQamanager_eid() {
        return qamanager_eid;
    }

    public void setQamanager_eid(Integer qamanager_eid) {
        this.qamanager_eid = qamanager_eid;
    }

    public String getGit_repo() {
        return git_repo;
    }

    public void setGit_repo(String git_repo) {
        this.git_repo = git_repo;
    }

    public String getServer_root() {
        return server_root;
    }

    public void setServer_root(String server_root) {
        this.server_root = server_root;
    }

    public String getMail_list() {
        return mail_list;
    }

    public void setMail_list(String mail_list) {
        this.mail_list = mail_list;
    }

    public String getArchive00() {
        return archive00;
    }

    public void setArchive00(String archive00) {
        this.archive00 = archive00;
    }

    public String getArchive01() {
        return archive01;
    }

    public void setArchive01(String archive01) {
        this.archive01 = archive01;
    }

    public String getArchive02() {
        return archive02;
    }

    public void setArchive02(String archive02) {
        this.archive02 = archive02;
    }

    public String getArchive03() {
        return archive03;
    }

    public void setArchive03(String archive03) {
        this.archive03 = archive03;
    }

    public String getArchive04() {
        return archive04;
    }

    public void setArchive04(String archive04) {
        this.archive04 = archive04;
    }

    public String getArchive05() {
        return archive05;
    }

    public void setArchive05(String archive05) {
        this.archive05 = archive05;
    }

    public String getArchive06() {
        return archive06;
    }

    public void setArchive06(String archive06) {
        this.archive06 = archive06;
    }

    public String getArchive07() {
        return archive07;
    }

    public void setArchive07(String archive07) {
        this.archive07 = archive07;
    }

    public String getArchive08() {
        return archive08;
    }

    public void setArchive08(String archive08) {
        this.archive08 = archive08;
    }

    public String getArchive09() {
        return archive09;
    }

    public void setArchive09(String archive09) {
        this.archive09 = archive09;
    }

    public String getArchive10() {
        return archive10;
    }

    public void setArchive10(String archive10) {
        this.archive10 = archive10;
    }

    public String getArchive11() {
        return archive11;
    }

    public void setArchive11(String archive11) {
        this.archive11 = archive11;
    }

    public String getArchive12() {
        return archive12;
    }

    public void setArchive12(String archive12) {
        this.archive12 = archive12;
    }

    public String getArchive13() {
        return archive13;
    }

    public void setArchive13(String archive13) {
        this.archive13 = archive13;
    }

    public String getArchive14() {
        return archive14;
    }

    public void setArchive14(String archive14) {
        this.archive14 = archive14;
    }

    public String getArchive15() {
        return archive15;
    }

    public void setArchive15(String archive15) {
        this.archive15 = archive15;
    }

    public String getArchive16() {
        return archive16;
    }

    public void setArchive16(String archive16) {
        this.archive16 = archive16;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getPm() {
        return pm;
    }

    public void setPm(Employee pm) {
        this.pm = pm;
    }

    public Employee getSup() {
        return sup;
    }

    public void setSup(Employee sup) {
        this.sup = sup;
    }

    public Employee getConfigurer() {
        return configurer;
    }

    public void setConfigurer(Employee configurer) {
        this.configurer = configurer;
    }

    public Employee getEpgleader() {
        return epgleader;
    }

    public void setEpgleader(Employee epgleader) {
        this.epgleader = epgleader;
    }

    public Employee getQamanager() {
        return qamanager;
    }

    public void setQamanager(Employee qamanager) {
        this.qamanager = qamanager;
    }

    Integer wid;
    @ApiModelProperty("表示工作流的比特位")
    Integer flowbits;
    @ApiModelProperty("外键：项目经理的id")
    Integer pm_eid;
    @ApiModelProperty("外键：上级的id")
    Integer sup_eid;
    @ApiModelProperty("外键：配置管理员的id")
    Integer configurer_eid;
    @ApiModelProperty("外键：EPG领导的id")
    Integer epgleader_eid;
    @ApiModelProperty("外键：QA经理的id")
    Integer qamanager_eid;

    String git_repo;
    String server_root;
    String mail_list;

    @ApiModelProperty("项目的状态，分别由\n" +
            "rejected(立项拒绝)\n" +
            "applying(申请中)\n" +
            "approved(立项已批准，Config、EPG、QA、PM未进行项目配置)\n" +
            "started(项目启动)\n" +
            "delivering(项目正在交付)\n" +
            "submitted(项目已交付)\n" +
            "achieved(项目已归档)\n" +
            "构成")
    String status;

    @ApiModelProperty("项目基础数据表")
    String archive00;
    @ApiModelProperty("项目提案书")
    String archive01;
    @ApiModelProperty("项目报价书")
    String archive02;
    @ApiModelProperty("项目估算表(功能点)")
    String archive03;
    @ApiModelProperty("项目计划书")
    String archive04;
    @ApiModelProperty("项目过程裁剪表")
    String archive05;
    @ApiModelProperty("项目成本管理表")
    String archive06;
    @ApiModelProperty("项目需求变更管理表")
    String archive07;
    @ApiModelProperty("项目风险管理表")
    String archive08;
    @ApiModelProperty("客户验收问题表")
    String archive09;
    @ApiModelProperty("客户验收报告")
    String archive10;
    @ApiModelProperty("项目总结")
    String archive11;
    @ApiModelProperty("最佳经验和教训")
    String archive12;
    @ApiModelProperty("开发工具")
    String archive13;
    @ApiModelProperty("开发模板(设计模板/测试模板)")
    String archive14;
    @ApiModelProperty("各阶段检查单")
    String archive15;
    @ApiModelProperty("QA总结")
    String archive16;

    Employee pm;
    Employee sup;
    Employee configurer;
    Employee epgleader;
    Employee qamanager;
}
