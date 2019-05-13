package com.payment.demo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.demo.MpesaMethods.MpesaRegister;
import com.payment.demo.MpesaMethods.MpesaResponseObject;
import com.payment.demo.MpesaMethods.MpesaSimulation;
import com.payment.demo.models.MpesaAuthorizationResponse;
import com.payment.demo.utils.HttpCall;
import com.sun.net.httpserver.Headers;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.apache.bcel.generic.RET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service

public class mpesaService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpCall<MpesaAuthorizationResponse> authorizationHttpCall;

    @Autowired
    private HttpCall<MpesaResponseObject> mpesaResponseObjectHttpCall;

    @Value("${mpesa.api.consumerkey}")
    private String consumerkey;


    @Value("${mpesa.api.consumersecret}")
    private String consumersecret;
// api mpesa reg
    @Value("${mpesa.api.register}")
    private String registerurl;
//ngrok
    @Value("${mpesa.api.validationurl}")
    private String validationUrl;
//ngrok
    @Value("${mpesa.api.confirmationurl}")
    private String confirmationUrl;
// mpesa simulate
    @Value("${mpesa.api.simulatec2b}")
    private String simulateurl;

    @Value("${mpesa.api.url}")
    private String url;

//invoke token from daraja
    public Map<String, Object> generateToken() {

        Map<String, Object> map = new HashMap<>();

        ObjectMapper mapper= new ObjectMapper();

        try{
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String keys= consumerkey + ":" + consumersecret;
            byte[] auth= Base64.encodeBase64(keys.getBytes(Charset.forName("US-ASCII")));

            String authHeader = "Basic " + new String(auth);
            headers. set("Authorization", authHeader);
            final ResponseEntity<MpesaAuthorizationResponse> mpesaResponse = authorizationHttpCall.sendAPIGatewayGETRequest(url,  headers, MpesaAuthorizationResponse.class);
            MpesaAuthorizationResponse authorizationResponse =mpesaResponse.getBody();
            if (mpesaResponse.getStatusCode() == HttpStatus.OK) {

                logger.info("STATE 200 RESPONSE FROM MPESA GATEWAY: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(authorizationResponse), Console.class);
                map.put("status", "00");
                map.put("accessToken", authorizationResponse.getAccess_token());
                map.put("expiresIn", authorizationResponse.getExpires_in());
                map.put("message", "Success.");

            }
        }catch (Exception e){
        }
        return map;
    }
    public void registerC2B(){

        ObjectMapper mapper= new ObjectMapper();
        try{
            MpesaRegister mpesaRegister=new MpesaRegister();
            String token=generateToken().get("accessToken").toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer " + token);
            mpesaRegister.setShortCode("602973");
            mpesaRegister.setResponseType(" ");
            mpesaRegister.setConfirmationURL(confirmationUrl);
            mpesaRegister.setValidationURL(validationUrl);
            final ResponseEntity<MpesaResponseObject> mpesaResponse = mpesaResponseObjectHttpCall.sendAPIGatewayPOSTRequest(registerurl, mapper.writeValueAsString(mpesaRegister), headers, MpesaResponseObject.class);
            MpesaResponseObject mpesaResponseObject=mpesaResponse.getBody();
            logger.info(mapper.writeValueAsString(mpesaResponseObject));
        }
        catch(Exception e){
        }
    }
    public Map<String,Object> simulateC2B() {
        Map<String,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try{
            registerC2B();
            String Token = generateToken().get("accessToken").toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " +Token);

            MpesaSimulation mpesaC2BSimulation =new  MpesaSimulation();
            mpesaC2BSimulation.setAmount("100");
            mpesaC2BSimulation.setBillRefNumber("ABCDEFGH1234");
            mpesaC2BSimulation.setCommandID("CustomerPayBillOnline");
            mpesaC2BSimulation.setMsisdn("254708374149");
            mpesaC2BSimulation.setShortCode("602973");
            final ResponseEntity<MpesaResponseObject> mpesaResponseObjectResponseEntity = mpesaResponseObjectHttpCall.sendAPIGatewayPOSTRequest(simulateurl, mapper.writeValueAsString(mpesaC2BSimulation),headers, MpesaResponseObject.class );
            MpesaResponseObject mpesaResponseObject = mpesaResponseObjectResponseEntity.getBody();
            if (mpesaResponseObjectResponseEntity.getStatusCode() == HttpStatus.OK) {
                logger.info("STATE 200 RESPONSE FROM MPESA GATEWAY: " + mapper.writeValueAsString(mpesaResponseObject), Console.class);
                map.put("status", HttpStatus.OK);
                map.put("ConversationID", mpesaResponseObject.getConversationID());
                map.put("OriginatorCoversationID", mpesaResponseObject.getOriginatorConverstionID());
                map.put("ResponseDescription", mpesaResponseObject.getResponseDescription());
                return  map;
            }

        }catch (Exception e){

        }
        return null;
    }
   }

