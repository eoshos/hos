package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosTradeCoinToChainDto extends BaseBeanDto{

	private static final long serialVersionUID = 2644862854242312805L;
	
	@ApiModelProperty(value="客户ID", name="userId", example="", required=true, hidden=false)
	private Long userId;
	
	@ApiModelProperty(value="客户账户ID", name="userAccountId", example="", required=false, hidden=false)
	private Long userAccountId;
	
	@ApiModelProperty(value="手机号码", name="phone", example="", required=false, hidden=false)
	private String phone;	
	
	@ApiModelProperty(value="状态", name="stat", example="", required=false, hidden=false)
	private String stat;	
	
	@ApiModelProperty(value="申请开始时间", name="beginDate", example="", required=false, hidden=false)
    private Long beginDate;	
	
	@ApiModelProperty(value="申请结束时间", name="endDate", example="", required=false, hidden=false)
    private Long endDate;		

	@Override
	public String toString() {
		return "HosTradeCoinToChainDto [userId=" + userId + ", userAccountId=" + userAccountId + ", toString()="
				+ super.toString() + ", getOffset()=" + getOffset() + ", getLimit()=" + getLimit() + ", getId()="
				+ getId() + ", getNotes()=" + getNotes() + ", getJwt()=" + getJwt() + ", getChannel()=" + getChannel()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
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

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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
