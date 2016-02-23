package com.emc.awg.scanner;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

import com.emc.awg.scanner.service.ReportService;
import com.emc.awg.scanner.service.ScanService;
import com.emc.awg.scanner.service.domain.Report;
import com.emc.awg.scanner.service.domain.RuleSet;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	ScanService scanService;
	
	@Autowired
	ReportService<Long> reportService;
	
	@Autowired
	private RuleSet ruleSet;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("starting...");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object reportId = scanService.startScan(args[0]);
		stopWatch.stop();
		System.out.println("...finished in " + stopWatch.getTaskCount() + "ms");
		//retrieve
		Report report = reportService.getReport((Long)reportId);
		System.out.println(report);
	}
	
	@Bean
	ObjectMapper mapper() {
		return new ObjectMapper();
	}
	


}
