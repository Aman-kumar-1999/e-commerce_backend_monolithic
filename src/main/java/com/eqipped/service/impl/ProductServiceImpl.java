package com.eqipped.service.impl;

import com.eqipped.config.FileUpload;
import com.eqipped.entities.Product;
import com.eqipped.helper.Helper;
import com.eqipped.repositories.ProductRepository;
import com.eqipped.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Map<String, Object> createProductData(
            MultipartFile images,
            String vendorId,
            String vendorName,
            String brandName,
            String vendorEmail,
            String productName,
            String productDescription,
            String productLongDescription,
            String category,
            String subCategory,
            String tag,
            String countryOrigin,
            Integer productQuantity,
            float individualProductPrice,
            float discountPercentage,
            String status,
            String action,
            String bulkCode,
            String variationName,
            String variationId,
            float gst,
            String hsn,
            String isVerified,
            String tierNo,
            String containLiquid,
            String companyCode,
            String bulkPack,
            float bulkPrice

    ){


        Map<String,Object> map = new HashMap<>();

        if (!images.isEmpty()) {
            try {
                Map<String,Object> uploadMap  = fileUpload.uploadFile(images);
                map.put("File Exception: ",uploadMap.get("e"));
                boolean uploaded = (boolean) uploadMap.get("f");
                if(uploaded) {
                    Product product = new Product();
                    product.setVendorId(vendorId);
                    product.setVendorName(vendorName);
                    product.setVendorEmail(vendorEmail);
                    product.setProductName(productName);
                    product.setBrandName(brandName);
                    product.setProductDescription(productDescription);
                    product.setProductLongDescription(productLongDescription);
                    product.setCategory(category);
                    product.setSubCategory(subCategory);
                    product.setTag(tag);
                    product.setCountryOrigin(countryOrigin);
                    product.setProductQuantity(productQuantity);
                    product.setIndividualProductPrice(individualProductPrice);
                    float discountPrice = (discountPercentage / 100) * individualProductPrice;
                    float gst1 = (gst /100) * individualProductPrice;
                    float natePriceWithDiscount = individualProductPrice - discountPrice;
                    product.setNatePriceWithDiscount(natePriceWithDiscount);
                    float totalProductPrice;
                    totalProductPrice = natePriceWithDiscount * productQuantity + gst1;
                    product.setTotalProductPrice(totalProductPrice);
                    product.setStatus(status);
                    product.setAction(action);
                    product.setDate(LocalDate.now());
                    product.setImageName(uploadMap.get("fileName").toString());
                    product.setImagePath(uploadMap.get("filePath").toString());
                    product.setDiscountPercentage(discountPercentage);
                    product.setBulkCode(bulkCode);
                    product.setVariationName(variationName);
                    product.setVariationId(variationId);
                    product.setGst(gst);
                    product.setHsn(hsn);
                    product.setIsVerified(isVerified);
                    product.setTierNo(tierNo);
                    product.setContainLiquid(containLiquid);
                    product.setCompanyCode(companyCode);
                    product.setBulkPack(bulkPack);
                    product.setBulkPrice(bulkPrice);

                    //float discountPrice = (discountPercentage/100)*totalProductPrice;
                    //product.setNatePriceWithDiscount(totalProductPrice-discountPrice);

                    productRepository.save(product);
                    map.put("STATUS", "SUCCESS");
                    map.put("PRODUCT", product);
                    //map.put("filePath",uploadMap.get("filePath"));
                }else
                    map.put("STATUS","FAILED");

            } catch (Exception e) {
                map.put("STATUS","InternalServerError");
                map.put("EXCEPTION",e);
            }
        }

        return map;
    }

    public Map<String, Object> updateProductData(
            String productId,
            MultipartFile images,
            String vendorId,
            String vendorName,
            String brandName,
            String vendorEmail,
            String productName,
            String productDescription,
            String productLongDescription,
            String category,            
            String subCategory,
            String tag,
            String countryOrigin,
            Integer productQuantity,
            float individualProductPrice,
            float discountPercentage,
            String status,
            String action,
            String bulkCode,
            String variationName,
            String variationId,
            float gst,
            String hsn,
            String isVerified,
            String tierNo,
            String containLiquid,            
            String companyCode,
            String bulkPack,
            float bulkPrice
    ){


        Map<String,Object> map = new HashMap<>();

        if (!images.isEmpty()) {
            try {
                Map<String,Object> uploadMap  = fileUpload.uploadFile(images);
                map.put("File Exception: ",uploadMap.get("e"));
                boolean uploaded = (boolean) uploadMap.get("f");
                if(uploaded) {
                    Product product1 = productRepository.findById(productId).get();
                    if (product1!=null) {
                        Product product = new Product();
                        product.setProductId(productId);
                        product.setVendorId(vendorId);
                        product.setVendorName(vendorName);
                        product.setVendorEmail(vendorEmail);
                        product.setProductName(productName);
                        product.setBrandName(brandName);
                        product.setProductDescription(productDescription);
                        product.setProductLongDescription(productLongDescription);
                        product.setCategory(category);
                        product.setSubCategory(subCategory);
                        product.setTag(tag);
                        product.setCountryOrigin(countryOrigin);
                        product.setProductQuantity(productQuantity);
                        product.setIndividualProductPrice(individualProductPrice);
                        float discountPrice = (discountPercentage / 100) * individualProductPrice;
                        float gst1 = (gst /100) * individualProductPrice;
                        float natePriceWithDiscount = individualProductPrice - discountPrice;
                        product.setNatePriceWithDiscount(natePriceWithDiscount);
                        float totalProductPrice;
                        totalProductPrice = natePriceWithDiscount * productQuantity + gst1;
                        product.setTotalProductPrice(totalProductPrice);
                        product.setStatus(status);
                        product.setAction(action);
                        product.setDate(LocalDate.now());
                        product.setImageName(uploadMap.get("fileName").toString());
                        product.setImagePath(uploadMap.get("filePath").toString());
                        product.setDiscountPercentage(discountPercentage);
                        product.setBulkCode(bulkCode);
                        product.setVariationName(variationName);
                        product.setVariationId(variationId);
                        product.setGst(gst);
                        product.setHsn(hsn);
                        product.setIsVerified(isVerified);
                        product.setTierNo(tierNo);
                        product.setContainLiquid(containLiquid);
                        product.setCompanyCode(companyCode);
                        product.setBulkPack(bulkPack);
                        product.setBulkPrice(bulkPrice);
                        //float discountPrice = (discountPercentage / 100) * totalProductPrice;
                        //product.setNatePriceWithDiscount(totalProductPrice - discountPrice);
                        product1 = product;
                        productRepository.save(product1);
                        map.put("STATUS", "SUCCESS");
                        map.put("PRODUCT", product);
                    }else
                        map.put("STATUS","Product is not Present");
                    //map.put("filePath",uploadMap.get("filePath"));
                }else
                    map.put("STATUS","FAILED");

            } catch (Exception e) {
                map.put("STATUS","InternalServerError");
                map.put("EXCEPTION",e);
            }
        }

        return map;
    }
    public List<Product> getAllProductData(){
        return productRepository.findAll();
    }

    public List<Product> getAllProductDataWithRange(int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1,dataLimit1);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> filteredList = page.getContent();

