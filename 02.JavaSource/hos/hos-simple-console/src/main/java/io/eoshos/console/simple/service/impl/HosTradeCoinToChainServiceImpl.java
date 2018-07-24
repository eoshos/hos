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

import io.eoshos.console.simple.bean.bo.HosTradeCoinToChainBo;
import io.eoshos.console.simple.bean.dto.HosTradeCoinToChainDto;
import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.po.HosTradeCoinToChain;
import io.eoshos.console.simple.bean.po.HosTradeList;
import io.eoshos.console.simple.bean.po.HosUserAccount;
import io.eoshos.console.simple.bean.po.HosUserAccountTrans;
import io.eoshos.console.simple.bean.vo.HosTradeCoinToChainVo;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;
import io.eoshos.console.simple.dao.mapper.HosTradeCoinToChainMapper;
import io.eoshos.console.simple.dao.mapper.HosTradeListMapper;
import io.eoshos.console.simple.dao.mapper.HosUserAccountMapper;
import io.eoshos.console.simple.dao.mapper.HosUserAccountTransMapper;
import io.eoshos.console.simple.service.IHosTradeCoinToChainService;
import io.eoshos.console.simple.util.DateUtil;

@Service("hosTradeCoinToChainService")
public class HosTradeCoinToChainServiceImpl implements IHosTradeCoinToChainService {

	private static final Logger logger = LogManager.getLogger(HosTradeCoinToChainServiceImpl.class);

	@Autowired
	private HosTradeCoinToChainMapper hosTradeCoinToChainMapper;
	
	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;
	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;
	
	@Autowired
	private HosTradeListMapper hosTradeListMapper;

	@Override
	public CKResponse save(HosTradeCoinToChainBo bo) {
		/*HosTradeCoinToChain hosTradeCoinToChain = bo.getHosTradeCoinToChain();
		CKResponse cKResponse = new CKResponse();
		String errorCode = "";
		String errorMsg = "";
		int i = 0;

		// 保存用户账户信息，如果id为空是插入，否则修改
		boolean bActionIsAdd = hosTradeCoinToChain.getUserId() == null;
		try {
			i = bActionIsAdd ? hosTradeCoinToChainMapper.insert(hosTradeCoinToChain)
					: hosTradeCoinToChainMapper.update(hosTradeCoinToChain);
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

		return cKResponse;*/
		return null;
	}

	@Override
	public CKResponse delete(HosTradeCoinToChainBo bo) {
		/*CKResponse cKResponse = new CKResponse();
		HosTradeCoinToChain hosTradeCoinToChain = bo.getHosTradeCoinToChain();
		HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();

		hosTradeCoinToChainDto.setUserId(hosTradeCoinToChain.getUserId());

		String errorCode = ConstantApi.ERROR_CODE.ERROR;
		String errorMsg = ConstantApi.ERROR_MSG.ERROR;

		int total = 0;
		int i = 0;

		try {
			// 先查询再删除
			total = hosTradeCoinToChainMapper.countObjects(hosTradeCoinToChainDto);
			if (total > 0) {
				i = hosTradeCoinToChainMapper.delete(hosTradeCoinToChainDto);

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

		return cKResponse;*/
		return null;
	}

