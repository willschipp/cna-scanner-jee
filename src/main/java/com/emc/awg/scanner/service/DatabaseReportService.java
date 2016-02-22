package com.emc.awg.scanner.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.awg.scanner.service.domain.Report;
import com.emc.awg.scanner.service.domain.ReportRepository;

@Service
public class DatabaseReportService implements ReportService<Long> {
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Override
	public Long newReportId(String location) {
		//create
		Report report = new Report();
		//add
		report.setLocation(location);
		report.setCreatedDate(new Date());
		//save
		report = reportRepository.save(report);
		//return
		return report.getId();
	}

	@Override
	public void updateFailed(Long id) {
		reportRepository.findOne(id);
	}

	@Override
	public void updateCompleted(Long id) {
		updateCompleted(id,null);
	}

	@Override
	public void updateCompleted(Long id, String content) {
		Report report = reportRepository.findOne(id);
		report.setContent(content);
		report.setCompletedDate(new Date());
		reportRepository.save(report);
	}

	@Override
	public void updateContent(Long id,String content) {
		Report report = reportRepository.findOne(id);
		report.setContent(content);
		reportRepository.save(report);
	}

	@Override
	public Report getReport(Long id) {
		// TODO Auto-generated method stub
		return reportRepository.findOne(id);
	}

}
