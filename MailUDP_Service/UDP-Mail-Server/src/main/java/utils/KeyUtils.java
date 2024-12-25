/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

    public static PrivateKey getServerPrivateKey() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get("server_private_key.pem")));
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey getPublicKeyByEmail(String email) throws Exception {
        // Giả sử bạn lưu trữ các khóa công khai trong một cơ sở dữ liệu hoặc file
        byte[] keyBytes = Files.readAllBytes(Paths.get("public_keys/" + email + "_public_key.pem"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

}
