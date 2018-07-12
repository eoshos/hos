package io.eoshos.console.simple.bean.po;

import com.chuangke18.framework.api.bean.BaseBeanPo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserAccountTrans extends BaseBeanPo{

	private static final long serialVersionUID = 3408930574913902908L;

	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;
	
	@ApiModelProperty(value="D-借方(资金减少) C-贷方(资金增加)", name="dcFlag", example="", required=true, hidden=false)
    private String dcFlag;

	@ApiModelProperty(value="1-活动增加 2-", name="transType", example="", required=true, hidden=false)
    private String transType;

	@ApiModelProperty(value="对方UID,活动增加时other_user_id=1", name="otherUserId", example="", required=true, hidden=false)
    private Long otherUserId;

	@ApiModelProperty(value="交易币个数", name="coinNumber", example="", required=true, hidden=false)
    private Double coinNumber;

	@ApiModelProperty(value="交易后余额", name="coinBal", example="", required=false, hidden=false)
    private Double coinBal;

	@ApiModelProperty(value="状态：1-生效，0-未生效", name="stat", example="", required=true, hidden=false)
    private String stat;

	@Override
	public String toString() {
		return "HosUserAccountTrans [userId=" + userId + ", dcFlag=" + dcFlag + ", transType=" + transType
				+ ", otherUserId=" + otherUserId + ", coinNumber=" + coinNumber + ", coinBal=" + coinBal + ", stat="
				+ stat + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public Long getOtherUserId() {
		return otherUserId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public void setOtherUserId(Long otherUserId) {
		this.otherUserId = otherUserId;
	}

	public Double getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Double coinNumber) {
		this.coinNumber = coinNumber;
	}

	public Double getCoinBal() {
		return coinBal;
	}

	public void setCoinBal(Double coinBal) {
		this.coinBal = coinBal;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}		

	}