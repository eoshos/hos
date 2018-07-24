package io.eoshos.core.provider.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosTradeListBo;
import io.eoshos.core.api.bean.dto.HosTradeListDto;
import io.eoshos.core.api.bean.po.HosTradeList;
import io.eoshos.core.api.bean.vo.HosTradeListVo;
import io.eoshos.core.api.service.IHosTradeListService;
import io.eoshos.core.provider.dao.HosTradeListMapper;

@Service("hosTradeListService")
public class HosTradeListServiceImpl implements IHosTradeListService {

	private static final Logger logger = LogManager.getLogger(HosTradeListServiceImpl.class);

	@Autowired
	private HosTradeListMapper hosTradeListMapper;

	@Override
	public CKResponse save(HosTradeListBo bo) {
		HosTradeList hosTradeList = bo.getHosTradeList();
		CKResponse cKResponse = new CKResponse();
		String errorCode = "";
		String errorMsg = "";
		int i = 0;

		// 保存用户账户信息，如果id为空是插入，否则修改
		boolean bActionIsAdd = hosTradeList.getUserId() == null;
		try {
			i = bActionIsAdd ? hosTradeListMapper.insert(hosTradeList)
					: hosTradeListMapper.update(hosTradeList);
		} catch (SQLException e) {
			String str = bActionIsAdd ? "增加HOS币链上交易明细异常:" : "修改HOS币链上交易明细异常:";
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
	public CKResponse delete(HosTradeListBo bo) {
		CKResponse cKResponse = new CKResponse();
		HosTradeList hosTradeList = bo.getHosTradeList();
		HosTradeListDto hosTradeListDto = new HosTradeListDto();

		hosTradeListDto.setUserId(hosTradeList.getUserId());

		String errorCode = ConstantApi.ERROR_CODE.ERROR;
		String errorMsg = ConstantApi.ERROR_MSG.ERROR;

		int total = 0;
		int i = 0;

		try {
			// 先查询再删除
			total = hosTradeListMapper.countObjects(hosTradeListDto);
			if (total > 0) {
				i = hosTradeListMapper.delete(hosTradeListDto);

				// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
				errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
				errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
			}
		} catch (SQLException e) {
			String str = total > 0 ? "删除HOS币链上交易明细异常:" : "查询HOS币链上交易明细总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
	}

	@Override
	public CKResponse getObject(HosTradeListDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosTradeListVo hosTradeListVo = null;
		String errorCode = "";
		String errorMsg = "";

		try {
			hosTradeListVo = hosTradeListMapper.getObject(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosTradeListVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosTradeListVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询HOS币链上交易明细异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosTradeListVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosTradeListDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosTradeListVo> hosTradeListList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosTradeListList = hosTradeListMapper.listAllObjects(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeListList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeListList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询HOS币链上交易明细异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosTradeListList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosTradeListDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosTradeListVo> hosTradeListPage = new Page<HosTradeListVo>();
		// 查询用户账户信息列表
		List<HosTradeListVo> hosTradeListList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosTradeListList = hosTradeListMapper.listPagingObjects(dto);
			// 查询总数
			total = hosTradeListMapper.countObjects(dto);
			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosTradeListList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosTradeListList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosTradeListList == null ? "查询我的交易异常:" : "查询我的交易总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosTradeListPage.setTotal(total);
		hosTradeListPage.setRows(hosTradeListList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosTradeListPage);
		return cKResponse;
	}

}
