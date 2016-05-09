package com.rh.interfaces;

import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;


/**
 * This interface defined REST API for BirthdayReminder
 */
public interface APIInterface {

	void updatePokktInfo(String json);	
	void updateUnReg(String id);
	//void deleteUser(Long ettId);
}