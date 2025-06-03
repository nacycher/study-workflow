package com.tools.studyflowable.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyListener implements TaskListener {

    /**
     * 监听器调用时触发的方法
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("--->自定义的监听器执行了");
        if (EVENTNAME_CREATE.equals(delegateTask.getEventName())) {
            System.out.println("创建任务的监听被触发了...");
            // 指定当前任务的处理人
            delegateTask.setAssignee("张三");
        }
    }
}
