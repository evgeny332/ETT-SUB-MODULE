package com.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.domain.entity.BreakingAlerDailyCheck;
import com.domain.entity.FirstHitDay;
import com.domain.entity.User;
import com.repository.jpa.BreakingAlerDailyCheckRepository;
import com.repository.jpa.FirstHitDayRepository;
import com.service.FirstHitService;

@Service
public class FirstHitServiceImpl implements FirstHitService {

	private static Logger LOGGER = LoggerFactory.getLogger(FirstHitServiceImpl.class);
	
	@Resource
	private FirstHitDayRepository firstHitDayRepository;
	
	@Resource
	private BreakingAlerDailyCheckRepository breakingAlerDailyCheckRepository;
	
	@Override
	public boolean checkFistHitDay(User user) {
		
		List<FirstHitDay> firstHit = firstHitDayRepository.findByettIdToday(user.getEttId());
		
		if(firstHit == null || firstHit.size()<=0)
			return true;
		FirstHitDay firstHitDay = firstHit.get(0);
		
		//LOGGER.info("[Get the FirstHitDay object] {}",firstHitDay);
		
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int timezone = user.getTimeZoneInSeconds();
		if(timezone == 0)
			timezone = 19800;
		//Date date = DateTime.now(DateTimeZone.forOffsetMillis(timezone*1000)).toDateMidnight().toDate();
		Date date = new Date();
		date.setTime(date.getTime()+timezone*1000);
		date.setHours(0);
		date.setMinutes(0);
		//String today = df.format(date);
		firstHitDay.setCreatedTime(new Date(firstHitDay.getCreatedTime().getTime()+timezone*1000));
		if(date.after(firstHitDay.getCreatedTime()))
		{
			LOGGER.info("[Get the FirstHitDay object] {},[current Day]{},{}",firstHitDay,date,"false");
			return true;
		}
		LOGGER.info("[Get the FirstHitDay object] {},[current Day]{},{}",firstHitDay,date,"true");
		return false;
	}
	
	
	@Override
	public boolean checkBreakingAlertDailyCheck(User user) {
		List<BreakingAlerDailyCheck> breakingAlerDailyChecks = breakingAlerDailyCheckRepository.findByettIdBreakingAlerDailyCheck(user.getEttId());
		if(breakingAlerDailyChecks == null || breakingAlerDailyChecks.size()<=0)
			return true;
		BreakingAlerDailyCheck breakingAlerDailyCheck = breakingAlerDailyChecks.get(0);
		
		//LOGGER.info("[Get the FirstHitDay object] {}",firstHitDay);
		
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int timezone = user.getTimeZoneInSeconds();
		if(timezone == 0)
			timezone = 19800;
		//Date date = DateTime.now(DateTimeZone.forOffsetMillis(timezone*1000)).toDateMidnight().toDate();
		Date date = new Date();
		date.setTime(date.getTime()+timezone*1000);
		date.setHours(0);
		date.setMinutes(0);
		//String today = df.format(date);
		breakingAlerDailyCheck.setCreatedTime(new Date(breakingAlerDailyCheck.getCreatedTime().getTime()+timezone*1000));
		if(date.after(breakingAlerDailyCheck.getCreatedTime()))
		{
			LOGGER.info("[Get the breakingAlerDailyCheck object] {},[current Day]{},{}",breakingAlerDailyCheck,date,"false");
			return true;
		}
		LOGGER.info("[Get the breakingAlerDailyCheck object] {},[current Day]{},{}",breakingAlerDailyCheck,date,"true");
		return false;
	}


}
