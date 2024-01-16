package com.example.communidrive;

public class chatDataModel {

    public String name;
    public String start;
    public String end;
    public String members;

    public chatDataModel(String field, String start, String end) {
        this.name = field;
        this.start = start;
        this.end = end;
        this.members = members;
    }

    public chatDataModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
