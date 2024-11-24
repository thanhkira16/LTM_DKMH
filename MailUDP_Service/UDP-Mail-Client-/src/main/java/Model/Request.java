/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.io.Serializable;

public class Request implements Serializable{
    private static final long serialVersionUID = 1L;
    private String message;
    private Object data;

    public Request() {
    }
    
    public Request(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    
    public Object getData() {
        return data;
    }

        public Account getAccount() {
        return (Account) data;
    }
    
    public Mail getMail() {
        return (Mail) data;
    }

    
    public void setData(Object data) {
        this.data = data;
    }

    
    
    public Request(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    } 
}

