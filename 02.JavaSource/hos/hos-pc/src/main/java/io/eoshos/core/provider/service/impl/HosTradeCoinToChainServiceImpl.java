package io.eoshos.core.provider.service.impl;

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

import io.eoshos.core.api.bean.bo.HosTradeCoinToChainBo;
import io.eoshos.core.api.bean.dto.HosTradeCoinToChainDto;
import io.eoshos.core.api.bean.po.HosTradeCoinToChain;
import io.eoshos.core.api.bean.po.HosUserAccount;
import io.eoshos.core.api.bean.po.HosUserAccountTrans;
import io.eoshos.core.api.bean.vo.HosTradeCoinToChainVo;
import io.eoshos.core.api.service.IHosTradeCoinToChainService;
import io.eoshos.core.provider.dao.HosTradeCoinToChainMapper;
import io.eoshos.core.provider.dao.HosUserAccountMapper;
import io.eoshos.core.provider.dao.HosUserAccountTransMapper;

@Service("hosTradeCoinToChainService")
public class HosTradeCoinToChainServiceImpl implements IHosTradeCoinToChainService {

	private static final Logger logger = LogManager.getLogger(HosTradeCoinToChainServiceImpl.class);

	@Autowired
	private HosTradeCoinToChainMapper hosTradeCoinToChainMapper;
	
	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;
	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;

	@Override
	public CKResponse save(HosTradeCoinToChainBo bo) {
		HosTradeCoinToChain hosTradeCoinToChain = bo.getHosTradeCoinToChain();
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
			String str = bActionIsAdd ? "增加HOS币上链异常:" : "修改HOS币上链异常:";
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
	public CKResponse delete(HosTradeCoinToChainBo bo) {
		CKResponse cKResponse = new CKResponse();
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
			String str = total > 0 ? "删除HOS币上链异常:" : "查询HOS币上链总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
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
			logger.error("查询HOS币上链异常:" + e.getMessage());
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
		CKResponse cKResponse = new CKResponse();
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
			logger.error("查询HOS币上链异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosTradeCoinToChainList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosTradeCoinToChainDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosTradeCoinToChainVo> hosTradeCoinToChainPage = new Page<HosTradeCoinToChainVo>();
		// 查询用户账户信息列表
		List<HosTradeCoinToChainVo> hosTradeCoinToChainList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosTradeCoinToChainList = hosTradeCoinToChainMapper.listPagingObjects(dto);
			// 查询总数
			total = hosTradeCoinToChainMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeCoinToChainList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosTradeCoinToChainList == null ? "查询HOS币上链异常:" : "查询HOS币上链总数异常:";
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
	@Transactional(rollbackFor = Exception.class)
	public CKResponse withdraw(HosTradeCoinToChainBo bo) throws SQLException {
		//保存提币请求，分三步：1.冻结可用金额；2.保存交易记录；3.保存提币请求
		CKResponse cKResponse = new CKResponse();
		HosUserAccount hosUserAccount = bo.getHosUserAccount();
		HosTradeCoinToChain hosTradeCoinToChain = bo.getHosTradeCoinToChain();
		int i = 0;
		//保存用户账户信息
		logger.debug("保存用户账户信息");
		i += hosUserAccountMapper.updateByTrade(hosUserAccount);
		
		// 新增交易记录
		logger.debug("新增交易记录");
		HosUserAccountTrans hosUserAccountTrans = new HosUserAccountTrans();
		hosUserAccountTrans.setUserId(hosUserAccount.getUserId());
		hosUserAccountTrans.setDcFlag("N");
		hosUserAccountTrans.setTransType("8");
		hosUserAccountTrans.setOtherUserId(hosUserAccount.getUserId());
		hosUserAccountTrans.setCoinNumber(hosTradeCoinToChain.getCoinNumber());
		hosUserAccountTrans.setCoinBal(hosUserAccount.getCoinTotal());
		hosUserAccountTrans.setStat("1");
		hosUserAccountTrans.setNotes("1");//备注：1-提币处理中，2-提币失败，资金已返回，3-提币成功
		i += hosUserAccountTransMapper.insert(hosUserAccountTrans);
		
		// 新增提币请求
		logger.debug("新增提币请求");
		i += hosTradeCoinToChainMapper.insert(hosTradeCoinToChain);
		
		if (i < 3) {
			logger.error("保存信息异常！");
			throw new SQLException("保存信息异常！");
		}
		
		cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);
		return cKResponse;
	}

}
