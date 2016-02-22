package com.emc.awg.scanner.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emc.awg.scanner.service.ScanService;

@RestController
@RequestMapping("/api/scan-service")
public class ScanServiceEndpoint {

	@Autowired
	private ScanService scanService;
	
	@RequestMapping(method=RequestMethod.GET)
	public Long scanLocation(@RequestParam("location") String location) {
		return (Long) scanService.startScan(location);
	}
	
}