	@Override
	public CKResponse getObject(HosTradeCoinToChainDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosTradeCoinToChainVo hosTradeCoinToChainVo = null;
		String errorCode = "";
		String errorMsg = "";

		try {
			hosTradeCoinToChainVo = hosTradeCoinToChainMapper.getObject(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosTradeCoinToChainVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosTradeCoinToChainVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosTradeCoinToChainVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosTradeCoinToChainDto dto) {
		/*CKResponse cKResponse = new CKResponse();
		List<HosTradeCoinToChainVo> hosTradeCoinToChainList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosTradeCoinToChainList = hosTradeCoinToChainMapper.listAllObjects(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户帐户异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosTradeCoinToChainList);
		return cKResponse;*/
		return null;
	}

	@Override
	public CKResponse listPagingObjects(HosTradeCoinToChainDto hosTradeCoinToChainDto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosTradeCoinToChainVo> hosTradeCoinToChainPage = new Page<HosTradeCoinToChainVo>();
		// 查询用户账户信息列表
		List<HosTradeCoinToChainVo> hosTradeCoinToChainList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosTradeCoinToChainList = hosTradeCoinToChainMapper.listPagingObjects(hosTradeCoinToChainDto);
			// 查询总数
			total = hosTradeCoinToChainMapper.countObjects(hosTradeCoinToChainDto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosTradeCoinToChainList == null ? "查询上链异常:" : "查询上链总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosTradeCoinToChainPage.setTotal(total);
		hosTradeCoinToChainPage.setRows(hosTradeCoinToChainList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosTradeCoinToChainPage);
		return cKResponse;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CKResponse withdraw(HosTradeCoinToChain hosTradeCoinToChain, HosTradeList hosTradeList) throws SQLException {
		CKResponse cKResponse = new CKResponse();
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		HosUserAccount hosUserAccount = new HosUserAccount();
		int i = 0;
		int j = 0;
		
		//查询用户账户信息
		hosUserAccountDto.setUserId(hosTradeCoinToChain.getUserId());
		HosUserAccountVo hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
		
		//审批成功，更新账户信息(主张户和个人账户)和HOS币上链，新增交易记录和上链交易明细
		if("9".equals(hosTradeCoinToChain.getStat())){
			//更新HOS币上链信息
			logger.debug("上链成功，更新HOS币上链信息!");
			i += hosTradeCoinToChainMapper.update(hosTradeCoinToChain);
			j++;
			//新增上链交易明细
			logger.debug("上链成功，新增HOS币链上交易明细!");
			i += hosTradeListMapper.insert(hosTradeList);
			j++;
			
			//更新用户账户信息，扣除对应冻结金额
			logger.debug("更新用户账户信息");
			hosUserAccount.setId(hosUserAccountVo.getId());
			hosUserAccount.setUserId(hosTradeCoinToChain.getUserId());
			hosUserAccount.setGmtModified(DateUtil.getTime(hosUserAccountVo.getGmtModifiedStr()));
			hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal() - hosTradeCoinToChain.getCoinNumber());
			hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable());
			hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze() - hosTradeCoinToChain.getCoinNumber());
			i += hosUserAccountMapper.updateCoin(hosUserAccount);
			j++;
			//保存交易记录信息
			logger.debug("新增用户交易记录信息");
			HosUserAccountTrans hosUserAccountTrans = new HosUserAccountTrans();
			hosUserAccountTrans.setUserId(hosTradeCoinToChain.getUserId());
			hosUserAccountTrans.setDcFlag("D");
			hosUserAccountTrans.setTransType("1");
			hosUserAccountTrans.setOtherUserId(1L);
			hosUserAccountTrans.setCoinNumber(hosTradeCoinToChain.getCoinNumber());
			hosUserAccountTrans.setCoinBal(hosUserAccount.getCoinTotal());
			hosUserAccountTrans.setStat("1");
			i += hosUserAccountTransMapper.insert(hosUserAccountTrans);
			j++;
			
			logger.debug("查询总账户信息");
			hosUserAccountDto = new HosUserAccountDto();
			hosUserAccountDto.setId(1L);
			hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
			//更新总账户信息，增加对应币总数和可用数
			logger.debug("更新总账户信息");
			hosUserAccount.setId(hosUserAccountVo.getId());
			hosUserAccount.setUserId(1L);
			hosUserAccount.setGmtModified(DateUtil.getTime(hosUserAccountVo.getGmtModifiedStr()));
			hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal() + hosTradeCoinToChain.getCoinNumber());
			hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() + hosTradeCoinToChain.getCoinNumber());
			hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze());
			i += hosUserAccountMapper.updateCoin(hosUserAccount);
			j++;
		}else{
			//审批失败，更新账户信息和HOS币上链，新增解冻交易记录
			//更新HOS币上链信息
			logger.debug("上链失败，更新HOS币上链信息!");
			i += hosTradeCoinToChainMapper.update(hosTradeCoinToChain);
			j++;
			
			//更新用户账户信息，解冻对应冻结金额
			logger.debug("更新用户账户信息");
			hosUserAccount.setId(hosUserAccountVo.getId());
			hosUserAccount.setUserId(hosTradeCoinToChain.getUserId());
			hosUserAccount.setGmtModified(DateUtil.getTime(hosUserAccountVo.getGmtModifiedStr()));
			hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal());
			hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() + hosTradeCoinToChain.getCoinNumber());
			hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze() - hosTradeCoinToChain.getCoinNumber());
			i += hosUserAccountMapper.updateCoin(hosUserAccount);
			j++;
			//保存交易记录信息
			logger.debug("新增用户交易记录信息");
			HosUserAccountTrans hosUserAccountTrans = new HosUserAccountTrans();
			hosUserAccountTrans.setUserId(hosTradeCoinToChain.getUserId());
			hosUserAccountTrans.setDcFlag("N");
			hosUserAccountTrans.setTransType("9");
			hosUserAccountTrans.setOtherUserId(1L);
			hosUserAccountTrans.setCoinNumber(hosTradeCoinToChain.getCoinNumber());
			hosUserAccountTrans.setCoinBal(hosUserAccount.getCoinTotal());
			hosUserAccountTrans.setStat("1");
			hosUserAccountTrans.setNotes("2");
			i += hosUserAccountTransMapper.insert(hosUserAccountTrans);
			j++;
		}
		
		if (i != j) {
			logger.error("处理用户上链信息到数据库异常！");
			throw new SQLException("处理用户上链信息到数据库异常！");
		}
		
		cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);
		return cKResponse;
	}

	@Override
	public CKResponse listWithdrawApply(HosTradeCoinToChainDto hosTradeCoinToChainDto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosTradeCoinToChainVo> hosTradeCoinToChainPage = new Page<HosTradeCoinToChainVo>();
		// 查询用户账户信息列表
		List<HosTradeCoinToChainVo> hosTradeCoinToChainList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosTradeCoinToChainList = hosTradeCoinToChainMapper.listWithdrawApply(hosTradeCoinToChainDto);
			// 查询总数
			total = hosTradeCoinToChainMapper.countWithdrawApply(hosTradeCoinToChainDto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosTradeCoinToChainList == null ? "查询上链异常:" : "查询上链总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosTradeCoinToChainPage.setTotal(total);
		hosTradeCoinToChainPage.setRows(hosTradeCoinToChainList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosTradeCoinToChainPage);
		return cKResponse;
	}
	
}
