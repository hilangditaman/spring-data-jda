package com.dana.bijak.controller;

import com.dana.bijak.entity.Term;
import com.dana.bijak.service.PinjamanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class PinjamanController {
    @Autowired
    private PinjamanServiceImpl pinjamanServiceImpl;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public String apply(@RequestParam(value = "name") String name,
                        @RequestParam(value = "phoneNumber") String phoneNumber,
                        @RequestParam(value = "amount")BigDecimal amount,
                        @RequestParam(value = "term") Term term){
        return pinjamanServiceImpl.apply (name, phoneNumber, amount, term);
    }

    @RequestMapping(value = "/reportByPhoneNumber", method = RequestMethod.POST)
    public String apply(@RequestParam(value = "phoneNumber")String phoneNumber){
        return pinjamanServiceImpl.reportByPhoneNumber (phoneNumber);
    }
}
