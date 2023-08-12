package com.eqipped.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.eqipped.entities.User;


//@Service
public interface UserService {

	// create User
	public User saveUser(User user);

	// create Vendor
	public User saveVendor(User user);
	// create Admin
	public User saveAdmin(User user);
	
	// get all User 
	public List<User> getAllUser();
	
	// get User by Id
	public User getUserById(String userId);

	// get User BY username for Email
	public User getUserByUsernameForEmail(String username);

	// get User By username
	public User getUserByUsername(String username);

	// get User By email
	public User getUserByEmailId(String email);
	
	// update User Details
	public Map<String, Object> updateUser(User user);
	
	public Map<String, Object> updateUserImage(MultipartFile images, String id);
	
	// delete User
	public Map<String, Object> deleteUser(String userId);

	// filter user according to roleName
	public List<User> filterUser(String roleId);

}
