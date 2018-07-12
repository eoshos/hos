package io.eoshos.core.api.bean.vo;

import com.chuangke18.framework.api.bean.BaseBeanVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserVo extends BaseBeanVo{

	private static final long serialVersionUID = 1408930574913902908L;


	@ApiModelProperty(value="用户类型0-系统内置用户 1-普通用户", name="userType", example="", required=true, hidden=false)
    private String userType;

	@ApiModelProperty(value="手机号", name="phone", example="", required=true, hidden=false)
    private String phone;

	@ApiModelProperty(value="密码", name="password", example="", required=true, hidden=false)
    private String password;
	
	@ApiModelProperty(value="用户名,默认与手机号相同", name="userName", example="", required=true, hidden=false)
    private String userName;	

	@ApiModelProperty(value="证件号", name="cardId", example="", required=false, hidden=false)
    private String cardId;
	
	@ApiModelProperty(value="真实姓名", name="realName", example="", required=false, hidden=false)
    private String realName;	
	
	@ApiModelProperty(value="正面照", name="picPath1", example="", required=false, hidden=false)
    private String picPath1;
	
	@ApiModelProperty(value="反面照", name="picPath2", example="", required=false, hidden=false)
    private String picPath2;	
	
	@ApiModelProperty(value="手持照", name="picPath3", example="", required=false, hidden=false)
    private String picPath3;
	
	@ApiModelProperty(value="状态：0-未认证，1-认证成功,2-认证失败", name="realStat", example="", required=true, hidden=false)
    private String realStat;
	
	@ApiModelProperty(value="认证时间", name="authTime", example="", required=false, hidden=false)
    private Long authTime;	
	
	@ApiModelProperty(value="申请认证时间", name="applyAuthTimeStr", example="", required=false, hidden=false)
    private String applyAuthTimeStr;
	
	@ApiModelProperty(value="EOS钱包地址", name="eosWallet", example="", required=true, hidden=false)
    private String eosWallet;
	
	@ApiModelProperty(value="邀请人uid", name="inviteUserId", example="", required=false, hidden=false)
    private String inviteUserId;	
	
	@ApiModelProperty(value="邀请码", name="inviteCode", example="", required=false, hidden=false)
    private String inviteCode;
	
	@ApiModelProperty(value="状态：1-生效，0-未生效", name="stat", example="", required=true, hidden=false)
    private String stat;		
	
	@ApiModelProperty(value="用户IP", name="operIp", example="", required=false, hidden=false)
    private String operIp;
	
	@Override
	public String toString() {
		return "HosUserVo [userType=" + userType + ", phone=" + phone + ", password=" + password + ", userName="
				+ userName + ", cardId=" + cardId + ", realName=" + realName + ", picPath1=" + picPath1 + ", picPath2="
				+ picPath2 + ", picPath3=" + picPath3 + ", realStat=" + realStat + ", authTime=" + authTime
				+ ", applyAuthTimeStr=" + applyAuthTimeStr + ", eosWallet=" + eosWallet + ", inviteUserId=" + inviteUserId
				+ ", inviteCode=" + inviteCode + ", stat=" + stat + ", operIp=" + operIp + "]";
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPicPath1() {
		return picPath1;
	}

	public void setPicPath1(String picPath1) {
		this.picPath1 = picPath1;
	}

	public String getPicPath2() {
		return picPath2;
	}

	public void setPicPath2(String picPath2) {
		this.picPath2 = picPath2;
	}

	public String getPicPath3() {
		return picPath3;
	}

	public void setPicPath3(String picPath3) {
		this.picPath3 = picPath3;
	}

	public String getRealStat() {
		return realStat;
	}

	public void setRealStat(String realStat) {
		this.realStat = realStat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Long getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Long authTime) {
		this.authTime = authTime;
	}

	public String getApplyAuthTimeStr() {
		return applyAuthTimeStr;
	}

	public void setApplyAuthTimeStr(String applyAuthTimeStr) {
		this.applyAuthTimeStr = applyAuthTimeStr;
	}

	public String getEosWallet() {
		return eosWallet;
	}

	public void setEosWallet(String eosWallet) {
		this.eosWallet = eosWallet;
	}

	public String getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(String inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getOperIp() {
		return operIp;
	}

	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStat() {
		return stat;
	}	

}