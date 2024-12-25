package utils;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.SecretKey;

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
}
