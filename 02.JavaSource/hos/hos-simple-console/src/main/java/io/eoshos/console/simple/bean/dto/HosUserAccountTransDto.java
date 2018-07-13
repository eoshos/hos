package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserAccountTransDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;
	
	@ApiModelProperty(value="对方用户id", name="otherUserId", example="", required=true, hidden=false)
    private Long otherUserId;
	
	@ApiModelProperty(value="D-借方(资金减少) C-贷方(资金增加)", name="dcFlag", example="", required=true, hidden=false)
    private String dcFlag;		
	
	@ApiModelProperty(value="交易币个数", name="coinNumber", example="", required=true, hidden=false)
    private Double coinNumber;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOtherUserId() {
		return otherUserId;
	}

	public void setOtherUserId(Long otherUserId) {
		this.otherUserId = otherUserId;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public Double getCoinNumber() {
		return coinNumber;
	}

	public void setCoinNumber(Double coinNumber) {
		this.coinNumber = coinNumber;
	}

}