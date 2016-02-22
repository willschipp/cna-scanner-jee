package com.emc.awg.scanner.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleContentService implements ContentService {

	@Override
	public boolean isCNA(String path) {
		//check the path
		//TODO expand with ability get from properties/other sources
		if (path.contains("xml")) {
			return false;
		}//end if
		//return
		return true;
	}

}
