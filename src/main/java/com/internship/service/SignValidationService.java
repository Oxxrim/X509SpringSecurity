package com.internship.service;

import javax.servlet.http.HttpServletRequest;

public interface SignValidationService {

    byte[] generateSignature(String message) throws Exception;

    boolean signatureValidation(String message, byte[] signature/*, HttpServletRequest request*/) throws Exception;
}
