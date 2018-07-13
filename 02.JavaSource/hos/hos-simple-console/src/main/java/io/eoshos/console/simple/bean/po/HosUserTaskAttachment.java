package io.eoshos.console.simple.bean.po;

import com.chuangke18.framework.api.bean.BaseBeanPo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class HosUserTaskAttachment extends BaseBeanPo{

	private static final long serialVersionUID = 1406930574913902908L;


	@ApiModelProperty(value="用户id", name="userId", example="", required=true, hidden=false)
    private Long userId;	
	
	@ApiModelProperty(value="交易id", name="transId", example="", required=true, hidden=false)
    private Long transId;		

	@ApiModelProperty(value="上传人ID", name="staffId", example="", required=false, hidden=false)
    private String staffId;

	@ApiModelProperty(value="上传人", name="staffName", example="", required=false, hidden=false)
    private String staffName;

	@ApiModelProperty(value="原始文件名称", name="fileName", example="", required=true, hidden=false)
    private String fileName;

	@ApiModelProperty(value="实际文件名称", name="fullFileName", example="", required=true, hidden=false)
    private String fullFileName;
	
	@ApiModelProperty(value="缩略文件名称", name="thumbnailFileName", example="", required=true, hidden=false)
    private String thumbnailFileName;

	@Override
	public String toString() {
		return "HosUserTaskAttachment [userId=" + userId + ", staffId=" + staffId + ", staffName=" + staffName
				+ ", fileName=" + fileName + ", fullFileName=" + fullFileName + ", thumbnailFileName="
				+ thumbnailFileName + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public String getThumbnailFileName() {
		return thumbnailFileName;
	}

	public void setThumbnailFileName(String thumbnailFileName) {
		this.thumbnailFileName = thumbnailFileName;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}	
	
}