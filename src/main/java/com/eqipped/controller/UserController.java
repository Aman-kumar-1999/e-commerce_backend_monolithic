package com.eqipped.controller;

import com.eqipped.config.CryptoUtils;
import com.eqipped.entities.User;
import com.eqipped.helper.ChangePasswordDetails;
import com.eqipped.helper.EmailDetails;
import com.eqipped.helper.JwtRequest;
import com.eqipped.helper.RendomString;
import com.eqipped.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eqipped.service.EmailService;
import com.eqipped.service.UserService;

//import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/api/login")
	public ResponseEntity<?> validationForUser(@RequestBody JwtRequest request) {

		Map<String,Object> map = new HashMap<>();

		try{
			System.out.println("UI Username : "+request.getUsername());
			System.out.println("UI Password : "+request.getPassword());
			User user = userService.getUserByUsername(request.getUsername());
			String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(request.getPassword()));
			System.out.println("Back-End Password : "+user.getPassword());
			if(user!=null){
				if(user.getPassword().equals(hashpassword)){
					map.put("STATUS","SUCCESS");
					map.put("USER",user);
				}
				else {
					map.put("STATUS","FAILED");
					map.put("MSG"," Invalid Username and Password");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			map.put("STATUS","FAILED");
			map.put("MSG", "Sorry ! "+request.getUsername()+" are unable to login in");
		}

		return ResponseEntity.ok(map);
	}


	@PostMapping("/admin")
	public ResponseEntity<?> createAdmin(@RequestBody User user) {
		return ResponseEntity.ok(userService.saveAdmin(user));
	}
	@PostMapping("/vendor")
	public ResponseEntity<?> createVendor(@RequestBody User user)  {
		return ResponseEntity.ok(userService.saveVendor(user));
	}
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showAllUser(){
		return ResponseEntity.ok(userService.getAllUser());
	}


	@PutMapping("/forgetPassword")
	public Map<String, Object> forgetPassword(@RequestBody EmailDetails details) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			String status;
			String otp = RendomString.getAlphaNumericString(8);
			String hashpassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(otp));
			//Random random = new Random();
			//int otp = 100000 + random.nextInt(900000);

			User user = userService.getUserByUsernameForEmail(details.getRecipient());
			if (user != null) {
				if (user.getUsername().equalsIgnoreCase(details.getRecipient())) {
					user.setPassword(hashpassword);

					String signature = "\n\nThanks & Regards,\nEqipped Help Desk Team\n";

					userRepository.save(user);
					EmailDetails email = new EmailDetails();
					email.setMsgBody("Dear " + user.getFirstName()
							+ ",\n\n\t\t\tDon't Share Password from Anyone.\n \n\t\t\tPassword : " + otp + "\n\t\t\t"
							+ signature);
					email.setSubject("New Changed Password");
					email.setRecipient(user.getEmail());
					status = emailService.sendSimpleMail(email);
					map.put("STATUS", "SUCCESS");
					map.put("Email", status);

				}
			} else {
				status = "Your Email Id is not Present in our data database";
				map.put("STATUS", "FAILED");
				map.put("Email", status);
			}
		} catch (Exception e) {
			// TODO: handle exception
			//status = "Server API Through Exception";
			e.printStackTrace();
			map.put("STATUS", "FAILED");
			map.put("Email", "Server API Through Exception");
		}
		return map;

	}

	@GetMapping("/encryptString/{data}")
	public String encryptString(@PathVariable String data){
		String hashPassword;
		try {
			hashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(data));


		} catch (Exception e) {
			hashPassword = "You are unable to Encrypt Data";
			throw new RuntimeException(e);
		}
		return hashPassword;

	}

	@PutMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDetails newUser) throws Exception {

		Map<String,Object> map = new HashMap<>();

		try{
			String status;
			System.out.println("email : "+newUser.getEmail()+"\nNew Password : "+newUser.getNewPassword()+"\nOld Password : "+newUser.getOldPassword());

			User oldUser = userService.getUserByUsernameForEmail(newUser.getEmail());


			String oldHashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(newUser.getOldPassword()));
			String newHashPassword = CryptoUtils.byteArrayToHexString(CryptoUtils.computeHash(newUser.getNewPassword()));

			if (oldUser.getEmail().equalsIgnoreCase(newUser.getEmail()) && oldUser.getPassword().equals(oldHashPassword)) {
				oldUser.setPassword(newHashPassword);
				map.put("STATUS","SUCCESS");
//				System.out.println("User : "+oldUser.toString());
				userRepository.save(oldUser);
				status = "Password has changed Successfully";
				map.put("MSG",status);
			} else {
				status = "Error has been Occurred for Changing Password";
				map.put("MSG", status);
			}

		}catch (Exception e){
			map.put("STATUS","FAILED");
			e.printStackTrace();
		}

		return ResponseEntity.ok(map);
	}
	
//	@GetMapping("/{Id}")
//	//@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
//	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
//	public ResponseEntity<?> showUserById(@PathVariable String Id){
//		Map<String,Object> map = new HashMap<>();
//		try {
//			User user = userService.getUserById(Id);
//			map.put("STATUS","SUCCESS");
//			map.put("USER", user);
//		}catch (Exception e)
//		{
//			e.printStackTrace();
//			map.put("STATUS","FAILED");
//			map.put("MSG","Sorry ! Unable to find the data in data base");
//		}
//
//		return ResponseEntity.ok(map);
//	}
	
	//creating fall back  method for circuitbreaker
//    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
//        //logger.info("Fallback is executed because service is down : ", ex.getMessage());
//
//        ex.printStackTrace();
//
//        //User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created dummy because some service is down").userId("141234").build();
//        User user = userService.getUserById(userId);
//        // user.setEmail("failed@gmail.com");
//        // user.setFirstName("Failed");
//        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
//    }

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username){
		Map<String,Object> map = new HashMap<>();
		try {
			User user = userService.getUserByUsername(username);
			map.put("STATUS","SUCCESS");
			map.put("USER", user);
		}catch (Exception e)
		{
			e.printStackTrace();
			map.put("STATUS","FAILED");
			map.put("MSG","Sorry ! Unable to find the data in data base");
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<?> getUserByEmailId(@PathVariable String email){
		Map<String,Object> map = new HashMap<>();
		try {
			User user = userService.getUserByEmailId(email);
			map.put("STATUS","SUCCESS");
			map.put("USER", user);
		}catch (Exception e)
		{
			e.printStackTrace();
			map.put("STATUS","FAILED");
			map.put("MSG","Sorry ! Unable to find the data in data base");
		}

		return ResponseEntity.ok(map);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateUser(@RequestBody User user){
		return ResponseEntity.ok(userService.updateUser(user));
	}
	
	@DeleteMapping("/deleteUserById/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId){
		return ResponseEntity.ok(userService.deleteUser(userId));
	}

	@GetMapping("/filterUser/{roleId}")
	public ResponseEntity<?> getFilterUser(@PathVariable String roleId){

		List<User> li = userService.filterUser(roleId);
		return ResponseEntity.ok(li);
	}

}
