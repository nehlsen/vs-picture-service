package org.nehlsen.venueshot.pictureservice.storage;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * mostly from https://www.baeldung.com/sha-256-hashing-java
 */
@Component
public class MessageDigestFilenameHasher implements FilenameHasher {

    @Override
    public String hash(String filename) {
        try {
            return doWithAlgorithm(filename, "SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String doWithAlgorithm(String data, String algorithm) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance(algorithm);
        final byte[] hashbytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
