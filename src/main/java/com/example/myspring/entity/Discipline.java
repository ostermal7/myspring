package com.example.myspring.entity;

import javax.validation.constraints.NotEmpty;

public class Discipline {

    @NotEmpty(message = "Поле имя не может быть пустым!")
    private String disciplines;
    private int id;
    private boolean status=true;

    public Discipline() {

    }

    public Discipline(String disciplines, int id, boolean status) {
        this.disciplines = disciplines;
        this.id = id;
        this.status = status;
    }
    public String getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(String disciplines) {
        this.disciplines = disciplines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        Discipline that = (Discipline) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        return disciplines != null ? disciplines.equals(that.disciplines) : that.disciplines == null;
    }

    @Override
    public int hashCode() {
        int result = disciplines != null ? disciplines.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "disciplines='" + disciplines + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
