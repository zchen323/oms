package com.ccg.security.jaas;

import java.util.ArrayList;
import java.util.List;

public class UserRolesResponse {
	private String status;
	private List<String> result = new ArrayList<String>();
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
}
