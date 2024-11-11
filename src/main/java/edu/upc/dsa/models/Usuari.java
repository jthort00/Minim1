package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.time.LocalDate;

public class Usuari {

    String id;
    String name;
    String surname;
    String email;
    LocalDate birthDate;

    public Usuari() {
        this.setId(RandomUtils.getId());
    }
    public Usuari(String name, String surname, String email, LocalDate birthDate) {
        this(null, name, surname, email, birthDate);
    }

    public Usuari(String id, String name, String surname, String email, LocalDate birthDate) {
        this();
        if (id != null) this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setBirthDate(birthDate);
    }

    public String getId() {return this.id;}
    public void setId(String id) {this.id=id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname(){return surname;}
    public void setSurname(String surname) {this.surname = surname;}

    public String getEmail(){return email;}
    public void setEmail(String email) {this.email = email;}

    public LocalDate getBirthDate(){return birthDate;}
    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}


    @Override
    public String toString() {
        return "Usuari [id="+id+", name=" + name + ", surname=" + surname +"]";
    }

}