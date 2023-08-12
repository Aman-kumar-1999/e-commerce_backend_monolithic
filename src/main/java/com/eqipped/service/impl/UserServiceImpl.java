package com.eqipped.service.impl;


import java.util.*;

import com.eqipped.config.CryptoUtils;
import com.eqipped.config.FileUpload;
import com.eqipped.entities.Role;
import com.eqipped.repositories.RoleRepository;
import com.eqipped.repositories.RoleSideBarOptionsRepository;
import com.eqipped.entities.User;

import com.eqipped.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eqipped.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleSideBarOptionsRepository roleSideBarOptionsRepository;


	@Autowired
	private MongoTemplate mongoTemplate;


	@Autowired
	private FileUpload fileUpload;

	@Override
	public Map<String, Object> updateUserImage(MultipartFile images,String id){

		Map<String,Object> map = new HashMap<>();

		if (!images.isEmpty()) {
			try {
				Map<String, Object> uploadMap = fileUpload.uploadFile(images);
				map.put("File Exception: ", uploadMap.get("e"));
				boolean uploaded = (boolean) uploadMap.get("f");
				if (uploaded) {

					User oldUser = userRepository.findById(id).isPresent()
							? userRepository.findById(id).get()
							: null;
					if (oldUser != null) {

						User newUser;
						newUser = oldUser;
						newUser.setImageName(uploadMap.get("fileName").toString());
						newUser.setImagePath(uploadMap.get("filePath").toString());
						System.out.println("Before save");

						userRepository.save(newUser);

						System.out.println("After Save");
						map.put("STATUS", "SUCCESS");
						map.put("USER", newUser);

					} else {
						map.put("STATUS", "FAILED");
						map.put("MSG","User is not Present");

					}
				}else
					map.put("STATUS","FAILED");

			}catch (Exception e){
				map.put("STATUS","InternalServerError");
				map.put("EXCEPTION",e);
			}
		}

		return map;
	}


	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub

		try {


		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(user.getPassword()));
		user.setPassword(hashpassword);
		// user.setRoleId(roleRepository.findById());
		Query query = new Query();
		query.addCriteria(Criteria.where("roleName").is("User"));
		Role roleData = mongoTemplate.findOne(query, Role.class);
		//Optional<Role> role = roleRepository.findById(user.getRoleId()).isPresent() ? roleRepository.findById(user.getRoleId()) : null;
		user.setUsername(user.getEmail());
			user.setRoleId(roleData.getRoleId());
		userRepository.save(user);
		}catch (Exception e){
			e.printStackTrace();
		}
		return user;
	}


	@Override
	public User saveVendor(User user) {
		try {


			String userId = UUID.randomUUID().toString();
			user.setId(userId);
			String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(user.getPassword()));
			user.setPassword(hashpassword);
			// user.setRoleId(roleRepository.findById());

			Query query = new Query();
			query.addCriteria(Criteria.where("roleName").is("Vendor"));
			Role roleData = mongoTemplate.findOne(query, Role.class);
			//Optional<Role> role = roleRepository.findById(user.getRoleId()).isPresent() ? roleRepository.findById(user.getRoleId()) : null;
			user.setUsername(user.getEmail());
			user.setRoleId(roleData.getRoleId());
			userRepository.save(user);
		}catch (Exception e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User saveAdmin(User user){
		try {


			String userId = UUID.randomUUID().toString();
			user.setId(userId);
			String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(user.getPassword()));
			user.setPassword(hashpassword);
			// user.setRoleId(roleRepository.findById());
			Query query = new Query();
			query.addCriteria(Criteria.where("roleName").is("Admin"));
			Role roleData = mongoTemplate.findOne(query, Role.class);
			//Optional<Role> role = roleRepository.findById(user.getRoleId()).isPresent() ? roleRepository.findById(user.getRoleId()) : null;
			user.setUsername(user.getEmail());
			user.setRoleId(roleData.getRoleId());
			userRepository.save(user);
		}catch (Exception e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> oldUsers =userRepository.findAll();
		List<Role> roleList = roleRepository.findAll();
		List<User> newUsers = new ArrayList<>();
		for (User user: oldUsers) {
			for (Role i: roleList) {
				Optional<Role> role = roleRepository.findById(user.getRoleId()).isPresent() ? roleRepository.findById(user.getRoleId()) : null;
				role.get().setRoleSideBarOptions(roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()).isPresent() ? roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()) : null);
				user.setRole(role.get());

			}
			newUsers.add(user);
		}
		System.out.println(" Role : "+getOrdersWithCustomers().toString());

		return newUsers;
	}

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(userId));
		User userData = mongoTemplate.findOne(query, User.class);
		System.out.println("UserData : "+userData);
		User user = userRepository.findById(userId).isPresent()
				? userRepository.findById(userId).get()
				: null;
		Optional<Role> role = roleRepository.findById(user.getRoleId()).isPresent()
				? roleRepository.findById(user.getRoleId())
				: null;
		Role roleOptional = new Role();
		role.get().setRoleSideBarOptions(roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()).isPresent()
				? roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId())
				:null);
		user.setRole(role.get());

		return user;
	}
	public User getUserByUsernameForEmail(String username) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		User userData = mongoTemplate.findOne(query, User.class);
		return userData;
	}
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		User userData = mongoTemplate.findOne(query, User.class);
		System.out.println("UserData : "+userData.toString());
		//User user = userRepository.findById(username).isPresent()
		//		? userRepository.findById(username).get()
		//		: null;
		Optional<Role> role = roleRepository.findById(userData.getRoleId()).isPresent()
				? roleRepository.findById(userData.getRoleId())
				: null;
		Role roleOptional = new Role();
		role.get().setRoleSideBarOptions(roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()).isPresent()
				? roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId())
				:null);
		userData.setRole(role.get());
		return userData;
	}
	public User getUserByEmailId(String email) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		User userData = mongoTemplate.findOne(query, User.class);
		System.out.println("UserData : "+userData.toString());
		//User user = userRepository.findById(username).isPresent()
		//		? userRepository.findById(username).get()
		//		: null;
		Optional<Role> role = roleRepository.findById(userData.getRoleId()).isPresent()
				? roleRepository.findById(userData.getRoleId())
				: null;
		Role roleOptional = new Role();
		role.get().setRoleSideBarOptions(roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()).isPresent()
				? roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId())
				:null);
		userData.setRole(role.get());
		return userData;
	}

	@Override
	public Map<String, Object> updateUser(User user){
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<>();

		User oldUser = userRepository.findById(user.getId()).isPresent()
				? userRepository.findById(user.getId()).get()
				: null;
		if (oldUser != null) {

			User newUser = new User();
			newUser.setId(user.getId());
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setAddress(user.getAddress());
			newUser.setCountry(user.getCountry());
			newUser.setCity(user.getCity());
			newUser.setAvatar(user.getAvatar());
			newUser.setDocument(user.getDocument());
			newUser.setDocumentNumber(user.getDocumentNumber());
			newUser.setEmail(user.getEmail());
			newUser.setEnabled(user.getEnabled());
			newUser.setInstitutionName(user.getInstitutionName());
			newUser.setIsemailverified(user.getIsemailverified());
			newUser.setIsverified(user.getIsverified());
			newUser.setIsuploded(user.getIsuploded());
			newUser.setPhone(user.getPhone());
			newUser.setUsername(user.getUsername());
			newUser.setImageName(user.getImageName());
			newUser.setImagePath(user.getImagePath());
			newUser.setSubVendorId(user.getSubVendorId());

			try{
//				String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(user.getPassword()));
				newUser.setPassword(user.getPassword());
			}catch (Exception e){
				map.put("STATUS", "FAILED");
				e.printStackTrace();
			}

			newUser.setPincode(user.getPincode());
			newUser.setProfile(user.getProfile());
			newUser.setRegisterAs(user.getRegisterAs());
			newUser.setState(user.getState());
			newUser.setRoleId(user.getRoleId());
			newUser.setRolePermission(user.getRolePermission());
//			newUser = user;

			System.out.println("Before save");

			userRepository.save(newUser);

			System.out.println("After Save");
			map.put("STATUS", "SUCCESS");

			Query query = new Query();
			query.addCriteria(Criteria.where("username").is(newUser.getUsername()));
			User userData = mongoTemplate.findOne(query, User.class);
			System.out.println("UserData : "+userData.toString());
			//User user = userRepository.findById(username).isPresent()
			//		? userRepository.findById(username).get()
			//		: null;
			Optional<Role> role = roleRepository.findById(userData.getRoleId()).isPresent()
					? roleRepository.findById(userData.getRoleId())
					: null;
			Role roleOptional = new Role();
			role.get().setRoleSideBarOptions(roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId()).isPresent()
					? roleSideBarOptionsRepository.findById(role.get().getRoleSideBarId())
					:null);
			userData.setRole(role.get());

			map.put("USER", userData);

		} else {
			map.put("STATUS", "FAILED");

		}

		return map;
	}


	public List<Role> getOrdersWithCustomers() {
		AggregationOperation lookup = Aggregation.lookup("user", "roleId", "_id", "users");
		Aggregation aggregation = Aggregation.newAggregation(lookup);
		AggregationResults<Role> results = mongoTemplate.aggregate(aggregation, "role", Role.class);
		return results.getMappedResults();
	}
	@Override
	public Map<String, Object> deleteUser(String userId) {

		Map<String, Object> map = new HashMap<>();

		User oldUser = userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;

		if (userId.equals(oldUser.getId())) {

			userRepository.deleteById(userId);

			map.put("STATUS", "SUCCUSS");

		} else {
			map.put("STATUS", "FAILED");

		}
		return map;
	}

	@Override
	public List<User> filterUser(String roleId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("roleId").is(roleId));
		List<User> users = mongoTemplate.find(query, User.class);
		return users;
	}
}
