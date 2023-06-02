package com.example.demo.securité;

public class User {
    private Integer Id;

    public void setId(Integer id) {
        Id = id;
    }
    public Integer getId() {
        return Id;
    }
    private String nom ;
    private String prenom;
    private String login;
    private String password;

    public User(Integer id,String nom, String prenom, String login, String password) {
        this.Id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
