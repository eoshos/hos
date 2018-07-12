package io.eoshos.core.api.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserAccountTransDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;

	@ApiModelProperty(value="查询起始时间", name="gmtStart", example="", required=false, hidden=false)
	private Long gmtStart;
	
	@ApiModelProperty(value="查询终止时间", name="gmtEnd", example="", required=false, hidden=false)
	private Long gmtEnd;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGmtStart() {
		return gmtStart;
	}

	public void setGmtStart(Long gmtStart) {
		this.gmtStart = gmtStart;
	}

	public Long getGmtEnd() {
		return gmtEnd;
	}

	public void setGmtEnd(Long gmtEnd) {
		this.gmtEnd = gmtEnd;
	}

}