//        List<Product> filteredList = new ArrayList<>();
//        int pageNo = pageNo1;
//        int dataLimit = dataLimit1;
//        for (int i=0; i < productList.size(); i++) {
//            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
//                if(productList.get(i)!=null) {
//                    filteredList.add(productList.get(i));
//                    System.out.println("Index : " + i);
//                }
//            }
//        }
        return filteredList;
    }

    public List<Product> getAllProductCategoryWithRange(String category, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("category").is(category));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

//        Query query = new Query().with(PageRequest.of(pageNo1,dataLimit1));
//        query.addCriteria(Criteria.where("category").is(category));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//        List<Product> filteredList = new ArrayList<>();
//        int pageNo = pageNo1;
//        int dataLimit = dataLimit1;
//        for (int i=0; i < products.size(); i++) {
//            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
//                if(products.get(i)!=null) {
//                    filteredList.add(products.get(i));
//                    System.out.println("Index : " + i);
//                }
//            }
//        }

        return filteredList;
    }

    public List<Product> getAllProductCategory(String category){
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<Product> products = mongoTemplate.find(query, Product.class);

        return products;
    }

    public List<Product> getAllProductVendorId(String vendorId){
        Query query = new Query();
        query.addCriteria(Criteria.where("vendorId").is(vendorId));
        List<Product> products = mongoTemplate.find(query, Product.class);

        return products;
    }

    @Override
    public List<Product> getAllProductVendorIdByRange(String vendorId, int pageNo1, int dataLimit1) {
        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("vendorId").is(vendorId));
        List<Product> list = mongoTemplate.find(query, Product.class);

         Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

         List<Product>  filteredList =  page.getContent();
