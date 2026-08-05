package com.umb.taller.domain;

public class Author extends Person {
    private String biography;

    public Author() {
    }

    public Author(String name, String email, String phone, String biography) {
        super(name, email, phone);
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}

