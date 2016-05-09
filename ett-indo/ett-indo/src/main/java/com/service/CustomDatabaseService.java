package com.service;

import java.util.List;

import com.domain.entity.DeviceIdInstalledApps;
import com.domain.entity.InstalledApps;
import com.domain.entity.InstalledAppsFirstTime;
import com.web.rest.dto.TarrotDto;

public interface CustomDatabaseService {

	void saveInstalledAppsFirstTime(List<InstalledAppsFirstTime> installedAppsFirstTimeList);

	void saveInstalledApps(List<InstalledApps> installedAppsList);

	void saveInstalledAppsFirstTime1(
			List<InstalledAppsFirstTime> installedAppsFirstTimeList);

	void saveDeviceIdInstalledApps(
			List<DeviceIdInstalledApps> deviceIdInstalledAppsList);

	List<TarrotDto> getTarrotDto(String tarrotName,List<Long> update_id);
}
