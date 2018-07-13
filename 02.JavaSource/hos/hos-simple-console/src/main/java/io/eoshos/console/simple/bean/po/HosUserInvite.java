package io.eoshos.console.simple.bean.po;

import com.chuangke18.framework.api.bean.BaseBeanPo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserInvite extends BaseBeanPo{

	private static final long serialVersionUID = 1408930574913902908L;
	
	@ApiModelProperty(value="邀请层级 1-一级邀请人 2-二级邀请人", name="inviteLevel", example="", required=true, hidden=false)
    private Long inviteLevel;
	
	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;

	@ApiModelProperty(value="邀请人手机号", name="phone", example="", required=true, hidden=false)
    private String phone;

	@ApiModelProperty(value="被邀请人UID", name="inviteUserId", example="", required=true, hidden=false)
    private Long inviteUserId;

	@ApiModelProperty(value="被邀请人手机号", name="invitePhone", example="", required=true, hidden=false)
    private String invitePhone;
	
	@ApiModelProperty(value="奖励币数", name="coinReward", example="", required=true, hidden=false)
    private Double coinReward;		
	
	@ApiModelProperty(value="状态：1-生效，0-未生效", name="stat", example="", required=true, hidden=false)
    private String stat;		
	
	@ApiModelProperty(value="用户IP", name="operIp", example="", required=false, hidden=false)
    private String operIp;

	@Override
	public String toString() {
		return "HosUserInvite [inviteLevel=" + inviteLevel + ", userId=" + userId + ", phone=" + phone
				+ ", inviteUserId=" + inviteUserId + ", invitePhone=" + invitePhone + ", stat=" + stat + ", operId="
				+ operIp + "]";
	}

	public Long getInviteLevel() {
		return inviteLevel;
	}

	public void setInviteLevel(Long inviteLevel) {
		this.inviteLevel = inviteLevel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getInvitePhone() {
		return invitePhone;
	}

	public void setInvitePhone(String invitePhone) {
		this.invitePhone = invitePhone;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getOperIp() {
		return operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	public Double getCoinReward() {
		return coinReward;
	}

	public void setCoinReward(Double coinReward) {
		this.coinReward = coinReward;
	}

}