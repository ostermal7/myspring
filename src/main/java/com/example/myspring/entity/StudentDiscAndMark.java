package com.example.myspring.entity;

public class StudentDiscAndMark {
    private int disciplineId;
    private String disciplines;
    private int mark;

    public StudentDiscAndMark() {
    }

    public StudentDiscAndMark(int disciplineId, String disciplines, int mark) {
        this.disciplineId = disciplineId;
        this.disciplines = disciplines;
        this.mark = mark;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(String disciplines) {
        this.disciplines = disciplines;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentDiscAndMark that = (StudentDiscAndMark) o;

        if (disciplineId != that.disciplineId) return false;
        if (mark != that.mark) return false;
        return disciplines != null ? disciplines.equals(that.disciplines) : that.disciplines == null;
    }

    @Override
    public int hashCode() {
        int result = disciplineId;
        result = 31 * result + (disciplines != null ? disciplines.hashCode() : 0);
        result = 31 * result + mark;
        return result;
    }
}
