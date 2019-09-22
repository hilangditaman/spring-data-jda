package com.dana.bijak.service;

import com.dana.bijak.entity.Pinjaman;
import com.dana.bijak.entity.Term;
import com.dana.bijak.repository.PinjamanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Service
public class PinjamanServiceImpl implements PinjamanService{
    private static final Logger log = LoggerFactory.getLogger(PinjamanServiceImpl.class);

    @Autowired
    private PinjamanRepository pinjamanRepository;

    private static final Double interest = 1.5;

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    private static final DateFormat dateFormat = new SimpleDateFormat (DATE_FORMAT);


    @Override
    public String apply(String name, String phoneNumber, BigDecimal amount, Term term) {
        Pinjaman pinjaman = new Pinjaman ();
        try {
            pinjaman.setName (name);
            pinjaman.setPhoneNumber (phoneNumber);
            pinjaman.setAmount (amount);
            pinjaman.setTerm (term.toString ());
            pinjaman.setDueDate (new SimpleDateFormat (DATE_FORMAT).parse (dueDate (term)));
            pinjaman.setIssueDate (Calendar.getInstance().getTime());
            pinjaman.setInterest (interest);
            pinjaman.setInterestAmount (new BigDecimal (calculateFee (amount)));
            pinjamanRepository.save (pinjaman);
        }catch (Exception e){
            e.getMessage ();
        }
        return pinjaman.toString ();
    }

    @Override
    public String reportByPhoneNumber(String phoneNumber) {
        return pinjamanRepository.findByPhoneNumber (phoneNumber).toString ();
    }

    private Date dateNow() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date dateNow = new SimpleDateFormat (DATE_FORMAT).parse (date.toString ());
        return dateNow;
    }

    private String dueDate(Term term){
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusWeeks (issueDate (term));
        Date currentDatePlusWeek = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(currentDatePlusWeek);
    }

    private Integer issueDate(Term term){
        if(term.toString ().equalsIgnoreCase (Term.ONE_WEEK.toString ())){
            return 1;
        }else if(term.toString ().equalsIgnoreCase (Term.TWO_WEEK.toString ())){
            return 2;
        }else if(term.toString ().equalsIgnoreCase (Term.THRE_WEEK.toString ())){
            return 3;
        }else{
            return 4;
        }
    }

    private double calculateFee(BigDecimal amount){
        return (amount.doubleValue ()*interest)/100;
    }
}
