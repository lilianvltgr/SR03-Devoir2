package fr.utc.sr03.chat_admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
/*
Dans le contexte de JPA, une entité représente une table dans la base de données relationnelle.
Chaque instance de cette classe représente une ligne dans cette table.
 */
@Entity
@Table(name = "sr03_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    @Column(name = "admin")
    boolean admin;
    @Column(name = "active")
    boolean active;
    @Column(name = "lastname")
    @Size(min = 2, message = "Le nom doit comporter au moins 2 caractères ")
    @NotEmpty(message = "Nom obligatoire")
    String lastname;
    @Column(name = "firstname")
    @Size(min = 2, message = "Le prénom doit comporter au moins 2 caractères ")
    @NotEmpty(message = "Prénom obligatoire")
    String firstname;
    @Column(name = "mail")
    @NotEmpty(message = "mail obligatoire")
    String mail;
    @Column(name = "password")
    @NotEmpty(message = "mot de passe obligatoire")
    @Size(min = 8, message = "Le mot de passe doit comporter au moins 8 caractères")
    String password;

    public User(boolean admin, boolean actif, String lastname, String firstname, String mail, String password) {
        this.admin = admin;
        this.active = actif;
        this.lastname = lastname;
        this.firstname = firstname;
        this.mail = mail;
        this.password = password;
    }

    public User() {
    }
    //region setters and getters
    public boolean getAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean actif) {
        this.active = actif;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String nom) {
        this.lastname = nom;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String prenom) {
        this.firstname = prenom;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String passWord) {
        this.password = passWord;
    }
    //endregion
    public static void main(String[] args) {
    }
}
