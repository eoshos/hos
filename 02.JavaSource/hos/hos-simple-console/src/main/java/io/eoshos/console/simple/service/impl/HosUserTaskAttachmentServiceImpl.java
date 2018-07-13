package io.eoshos.console.simple.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserTaskAttachmentBo;
import io.eoshos.console.simple.bean.dto.HosUserTaskAttachmentDto;
import io.eoshos.console.simple.bean.po.HosUserTaskAttachment;
import io.eoshos.console.simple.bean.vo.HosUserTaskAttachmentVo;
import io.eoshos.console.simple.dao.mapper.HosUserTaskAttachmentMapper;
import io.eoshos.console.simple.service.IHosUserTaskAttachmentService;

@Service("hosUserTaskAttachmentService")
public class HosUserTaskAttachmentServiceImpl implements IHosUserTaskAttachmentService{
	
	private static final Logger logger = LogManager.getLogger(HosUserTaskAttachmentServiceImpl.class);
	
	@Autowired
	private HosUserTaskAttachmentMapper HosUserTaskAttachmentMapper;

	@Override
	public CKResponse save(HosUserTaskAttachmentBo bo) {
		CKResponse cKResponse = new CKResponse();
		HosUserTaskAttachment hosUserTaskAttachment  = bo.getHosUserTaskAttachment();
		HosUserTaskAttachmentDto hosUserTaskAttachmentDto = new HosUserTaskAttachmentDto();
		hosUserTaskAttachmentDto.setFullFileName(hosUserTaskAttachment.getFullFileName());
		HosUserTaskAttachmentVo hosUserTaskAttachmentVo = null;
		try {
			hosUserTaskAttachmentVo = HosUserTaskAttachmentMapper.getObject(hosUserTaskAttachmentDto);
		} catch (SQLException e1) {
			logger.error("查询活动附件错误：" + e1.getMessage());
		}
		boolean bActionIsAdd = (null == hosUserTaskAttachmentVo);
		
		int i = 0;
		String errorCode = "";
		String errorMsg = "";	
		boolean bSuccess = true;
		try {
			i = bActionIsAdd ? HosUserTaskAttachmentMapper.insert(hosUserTaskAttachment) : 
				HosUserTaskAttachmentMapper.update(hosUserTaskAttachment);
			// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
			errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
			errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
		} catch (SQLException e) {
			String str = "更新活动附件表错误:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
			bSuccess = false;
		}
		cKResponse.setSuccess(bSuccess);
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
	}

	@Override
	public CKResponse delete(HosUserTaskAttachmentBo bo) {
		//此功能不会有
		return null;
	}

	@Override
	public CKResponse getObject(HosUserTaskAttachmentDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosUserTaskAttachmentVo HosUserTaskAttachmentVo = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			HosUserTaskAttachmentVo = HosUserTaskAttachmentMapper.getObject(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = HosUserTaskAttachmentVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = HosUserTaskAttachmentVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户邀请信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(HosUserTaskAttachmentVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosUserTaskAttachmentDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosUserTaskAttachmentVo> HosUserTaskAttachmentVoList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			HosUserTaskAttachmentVoList = HosUserTaskAttachmentMapper.listAllObjects(dto);
			
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(HosUserTaskAttachmentVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(HosUserTaskAttachmentVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户邀请信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(HosUserTaskAttachmentVoList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosUserTaskAttachmentDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserTaskAttachmentVo> HosUserTaskAttachmentVoPage = new Page<HosUserTaskAttachmentVo>();
		//查询用户账户交易信息列表
		List<HosUserTaskAttachmentVo> HosUserTaskAttachmentVoList = null;
		int total = 0;
		String errorCode = "";
		String errorMsg = "";
		try {
			HosUserTaskAttachmentVoList = HosUserTaskAttachmentMapper.listPagingObjects(dto);
			//查询总数
			total = HosUserTaskAttachmentMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(HosUserTaskAttachmentVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(HosUserTaskAttachmentVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			//String str = HosUserTaskAttachmentVoList == null ? "查询用户邀请信息异常:" : "查询用户邀请信息总数异常:";
			logger.error(e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		HosUserTaskAttachmentVoPage.setTotal(total);
		HosUserTaskAttachmentVoPage.setRows(HosUserTaskAttachmentVoList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(HosUserTaskAttachmentVoPage);
		return cKResponse;
	}
	
}
