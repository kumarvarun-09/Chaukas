//package com.chaukas.auth.jwt;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//
//public class RSAKeyGenerator {
//    public static void main(String[] args) throws Exception {
//
//        KeyPair keyPair = (new RSAKeyGenerator()).generateKeyPair();
//        String privateKey = Base64.getEncoder()
//                .encodeToString(
//                        keyPair.getPrivate().getEncoded()
//                );
//        System.out.println("Private key: " + privateKey);
//        String publicKey = Base64.getEncoder()
//                .encodeToString(
//                        keyPair.getPublic().getEncoded()
//                );
//        System.out.println("Public key: " + publicKey);
//    }
//
//    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
//        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//        generator.initialize(2048);
//        return generator.generateKeyPair();
//    }
//}
