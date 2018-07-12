package io.eoshos.core.api.bean.bo;

import io.eoshos.core.api.bean.po.HosUserAccount;

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
