/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyUtils {

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);  // Độ dài khóa 2048 bit
        return keyGen.generateKeyPair();
    }

    public static PublicKey getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey(KeyPair keyPair) {
        return keyPair.getPrivate();
    }
}
