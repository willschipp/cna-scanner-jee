package com.emc.awg.scanner.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.emc.awg.scanner.service.domain.Rule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JarScanService extends AbstractScanService implements DirectoryScanService {

	private static final Log logger = LogFactory.getLog(JarScanService.class);
	
	@Autowired
	private ContentService contentService;

	@Autowired
	private ObjectMapper mapper;	
	
	@Override
	public Object startScan(String location) {
		Object id = super.startScan(location);
		//extract the file and check the contents
		//invoke the super
		Object reportId = super.startScan(location);
		//continue
		execute((Long)reportId,location);
		//return
		return reportId;
	}
	

	protected void execute(Long id,String location) {
		try {
			//load the directory
			JarFile jarFile = new JarFile(location);
			//now get the files and loop
			List<Map<String,String>> reports = new ArrayList<Map<String,String>>();
			Enumeration<JarEntry> enumeration = jarFile.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry entry = enumeration.nextElement();
				String entryName = entry.getName();
				logger.info(entryName);
				//check the name
				Rule rule = contentService.isCNA(entryName);
				if (rule != null) {
					Map<String,String> reportContent = new HashMap<String,String>();
					reportContent.put(entryName, rule.getDescription());
					reports.add(reportContent);
				}//end if
			}//end while
			//process the report
			String content = mapper.writeValueAsString(reports);
			//update
			this.reportService.updateCompleted(id, content);			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e); //TODO - fix this throw
		}
	}
	
	
}
