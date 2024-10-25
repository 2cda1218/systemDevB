package org.tokai_2cda1218.bingo_crad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CardHash {
    public static String hashString(String hash){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] hashbyte = digest.digest(hash.getBytes());

            StringBuilder builder = new StringBuilder();
            for(byte b : hashbyte){
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException("ハッシュアルゴリズムがありません",e);
        }
    }
}
