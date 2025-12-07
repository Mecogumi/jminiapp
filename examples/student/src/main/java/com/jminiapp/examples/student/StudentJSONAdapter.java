package com.jminiapp.examples.student;

import com.jminiapp.core.adapters.JSONAdapter;

public class StudentJSONAdapter implements JSONAdapter<StudentState> {

    @Override
    public Class<StudentState> getstateClass() {
        return StudentState.class;
    }
}