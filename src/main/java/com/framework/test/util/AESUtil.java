package com.framework.test.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加解密工具类
 */
public class AESUtil {
    private static final String encodeRules = "framework-maven-project";

    public static String AESEncode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byte_encode = content.getBytes("utf-8");
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String AESDecode(String content) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String[] keys = {
                "", "123456", "root"
        };
        System.out.println("key | AESEncode | AESDecode");
        for (String key : keys) {
            System.out.print(key + " | ");
            String encryptString = AESEncode(key);
            System.out.print(encryptString + " | ");
            String decryptString = AESDecode(encryptString);
            System.out.println(decryptString);

        }

    }

}