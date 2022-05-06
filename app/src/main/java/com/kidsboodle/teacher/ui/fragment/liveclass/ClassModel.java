package com.kidsboodle.teacher.ui.fragment.liveclass;

public class ClassModel {

    private String id, classname;

    public ClassModel(String id, String classname) {
        this.id = id;
        this.classname = classname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
