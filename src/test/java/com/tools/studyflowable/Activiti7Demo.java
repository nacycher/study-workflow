package com.tools.studyflowable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
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
}
