package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserAccountDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="邀请者id", name="userId", example="", required=true, hidden=false)
    private Long userId;
	
	@ApiModelProperty(value="被邀请者id", name="userId", example="", required=true, hidden=false)
    private Long inviteUserId;	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

}