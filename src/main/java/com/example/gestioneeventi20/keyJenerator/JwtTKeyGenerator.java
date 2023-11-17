package com.example.gestioneeventi20.keyJenerator;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class JwtTKeyGenerator {
    public static void main(String[] args) {
        // Step 1: Generate a secure key
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

        // Step 2: Convert the key to a Base64-encoded string
        String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);

        // Step 3: Store the Base64-encoded key in the properties file
        storeKeyInPropertiesFile(base64Key);
    }

    private static void storeKeyInPropertiesFile(String key) {
        Properties properties = new Properties();
        properties.setProperty("JWT_SECRET", key);

        try (OutputStream fos = new FileOutputStream("env.properties")) {
            properties.store(fos, "JWT Secret Key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
