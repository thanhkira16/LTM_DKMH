/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Base64;

public class Base64Utils {

    // Mã hóa dữ liệu thành chuỗi Base64
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Giải mã chuỗi Base64 thành dữ liệu gốc
    public static byte[] decode(String encodedData) {
        return Base64.getDecoder().decode(encodedData);
    }
}
