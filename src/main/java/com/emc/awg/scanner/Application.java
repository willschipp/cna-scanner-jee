package com.emc.awg.scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.emc.awg.scanner.service.DelegateScanService;
import com.emc.awg.scanner.service.DirectoryScanService;
import com.emc.awg.scanner.service.JarScanService;
import com.emc.awg.scanner.service.ScanService;
import com.emc.awg.scanner.service.SimpleDirectoryScanService;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	DirectoryScanService directoryScanService() {
		return new SimpleDirectoryScanService();
	}
	
	@Bean
	ScanService scanService() {
		return new DelegateScanService();
	}
	
	@Bean
	JarScanService jarScanService() {
		return new JarScanService();
	}

}
