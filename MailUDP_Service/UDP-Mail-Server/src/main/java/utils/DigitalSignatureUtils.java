package utils;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DigitalSignatureUtils {

    // Mã hóa AES Key bằng khóa công khai (RSA)
    public static byte[] encryptKeyWithPublicKey(SecretKey aesKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.PUBLIC_KEY, publicKey);
        return cipher.doFinal(aesKey.getEncoded());
    }

    // Tạo chữ ký số cho AES Key bằng khóa riêng (RSA)
    public static byte[] signAESKey(SecretKey aesKey, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(aesKey.getEncoded());
        return signature.sign();
    }

    // Kiểm tra chữ ký số của AES Key (dùng khóa công khai)
    public static boolean verifySignature(byte[] signedData, SecretKey aesKey, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(aesKey.getEncoded());
        return signature.verify(signedData);
    }
    public static boolean verifyAESKeySignature(SecretKey aesKey, String path, PublicKey publicKey) throws Exception {
    // Giả sử bạn có mã để tạo và xác thực chữ ký số
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initVerify(publicKey);
    signature.update(aesKey.getEncoded());
    return signature.verify(Base64.getDecoder().decode(path));  // Giả sử 'path' là chữ ký
}


    public static SecretKey decryptKeyWithPrivateKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);
        return new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
    }

}
