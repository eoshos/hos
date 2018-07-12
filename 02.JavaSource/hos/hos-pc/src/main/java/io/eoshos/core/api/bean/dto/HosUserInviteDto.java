package io.eoshos.core.api.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserInviteDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}