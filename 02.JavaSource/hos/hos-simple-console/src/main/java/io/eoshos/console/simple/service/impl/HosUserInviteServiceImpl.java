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

import io.eoshos.console.simple.bean.bo.HosUserInviteBo;
import io.eoshos.console.simple.bean.dto.HosUserInviteDto;
import io.eoshos.console.simple.bean.vo.HosUserInviteVo;
import io.eoshos.console.simple.dao.mapper.HosUserInviteMapper;
import io.eoshos.console.simple.service.IHosUserInviteService;

@Service("hosUserInviteService")
public class HosUserInviteServiceImpl implements IHosUserInviteService{
	
	private static final Logger logger = LogManager.getLogger(HosUserInviteServiceImpl.class);
	
	@Autowired
	private HosUserInviteMapper hosUserInviteMapper;

	@Override
	public CKResponse save(HosUserInviteBo bo) {
		// TODO Auto-generated method stub
		//保存功能建议放在hosUser服务中
		return null;
	}

	@Override
	public CKResponse delete(HosUserInviteBo bo) {
		// TODO Auto-generated method stub
		//此功能不会有
		return null;
	}

	@Override
	public CKResponse getObject(HosUserInviteDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosUserInviteVo hosUserInviteVo = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserInviteVo = hosUserInviteMapper.getObject(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosUserInviteVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosUserInviteVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户邀请信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosUserInviteVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosUserInviteDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosUserInviteVo> hosUserInviteVoList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserInviteVoList = hosUserInviteMapper.listAllObjects(dto);
			
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserInviteVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserInviteVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户邀请信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosUserInviteVoList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosUserInviteDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserInviteVo> hosUserInviteVoPage = new Page<HosUserInviteVo>();
		//查询用户账户交易信息列表
		List<HosUserInviteVo> hosUserInviteVoList = null;
		int total = 0;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserInviteVoList = hosUserInviteMapper.listPagingObjects(dto);
			//查询总数
			total = hosUserInviteMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserInviteVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserInviteVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosUserInviteVoList == null ? "查询用户邀请信息异常:" : "查询用户邀请信息总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		hosUserInviteVoPage.setTotal(total);
		hosUserInviteVoPage.setRows(hosUserInviteVoList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosUserInviteVoPage);
		return cKResponse;
	}
	
}
