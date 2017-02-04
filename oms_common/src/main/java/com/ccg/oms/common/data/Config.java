package com.ccg.oms.common.data;

import java.util.ArrayList;
import java.util.List;

import com.ccg.util.JSON;

public class Config {
	private List<KeyValue> list;

	public List<KeyValue> getList() {
		if(list == null)
			list = new ArrayList<KeyValue>();
		return list;
	}

	public void setList(List<KeyValue> list) {
		this.list = list;
	}
	
	public static void main(String[] args){
		
		List<KeyValue> list = new ArrayList<KeyValue>();
		list.add(new KeyValue("AAA", "aaa"));
		list.add(new KeyValue("AAAA", "bbb"));
		
		Config config = new Config();
		config.getList().addAll(list);
		
		String json = JSON.toJson(config);
		System.out.println(json);
		
		Config myList = JSON.fromJson(json, Config.class);
		
		System.out.println(JSON.toJson(myList));
		
	}
}
