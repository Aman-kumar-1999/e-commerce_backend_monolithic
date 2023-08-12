package com.eqipped.service.impl;

import com.eqipped.config.AppConfig;
import com.eqipped.service.WhatsAppService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.paytm.pg.merchant.PaytmChecksum;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.*;

@Service
public class WhatsApps implements WhatsAppService {

    @Value("${key.campaigname}")
    private String campaignName;

    @Value("${key.updatecampaigname}")
    private String updateCampaignName;

    @Value("${key.source}")
    private String source;

    @Value("${key.templateparams}")
    private String templateParams;

    @Value("${key.updatetemplateparams}")
    private String updatetemplateParams;





    @Autowired
    RestTemplate restTemplate;
    @Override
    public String sendWhatsAppMassage(String customerName,
                                      String customerPhone,
                                      String orderId,
                                      String productName,
                                      float totalPrice,
                                      String orderStatus) {




        //MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Map<String,Object> body = new HashMap<>();

        body.put("apiKey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0MTk0NWFlMzAzOTQ4NGE5ZDQ1ZTZhOSIsIm5hbWUiOiJHZWFybG9vc2UgTGFicyBQcml2YXRlwqBMaW1pdGVkIiwiYXBwTmFtZSI6IkFpU2Vuc3kiLCJjbGllbnRJZCI6IjY0MTk0NWFlMzAzOTQ4NGE5ZDQ1ZTZhNCIsImFjdGl2ZVBsYW4iOiJCQVNJQ19NT05USExZIiwiaWF0IjoxNjc5Mzc3ODM4fQ.CzEUV2FWmH-0RY9otRS9PL1A_u77mmJnJduaZsU78Hs");
        body.put("campaignName", campaignName);
        System.out.println("campaignName : "+campaignName);
        body.put("destination", customerPhone.toString());
        body.put("userName", customerName);
        body.put("source", source);
        System.out.println("source : "+source);
        System.out.println("templateParams : "+templateParams);
        List<String> templateParams1 = new ArrayList<String>();
        JsonArray jsonArray = new JsonArray();
        if(
                templateParams.equals("Not") ||
                        templateParams.isBlank() ||
                        templateParams.isEmpty() ||
                        templateParams.equals(false))
        {
            System.out.println("Under if templateParams : "+templateParams);
        }else {
            if(customerName.isEmpty() || customerName.isBlank() || customerName == null){
                customerName="Customer";
            }
            templateParams1.add(customerName);
            templateParams1.add(orderId);

            //templateParams1.add(productName.trim());
            System.out.println("Order Id : "+orderId+"\nTotal Price : Rs "+String.valueOf(totalPrice));
            templateParams1.add(String.valueOf(totalPrice));
            //templateParams1.add(orderStatus);
            //templateParams1.add("9790910478");
            // Json Array
            //jsonArray.add(customerName);
            //jsonArray.add(orderId);
            //jsonArray.add(productName.trim());
            //jsonArray.add(String.valueOf(totalPrice));
            //jsonArray.add(orderStatus);
            //jsonArray.add("9790910478");
            body.put("templateParams",templateParams1);
            System.out.println("Template Details : "+templateParams1);
            System.out.println("Body Details : "+templateParams1);
        }


        String response = null;

        try {



            String url = "https://backend.aisensy.com/campaign/t1/api";
            //Gson gson = new Gson();
            //String jsonBody = gson.toJson(body);
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
    @Override
    public String sendUpdateWhatsAppMassage(String customerName,
                                      String customerPhone,
                                      String orderId,
                                      String productName,
                                      float totalPrice,
                                      String orderStatus) {




        //MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Map<String,Object> body = new HashMap<>();
        body.put("apiKey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY0MTk0NWFlMzAzOTQ4NGE5ZDQ1ZTZhOSIsIm5hbWUiOiJHZWFybG9vc2UgTGFicyBQcml2YXRlwqBMaW1pdGVkIiwiYXBwTmFtZSI6IkFpU2Vuc3kiLCJjbGllbnRJZCI6IjY0MTk0NWFlMzAzOTQ4NGE5ZDQ1ZTZhNCIsImFjdGl2ZVBsYW4iOiJCQVNJQ19NT05USExZIiwiaWF0IjoxNjc5Mzc3ODM4fQ.CzEUV2FWmH-0RY9otRS9PL1A_u77mmJnJduaZsU78Hs");
        body.put("campaignName", updateCampaignName);
        System.out.println("campaignName : "+updateCampaignName);
        body.put("destination", customerPhone.toString());
        body.put("userName", customerName);
        body.put("source", source);
        System.out.println("source : "+source);
        System.out.println("templateParams : "+updatetemplateParams);
        List<String> templateParams1 = new ArrayList<>();
        if(
                updatetemplateParams.equals("Not") ||
                        updatetemplateParams.isBlank() ||
                        updatetemplateParams.isEmpty() ||
                        updatetemplateParams.equals(false))
        {
            System.out.println("Under if templateParams : "+updatetemplateParams);
        }else {
            if(customerName.isEmpty() || customerName.isBlank() || customerName == null){
                customerName="Customer";
            }
            templateParams1.add(customerName);
            templateParams1.add(orderId);
            templateParams1.add(productName);
            System.out.println("Total Price : Rs "+String.valueOf(totalPrice));
            templateParams1.add(String.valueOf(totalPrice));
            templateParams1.add(orderStatus);
            templateParams1.add("9790910478");
            body.put("templateParams",templateParams1);
        }


        String response = null;

        try {



            String url = "https://backend.aisensy.com/campaign/t1/api";

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

    // function to generate a random string of length n
    public String getAlphaNumericString(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
