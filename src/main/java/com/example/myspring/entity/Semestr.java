package com.example.myspring.entity;

public class Semestr {
    private String name;
    private String duration;
    private boolean status= true;
    private int id;

    public Semestr() {
    }

    public Semestr(String name, String duration, boolean status, int id) {
        this.name = name;
        this.duration = duration;
        this.status = status;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Semestr semestr = (Semestr) o;

        if (status != semestr.status) return false;
        if (id != semestr.id) return false;
        if (name != null ? !name.equals(semestr.name) : semestr.name != null) return false;
        return duration != null ? duration.equals(semestr.duration) : semestr.duration == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + id;
        return result;
    }
}
