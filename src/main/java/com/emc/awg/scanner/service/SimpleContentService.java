package com.emc.awg.scanner.service;

import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.awg.scanner.service.domain.Rule;
import com.emc.awg.scanner.service.domain.RuleSet;

@Service
public class SimpleContentService implements ContentService {
	
	private static final Log logger = LogFactory.getLog(SimpleContentService.class);

	@Autowired
	private RuleSet ruleSet;
	
	@Override
	public Rule isCNA(String path) {
		Rule rule = null;
		//check the path
		for (Entry<String,String> r : ruleSet.getRules().entrySet()) {
			if (path.contains(r.getKey())) {
				rule = new Rule();
				rule.setAntString(r.getKey());
				rule.setDescription(r.getValue());
				break;
			}//end if
		}//end for
		//return
		return rule;
	}

}
