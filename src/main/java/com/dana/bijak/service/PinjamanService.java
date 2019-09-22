package com.dana.bijak.service;

import com.dana.bijak.entity.Term;

import java.math.BigDecimal;

public interface PinjamanService {
    /**
     * @param amount
     * @param term
     * @return
     */
    String apply(String name, String phoneNumber, BigDecimal amount, Term term);

    String reportByPhoneNumber(String phoneNumber);
}
