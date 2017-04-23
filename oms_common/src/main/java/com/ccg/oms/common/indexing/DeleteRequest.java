package com.ccg.oms.common.indexing;

import java.util.ArrayList;
import java.util.List;

public class DeleteRequest {
	List<String> delete = new ArrayList<String>();

	public List<String> getDelete() {
		return delete;
	}

	public void setDelete(List<String> delete) {
		this.delete = delete;
	}
	
}
