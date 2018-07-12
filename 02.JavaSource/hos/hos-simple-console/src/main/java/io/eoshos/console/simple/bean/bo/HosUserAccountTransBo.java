package io.eoshos.console.simple.bean.bo;

import io.eoshos.console.simple.bean.po.HosUserAccountTrans;

public class HosUserAccountTransBo {
	
	private HosUserAccountTrans hosUserAccountTrans;
	
	@Override
	public String toString() {
		return "HosUserAccountTransBo [HosUserAccountTrans=" + hosUserAccountTrans + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public HosUserAccountTrans getHosUserAccountTrans() {
		return hosUserAccountTrans;
	}

	public void setHosUserAccountTrans(HosUserAccountTrans HosUserAccountTrans) {
		this.hosUserAccountTrans = HosUserAccountTrans;
	}

}
