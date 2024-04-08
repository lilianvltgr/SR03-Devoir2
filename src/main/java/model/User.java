package model;

public class User {
    boolean admin;
    boolean actif;
    int userId;
    String nom;
    String prenom;
    String mail;
    String passWord;

    //region setters and getters
    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    //endregion
    public User(boolean admin, boolean actif, int userId, String nom, String prenom, String mail, String passWord) {
        this.admin = admin;
        this.actif = actif;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.passWord = passWord;
    }

    public static void main(String[] args) {
        User user = new User(true, true, 1, "Voltigeur", "Lilian", "lilian.voltigeur@etu.utc.fr", "testestest");
        System.out.println("Informations de l'utilisateur :");
        System.out.println("Nom : " + user.getNom());
        System.out.println("Prénom : " + user.getPrenom());
        System.out.println("Adresse e-mail : " + user.getMail());
        System.out.println("ID : " + user.getUserId());
        System.out.println("Mot de passe : " + user.getPassWord());

    }

}
