package com.emc.awg.scanner.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractScanService implements ScanService {

	@Autowired
	ReportService<Long> reportService;
	
	//generates a report id for async thread management
	@Override
	public Object startScan(String location) {
		return reportService.newReportId(location);
	}

}
