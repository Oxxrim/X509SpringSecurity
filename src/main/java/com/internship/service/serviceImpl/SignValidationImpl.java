package com.internship.service.serviceImpl;

import com.internship.service.SignValidationService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.X509Certificate;

@Service
public class SignValidationImpl implements SignValidationService {
    @Override
    public byte[] generateSignature(String message) throws Exception {

        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());

        FileInputStream instream = new FileInputStream(new File(System.getProperty("user.dir") + "/keystore/cid.p12"));

        store.load(instream, "password".toCharArray());

        PrivateKey privateKey = (PrivateKey) store.getKey("cid_pk","password".toCharArray());

        Signature sign = Signature.getInstance("SHA512withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes());

        byte[] signature = sign.sign();

        return signature;
    }

    @Override
    public boolean signatureValidation(String message, byte[] signature/*, HttpServletRequest request*/) throws Exception {

        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        /*X509Certificate[] cert = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");*/
        FileInputStream instream = new FileInputStream(new File(System.getProperty("user.dir") + "/keystore/truststore.jks"));

        store.load(instream, "password".toCharArray());

        X509Certificate cert = (X509Certificate) store
                .getCertificate("cid");

        PublicKey publicKey = (PublicKey) cert.getPublicKey();
        Signature sig = Signature.getInstance("SHA512withRSA");

        sig.initVerify(publicKey);
        sig.update(message.getBytes());

        return sig.verify(signature);
    }
}
