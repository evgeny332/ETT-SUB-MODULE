package com.service;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.User;

public interface DataUsageService {
	
	public boolean checkDateEligiblity(DataUsagePendingCredits usagePendingCredits);
	
	public int getThresholdChunk(DataUsagePendingCredits dataUsage, long usedData, float totalEarnedAmount, float todaysEarnedAmount);
	
	public void creditUserForDataUsage(User user, DataUsagePendingCredits dataUsagePendingCredits, int dataChunks);

}
