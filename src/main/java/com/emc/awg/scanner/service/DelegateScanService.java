package com.emc.awg.scanner.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;

public class DelegateScanService extends AbstractScanService {
	
	private static final Log logger = LogFactory.getLog(DelegateScanService.class);
	
	private static final Detector DETECTOR = new DefaultDetector(MimeTypes.getDefaultMimeTypes());	

	@Autowired
	private DirectoryScanService directoryScanService;
	
	@Autowired
	private JarScanService jarScanService;

	@Override
	public Object startScan(String location) {
		Object id = null;
		//check the 'type' of location
		logger.info("checking type");
		//if it's a directory --> directoryScanService
		File file = new File(location);
		if (file.isDirectory()) {
			logger.info("it's a database");
			return directoryScanService.startScan(location);
		} else {
			//if it's a file --> type of file --> extract --> scan
			TikaInputStream tis;
			try {
				tis = TikaInputStream.get(file);
				int result = DETECTOR.detect(tis, new Metadata()).compareTo(MediaType.APPLICATION_ZIP);
				logger.info(result);
				if (result == 0) {
					//it's a compressed file (jar)
					//-->extract to a location
					return jarScanService.startScan(location);
				} else {
					logger.error("incorrect format for file");
					return null;
				}//end if
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end if
		
		return id;
	}
	
	
	
}
