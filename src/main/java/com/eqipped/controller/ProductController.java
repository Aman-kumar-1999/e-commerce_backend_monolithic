package com.eqipped.controller;

import com.eqipped.config.FileUpload;
import com.eqipped.entities.*;
import com.eqipped.helper.Helper;
import com.eqipped.repositories.BrandRepository;
import com.eqipped.repositories.VariationRepository;
import com.eqipped.service.ProductService;
import com.eqipped.service.TierService;
import com.eqipped.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {


    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private ProductService productService;

    @Autowired
    private VariationRepository variationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TierService tierService;

    @Autowired
    private BrandRepository brandRepository;


    @PostMapping("/")
    public ResponseEntity<?> createProductDataWithUserData(
            @RequestParam("images") MultipartFile images,
            @RequestParam("products") String product

    ){
        Product user = null;
        try {
            user = objectMapper.readValue(product,Product.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
            //throw new RuntimeException(e);

        }
        Map<String,Object> map = new HashMap<>();
        map = productService.createProductData(
                images,
                user.getVendorId(),
                user.getVendorName(),
                user.getBrandName(),
                user.getVendorEmail(),
                user.getProductName(),
                user.getProductDescription(),
                user.getProductLongDescription(),
                user.getCategory(),
                user.getSubCategory(),
                user.getTag(),
                user.getCountryOrigin(),
                user.getProductQuantity(),
                user.getIndividualProductPrice(),
                user.getDiscountPercentage(),
                user.getStatus(),
                user.getAction(),
                user.getBulkCode(),
                user.getVariationName(),
                user.getVariationId(),
                user.getGst(),
                user.getHsn(),
                user.getIsVerified(),
                user.getTierNo(),
                user.getContainLiquid(),// is also size
                user.getCompanyCode(),
                user.getBulkPack(),
                user.getBulkPrice()
        );

        return ResponseEntity.ok(map);
    }
    @PutMapping("/")
    public ResponseEntity<?> updateProductData(
            @RequestParam("images") MultipartFile images,
            @RequestParam("products") String product
    ){
        Product user = null;
        try {
            user = objectMapper.readValue(product,Product.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
            //throw new RuntimeException(e);

        }

        Map<String,Object> map = new HashMap<>();
        map = productService.updateProductData(
                user.getProductId(),
                images,
                user.getVendorId(),
                user.getVendorName(),
                user.getBrandName(),
                user.getVendorEmail(),
                user.getProductName(),
                user.getProductDescription(),
                user.getProductLongDescription(),
                user.getCategory(),
                user.getSubCategory(),
                user.getTag(),
                user.getCountryOrigin(),
                user.getProductQuantity(),
                user.getIndividualProductPrice(),
                user.getDiscountPercentage(),
                user.getStatus(),
                user.getAction(),
                user.getBulkCode(),
                user.getVariationName(),
                user.getVariationId(),
                user.getGst(),
                user.getHsn(),
                user.getIsVerified(),
                user.getTierNo(),
                user.getContainLiquid(),
                user.getCompanyCode(),
                user.getBulkPack(),
                user.getBulkPrice()
        );

        return ResponseEntity.ok(map);
    }


//    @GetMapping("/")
//    public ResponseEntity<?> getAllProductData(){
//        return ResponseEntity.ok(productService.getAllProductData());
//    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProductDataWithRange(@RequestParam(value = "pageNo", defaultValue = "1" , required = false) int pageNo,@RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){
        return ResponseEntity.ok(productService.getAllProductDataWithRange(pageNo, dataLimit));
    }

    @GetMapping("/filter/{productName}")
    public ResponseEntity<?> getAllProductByProductName(@PathVariable String productName){

        return ResponseEntity.ok(productService.getProductByProductName(productName));

    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getAllProductCategoryWithRange(@PathVariable("category") String category, @RequestParam(value="pageNo",defaultValue = "1", required = false) int pageNo,@RequestParam(value = "dataLimit",defaultValue = "10" ,required = false ) int dataLimit){
        List<Product> products = productService.getAllProductCategoryWithRange(category,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }
//    @GetMapping("/category/{category}")
//    public ResponseEntity<?> getAllProductCategory(@PathVariable("category") String category){
//        List<Product> products = productService.getAllProductCategory(category);
//
//        return ResponseEntity.ok(products);
//    }



//    @GetMapping("/variationId/{variationId}")
//    public ResponseEntity<?> getAllProductCategoryText(@PathVariable String variationId){
//        List<Product> products = productService.getAllProductVariationId(variationId);
//
//        return ResponseEntity.ok(products);
//    }





    @GetMapping("/variationId/{variationId}")
    public ResponseEntity<?> getAppProductWithVariationByRange(@PathVariable String variationId,
                                                               @RequestParam(value = "pageNo" , defaultValue = "1" , required = false) Integer pageNo,
                                                               @RequestParam(value = "dataLimit", defaultValue = "10", required = false) Integer dataLimit){
        List<Product> products = productService.getAllProductVariationIdByRange(variationId,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }


//    @GetMapping("/vendorId/{vendorId}")
//    public ResponseEntity<?> getAllProductVendorId(@PathVariable String vendorId){
//
//        List<Product> products = productService.getAllProductVendorId(vendorId);
//
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/vendorId/{vendorId}")
    public ResponseEntity<?> getAllProductVendorIdByRange(@PathVariable("vendorId") String vendorId,
                                                          @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
                                                          @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){

        List<Product> products = productService.getAllProductVendorIdByRange(vendorId,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/productName/{productName}")
    public ResponseEntity<?> getAllProductByProductName(@PathVariable("productName") String productName,
                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){

        List<Product> products = productService.getAllProductByProductName(productName,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/productNameOrBrandName/{productNameOrBrandName}")
    public ResponseEntity<?> getAllProductByProductNameOrBrandName(@PathVariable("productNameOrBrandName") String productNameOrBrandName,
                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){

        List<Product> products = productService.getAllProductByProductNameOrBrandName(productNameOrBrandName,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/vendorIdProductNameOrBrandName/{vendorId}/{productNameOrBrandName}")
    public ResponseEntity<?> getVendorProductByProductNameOrBrandName(
    													@PathVariable("productNameOrBrandName") String productNameOrBrandName,
    													@PathVariable("vendorId") String vendorId,    													
                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){

        List<Product> products = productService.getVendorProductByProductNameOrBrandName(vendorId, productNameOrBrandName, pageNo, dataLimit);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/brandName/{brandName}")
    public ResponseEntity<?> getAllProductByBrandName(@PathVariable("brandName") String brandName,
                                                        @RequestParam(value = "pageNo" , defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "dataLimit", defaultValue = "10", required = false) int dataLimit){

        List<Product> products = productService.getAllProductByBrandName(brandName,pageNo,dataLimit);

        return ResponseEntity.ok(products);
    }
    @GetMapping("/countAll")
    public ResponseEntity<?> countProduct(){

        Integer count = productService.countOfAllProduct();

        return ResponseEntity.ok(count);
    }
    @GetMapping("/countAllVendorId/{vendorId}")
    public ResponseEntity<?> countProductByVendorId(@PathVariable String vendorId){

        Integer count = productService.countOfAllProductWithVendorId(vendorId);

        return ResponseEntity.ok(count);
    }



    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductData(@PathVariable String productId){
        productService.deleteProductData(productId);
        return ResponseEntity.ok("Product is deleted");
    }
    @GetMapping("/productId/{productId}")
    public ResponseEntity<?> getProductDataByProductId(@PathVariable String productId){
        Product product = productService.getProductDataByProductId(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/tier/productId/{productId}")
    public ResponseEntity<?> getProductDataByProductIdWithTierData(@PathVariable String productId){

        Map<String,Object> map = new HashMap<>();
        List<Tier> tierList = new ArrayList<>();
        Product product = productService.getProductDataByProductId(productId);
        map.put("PRODUCT",product);
        if(product != null){
            tierList = tierService.getAllTierByTierCode(product.getTierNo());
            map.put("TIER",tierList);
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> createProductDataWithExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("products") String product

    ){
        Map<String,Object> map = new HashMap<>();
        if (Helper.checkExcelFormat(file)) {
            //true
            Product user = null;
            try {
                user = objectMapper.readValue(product,Product.class);
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
                //throw new RuntimeException(e);

            }

            map = productService.saveProductThroughExcel(file,user.getVendorEmail(), user.getVendorId(), user.getVendorName());

            return ResponseEntity.ok(map);


        }else {
            map.put("STATUS","Kindly upload Excel file only");
        }

        return ResponseEntity.ok(map);
    }

    @PutMapping("/userUpdate")
    public ResponseEntity<?> updateUser(
            @RequestParam("images") MultipartFile images,
            @RequestParam("userData") String user
    ){
        User user1;
        try {
            user1 = objectMapper.readValue(user,User.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
            //throw new RuntimeException(e);
        }
        Map<String,Object> map;
        map = userService.updateUserImage(
                images,
                user1.getId()
        );
        return ResponseEntity.ok(map);
    }


    // Tier Controller

    @GetMapping("/tier")
    public ResponseEntity<?> getAllTierData(){
        List<Tier> tierList = tierService.getAllTier();
        return ResponseEntity.ok(tierList);
    }

    @GetMapping("/tier/tierCode/{tierCode}")
    public ResponseEntity<?> getAllTierData(@PathVariable String tierCode){
        List<Tier> tierList = tierService.getAllTierByTierCode(tierCode);
        return ResponseEntity.ok(tierList);
    }

    @PostMapping("/tier/create")
    public ResponseEntity<?> createTier(@RequestBody Tier tier){
        return ResponseEntity.ok(tierService.createTier(tier));
    }
    @PutMapping("/tier/update")
    public ResponseEntity<?> updateTier(@RequestBody Tier tier){
        return ResponseEntity.ok(tierService.updateTier(tier));
    }

    @PostMapping("/tier/delete/{id}")
    public ResponseEntity<?> deleteTier(@PathVariable String id){
        tierService.deleteTier(id);
        return ResponseEntity.ok("Done");

    }


    // variation
    @GetMapping("/variationData/{variationName}")
    public ResponseEntity<?> createVariation(@PathVariable String variationName){
        Variation variation = new Variation();
        variation.setVariationName(variationName);
        return ResponseEntity.ok(variationRepository.save(variation));
    }

    @GetMapping("/variationData/getAll")
    public ResponseEntity<?> getAllVariation(){
        List<Variation> variationList = variationRepository.findAll();
        return ResponseEntity.ok(variationList);
    }

    // brand
    @PostMapping("/brand")
    public ResponseEntity<?> createBrand(Brand brand){
        return ResponseEntity.ok(brandRepository.save(brand));
    }

    @GetMapping("/brand")
    public ResponseEntity<?> getAllBrand(){
        return ResponseEntity.ok(brandRepository.findAll());
    }



}