//        Query query = new Query();
//        query.addCriteria(Criteria.where("vendorId").is(vendorId));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//        List<Product> filteredList = new ArrayList<>();
//        int pageNo = pageNo1;
//        int dataLimit = dataLimit1;
//        for (int i=0; i < products.size(); i++) {
//            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
//                if(products.get(i)!=null) {
//                    filteredList.add(products.get(i));
//                    System.out.println("Index : " + i);
//                }
//            }
//        }


        return filteredList;
    }

    public List<Product> getAllProductVariationId(String variationId){
        Query query = new Query();
        query.addCriteria(Criteria.where("variationId").is(variationId.trim()));
        List<Product> products = mongoTemplate.find(query, Product.class);

        return products;
    }

    public List<Product> getAllProductVariationIdByRange(String variationId, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("variationId").is(variationId.trim()));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

//        Query query = new Query();
//        query.addCriteria(Criteria.where("variationId").is(variationId));
//        List<Product> products = mongoTemplate.find(query, Product.class);
//        List<Product> filteredList = new ArrayList<>();
//        int pageNo = pageNo1;
//        int dataLimit = dataLimit1;
//        for (int i=0; i < products.size(); i++) {
//            if(i< pageNo*dataLimit && i>=(pageNo*dataLimit)-dataLimit){
//                if(products.get(i)!=null) {
//                    filteredList.add(products.get(i));
//                    System.out.println("Index : " + i);
//                }
//            }
//        }


        return filteredList;
    }

    public List<Product> getAllProductByProductName(String productName, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "productName"));
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("productName").regex("^"+productName,"i"));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

        return filteredList;
    }

    public List<Product> getAllProductByProductNameOrBrandName(String productName, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "productName"));
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("").orOperator(Criteria.where("productName").regex("^"+productName,"i"),Criteria.where("brandName").regex("^"+productName,"i")));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

        return filteredList;
    }
    
    public List<Product> getVendorProductByProductNameOrBrandName(String vendorId,String productName, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "productName"));
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("").andOperator(Criteria.where("vendorId").is(vendorId)).orOperator(Criteria.where("productName").regex("^"+productName,"i"),Criteria.where("brandName").regex("^"+productName,"i")));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

        return filteredList;
    }


    public List<Product> getAllProductByBrandName(String brandName, int pageNo1, int dataLimit1){

        Pageable pageable = PageRequest.of(pageNo1, dataLimit1,Sort.by(Sort.Direction.ASC, "brandName"));
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("brandName").is(brandName));
        List<Product> list = mongoTemplate.find(query, Product.class);

        Page<Product> page = PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class));

        List<Product>  filteredList =  page.getContent();

        return filteredList;
    }

    public Integer countOfAllProduct(){
        return Math.toIntExact(productRepository.count());

    }
    public Integer countOfAllProductWithVendorId(String vendorId){
        Query query = new Query();
        query.addCriteria(Criteria.where("vendorId").is(vendorId));
        long count = mongoTemplate.count(query, Product.class);
        return Math.toIntExact(count);

    }

    public String deleteProductData(String productId){
        productRepository.deleteById(productId);
        return "Product is deleted";
    }
    public Product getProductDataByProductId(String productId){
        Product product = productRepository.findById(productId).get();
        return product;
    }

    @Override
    public List<Product> getProductByProductName(String productName) {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, productName));
        return products;
    }

    public Map<String,Object> saveProductThroughExcel(MultipartFile file, String vendorEmail, String vendorId, String vendorName) {

        Map<String,Object> map = new HashMap<>();
        try {
            map=Helper.convertExcelToListOfProduct(file.getInputStream());

            List<Product> products = (List<Product>) map.get("PRODUCTS");
            //List<Product> products = Helper.convertExcelToListOfProduct(file.getInputStream());
            if(products!=null || products.isEmpty()) {
                List<Product> productList = products.stream().map(product -> {

                    float discountPrice = (product.getDiscountPercentage() / 100) * product.getIndividualProductPrice();
                    float natePriceWithDiscount = product.getIndividualProductPrice() - discountPrice;
                    product.setNatePriceWithDiscount(natePriceWithDiscount);
                    float totalProductPrice;
                    totalProductPrice = natePriceWithDiscount * product.getProductQuantity();
                    product.setTotalProductPrice(totalProductPrice);
                    product.setDate(LocalDate.now());
                    product.setImageName("No");
                    product.setImagePath("No");
                    product.setVendorId(vendorId);
                    product.setVendorName(vendorName);
                    product.setVendorEmail(vendorEmail);

                    return product;
                }).collect(Collectors.toList());
                this.productRepository.saveAll(productList);
                map.put("MSG","Product has been created Successful");
                map.put("STATUS","SUCCESS");
                map.put("PRODUCTS",productList);
            }else {
                map.put("STATUS","FAILED");
                map.put("MSG","Cause exception");
                map.put("PRODUCTS","NULL");
            }

        } catch (Exception e) {
            map.put("STATUS","FAILED");
            map.put("PRODUCTS","NULL");
            e.printStackTrace();
        }
        return map;
    }
}
