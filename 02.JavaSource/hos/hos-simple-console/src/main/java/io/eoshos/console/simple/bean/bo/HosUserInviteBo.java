package io.eoshos.console.simple.bean.bo;

import io.eoshos.console.simple.bean.po.HosUserInvite;

public class HosUserInviteBo {
	
	private HosUserInvite hosUserInvite;
	
	@Override
	public String toString() {
		return "HosUserInviteBo [HosUserInvite=" + hosUserInvite + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public HosUserInvite getHosUserInvite() {
		return hosUserInvite;
	}

	public void setHosUserInvite(HosUserInvite HosUserInvite) {
		this.hosUserInvite = HosUserInvite;
	}

}
