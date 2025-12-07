package com.jminiapp.examples.student;

import com.jminiapp.core.engine.JMiniAppRunner;

public class StudentAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(StudentApp.class)
            .withState(StudentState.class)
            .withAdapters(new StudentJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("Student")
            .run(args);
    }
}
