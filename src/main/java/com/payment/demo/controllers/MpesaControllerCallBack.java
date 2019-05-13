package com.payment.demo.controllers;

import com.payment.demo.MpesaMethods.CallBackResponse;
import com.payment.demo.MpesaMethods.MpesaResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MpesaControllerCallBack {

        public static final Logger logger = LoggerFactory.getLogger(MpesaControllerCallBack.class);


    @RequestMapping(value = "/api/v1/c2b/callback/validation", method = RequestMethod.POST)
    public Map <String , Object> MPESAC2BValidation(@RequestBody CallBackResponse request) {
        //print you response and do other stuff
        logger.info("...................Validation url...................");
        logger.info("BILL REF NUMBER   :"+request.getBillRefNumber());
//        logger.info(request.getBusinessShortCode());
        return  new HashMap<>();
    }

    @RequestMapping(value = "/api/v1/c2b/callback/confirmation", method = RequestMethod.POST)
    public Map <String , Object> MPESAC2BConfirmation(@RequestBody CallBackResponse request) {
        //print you response and do other stuff
        logger.info("...................Confirmation url...................");
        logger.info(request.getBillRefNumber());
        logger.info(request.getBusinessShortCode());
        return  new HashMap<>();
    }

}
