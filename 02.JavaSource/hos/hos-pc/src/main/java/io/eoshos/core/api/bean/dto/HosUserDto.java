package io.eoshos.core.api.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * 用于登录
 */
@ApiModel
public class HosUserDto {

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="手机号", name="phone", example="", required=true, hidden=false)
    private String phone;

	@ApiModelProperty(value="MD5密码", name="password", example="", required=true, hidden=false)
    private String password;
	
	@ApiModelProperty(value="验证码", name="valicode", example="", required=true, hidden=false)
    private String valicode;	
	
	@ApiModelProperty(value="邀请码", name="inviteCode", example="", required=true, hidden=false)
    private String inviteCode;	
	
	@ApiModelProperty(value="证件号", name="cardId", example="", required=false, hidden=false)
    private String cardId;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValicode() {
		return valicode;
	}

	public void setValicode(String valicode) {
		this.valicode = valicode;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}