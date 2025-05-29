package com.tools.studyflowable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;

public class Activiti7Demo {

    /**
     * 获取ProcessEngine对象的方式
     */
    @Test
    public void getProcessEngine(){
        // 获取默认的ProcessEngine对象，会加载resource目录下的activiti.cfg.xml文件
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 通过ProcessEngineConfiguration创建ProcessEngine对象
     */
    @Test
    public void getProcessEngine2(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti7")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setDatabaseSchemaUpdate("true")// 设置自动维护表结构
                .buildProcessEngine();

        System.out.println(processEngine);
    }

    /**
     * 流程部署操作
     */
    @Test
    public void deployFlow(){
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取RepositoryService对象
        RepositoryService service = processEngine.getRepositoryService();
        // 3.完成部署操作
        Deployment deploy = service
                .createDeployment()
                .addClasspathResource("flow/test1.bpmn20.xml")
                .name("第一个流程")// 是一个流程部署的行为，可以部署多个流程定义
                .deploy();

        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    /**
     * 查询当前部署的流程有那些
     */
    @Test
    public void queryFlow(){
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取RepositoryService对象
        RepositoryService service = processEngine.getRepositoryService();
        // 3.完成部署信息
        service.createDeploymentQuery()
               .list()
               .forEach(deployment -> {
                   System.out.println(deployment.getId());
                   System.out.println(deployment.getName());
               });
        // 查询定义信息
        service.createProcessDefinitionQuery().list().forEach(processDefinition -> {
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getName());
        });
    }
}
