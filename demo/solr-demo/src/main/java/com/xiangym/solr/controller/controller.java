package com.xiangym.solr.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiangym.solr.service.Service;

@RestController
public class controller {
	@Autowired
	private Service service;
	@RequestMapping("/getData")	
	public Map<String, Map<String, List<String>>>  test() {
		try {
			return service.getDate();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
