package io.eoshos.core.api.service;

import java.sql.SQLException;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosTradeCoinToChainBo;
import io.eoshos.core.api.bean.dto.HosTradeCoinToChainDto;

public interface IHosTradeCoinToChainService extends IBaseService<HosTradeCoinToChainBo, HosTradeCoinToChainDto>{
	
	/**
	 * 
	* @Title: withdraw 
	* @Description: 提币
	* @param @param bo
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带删除是否成功信息)
	* 
	 */
	CKResponse withdraw(HosTradeCoinToChainBo bo) throws SQLException ;
}
