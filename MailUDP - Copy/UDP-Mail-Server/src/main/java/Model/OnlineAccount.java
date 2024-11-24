/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.net.InetAddress;
import java.util.Date;

public class OnlineAccount {
    private String email;
    private InetAddress IPAddress;
    private int port;
    private Date date;

        public OnlineAccount(String email, InetAddress IPAddress, int port) {
            this.email = email;
            this.IPAddress = IPAddress;
            this.port = port;
            this.date = new Date();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public InetAddress getIPAddress() {
            return IPAddress;
        }

        public void setIPAddress(InetAddress IPAddress) {
            this.IPAddress = IPAddress;
        }    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
        
        
        
}