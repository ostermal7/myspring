package com.example.myspring.entity;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

public class Student {
    private int id;
    @NotEmpty(message = "Поле имя не может быть пустым")
    private String name;
    @NotEmpty(message = "Поле фамилия не может быть пустым")
    private String sername;
    @NotEmpty(message = "Поле группа не может быть пустым")
    private String group;
    @NotEmpty(message = "Поле дата не может быть пустым")
    private Date dateofadmission;
    private boolean status=true;

    public Student() {
    }

    public Student(int id, String name, String sername, String group, Date dateofadmission, boolean status) {
        this.id = id;
        this.name = name;
        this.sername = sername;
        this.group = group;
        this.dateofadmission = dateofadmission;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getDateofadmission() {
        return dateofadmission;
    }

    public void setDateofadmission(Date dateofadmission) {
        this.dateofadmission = dateofadmission;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (status != student.status) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (sername != null ? !sername.equals(student.sername) : student.sername != null) return false;
        if (group != null ? !group.equals(student.group) : student.group != null) return false;
        return dateofadmission != null ? dateofadmission.equals(student.dateofadmission) : student.dateofadmission == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sername != null ? sername.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (dateofadmission != null ? dateofadmission.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}
