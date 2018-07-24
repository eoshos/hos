package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosTradeListDto extends BaseBeanDto{

	private static final long serialVersionUID = -7556104415201191154L;

	@ApiModelProperty(value="客户ID", name="userId", example="", required=true, hidden=false)
	private Long userId;
	
	@ApiModelProperty(value="客户账户ID", name="userAccountId", example="", required=false, hidden=false)
	private Long userAccountId;

	@Override
	public String toString() {
		return "HosTradeListDto [userId=" + userId + ", userAccountId=" + userAccountId + "]";
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
	
}
