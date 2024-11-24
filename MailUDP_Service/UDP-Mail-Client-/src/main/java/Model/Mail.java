/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author iemmeiemi
 */
public class Mail implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer ID;
    private String path;
    private String title;
    private String content;
    private byte[] byteContent;
    private String emailSend;
    private String emailReceive;
    private Timestamp sendedAt;

    public Mail() {
    }

    public Mail(String path, String emailSend, String emailReceive, Timestamp sendedAt) {
        this.path = path;
        this.emailSend = emailSend;
        this.emailReceive = emailReceive;
        this.sendedAt = sendedAt;
    }

    public Mail(String title, String content) {
        this.title = title;
        this.content = content;
    }

    
    
    public Mail(String title, String content, String emailSend, String emailReceive) {
        this.title = title;
        this.content = content;
        this.emailSend = emailSend;
        this.emailReceive = emailReceive;
    }

    public Mail(String title, byte[] byteContent) {
        this.title = title;
        this.byteContent = byteContent;
    }

    public Mail(Integer ID, String path, String emailSend, String emailReceive, Timestamp sendedAt) {
        this.ID = ID;
        this.path = path;
        this.emailSend = emailSend;
        this.emailReceive = emailReceive;
        this.sendedAt = sendedAt;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEmailSend() {
        return emailSend;
    }

    public void setEmailSend(String emailSend) {
        this.emailSend = emailSend;
    }

    public String getEmailReceive() {
        return emailReceive;
    }

    public void setEmailReceive(String emailReceive) {
        this.emailReceive = emailReceive;
    }

    public Timestamp getSendedAt() {
        return sendedAt;
    }

    public void setSendedAt(Timestamp sendedAt) {
        this.sendedAt = sendedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getByteContent() {
        return byteContent;
    }

    public void setByteContent(byte[] byteContent) {
        this.byteContent = byteContent;
    }
    
    
}