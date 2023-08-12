package com.eqipped.service;

import com.eqipped.entities.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
public interface ProductService {

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
            String containLiquid,// it is also called size
            String companyCode,
            String bulkPack,
            float bulkPrice
    );

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
            String containLiquid,// it is also called size
            String companyCode,
            String bulkPack,
            float bulkPrice
    );
    public Product getProductDataByProductId(String productId);

    public Integer countOfAllProduct();

    Integer countOfAllProductWithVendorId(String vendorId);

    public List<Product> getAllProductByBrandName(String brandName, int pageNo1, int dataLimit1);

    public List<Product> getAllProductByProductName(String productName, int pageNo1, int dataLimit1);

    public List<Product> getAllProductByProductNameOrBrandName(String productName, int pageNo1, int dataLimit1);
    
    public List<Product> getVendorProductByProductNameOrBrandName(String vendorId,String productName, int pageNo1, int dataLimit1);

    public List<Product> getProductByProductName(String productName);

    public List<Product> getAllProductData();

    public List<Product> getAllProductDataWithRange(int pageNo1, int dataLimit1);

    public List<Product> getAllProductCategory(String category);
    public List<Product> getAllProductCategoryWithRange(String category, int pageNo1, int dataLimit1);

    public List<Product> getAllProductVendorId(String vendorId);

    public List<Product> getAllProductVendorIdByRange(String vendorId, int pageNo, int dataLimit);

    public List<Product> getAllProductVariationId(String variationId);

    public List<Product> getAllProductVariationIdByRange(String variationId, int pageNo1, int dataLimit1);

    public String deleteProductData(String productId);

    public Map<String,Object> saveProductThroughExcel(MultipartFile file, String vendorEmail, String vendorId, String vendorName);

}
