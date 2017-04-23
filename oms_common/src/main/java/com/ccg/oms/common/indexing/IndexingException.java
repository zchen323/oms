package com.ccg.oms.common.indexing;

public class IndexingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IndexingException(){
		super();
	}
	
	public IndexingException(String errMsg){
		super(errMsg);
	}
	
	public IndexingException(String errMsg, Exception e){
		super(errMsg, e);
	}

}
