package io.eoshos.console.simple.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserAccountBo;
import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.po.HosUser;
import io.eoshos.console.simple.bean.po.HosUserAccount;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;
import io.eoshos.console.simple.dao.mapper.HosUserAccountMapper;
import io.eoshos.console.simple.dao.mapper.HosUserAccountTransMapper;
import io.eoshos.console.simple.dao.mapper.HosUserInviteMapper;
import io.eoshos.console.simple.dao.mapper.HosUserMapper;
import io.eoshos.console.simple.service.IHosUserAccountService;

@Service("hosUserAccountService")
public class HosUserAccountServiceImpl implements IHosUserAccountService {

	private static final Logger logger = LogManager.getLogger(HosUserAccountServiceImpl.class);

	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;
	
	@Override
	public CKResponse save(HosUserAccountBo bo) {
		HosUserAccount hosUserAccount = bo.getHosUserAccount();
		CKResponse cKResponse = new CKResponse();
		String errorCode = "";
		String errorMsg = "";
		int i = 0;

		// 保存用户账户信息，如果id为空是插入，否则修改
		boolean bActionIsAdd = hosUserAccount.getUserId() == null;
		try {
			i = bActionIsAdd ? hosUserAccountMapper.insert(hosUserAccount)
					: hosUserAccountMapper.update(hosUserAccount);
		} catch (SQLException e) {
			String str = bActionIsAdd ? "增加用户帐户异常:" : "修改用户帐户异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}
		// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
		if (StringUtils.isBlank(errorCode)) {
			errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
			errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
	}

	@Override
	public CKResponse delete(HosUserAccountBo bo) {
		CKResponse cKResponse = new CKResponse();
		HosUserAccount hosUserAccount = bo.getHosUserAccount();
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();

		hosUserAccountDto.setUserId(hosUserAccount.getUserId());

		String errorCode = ConstantApi.ERROR_CODE.ERROR;
		String errorMsg = ConstantApi.ERROR_MSG.ERROR;

		int total = 0;
		int i = 0;

		try {
			// 先查询再删除
			total = hosUserAccountMapper.countObjects(hosUserAccountDto);
			if (total > 0) {
				i = hosUserAccountMapper.delete(hosUserAccountDto);

				// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
				errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
				errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
			}
		} catch (SQLException e) {
			String str = total > 0 ? "删除用户帐户异常:" : "查询用户帐户总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
	}

	@Override
	public CKResponse getObject(HosUserAccountDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosUserAccountVo hosUserAccountVo = null;
		String errorCode = "";
		String errorMsg = "";

		try {
			hosUserAccountVo = hosUserAccountMapper.getObject(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosUserAccountVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosUserAccountVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosUserAccountVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosUserAccountDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosUserAccountVo> hosUserAccountList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserAccountList = hosUserAccountMapper.listAllObjects(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserAccountList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserAccountList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosUserAccountList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosUserAccountDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserAccountVo> hosUserAccountPage = new Page<HosUserAccountVo>();
		// 查询用户账户信息列表
		List<HosUserAccountVo> hosUserAccountList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosUserAccountList = hosUserAccountMapper.listPagingObjects(dto);
			// 查询总数
			total = hosUserAccountMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserAccountList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserAccountList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosUserAccountList == null ? "查询用户帐户异常:" : "查询用户帐户总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosUserAccountPage.setTotal(total);
		hosUserAccountPage.setRows(hosUserAccountList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosUserAccountPage);
		return cKResponse;
	}

}
