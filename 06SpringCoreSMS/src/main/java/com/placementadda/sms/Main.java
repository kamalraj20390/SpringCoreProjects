package com.placementadda.sms;

import com.placementadda.sms.controller.StudentController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        StudentController controller =
                context.getBean("studentController", StudentController.class);

        controller.run();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
