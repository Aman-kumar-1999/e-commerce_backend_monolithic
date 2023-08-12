package com.eqipped.service.impl;

import com.eqipped.config.CryptoUtils;
import com.eqipped.helper.CCAvenuePaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CCAvenue implements com.eqipped.service.CCAvenue {


    @Autowired
    RestTemplate restTemplate;

    @Override
    public String ccavenuePaymentGateway(CCAvenuePaymentRequest ccAvenuePaymentRequest) {


        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("order_no", "2390");
        requestBody.put("reference_no","28393");
        requestBody.put("order_email", "code.a2z.code@gmail.com");
        requestBody.put("order_bill_tel", "");
        requestBody.put("order_country", "India");
        requestBody.put("from_date", "20-07-2023");
        requestBody.put("to_date", "20-08-2023");
        requestBody.put("order_max_amount", "1000000");
        requestBody.put("order_min_amount", "1");
        requestBody.put("order_status", "Shipped");
        requestBody.put("order_fraud_status", "");
        requestBody.put("order_currency", "INR");
        requestBody.put("order_type", "OT-ORD");
        requestBody.put("order_payment_type", "NBK");
        requestBody.put("page_number",1);
        String hash;
        try {
             hash = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(requestBody.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Blob cryptoKey = Blob.valueOf('WORKING_KEY');
//        Blob hash = Crypto.generateDigest('MD5', cryptoKey );
//        Blob data = Blob.valueOf(PLAIN_TEXT);
//        Blob encryptedData = Crypto.encryptWithManagedIV('AES128', hash , data);
//        String encRequest = EncodingUtil.convertToHex(encryptedData );

//        String url = "https://apitest.ccavenue.com/apis/servlet/DoWebTrans?enc_request="+hash+"&access_code=AVNX88KG17BA17XNAB&command=confirmOrder&request_type=JSON&response_type=JSON&version=1.1";
        Map<String,Object> body = new HashMap<>();
        body.put("enc_request", hash);
        body.put("access_code", "AVBN92KG17CA49NBAC");
        System.out.println(" : ");
        body.put("command", "OrderLookUp");
        body.put("request_type", "JSON");
        body.put("response_type", "JSON");
        body.put("version",1);
        System.out.println("source : ");
        System.out.println("templateParams : ");
        List<String> templateParams1 = new ArrayList<>();



        String response = null;

        try {



            String url = "https://apitest.ccavenue.com/apis/servlet/DoWebTrans?enc_request="+hash+"&access_code=AVBN92KG17CA49NBAC&command=" +
                    "confirmOrder&request_type=JSON&response_type=JSON&version=1.1";

            //calling api
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(body,headers);

            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class).getBody();

            System.out.println(response);


        } catch (Exception e) {
            response = "Failed";
            e.printStackTrace();
        }

        return response;
    }


}

