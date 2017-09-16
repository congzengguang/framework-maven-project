package com.framework.test.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES�ӽ��ܹ�����
 *
 */
public class AESUtil {

    private static final String encodeRules = "zheng";

    /**
     * ����
     * 1.������Կ������
     * 2.����ecnodeRules�����ʼ����Կ������
     * 3.������Կ
     * 4.�����ͳ�ʼ��������
     * 5.���ݼ���
     * 6.�����ַ���
     */
    public static String AESEncode(String content) {
        try {
            //1.������Կ��������ָ��ΪAES�㷨,�����ִ�Сд
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.����ecnodeRules�����ʼ����Կ������
            //����һ��128λ�����Դ,���ݴ�����ֽ�����
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.����ԭʼ�Գ���Կ
            SecretKey original_key = keygen.generateKey();
            //4.���ԭʼ�Գ���Կ���ֽ�����
            byte[] raw = original_key.getEncoded();
            //5.�����ֽ���������AES��Կ
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.����ָ���㷨AES�Գ�������
            Cipher cipher = Cipher.getInstance("AES");
            //7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽��ܽ���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.��ȡ�������ݵ��ֽ�����(����Ҫ����Ϊutf-8)��Ȼ��������������ĺ�Ӣ�Ļ�����ľͻ����Ϊ����
            byte[] byte_encode = content.getBytes("utf-8");
            //9.�����������ĳ�ʼ����ʽ--���ܣ������ݼ���
            byte[] byte_AES = cipher.doFinal(byte_encode);
            //10.�����ܺ������ת��Ϊ�ַ���
            //������Base64Encoder�л��Ҳ�����
            //����취��
            //����Ŀ��Build path�����Ƴ�JRE System Library������ӿ�JRE System Library�����±�����һ�������ˡ�
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            //11.���ַ�������
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //����д�ͷ���nulll
        return null;
    }

    /**
     * ����
     * ���ܹ��̣�
     * 1.ͬ����1-4��
     * 2.�����ܺ���ַ������ĳ�byte[]����
     * 3.���������ݽ���
     */
    public static String AESDecode(String content) {
        try {
            //1.������Կ��������ָ��ΪAES�㷨,�����ִ�Сд
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.����ecnodeRules�����ʼ����Կ������
            //����һ��128λ�����Դ,���ݴ�����ֽ�����
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.����ԭʼ�Գ���Կ
            SecretKey original_key = keygen.generateKey();
            //4.���ԭʼ�Գ���Կ���ֽ�����
            byte[] raw = original_key.getEncoded();
            //5.�����ֽ���������AES��Կ
            SecretKey key = new SecretKeySpec(raw, "AES");
            //6.����ָ���㷨AES�Գ�������
            Cipher cipher = Cipher.getInstance("AES");
            //7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.�����ܲ����������ݽ�����ֽ�����
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * ����
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("�ֵܣ������ļ��е�������Ҫʹ��AES���ܣ���ʹ��com.zheng.common.util.AESUtil�������޸���Щֵ��");
            //e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //����д�ͷ���nulll
        return null;
    }

    public static void main(String[] args) {
        String[] keys = {
                "", "123456","root"
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
