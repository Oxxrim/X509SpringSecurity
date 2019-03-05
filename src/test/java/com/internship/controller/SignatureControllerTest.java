package com.internship.controller;

import com.internship.service.SignValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.security.cert.X509Certificate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.x509;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignValidationService service;

    @Autowired
    private SignatureController controller;

    @Test
    @WithMockUser(username = "cid", authorities = {"ROLE_USER"})
    public void checkForValidSignature() throws Exception{
        MockHttpServletRequestBuilder requestBuilder = multipart("/signature")
                .header("sign", service.generateSignature("message"))
                .content("message")
                /*.with(x509(getCertificateFromFile()))*/;

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Signature is valid"));

    }

}