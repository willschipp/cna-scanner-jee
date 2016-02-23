package com.emc.awg.scanner.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.awg.scanner.service.domain.Rule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SimpleDirectoryScanService extends AbstractScanService implements DirectoryScanService {

	private static final Log logger = LogFactory.getLog(SimpleDirectoryScanService.class);

	@Autowired
	private ContentService contentService;

	@Autowired
	private ObjectMapper mapper;

	//starts a scan and processes it by assuming the location is a directory location
	//from this it looks for a key file list (e.g. config xml, properties, etc.) and
	//where their location is (e.g. going to be packaged into the jar, or externalized
	//somewhere else
	@Override
	public Object startScan(String location) {
		//invoke the super
		Object reportId = super.startScan(location);
		//continue
		execute((Long)reportId,location);
		//return
		return reportId;
	}

	protected void execute(Long id,String location) {
		//load the directory
		File directory = new File(location);
		//check the location for a directory
		if (!directory.isDirectory()) {
			//failed --> update and exit
			logger.error("location is not a directory [" + location + "]");
			this.reportService.updateFailed(id);
			return;
		}//end if
		//now get the files and loop
		Iterator<File> files = FileUtils.iterateFiles(directory, null, true);
		List<Map<String,String>> reports = new ArrayList<Map<String,String>>();
		//loop and check
		while (files.hasNext()) {
			File f = files.next();
			//extract the path
			String path = f.getAbsolutePath();
			//now validate
			Rule rule = contentService.isCNA(path);
			if (rule != null) {
				Map<String,String> reportContent = new HashMap<String,String>();
				reportContent.put(path, rule.getDescription());
				reports.add(reportContent);
			}//end if
		}//end while
		//process the report
		String content;
		try {
			content = mapper.writeValueAsString(reports);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		//update
		this.reportService.updateCompleted(id, content);
	}
}
