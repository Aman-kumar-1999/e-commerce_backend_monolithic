package com.eqipped.service;

import org.springframework.stereotype.Repository;

import com.eqipped.helper.CCAvenuePaymentRequest;

@Repository
public interface CCAvenue {

    public String ccavenuePaymentGateway(CCAvenuePaymentRequest ccAvenuePaymentRequest);

}
