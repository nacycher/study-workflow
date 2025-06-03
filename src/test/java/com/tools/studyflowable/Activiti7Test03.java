package com.tools.studyflowable;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activiti7Test03 {

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
/*        Deployment deploy = service
                .createDeployment()
                .addClasspathResource("flow/test1.bpmn20.xml")
                .name("第一个流程")// 是一个流程部署的行为，可以部署多个流程定义
                .deploy();*/
        Deployment deploy = service
                .createDeployment()
                .addClasspathResource("flow/test3.bpmn20.xml")
                .name("请假流程")// 是一个流程部署的行为，可以部署多个流程定义
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

    /**
     * 发起（启动）一个流程
     */
    @Test
    public void startFlow(){
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取RuntimeService对象
        RuntimeService service = processEngine.getRuntimeService();
        // 3.发起流程,返回的是流程实例对象
        ProcessInstance processInstance = service.startProcessInstanceById("test1:3:25003");
        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getDeployment() = " + processInstance.getDeploymentId());
        System.out.println("processInstance.getDescription() = " + processInstance.getDescription());
    }

    /**
     * 发起（启动）一个流程
     * 发起流程前，需要将流程定义文件中的动态参数替换掉，然后再启动流程
     */
    @Test
    public void startFlowWithDyn() {
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取RuntimeService对象
        RuntimeService service = processEngine.getRuntimeService();
        // 3.发起流程,返回的是流程实例对象
        ProcessInstance processInstance = service.startProcessInstanceById("test2:1:12503");
        System.out.println("processInstance.getId() = " + processInstance.getId());
        System.out.println("processInstance.getDeployment() = " + processInstance.getDeploymentId());
        System.out.println("processInstance.getDescription() = " + processInstance.getDescription());
    }

    /**
     * 待办任务查询
     */
    @Test
    public void queryTodoTask(){
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取TaskService对象
        TaskService service = processEngine.getTaskService();
        // 3.查询任务,Task对象对应的是表act_ru_task
        service.createTaskQuery().taskAssignee("李四").list().forEach(task -> {
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());
        });
    }

    /**
     * 审批任务
     */
    @Test
    public void completeTask(){
        // 1.获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.获取TaskService对象
        TaskService service = processEngine.getTaskService();
        // 2.2 根据当前登录用户查询出代办任务
        List<Task> list = service.createTaskQuery().taskAssignee("李四").list();
        if (ObjectUtils.isEmpty(list)) {
            System.out.println("没有代办任务");
        } else {
            list.forEach(task -> {
                service.complete(task.getId());
            });
        }
        // 3.完成任务
//        service.complete("5005");
    }
}
