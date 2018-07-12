package io.eoshos.console.simple.bean.po;

import com.chuangke18.framework.api.bean.BaseBeanPo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserAccount extends BaseBeanPo{

	private static final long serialVersionUID = 2408930574913902908L;
	
	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;

	@ApiModelProperty(value="币总数", name="coinTotal", example="", required=false, hidden=false)
    private Double coinTotal;

	@ApiModelProperty(value="可用币数", name="coinAvailable", example="", required=false, hidden=false)
    private Double coinAvailable;

	@ApiModelProperty(value="冻结币数", name="coinFreeze", example="", required=false, hidden=false)
    private Double coinFreeze;
	
	@ApiModelProperty(value="状态：1-生效，0-未生效", name="stat", example="", required=true, hidden=false)
    private String stat;

	@Override
	public String toString() {
		return "HosUserAccount [userId=" + userId + ", coinTotal=" + coinTotal + ", coinAvailable=" + coinAvailable
				+ ", coinFreeze=" + coinFreeze + ", stat=" + stat + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Double getCoinFreeze() {
		return coinFreeze;
	}

	public void setCoinFreeze(Double coinFreeze) {
		this.coinFreeze = coinFreeze;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}		

}