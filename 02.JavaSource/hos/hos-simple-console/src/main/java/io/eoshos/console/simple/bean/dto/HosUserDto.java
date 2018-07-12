package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
 * 用于登录
 */
@ApiModel
public class HosUserDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="手机号", name="phone", example="", required=true, hidden=false)
    private String phone;

	@ApiModelProperty(value="MD5密码", name="password", example="", required=true, hidden=false)
    private String password;
	
	@ApiModelProperty(value="验证码", name="valicode", example="", required=true, hidden=false)
    private String valicode;	
	
	@ApiModelProperty(value="邀请码", name="inviteCode", example="", required=true, hidden=false)
    private String inviteCode;	
	
	@ApiModelProperty(value="认证状态", name="realStat", example="", required=false, hidden=false)
    private String realStat;
	
	@ApiModelProperty(value="认证开始时间", name="beginDate", example="", required=false, hidden=false)
    private Long beginDate;	
	
	@ApiModelProperty(value="认证结束时间", name="endDate", example="", required=false, hidden=false)
    private Long endDate;	

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

	public String getRealStat() {
		return realStat;
	}

	public void setRealStat(String realStat) {
		this.realStat = realStat;
	}

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

}