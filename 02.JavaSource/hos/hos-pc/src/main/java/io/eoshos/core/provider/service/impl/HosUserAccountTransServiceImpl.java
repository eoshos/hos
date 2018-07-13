package io.eoshos.core.provider.service.impl;

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

import io.eoshos.core.api.bean.bo.HosUserAccountTransBo;
import io.eoshos.core.api.bean.dto.HosUserAccountTransDto;
import io.eoshos.core.api.bean.po.HosUserAccountTrans;
import io.eoshos.core.api.bean.vo.HosUserAccountTransVo;
import io.eoshos.core.api.service.IHosUserAccountTransService;
import io.eoshos.core.provider.dao.HosUserAccountTransMapper;

@Service("hosUserAccountTransService")
public class HosUserAccountTransServiceImpl implements IHosUserAccountTransService{
	
	private static final Logger logger = LogManager.getLogger(HosUserAccountTransServiceImpl.class);
	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;

	//TODO 暂时未使用
	@Override
	public CKResponse save(HosUserAccountTransBo bo) {
		HosUserAccountTrans hosUserAccountTrans = bo.getHosUserAccountTrans();
		CKResponse cKResponse = new CKResponse();
		
		String errorCode = "";
		String errorMsg = "";
		int i = 0;
		
		//保存用户账户交易信息，直接插入
		try {
			i = hosUserAccountTransMapper.insert(hosUserAccountTrans);
			// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
			errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
			errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
		} catch (SQLException e) {
			logger.error("增加用户账户交易信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		
		return cKResponse;
	}

	//TODO 暂时未使用
	@Override
	public CKResponse delete(HosUserAccountTransBo bo) {
		CKResponse cKResponse = new CKResponse();
		HosUserAccountTrans hosUserAccountTrans = bo.getHosUserAccountTrans();
		HosUserAccountTransDto hosUserAccountTransDto = new HosUserAccountTransDto();
		
		hosUserAccountTransDto.setUserId(hosUserAccountTrans.getUserId());
		
		int total = 0;
		int i = 0;
		String errorCode = ConstantApi.ERROR_CODE.ERROR;
		String errorMsg = ConstantApi.ERROR_MSG.ERROR;
		
		try {
			//先查询再删除
			total = hosUserAccountTransMapper.countObjects(hosUserAccountTransDto);
			if(total > 0){
				i = hosUserAccountTransMapper.delete(hosUserAccountTransDto);
				// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
				errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
				errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
			}
		} catch (SQLException e) {
			String str = total > 0 ? "删除用户帐户交易信息异常:" : "查询用户帐户交易信息总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		return cKResponse;
	}

	@Override
	public CKResponse getObject(HosUserAccountTransDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosUserAccountTransVo hosUserAccountTransVo = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserAccountTransVo = hosUserAccountTransMapper.getObject(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosUserAccountTransVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosUserAccountTransVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户交易信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosUserAccountTransVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosUserAccountTransDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosUserAccountTransVo> hosUserAccountTransVoList = null;
		String errorCode = "";
		String errorMsg = "";
		
		try {
			hosUserAccountTransVoList = hosUserAccountTransMapper.listAllObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserAccountTransVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserAccountTransVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户交易信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosUserAccountTransVoList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosUserAccountTransDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserAccountTransVo> hosUserAccountTransVoPage = new Page<HosUserAccountTransVo>();
		//查询用户账户交易信息列表
		List<HosUserAccountTransVo> hosUserAccountTransList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosUserAccountTransList = hosUserAccountTransMapper.listPagingObjects(dto);
			//查询总数
			total = hosUserAccountTransMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserAccountTransList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserAccountTransList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosUserAccountTransList == null ? "查询用户帐户交易异常:" : "查询用户帐户交易总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosUserAccountTransVoPage.setTotal(total);
		hosUserAccountTransVoPage.setRows(hosUserAccountTransList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosUserAccountTransVoPage);
		return cKResponse;
	}
	
}
