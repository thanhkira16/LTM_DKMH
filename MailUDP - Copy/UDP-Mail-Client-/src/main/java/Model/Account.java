/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;

public class Account implements Serializable{
    private static final long serialVersionUID = 1L;

    private int ID;
    private String email;
    private String username;
    private String pass;

    public Account() {
    }

    public Account(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public Account(int ID, String email, String username) {
        this.ID = ID;
        this.email = email;
        this.username = username;
    }

    
    public Account(String email, String username, String pass) {
        this.email = email;
        this.username = username;
        this.pass = pass;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    
}