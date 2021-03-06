
package com.web.rest.controller;

import java.io.BufferedReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.User;
import com.domain.entity.UserBlackList;
import com.domain.entity.UserProfile;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserProfileRepository;
import com.repository.jpa.UserRepository;
import com.service.EttApis;
import com.service.RechargeService;
import com.web.rest.dto.ProfileActionDto;
import com.web.rest.dto.UserProfileDto;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class    UserProfileController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    @Resource
    UserProfileRepository userProfileRepository;
    @Resource
    UserBlackListRepository userBlackListRepository;

    @Resource
    EttApis ettApis;

    @Resource
	UserRepository userRepository;
    
    @Resource
    RechargeService rechargeService;
    
    @RequestMapping(value = "/user/profile/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> saveUserProfile(HttpServletRequest request,
    										@RequestParam(value="ettId") Long ettId,
    										@RequestParam(value="dob", required = false ) String dob,
    										@RequestParam(value="email", required = false ) String email,
    										@RequestParam(value="gender", required = false ) String gender,
    										@RequestParam(value="occupation", required = false ) String occupation,
    										@RequestParam(value="maritalStatus", required = false ) String maritalstatus,
    										@RequestParam(value="salary", required = false ) String income,
    										@RequestParam(value="latitude", required = false ) Double latitude,
    										@RequestParam(value="longitude", required = false ) Double longitude,
    										@RequestParam(value="location", required = false) String location,
    										@RequestParam("otp") int otp
    										
    										)
    										{
    	
    	try{
			String[] locationArray = location.split(",");
			latitude = Double.valueOf(locationArray[0]);
			longitude = Double.valueOf(locationArray[1]);
		}catch(Exception e){
			LOGGER.error(e+"/user/profile/ ettId = {}, dob = {}, email = {}, gender = {}, occupation = {}, maritalStatus = {}, income = {}, latitude = {}, longitude = {}", ettId, dob, email, gender, occupation, maritalstatus, income, latitude, longitude);
			//e.printStackTrace();
		}

    	LOGGER.info("API /user/profile/ ettId = {}, dob = {}, email = {}, gender = {}, occupation = {}, maritalStatus = {}, income = {}, latitude = {}, longitude = {}", ettId, dob, email, gender, occupation, maritalstatus, income, latitude, longitude);
    	UserBlackList userBlackList = userBlackListRepository.findByEttId(ettId);
    	if(userBlackList != null && userBlackList.getBlackListCounter()>=rechargeService.getBlackListBlockCounter()) {
    		LOGGER.info("ettId BlackListed {}",ettId);
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	User user = userRepository.findByEttId(ettId);
		if (user == null || !user.isVerified() || user.getOtp() != otp) {
			LOGGER.error("user not authorized in db ettId=" + ettId);
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		//changing the Indo to English
		try {
			gender = gender.replace("pria", "male").replace("perempuan", "female");
			occupation = occupation.replace("mahasiswa", "student").replace("bekerja", "employed").replace("pengangguran", "unemployed").replace("Ibu rumah tangga", "housewife").replace("pensiunan", "retired").replace("Wirausaha", "self employed");
			maritalstatus = maritalstatus.replace("belum menikah", "single").replace("menikah", "married");
		}
		catch(Exception ex) {}
		
		if (ettId == null) {
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
					jb.append(line);
			} catch (Exception e) {
				e.printStackTrace();
			}
			LOGGER.error("/user/profile/" + jb.toString());
			return new ResponseEntity<>(HttpStatus.OK);
		}

    	UserProfile userProfile = userProfileRepository.findByEttId(ettId);
		if (userProfile == null) {
			userProfile = new UserProfile();
			userProfile.setEttId(ettId);
		}
		userProfile.setDob(dob);
		userProfile.setEmail(email);
		userProfile.setGender(gender);
		userProfile.setOccupation(occupation);
		userProfile.setMaritalStatus(maritalstatus);
		userProfile.setIncome(income);
		userProfile.setUpdateDate(new java.util.Date());
		if(latitude!=null)
			userProfile.setLatitude(latitude);
		if(longitude!=null)
			userProfile.setLongitude(longitude);
    	userProfile = userProfileRepository.save(userProfile);
		 
		return new ResponseEntity<>(getUserProfileDto(userProfile), HttpStatus.OK);
  }
    
    
    @RequestMapping(value = "/user/profileAction/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> saveUserProfile(
    										@RequestParam(value="ettId") Long ettId,
    										@RequestParam("otp") int otp
    					
    										){
		LOGGER.info("/user/profileAction/ ettId={},otp={}",ettId,otp);
		if (ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}", ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if (user == null || !user.isVerified() || user.getOtp() != otp) {
			LOGGER.error("user not authorized in db ettId=" + ettId);
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		ProfileActionDto profileActionDto = new ProfileActionDto();
		//TODO: make configurable
		profileActionDto.setText("");
		profileActionDto.setActionUrl("");
		
		UserProfile userProfile = userProfileRepository.findByEttId(ettId);
		if(userProfile!=null){
			profileActionDto.setUserProfileDto(getUserProfileDto(userProfile));
		}
		
		return new ResponseEntity<>(profileActionDto, HttpStatus.OK);
  }

    
  private UserProfileDto getUserProfileDto(UserProfile userProfile)
  {
	  UserProfileDto userProfileDto = new UserProfileDto();
	  userProfileDto.setDob(userProfile.getDob());
	  userProfileDto.setEmail(userProfile.getEmail());
	  userProfileDto.setGender(userProfile.getGender());
	  userProfileDto.setOccupation(userProfile.getOccupation());
	  userProfileDto.setMaritalStatus(userProfile.getMaritalStatus());
	  userProfileDto.setIncome(userProfile.getIncome());
	  return userProfileDto;
  
  }

}
