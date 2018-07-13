package io.eoshos.console.simple.bean.bo;

import io.eoshos.console.simple.bean.po.HosUserAccount;

public class HosUserAccountBo {
	
	private HosUserAccount hosUserAccount;
	
	@Override
	public String toString() {
		return "HosUserAccountBo [HosUserAccount=" + hosUserAccount + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public HosUserAccount getHosUserAccount() {
		return hosUserAccount;
	}

	public void setHosUserAccount(HosUserAccount HosUserAccount) {
		this.hosUserAccount = HosUserAccount;
	}

}
