package com.rbi.security.entity.web.entity;

import com.alibaba.fastjson.JSON;
import com.rbi.security.entity.web.health.OccHealthProject;

/**
 * @ClassName TestJson
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/21 23:52
 * @Version 1.0
 **/
public class TestJson {
    public static void main(String[] args) {
        OccHealthProject occHealthProject  = new OccHealthProject();
        occHealthProject.setHealthProjectName("11");
        occHealthProject.setHealthProjectType("11");
        occHealthProject.setHealthProjectInvestment("11");
        occHealthProject.setHealthProjectDuration("11");
        occHealthProject.setHealthProjectDanger("11");
        occHealthProject.setHealthProjectEvaluateTime("11");
        occHealthProject.setHealthProjectEvaluateOrganization("11");
        occHealthProject.setHealthProjectEvaluateConclusion("11");
        occHealthProject.setHealthProjectDesignOrganization("11");
        occHealthProject.setHealthProjectDesignConclusion("11");
        occHealthProject.setHealthProjectDesignTime("11");
        occHealthProject.setHealthProjectCheckTime("11");
        occHealthProject.setHealthProjectCheckConclusion("11");
        occHealthProject.setHealthProjectCheckOrganization("12");

        String json = JSON.toJSONString(occHealthProject);
        System.out.println(json);

    }
}
