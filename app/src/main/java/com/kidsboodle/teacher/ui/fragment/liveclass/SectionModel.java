package com.kidsboodle.teacher.ui.fragment.liveclass;

public class SectionModel {

    private String id, section;

    public SectionModel(String id, String section) {
        this.id = id;
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
