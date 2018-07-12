package io.eoshos.core.api.bean.bo;

import io.eoshos.core.api.bean.po.HosUserAccountTrans;

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
