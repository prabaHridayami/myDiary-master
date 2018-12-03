package com.example.praba.prakmob.model;

public class Diary {
    private String title;
    private String diary;
    private String IDuser;
    private Boolean status;
    private String message;
    private String IDdiary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiary() {
        return diary;
    }

    public void setDiary(String diary) {
        this.diary = diary;
    }

    public String getIDuser() {
        return IDuser;
    }

    public void setIDuser(String IDuser) {
        this.IDuser = IDuser;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIDdiary() {
        return IDdiary;
    }

    public void setIDdiary(String IDdiary) {
        this.IDdiary = IDdiary;
    }

}
