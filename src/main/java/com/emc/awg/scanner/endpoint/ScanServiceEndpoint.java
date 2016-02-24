package com.emc.awg.scanner.endpoint;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emc.awg.scanner.service.ScanService;
import com.emc.awg.scanner.service.domain.UploadedFile;
import com.emc.awg.scanner.service.domain.UploadedFileRepository;

@RestController
@RequestMapping("/api/scan-service")
public class ScanServiceEndpoint {

	@Autowired
	private ScanService scanService;
	
	@Autowired
	private UploadedFileRepository uploadedFileRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public Long scanLocation(@RequestParam("location") String location) {
		return (Long) scanService.startScan(location);
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.GET)
	public Long scanUploadedFile(@RequestParam("link") String link) {
		//get the upload file
		UploadedFile uploadedFile = uploadedFileRepository.findOne(getId(link));
		//scan
		return (Long) scanService.startScan(uploadedFile.getLocation());
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public void handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletResponse response) throws Exception {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			File tempFile = File.createTempFile("upload", "jar");
			FileCopyUtils.copy(bytes, tempFile);
			//now create this as a location
			UploadedFile uploadedFile = new UploadedFile();
			uploadedFile.setName(file.getOriginalFilename());
			uploadedFile.setLocation(tempFile.getAbsolutePath());
			//save
			uploadedFileRepository.save(uploadedFile);
		}//end if
		//add good
		response.setStatus(HttpStatus.OK.value());
	}
	
	
	private Long getId(String link) {
		return Long.parseLong(link.substring(link.lastIndexOf("/")+1));
	}
}
