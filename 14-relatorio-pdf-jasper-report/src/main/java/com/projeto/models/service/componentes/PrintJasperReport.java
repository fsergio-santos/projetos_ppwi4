package com.projeto.models.service.componentes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PrintJasperReport {

	private String file;
	private Map<String, Object> params;
	private Collection<?> collection;
	

	public PrintJasperReport() {
		this.params = new HashMap<String, Object>();
	}
	
	
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public Collection<?> getCollection() {
		return collection;
	}
	
	public void setCollection(Collection<?> collection) {
		this.collection = collection;
	}
	
	
	public void addParams(String key, Object value) {
		getParams().put(key, value);
	}
	
	
	
	
}
