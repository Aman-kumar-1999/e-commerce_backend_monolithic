package com.eqipped.controller;

import com.eqipped.config.AppConfig;
import com.eqipped.entities.Cart;
import com.eqipped.entities.Order;
import com.eqipped.helper.CCAvenuePaymentRequest;
import com.eqipped.helper.EmailDetails;
import com.eqipped.helper.OrderStatusRequest;
import com.eqipped.service.CCAvenue;
import com.eqipped.service.CartService;
import com.eqipped.service.EmailService;
import com.eqipped.service.OrderService;
import com.eqipped.service.WhatsAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paytm.pg.merchant.PaytmChecksum;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

//  @Value("${paytm.mid}")
  private String MID = "KzHAQX87362223007289";  // KzHAQX87362223007289

//  @Value("${paytm.mkey}")
  private String MKEY = "To3XrXpBk7JudAF_";  // To3XrXpBk7JudAF_

//  @Value("${paytm.website}")
  private String WEBSITE = "WEBSTAGING"; // WEBSTAGING

  @Autowired
  private CCAvenue ccAvenue;


  @Autowired
  private OrderService orderService;

  @Autowired
  private CartService cartService;


  @Autowired
  private EmailService emailService;

  @Autowired
  private WhatsAppService whatsAppService;

  Random random = new Random();


  @GetMapping("/")
  public ResponseEntity<?> getAllOrder(){

      List list = orderService.getAllOrder();

      return ResponseEntity.ok(list);
  }
  @GetMapping("/orderId/{orderId}")
  public ResponseEntity<?> getOrderByOrderId(@PathVariable String orderId){
      Map<String, Object> map = new HashMap<>();
      try{
          Order order1 = orderService.getOrder(orderId);
          if (order1!=null){

              map.put("ORDER",order1);
          }
          else
              map.put("MSG","This Order can't be presented");
      }catch (Exception e){
          map.put("MSG","Unable to load this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }

  @PostMapping("/")
  public ResponseEntity<?> createOrder(@RequestBody Order order){

      Map<String,Object> map = new HashMap<>();

      try{
          String emailStatus;
          String whatsAppStatus;
          Order order1 = orderService.createOrder(order);
          EmailDetails email = new EmailDetails();
          String signature = "\n\nThanks & Regards,\nEqipped Help Desk Team\n";
          email.setMsgBody("Dear "+order1.getCustomerName()
                  + ",\n\n\t\t\t Your  order has been placed successfully. We received your purchase request. We'll be in touch shortly!\n\n\t\t\t Order Id : "
                  + order1.getOrderId()
                  + "\n\t\t\t Your Product Name :  " + order1.getProductName() +"\n"
                  + "\t\t\t Total Price :  Rs " + order1.getTotalProductPrice() +"\n"
                  + "\t\t\t Order Status :  " + order1.getStatus() +"\n\t\t\t"
                  + signature);

          email.setSubject("Eqipped Order has been placed");
          email.setRecipient(order1.getEmail());
          map.put("ORDER",order1);

          // sent Email
          emailStatus = emailService.sendSimpleMail(email);
          map.put("Email_STATUS", emailStatus);

          // sent What's App msg
          whatsAppStatus = whatsAppService.sendWhatsAppMassage(
                      order1.getCustomerName(),
                      order1.getPhone(),
                      order1.getOrderId(),
                      order1.getProductName(),
                      order1.getTotalProductPrice(),
                      order1.getStatus());
          map.put("WhatsApp_STATUS",whatsAppStatus);
      }catch (Exception e){
          map.put("Email_STATUS", "Failed");
          map.put("WhatsApp_STATUS","Failed");
          e.printStackTrace();
      }

      return ResponseEntity.ok(map);
  }
  @GetMapping("/getVendorOrder/{vendorId}")
  public ResponseEntity<?> getVendorOrder(@PathVariable String vendorId){

      List<Order> li = orderService.getVendorOrder(vendorId);

      return ResponseEntity.ok(li);
  }

  @PostMapping("/getStatusVendorOrder")
  public ResponseEntity<?> getStatusVendorOrder(@RequestBody OrderStatusRequest orderStatusRequest){

      List<Order> li = orderService.getStatusVendorOrder(orderStatusRequest);

      return ResponseEntity.ok(li);
  }



  @PostMapping("/getStatusOrder")
  public ResponseEntity<?> getStatusOrder(@RequestBody OrderStatusRequest orderStatusRequest){

      List<Order> li = orderService.getStatusOrder(orderStatusRequest);

      return ResponseEntity.ok(li);
  }

  @PutMapping("/updateOrder")
  public ResponseEntity<?> updateOrder(@RequestBody Order order){
      Map<String, Object> map = new HashMap<>();
      try{
          String whatsAppStatus;
          Order order1 = orderService.updateOrder(order);
          if (order1!=null){
              map.put("MSG","Order has been updated Successfully");
              EmailDetails email = new EmailDetails();
              String signature = "\n\nThanks & Regards,\nEqipped Help Desk Team\n";
              email.setMsgBody("Dear "+order1.getCustomerName()
                      + ",\n\n\t\t\t Your  order status has been placed successfully.\n\n\t\t\t Order Id : "
                      + order1.getOrderId()
                      + "\n\t\t\t Your Product Name :  " + order1.getProductName() +"\n"
                      + "\t\t\t Total Price :  Rs " + order1.getTotalProductPrice() +"\n"
                      + "\t\t\t Order Status :  " + order1.getStatus() +"\n\t\t\t"
                      + signature);
              email.setSubject("Eqipped Order Status has been changed");
              email.setRecipient(order1.getEmail());
              String status = emailService.sendSimpleMail(email);
              map.put("ORDER",order1);

              map.put("Email_STATUS", status);

              // sent What's App msg
              whatsAppStatus = whatsAppService.sendUpdateWhatsAppMassage(
                      order1.getCustomerName(),
                      order1.getPhone(),
                      order1.getOrderId(),
                      order1.getProductName(),
                      order1.getTotalProductPrice(),
                      order1.getStatus());
              map.put("WhatsApp_STATUS",whatsAppStatus);
          }
          else
              map.put("MSG","This Order can't be presented");
      }catch (Exception e){
          map.put("Email_STATUS", "Failed");
          map.put("WhatsApp_STATUS","Failed");
          map.put("MSG","Unable to update this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<?> deleteorder(@PathVariable String orderId){

      Map<String, Object> map = new HashMap<>();
      try{
          orderService.deleteOrder(orderId);
          map.put("MSG","Order has been deleted Successfully");
      }catch (Exception e){
          map.put("MSG","Unable to delete this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }





  @GetMapping("/emailId/{email}")
  public ResponseEntity<?> findOrderUserEmail(@PathVariable String email){

      List<Order> orders = new ArrayList<>();

      try{
          orders = orderService.getOrderByEmail(email);

      }catch (Exception e){

          e.printStackTrace();
      }
      return ResponseEntity.ok(orders);
  }

//Cart Controller

  @GetMapping("/cart")
  public ResponseEntity<?> getAllCart(){

      List<Cart> list = cartService.getAllCart();

      return ResponseEntity.ok(list);
  }

  @GetMapping("/cart/emailId/{email}")
  public ResponseEntity<?> findCartOfUserEmail(@PathVariable String email){

      List<Cart> cart = new ArrayList<>();
//      Map<String, Object> map = new HashMap<>();
      try{
          cart = cartService.findCartOfEmail(email);
//          map.put("MSG","Order has been find Successfully");
//          map.put("CART",cart);
      }catch (Exception e){
//          map.put("MSG","Unable to find this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(cart);
  }
  @PostMapping("/cart")
  public ResponseEntity<?> createCart(@RequestBody Cart cart){
      Cart cart1 = cartService.createCart(cart);
      return ResponseEntity.ok(cart1);
  }



  @PutMapping("/cart/updateCart")
  public ResponseEntity<?> updateCart(@RequestBody Cart cart){
      Map<String, Object> map = new HashMap<>();
      try{
          Cart cart1 = cartService.updateCart(cart);
          if (cart1!=null){
              map.put("MSG","Order has been updated Successfully");
              map.put("ORDER",cart1);
          }
          else
              map.put("MSG","This Order can't be presented");
      }catch (Exception e){
          map.put("MSG","Unable to update this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }

  @DeleteMapping("/cart/{orderId}")
  public ResponseEntity<?> deleteCart(@PathVariable String orderId){

      Map<String, Object> map = new HashMap<>();
      try{
          cartService.deleteCart(orderId);
          map.put("MSG","Order has been deleted Successfully");
      }catch (Exception e){
          map.put("MSG","Unable to delete this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }
  @DeleteMapping("/cart/email/{email}")
  public ResponseEntity<?> deleteCartOfUserEmail(@PathVariable String email){

      Map<String, Object> map = new HashMap<>();
      try{
          cartService.deleteCartOfEmail(email);
          map.put("MSG","Order has been deleted Successfully");
      }catch (Exception e){
          map.put("MSG","Unable to delete this Order");
          e.printStackTrace();
      }
      return ResponseEntity.ok(map);
  }


  // paytm payment gateway




//  Random random = new Random();


  @PostMapping("/start")
  public Map<String, Object> startPayment(
          @RequestBody Map<String, Object> data
  ) {


      String orderId = "ORDER" + random.nextInt(10000000);

      //param created
      JSONObject paytmParams = new JSONObject();


      //body information
      JSONObject body = new JSONObject();
      body.put("requestType", "Payment");
      body.put("mid", AppConfig.MID);
      body.put("websiteName", AppConfig.WEBSITE);
      body.put("orderId", orderId);
      body.put("callbackUrl", "https://api.eqipped.com");

      JSONObject txnAmount = new JSONObject();
      txnAmount.put("value", data.get("amount"));
      txnAmount.put("currency", "INR");

      JSONObject userInfo = new JSONObject();
      userInfo.put("custId", "CUST_001");


      body.put("txnAmount", txnAmount);
      body.put("userInfo", userInfo);

      String responseData = "";
      ResponseEntity<Map> response = null;

      try {

          String checksum = PaytmChecksum.generateSignature(body.toString(), AppConfig.MKEY);

          JSONObject head = new JSONObject();
          head.put("signature", checksum);

          paytmParams.put("body", body);
          paytmParams.put("head", head);

          String post_data = paytmParams.toString();

          /* for Staging */
          URL url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=" + AppConfig.MID + "&orderId=" + orderId + "");

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paytmParams.toMap(), headers);

          //calling api
          RestTemplate restTemplate = new RestTemplate();
          response = restTemplate.postForEntity(url.toString(), httpEntity, Map.class);

          System.out.println(response);


      } catch (Exception e) {
          e.printStackTrace();
      }

      Map body1 = response.getBody();
      body1.put("orderId", orderId);
      body1.put("amount", txnAmount.get("value"));
      return body1;
  }

  public void capturePayment(){
      //get the data from client

      //verify the payment

      //database mein bhi update kar do ki payment ho chuka hai...


      //allow user to access the service
  }



  @PostMapping("/whatsapp")
  public String sentWhatsAppMsg(){
      String response = whatsAppService.sendWhatsAppMassage(
              "Aman",
              "9128984478",
              "2938",
              "Test Tube",
              2939,
              "Success");
      return response;
  }

  @PostMapping("/test/ccav")
  public String ccavenue(){

      return ccAvenue.ccavenuePaymentGateway(new CCAvenuePaymentRequest());
  }


















}
