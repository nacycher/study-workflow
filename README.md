# study-workflow
三大工作流引擎技术Activiti、Flowable、Camunda

视频地址：https://www.bilibili.com/video/BV1EmRpYrExm

# 目录
- [Activiti7](#1.1 Activiti7)

# Activiti7

## 1.1 工作流框架版本
- 主流工作流版本：
- 都由jbmp4/activiti5发展而来，不断演变出Activiti、Flowable、Camunda。  
- 所以表结构，框架上有相似之处
![img.png](src/main/resources/note-Images/activiti7-1.1-01.png)

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