package io.eoshos.console.simple.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserAccountTransBo;
import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.dto.HosUserAccountTransDto;
import io.eoshos.console.simple.bean.po.HosUserAccount;
import io.eoshos.console.simple.bean.po.HosUserAccountTrans;
import io.eoshos.console.simple.bean.po.HosUserTaskAttachment;
import io.eoshos.console.simple.bean.vo.HosUserAccountTransVo;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;
import io.eoshos.console.simple.dao.mapper.HosUserAccountMapper;
import io.eoshos.console.simple.dao.mapper.HosUserAccountTransMapper;
import io.eoshos.console.simple.dao.mapper.HosUserTaskAttachmentMapper;
import io.eoshos.console.simple.service.IHosUserAccountTransService;
import io.eoshos.console.simple.util.DateUtil;
import io.eoshos.console.simple.util.ExceptionNullUpdate;

@Service("hosUserAccountTransService")
public class HosUserAccountTransServiceImpl implements IHosUserAccountTransService{
	
	private static final Logger logger = LogManager.getLogger(HosUserAccountTransServiceImpl.class);
	
	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;
	@Autowired
	private HosUserTaskAttachmentMapper hosUserTaskAttachmentMapper;	

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

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CKResponse trade(HosUserAccountTransDto hosUserAccountTransDto) throws ExceptionNullUpdate {
		CKResponse cKResponse = new CKResponse();
		Double tradeCoin = hosUserAccountTransDto.getCoinNumber();
		//超级账户
		HosUserAccountDto hosSuperUserAccountDto = new HosUserAccountDto();
		hosSuperUserAccountDto.setUserId(1L);
		HosUserAccountVo hosSuperUserAccountVo = null; 
		try {
			hosSuperUserAccountVo = hosUserAccountMapper.getObject(hosSuperUserAccountDto);
		} catch (SQLException e) {
			logger.error("查询超级帐户失败:" + e.getMessage());
			cKResponse.setSuccess(false);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询超级帐户失败:" + e.getMessage());
			return cKResponse;
		}
	
		
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		hosUserAccountDto.setUserId(hosUserAccountTransDto.getUserId());
		HosUserAccountVo hosUserAccountVo = null; 
		try {
			hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
		} catch (SQLException e) {
			logger.error("查询帐户失败:" + e.getMessage());
			cKResponse.setSuccess(false);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询帐户失败:" + e.getMessage());
			return cKResponse;
		}
		Double coinTotal = hosUserAccountVo.getCoinTotal();//当前总币数
		Double coinAvailable = hosUserAccountVo.getCoinAvailable();//当前可用币数
		Double coinFreeze = hosUserAccountVo.getCoinFreeze();//当前冻结币数
		
		HosUserAccount hosUserAccount1 = new HosUserAccount();
		hosUserAccount1.setId(1L);
		hosUserAccount1.setUserId(hosUserAccountTransDto.getOtherUserId());//此值为1L
		hosUserAccount1.setCoinTotal(hosSuperUserAccountVo.getCoinTotal() - tradeCoin);
		hosUserAccount1.setCoinAvailable(hosSuperUserAccountVo.getCoinAvailable() - tradeCoin);
		hosUserAccount1.setCoinFreeze(hosSuperUserAccountVo.getCoinTotal() - hosSuperUserAccountVo.getCoinAvailable());
		hosUserAccount1.setGmtModified(DateUtil.getTime(hosSuperUserAccountVo.getGmtModifiedStr()));
		HosUserAccount hosUserAccount2 = new HosUserAccount();
		hosUserAccount2.setId(hosUserAccountVo.getId());
		hosUserAccount2.setUserId(hosUserAccountTransDto.getUserId());
		hosUserAccount2.setCoinTotal(coinTotal + tradeCoin);
		hosUserAccount2.setCoinAvailable(coinAvailable + tradeCoin);
		hosUserAccount2.setCoinFreeze(coinFreeze);
		hosUserAccount2.setGmtModified(DateUtil.getTime(hosUserAccountVo.getGmtModifiedStr()));
		
		HosUserAccountTrans hosUserAccountTrans = new HosUserAccountTrans();
		hosUserAccountTrans.setUserId(hosUserAccountTransDto.getUserId());
		hosUserAccountTrans.setOtherUserId(hosUserAccountTransDto.getOtherUserId());
		hosUserAccountTrans.setDcFlag(hosUserAccountTransDto.getDcFlag());
		hosUserAccountTrans.setTransType("1");
		hosUserAccountTrans.setCoinNumber(hosUserAccountTransDto.getCoinNumber());
		hosUserAccountTrans.setCoinBal(coinTotal + hosUserAccountTransDto.getCoinNumber());
		hosUserAccountTrans.setStat("1");
		hosUserAccountTrans.setNotes(hosUserAccountTransDto.getNotes());
		HosUserTaskAttachment hosUserTaskAttachment = new HosUserTaskAttachment();
		hosUserTaskAttachment.setUserId(hosUserAccountTransDto.getUserId());
		
		int i = 0;
		try {
			i = hosUserAccountMapper.updateCoin(hosUserAccount1);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUserAccount1.toString());
			}
			i = hosUserAccountMapper.updateCoin(hosUserAccount2);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUserAccount2.toString());
			}
			i = hosUserAccountTransMapper.insert(hosUserAccountTrans);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUserAccountTrans.toString());
			}
			i = hosUserTaskAttachmentMapper.updateTradeId(hosUserTaskAttachment);//允许没有图片
		} catch (SQLException e) {
			logger.error("交易帐户失败:" + e.getMessage());
			cKResponse.setSuccess(false);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("交易帐户失败:" + e.getMessage());
		}
		//boolean bSuccess = i >= 3;
		//String errorCode = i >= 3 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
		//String errorMsg  = i >= 3 ? ConstantApi.ERROR_MSG.SUCCESS : "交易失败,请重试.";
		cKResponse.setSuccess(true);
		cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects1(HosUserAccountTransDto hosUserAccountTransDto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserAccountTransVo> hosUserAccountTransVoPage = new Page<HosUserAccountTransVo>();
		//查询用户账户交易信息列表
		List<HosUserAccountTransVo> hosUserAccountTransList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosUserAccountTransList = hosUserAccountTransMapper.listPagingObjects1(hosUserAccountTransDto);
			//查询总数
			total = hosUserAccountTransMapper.countObjects1(hosUserAccountTransDto);
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
