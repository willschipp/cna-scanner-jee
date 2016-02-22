package com.emc.awg.scanner.service;

import com.emc.awg.scanner.service.domain.Report;

public interface ReportService<T> {

	T newReportId(String location);
	
	void updateFailed(T id);
	
	void updateCompleted(T id);
	
	void updateCompleted(T id,String content);
	
	void updateContent(T id,String content);
	
	Report getReport(T id);
}
