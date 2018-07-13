package io.eoshos.console.simple.bean;


public class ExceptionCommonBean {
	private String errLile;//异常文件
	private String errLine;//异常所在行数
	private String errMethod;//异常所在方法
	public String getErrLile() {
		return errLile;
	}
	public void setErrLile(String errLile) {
		this.errLile = errLile;
	}

	public String getErrLine() {
		return errLine;
	}

	public void setErrLine(String errLine) {
		this.errLine = errLine;
	}

	public String getErrMethod() {
		return errMethod;
	}

	public void setErrMethod(String errMethod) {
		this.errMethod = errMethod;
	}

}
