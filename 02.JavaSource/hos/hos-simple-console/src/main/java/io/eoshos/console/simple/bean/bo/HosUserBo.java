package io.eoshos.console.simple.bean.bo;

import io.eoshos.console.simple.bean.po.HosUser;
import io.eoshos.console.simple.bean.po.HosUserInvite;

public class HosUserBo {
	
	private HosUser hosUser;
	
	private HosUserInvite hosUserInvite;
	
	private String inviteCode;
	
	@Override
	public String toString() {
		return "HosUserBo [hosUser=" + hosUser + ", hosUserInvite=" + hosUserInvite + ", inviteCode=" + inviteCode
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public HosUser getHosUser() {
		return hosUser;
	}

	public void setHosUser(HosUser HosUser) {
		this.hosUser = HosUser;
	}

	public HosUserInvite getHosUserInvite() {
		return hosUserInvite;
	}

	public void setHosUserInvite(HosUserInvite hosUserInvite) {
		this.hosUserInvite = hosUserInvite;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

}
