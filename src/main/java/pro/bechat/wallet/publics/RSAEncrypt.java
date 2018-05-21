package pro.bechat.wallet.publics;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

/**
 * create RSAEncrypt by huc
 * 2018/4/11  上午10:18
 */
public class RSAEncrypt {

    /**
     * 生成RAS公钥与私钥字符串，直接返回
     * @return
     */
    public static HashMap<String,String> getKeys(){
        HashMap<String,String> map = new HashMap<String,String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(512,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //得到公钥字符串
        String publicKey   = base64ToStr(keyPair.getPublic().getEncoded());
        //得到私钥字符串
        String privateKey  =  base64ToStr(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }


    /**
     * 从字符串中加载公钥
     * @param publicKeyStr  公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * 从字符串中加载私钥
     * @param privateKeyStr     私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 公钥加密过程
     * @param publicKey      公钥
     * @param plainTextData  明文数据
     * @return
     * @throws Exception     加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData)throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return base64ToStr(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey   私钥
     * @param cipherData   密文数据
     * @return                明文
     * @throws Exception   解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    public static String base64ToStr(byte[] b){
        return javax.xml.bind.DatatypeConverter.printBase64Binary(b);
    }

    public static byte[] strToBase64(String str){
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
    }
    public static void main(String[] args) {
        /*String prikey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJVTS2hq69lRc1R/oOI0v5pcwgJDPGnr0TFvIqJvmC0dGCM74XpJ9GfZonNF9k/OM7C8Fw4IAtpEe8CDQxB0hOZx6dzZUocF8cmmLw5FZqrNi7sXMnR1iBRwuSJQNVhIWV+JV+WB5z/y0zj5SXN0viVaNxUb/AuQJJ16coMLOvG1AgMBAAECgYBvVXYwuR91nUvu4WWzon6E+cnQ5A6ELsV/JQxerfN5F2sqwffANKmcUGDVr4ERSX2ytNo/pOtzj4HxLCzxdN6symh8TZKWRtUCVEjRLWvHLeBQXhvKemnsoCuzDZSJSY1Napw+33nmvyGEdpjYExpShpORD/1HfqNBMp3GAbPfNQJBANEAivCciJyBdDkWheXqPG1RM/0smuIv97iaDblt3OeVkcPWmle0faay5JPBNlAuH74gNRnH9PdXsh+TZMz46aMCQQC252Ni8MnG/MNArtH4naY+HS/yRc5k6FhvNjx6crf32RiHew0f2idMG/OueEg6p2lPoP8O3VtnJS3BqIntCpzHAkAA7MGwouCv4JrFDeZ1x/2QdkWGn0XoLlIoTPY2ldfeZnkIptB0BuZvcGc2iCpXXHzjeH0DqjDZT1gIb7yIc6mFAkAebygpeXmJw9tVcwF7Rfg4k7C7toPeZI76imLL2TTlZ9leCcqoRyuMzYXhvR//qoWqe1aViZ3A7v3LIJk6uS5XAkBFL55v7Z61kjXnE8U35UGN9vATfIc0q5ifYlqd58F0GJcT9NFlnjCZ2Nu8XH9rM2Z2Zuh+Uzxfdk/Qr6cFmKGf";
        System.out.println(getKeys().toString());

        strToBase64("")*/

        //Map map = getKeys();
        String pubkey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMhLsyvfrTvw9W7dgWXpUdSlWLqhTrTNYBBMeuNGFji1dcOGiUHSh2Gp1F9WIAh6+br07CF/MSqa3hPrCianRtcCAwEAAQ==";
        String prikey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAyEuzK9+tO/D1bt2BZelR1KVYuqFOtM1gEEx640YWOLV1w4aJQdKHYanUX1YgCHr5uvTsIX8xKpreE+sKJqdG1wIDAQABAkEAkql7hMDAJIgH7yoaufjihoacNewvqYxIryaRRSHo25QWlUVjrzwF+uioyIcW2qtoWAyVhLcR/0U+EruHXPVdQQIhAPjD/L72lxpPX0JXG46ML/HhMapaAMVYrlblWWTCN6r5AiEAzh7dEecysSs43GmTkz9LjE16NNHcfSfmx+GBpvgCpE8CIEyr4y9cQzAZvItu25BwxqovNSaC/O/WKE2h/I5gidXJAiEAqqmlU2iBcW4fI2iLFzUmIWIZGFjW3g3GqKMPzaa10UMCIQCYQmU6LHbBhABrmtsEdt5qdwwzvYlmfHnFjhnG0xyc+A==";

        System.out.println("pubkey:"+pubkey);
        System.out.println("prikey:"+prikey);
        try {
            RSAPublicKey publicKey = loadPublicKey(pubkey);
            RSAPrivateKey privateKey = loadPrivateKey(prikey);
            String str = "hello world";
            String s =  encrypt(publicKey,str.getBytes());
            System.out.println("密文"+s);
            String s1 = decrypt(privateKey,strToBase64(s));
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
