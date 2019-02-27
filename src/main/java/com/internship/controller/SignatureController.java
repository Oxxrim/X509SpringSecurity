package com.internship.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

@RestController
public class SignatureController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/signature")
    public String checkForValidSignature(@RequestBody String message, @RequestHeader("sign") byte[] signature, HttpServletRequest servletRequest) throws Exception {

        X509Certificate[] cert = (X509Certificate[]) servletRequest.getAttribute("javax.servlet.request.X509Certificate");

        Key publicKey = cert[0].getPublicKey();
        Signature sig = Signature.getInstance(cert[0].getSigAlgName());

        sig.initVerify((PublicKey) publicKey);
        sig.update(message.getBytes());

        if (sig.verify(signature)){
            return "All is ok!";
        }else {
            return "Bad request";
        }
    }
}
