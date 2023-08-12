package com.eqipped.entities;

//import jakarta.persistence.Id;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Collation("role-permission")
public class RolePermission {

    @Id
    private String rolePermissionId;
    private String newOrders;
    private String addProducts;
    private String verifyUserListedProducts;
    private String verifyUserDocuments;
    private String addNewCategory;
    private String deleteProduct;
    private String adminListedProducts;
    private String editProducts;
    private String viewCustomers;
    private String viewVendors;
    private String createUsers;
    private String verifiedUsers;
    private String viewPreDiscount;

    public String getPermissionId() {
        return rolePermissionId;
    }

    public void setPermissionId(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public String getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(String newOrders) {
        this.newOrders = newOrders;
    }

    public String getAddProducts() {
        return addProducts;
    }

    public void setAddProducts(String addProducts) {
        this.addProducts = addProducts;
    }

    public String getVerifyUserListedProducts() {
        return verifyUserListedProducts;
    }

    public void setVerifyUserListedProducts(String verifyUserListedProducts) {
        this.verifyUserListedProducts = verifyUserListedProducts;
    }

    public String getVerifyUserDocuments() {
        return verifyUserDocuments;
    }

    public void setVerifyUserDocuments(String verifyUserDocuments) {
        this.verifyUserDocuments = verifyUserDocuments;
    }

    public String getAddNewCategory() {
        return addNewCategory;
    }

    public void setAddNewCategory(String addNewCategory) {
        this.addNewCategory = addNewCategory;
    }

    public String getDeleteProduct() {
        return deleteProduct;
    }

    public void setDeleteProduct(String deleteProduct) {
        this.deleteProduct = deleteProduct;
    }

    public String getAdminListedProducts() {
        return adminListedProducts;
    }

    public void setAdminListedProducts(String adminListedProducts) {
        this.adminListedProducts = adminListedProducts;
    }

    public String getEditProducts() {
        return editProducts;
    }

    public void setEditProducts(String editProducts) {
        this.editProducts = editProducts;
    }

    public String getViewCustomers() {
        return viewCustomers;
    }

    public void setViewCustomers(String viewCustomers) {
        this.viewCustomers = viewCustomers;
    }

    public String getViewVendors() {
        return viewVendors;
    }

    public void setViewVendors(String viewVendors) {
        this.viewVendors = viewVendors;
    }

    public String getCreateUsers() {
        return createUsers;
    }

    public void setCreateUsers(String createUsers) {
        this.createUsers = createUsers;
    }

    public String getVerifiedUsers() {
        return verifiedUsers;
    }

    public void setVerifiedUsers(String verifiedUsers) {
        this.verifiedUsers = verifiedUsers;
    }

    public String getViewPreDiscount() {
        return viewPreDiscount;
    }

    public void setViewPreDiscount(String viewPreDiscount) {
        this.viewPreDiscount = viewPreDiscount;
    }
}
