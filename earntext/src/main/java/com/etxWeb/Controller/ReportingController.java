package com.etxWeb.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.etxWeb.entity.passive.EtxMsgSubmitDetails;
import com.etxWeb.repository.passive.EtxMsgSubmitDetailsRepository;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
@RequestMapping("/etx/v1")
public class ReportingController {
	private static Logger LOGGER = LoggerFactory.getLogger(ReportingController.class);
	
	@Autowired(required=true)
	private EtxMsgSubmitDetailsRepository etxMsgSubmitDetailsRepository;
	
	@RequestMapping(value="/report", method = RequestMethod.GET)
	public String report(
			@RequestParam(value="selectDate", required=false, defaultValue="") String selectDate
			){
    	//System.out.println(config.getDayLimitApi());
    	//List<EtxMsgSubmitDetails> etxMsgSubmitDetails = etxMsgSubmitDetailsRepository.findAll(new PageRequest(1, 20));
		System.out.println("report api log selectDate{}"+selectDate);
		//String date = null;
		if(selectDate.equals("")) {
			Date date1 = new Date();
			date1.setTime(date1.getTime()+19800000);
			selectDate = (1900+date1.getYear())+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
		}
		List<EtxMsgSubmitDetails> etxMsgSubmitDetails = etxMsgSubmitDetailsRepository.findByCreatedTimeIST(selectDate);
    	Gson gson = new Gson();
    	return gson.toJson(etxMsgSubmitDetails);
	}
	
	@RequestMapping(value="/report/selectDate", method = RequestMethod.GET)
	public String selectDate(){
    	List<String> distinctDate = etxMsgSubmitDetailsRepository.findDistinctCreatedTimeIST();
    	Gson gson = new Gson();
    	return gson.toJson(distinctDate);
	}
	
}
