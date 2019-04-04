package com.xiangym.solr.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
@org.springframework.stereotype.Service
public class Service {
	private HttpSolrServer httpSolrServer;
	private String url="http://localhost:8880/solr/job";
	
	public Map<String, Map<String, List<String>>> getDate() throws SolrServerException, IOException {
		  SolrQuery query = new SolrQuery();
	        // 设置查询条件,名称“q”是固定的且必须 的
	        query.set("q", "*:*");
	        query.setQuery("topic_ik:测试");
	        HttpSolrServer server =new HttpSolrServer("http://localhost:8880/solr/job");
	       // query.setFields("id");
	        query.setHighlight(true);
	        //params.setParam("hl.mergeContiguous","true");
	        query.setParam("hl.method","original");
	        //params.setParam("hl.method","fastVector");
	        //params.setParam("hl.useFastVectorHighligter","true");
	        
	        //params.setParam("hl.method","unified");

	        //红色粗体显示
	        //标签前缀
	        query.setHighlightSimplePre("<font color='red'><b>");
	        //标签后缀
	        query.setHighlightSimplePost("</b></font>");
	        //params.addHighlightField("text");
	        //高亮字段
	        query.addHighlightField("topic_ik");
	        //高亮字段
	        query.addHighlightField("title_ik");
	        // 调用server的查询方法，查询索引库
	        QueryResponse response = server.query(query);

	        // 查询结果
	        SolrDocumentList results1 = response.getResults();
	        query.setHighlightSnippets(4);
	        query.setHighlightFragsize(0);//如果为0，那么该字段不会被fragmented且整个字段的值会被返回

	        //QueryResponse response = solr.query(params);

	        //高亮集合
	        Map<String, Map<String, List<String>>> results = response.getHighlighting();
	        Set<String> keys = results.keySet();
	        Map<String, List<String>> innermap ;
	        Set<String> innerkeys ;
	        List<String> list ;
	        for(String key:keys){
	            System.out.println("id  =   " +key);
	            innermap = results.get( key);
	            innerkeys = innermap.keySet();
	            for(String innerKey:innerkeys){
	                list = innermap.get(innerKey);
	                for (String kk:list){
	                    System.out.println(innerKey +"  =   "+kk);
	                }
	            }
	            System.out.println("\n");
	        }
	    
		
		
		return results;
		
	}
	
	@Test
	public void setUp() throws Exception {
		   SolrQuery query = new SolrQuery();
	        // 设置查询条件,名称“q”是固定的且必须 的
	        query.set("q", "*:*");
	        HttpSolrServer server =new HttpSolrServer("http://localhost:8880/solr/job");

	        // 调用server的查询方法，查询索引库
	        QueryResponse response = server.query(query);

	        // 查询结果
	        SolrDocumentList results = response.getResults();
	        // 查询结果总数
	        long cnt = results.getNumFound();
	        System.out.println("查询结果总数:" + cnt);

	        for (SolrDocument solrDocument : results) {
	            System.out.println(solrDocument.get("id"));
	            System.out.println(solrDocument.get("address_ik"));
	            System.out.println(solrDocument.get("topic_ik"));
	            System.out.println(solrDocument.get("title_ik"));
	            System.out.println(solrDocument.get("content_ik"));

	        }
	    
        


    }
	@Test
	public void delete() throws Exception {
		HttpSolrServer server = new HttpSolrServer("http://localhost:8880/solr/job");
        // 根据ID删除
        server.deleteById("c0001");
        // 提交
        server.commit();

    }
}
