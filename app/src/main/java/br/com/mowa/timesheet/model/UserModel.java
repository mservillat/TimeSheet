package br.com.mowa.timesheet.model;

/**
 * Created by walky on 9/22/15.
 */
public class UserModel {
    private String id;
    private String name;
    private String userName;
    private String password;
    private String token;
    private boolean activite;
    private String profilePicture;
    private String updatedAt;


    public UserModel() {

    }

    public UserModel(String id, String username, String token, String name, String updatedAt) {
        this.id = id;
        this.userName = username;
        this.token = token;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}