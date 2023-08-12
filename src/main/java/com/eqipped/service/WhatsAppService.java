package com.eqipped.service;

public interface WhatsAppService {

    public String sendWhatsAppMassage(String customerName, String customerPhone, String orderId, String productName, float totalPrice, String orderStatus);

    public String sendUpdateWhatsAppMassage(String customerName,
                                            String customerPhone,
                                            String orderId,
                                            String productName,
                                            float totalPrice,
                                            String orderStatus);
}
