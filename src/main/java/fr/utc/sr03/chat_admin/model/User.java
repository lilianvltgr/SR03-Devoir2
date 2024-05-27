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
    @Size(min = 2)
    @NotEmpty(message = "lastname obligatoire")
    String lastname;
    @Column(name = "firstname")
    @Size(min = 2)
    @NotEmpty(message = "firstname obligatoire")
    String firstname;
    @Column(name = "mail")
    @NotEmpty(message = "mail obligatoire")
    String mail;
    @NotEmpty(message = "mot de passe obligatoire")
    @Size(min = 8)
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
        // constructeur sans argument demandé
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
        User user = new User(true, true,  "Voltigeur", "Lilian", "lilian.voltigeur@etu.utc.fr", "testestest");
        System.out.println("Informations de l'utilisateur :");
        System.out.println("Nom : " + user.getLastname());
        System.out.println("Prénom : " + user.getFirstname());
        System.out.println("Adresse e-mail : " + user.getMail());
        System.out.println("ID : " + user.getUserId());
        System.out.println("Mot de passe : " + user.getPassword());

    }

}
