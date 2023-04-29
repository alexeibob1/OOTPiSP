package com.lab2.trains;

import java.io.Serializable;

public class Driver implements Serializable {
    private int experience;
    private int id;
    private String name;

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public Driver(int experience, int id, String name) {
        this.experience = experience;
        this.id = id;
        this.name = name;
    }

    public Driver() {}

    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Driver's info: Name - ");
        sb.append(this.name);
        sb.append(", id - ");
        sb.append(this.id);
        sb.append(", experience - ");
        sb.append(this.experience);
        return sb.toString();
    }

//    @Override
//    public String toString() {
//        return "id\n" + id + "\n" + "experience\n" + experience + "\n" + "name" + "\n" +
//                name + "\n";
//    }
}
