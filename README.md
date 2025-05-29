# study-workflow
三大工作流引擎技术Activiti、Flowable、Camunda

视频地址：https://www.bilibili.com/video/BV1EmRpYrExm

# 目录
- [Activiti7](#Activiti7)

# Activiti7
- activiti-app.war 地址：src/main/resources/static


## 1.1 工作流框架版本
- 主流工作流版本：
- 都由jbmp4/activiti5发展而来，不断演变出Activiti、Flowable、Camunda。  
- 所以表结构，框架上有相似之处
![img.png](src/main/resources/note-Images/workflow-1.1-01.png)

## 1.2、1.3获取ProcessEngine
可以通过xml配置文件的，或者代码的方式获取ProcessEngine对象
```java
        // 获取默认的ProcessEngine对象，会加载resource目录下的activiti.cfg.xml文件
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);

        // 通过ProcessEngineConfiguration获取ProcessEngine对象
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/activiti7")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setDatabaseSchemaUpdate("true")// 设置自动维护表结构
                .buildProcessEngine();

```

## 1.4 在线流程设计器
绘制流程图，在官网下载activiti-app.war包，并在tomcat7中部署运行  
```shell
docker run -d -p 8080:8080 --name tomcat \
-v /root/activiti-app.war:/usr/local/tomcat/webapps/activiti-app.war \
tomcat:8.0
```
- 登录： admin test  
- 登录后进入，创建流程，进入流程绘制页面，绘制一个简单的流程。
![img.png](src/main/resources/note-Images/workflow-1.4-01.png)
![img_1.png](src/main/resources/note-Images/workflow-1.4-02.png)
- 下载后得到一个xml，将xml部署到数据库中，即可使用。

## 1.5 第一个流程的部署
```java
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
```
- act_re_deployment表：部署信息表
- act_re_procdef表：流程定义表

