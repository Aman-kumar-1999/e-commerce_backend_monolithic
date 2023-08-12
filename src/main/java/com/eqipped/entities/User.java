package com.eqipped.entities;

//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

//@Getter
//@Setter
@Document
@Collation("user")
public class User {

	@Id
	private String id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String enabled;
	private String profile;
	private String registerAs;
	private String institutionName;
	private String pincode;
	private String city;
	private String state;
	private String country;
	private String isuploded;
	private String isverified;
	private String isemailverified;
	private String documentNumber;
	private String address;
	private String avatar;
	private String document;
	private String roleId;
	private String subVendorId;
	private String rolePermissionId;
	private String imageName;
	private String imagePath;


	private Role role;

	@DBRef
	private RolePermission rolePermission;


//	permission



//	reviewed:[
//	{
//		variantRatedID: { type: Schema.Types.ObjectId, ref: 'Product'},
//
//	}],
//	cart: [
//	{
//		product: {type: Schema.Types.ObjectId, ref: 'Variation'},
//		quantity: { type: Number, default: 1},
//		total: { type: Number, required: true},
//		vendor: { type: Schema.Types.ObjectId, ref: 'User'}
//	}
//    ],

//	@Transient
//	private List<Rating> rating = new ArrayList<>();
	//private HashMap<String, Object> rating = new HashMap<>();


	public User() {

	}

//	@Override
//	public String toString() {
//		return "User{" +
//				"id='" + id + '\'' +
//				", username='" + username + '\'' +
//				", password='" + password + '\'' +
//				", firstName='" + firstName + '\'' +
//				", lastName='" + lastName + '\'' +
//				", email='" + email + '\'' +
//				", phone='" + phone + '\'' +
//				", enabled='" + enabled + '\'' +
//				", profile='" + profile + '\'' +
//				", registerAs='" + registerAs + '\'' +
//				", institutionName='" + institutionName + '\'' +
//				", pincode='" + pincode + '\'' +
//				", city='" + city + '\'' +
//				", state='" + state + '\'' +
//				", country='" + country + '\'' +
//				", isuploded='" + isuploded + '\'' +
//				", isverified='" + isverified + '\'' +
//				", isemailverified='" + isemailverified + '\'' +
//				", documentNumber='" + documentNumber + '\'' +
//				", address='" + address + '\'' +
//				", avatar='" + avatar + '\'' +
//				", document='" + document + '\'' +
//				", roleId='" + roleId + '\'' +
//				", rolePermissionId='" + rolePermissionId + '\'' +
//				", role=" + role +
//				", rolePermission=" + rolePermission +
//				'}';
//	}

	public String getSubVendorId() {
		return subVendorId;
	}

	public void setSubVendorId(String subVendorId) {
		this.subVendorId = subVendorId;
	}

	public String getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String isEnabled() {
		return enabled;
	}

	public String getProfile() {
		return profile;
	}

	public String getRegisterAs() {
		return registerAs;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public String getPincode() {
		return pincode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getIsuploded() {
		return isuploded;
	}

	public String getIsverified() {
		return isverified;
	}

	public String getIsemailverified() {
		return isemailverified;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getDocument() {
		return document;
	}

	public void setId(String id) {
		this.id = id;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setRegisterAs(String registerAs) {
		this.registerAs = registerAs;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setIsuploded(String isuploded) {
		this.isuploded = isuploded;
	}

	public void setIsverified(String isverified) {
		this.isverified = isverified;
	}

	public void setIsemailverified(String isemailverified) {
		this.isemailverified = isemailverified;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getEnabled() {
		return enabled;
	}

	public String getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(String rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	public Role getRole() {
		return role;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RolePermission getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(RolePermission rolePermission) {
		this.rolePermission = rolePermission;
	}
}
