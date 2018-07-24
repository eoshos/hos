package io.eoshos.console.simple.bean.vo;

import com.chuangke18.framework.api.bean.BaseBeanVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosTradeListVo extends BaseBeanVo{

	private static final long serialVersionUID = -85876819314747530L;

	@ApiModelProperty(value="客户ID", name="userId", example="", required=true, hidden=false)
	private Long userId;
	
	@ApiModelProperty(value="客户账户ID", name="userAccountId", example="", required=false, hidden=false)
	private Long userAccountId;
	
	@ApiModelProperty(value="TxHash", name="txHash", example="", required=false, hidden=false)
	private String txHash;
	
	@ApiModelProperty(value="区块ID", name="blockId", example="", required=false, hidden=false)
	private String blockId;
	
	@ApiModelProperty(value="交易时间", name="tradeTime", example="", required=false, hidden=false)
	private Long tradeTime;
	
	@ApiModelProperty(value="交易时间", name="tradeTimeStr", example="", required=false, hidden=false)
	private String tradeTimeStr;
	
	@ApiModelProperty(value="开始地址", name="addrFrom", example="", required=false, hidden=false)
	private String addrFrom;
	
	@ApiModelProperty(value="结束地址", name="addrTo", example="", required=false, hidden=false)
	private String addrTo;
	
	@ApiModelProperty(value="交易币个数", name="coinNumber", example="", required=false, hidden=false)
	private Double coinNumber;
	
	@ApiModelProperty(value="标签", name="tag", example="", required=false, hidden=false)
	private String tag;
	
	@ApiModelProperty(value="备注", name="notes", example="", required=false, hidden=false)
	private String notes;

	@Override
	public String toString() {
		return "HosTradeListVo [userId=" + userId + ", userAccountId=" + userAccountId + ", txHash=" + txHash
				+ ", blockId=" + blockId + ", tradeTime=" + tradeTime + ", tradeTimeStr=" + tradeTimeStr + ", addrFrom="
				+ addrFrom + ", addrTo=" + addrTo + ", coinNumber=" + coinNumber + ", tag=" + tag + ", notes=" + notes
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

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public Long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeTimeStr() {
		return tradeTimeStr;
	}

	public void setTradeTimeStr(String tradeTimeStr) {
		this.tradeTimeStr = tradeTimeStr;
	}

	public String getAddrFrom() {
		return addrFrom;
	}

	public void setAddrFrom(String addrFrom) {
		this.addrFrom = addrFrom;
	}

	public String getAddrTo() {
		return addrTo;
	}

	public void setAddrTo(String addrTo) {
		this.addrTo = addrTo;
	}

	public Double getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Double coinNumber) {
		this.coinNumber = coinNumber;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
