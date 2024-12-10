package kr.ac.kku.cs.wp.kwanho10.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtil {

    // SHA-256 해시 생성
    public static String hash(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 해시 처리 중 오류 발생", e);
        }
    }

    // Salt 생성
    public static String genSalt() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (Exception e) {
            throw new RuntimeException("Salt 생성 중 오류 발생", e);
        }
    }
    
}
