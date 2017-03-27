package com.ccg.oms.common.data.document.solr;

import java.util.ArrayList;
import java.util.List;

public class Response {
	private int numFound;
	private int start;
	private List<Doc> docs = new ArrayList<Doc>();
}
