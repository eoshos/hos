package io.eoshos.console.simple.bean.vo;

import com.chuangke18.framework.api.bean.BaseBeanVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosTradeCoinToChainVo extends BaseBeanVo{

	private static final long serialVersionUID = 9109526514360562394L;

	@ApiModelProperty(value="客户ID", name="userId", example="", required=true, hidden=false)
	private Long userId;
	
	@ApiModelProperty(value="客户账户ID", name="userAccountId", example="", required=false, hidden=false)
	private Long userAccountId;
	
	@ApiModelProperty(value="手机号码", name="phone", example="", required=false, hidden=false)
	private String phone;
	
	@ApiModelProperty(value="1提币到个人帐户2提币到交易所账户", name="accountType", example="", required=false, hidden=false)
	private String accountType;
	
	@ApiModelProperty(value="个人EOS帐户或交易所账户", name="eosAccount", example="", required=false, hidden=false)
	private String eosAccount;
	
	@ApiModelProperty(value="个人在交易所账户,account_type=1时为-1", name="exchangeAccount", example="", required=false, hidden=false)
	private String exchangeAccount;
	
	@ApiModelProperty(value="交易币个数", name="coinNumber", example="", required=true, hidden=false)
	private Double coinNumber;
	
	@ApiModelProperty(value="1申请8拒绝9批准", name="stat", example="", required=false, hidden=false)
	private String stat;
	
	@ApiModelProperty(value="申请时间", name="applyTime", example="", required=false, hidden=false)
	private Long applyTime;
	
	@ApiModelProperty(value="申请时间", name="applyTimeStr", example="", required=false, hidden=false)
	private String applyTimeStr;
	
	@ApiModelProperty(value="审批时间", name="auditTime", example="", required=false, hidden=false)
	private Long auditTime;
	
	@ApiModelProperty(value="审批时间", name="auditTimeStr", example="", required=false, hidden=false)
	private String auditTimeStr;
	
	@ApiModelProperty(value="审批结果", name="auditDesc", example="", required=false, hidden=false)
	private String auditDesc;
	
	@ApiModelProperty(value="备注", name="notes", example="", required=false, hidden=false)
	private String notes;
	
	@ApiModelProperty(value="币总数", name="coinTotal", example="", required=false, hidden=false)
    private Double coinTotal;

	@ApiModelProperty(value="可用币数", name="coinAvailable", example="", required=false, hidden=false)
    private Double coinAvailable;

	@Override
	public String toString() {
		return "HosTradeCoinToChainVo [userId=" + userId + ", userAccountId=" + userAccountId + ", phone=" + phone
				+ ", accountType=" + accountType + ", eosAccount=" + eosAccount + ", exchangeAccount=" + exchangeAccount
				+ ", coinNumber=" + coinNumber + ", stat=" + stat + ", applyTime=" + applyTime + ", applyTimeStr="
				+ applyTimeStr + ", auditTime=" + auditTime + ", auditTimeStr=" + auditTimeStr + ", auditDesc="
				+ auditDesc + ", notes=" + notes + ", coinTotal=" + coinTotal + ", coinAvailable=" + coinAvailable
				+ "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEosAccount() {
		return eosAccount;
	}

	public void setEosAccount(String eosAccount) {
		this.eosAccount = eosAccount;
	}

	public String getExchangeAccount() {
		return exchangeAccount;
	}

	public void setExchangeAccount(String exchangeAccount) {
		this.exchangeAccount = exchangeAccount;
	}

	public Double getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Double coinNumber) {
		this.coinNumber = coinNumber;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public Long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Long applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public Long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Long auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditTimeStr() {
		return auditTimeStr;
	}

	public void setAuditTimeStr(String auditTimeStr) {
		this.auditTimeStr = auditTimeStr;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getCoinTotal() {
		return coinTotal;
	}

	public void setCoinTotal(Double coinTotal) {
		this.coinTotal = coinTotal;
	}

	public Double getCoinAvailable() {
		return coinAvailable;
	}

	public void setCoinAvailable(Double coinAvailable) {
		this.coinAvailable = coinAvailable;
	}

}
