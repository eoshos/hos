package io.eoshos.console.simple.util;

public class ExceptionNullUpdate extends Exception{
	

	private static final long serialVersionUID = 5822605309880827069L;
	
	public ExceptionNullUpdate()
	  {

	  }
	  public ExceptionNullUpdate(String s)
	  {
	        super("数据零更新:" + s);
	  }
}
