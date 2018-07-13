package io.eoshos.console.simple.bean.dto;

import com.chuangke18.framework.api.bean.BaseBeanDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class HosUserTaskAttachmentDto extends BaseBeanDto{

	private static final long serialVersionUID = 1408930574913902908L;

	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;
	
	@ApiModelProperty(value="文件全称", name="fullFileName", example="", required=true, hidden=false)
    private String fullFileName;	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}


}