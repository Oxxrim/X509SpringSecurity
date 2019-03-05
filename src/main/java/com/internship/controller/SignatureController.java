package com.internship.controller;

import com.internship.service.SignValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SignatureController {

    @Autowired
    private SignValidationService validationService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/signature")
    public String checkForValidSignature(@RequestBody String message, @RequestHeader("sign") byte[] signature, HttpServletRequest servletRequest) throws Exception {

        if (validationService.signatureValidation(message, signature/*, servletRequest*/)){
            return "Signature is valid";
        }else {
            return "Signature is not valid";
        }
    }
}
