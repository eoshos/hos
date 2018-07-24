package io.eoshos.console.simple.service;

import java.sql.SQLException;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosTradeCoinToChainBo;
import io.eoshos.console.simple.bean.dto.HosTradeCoinToChainDto;
import io.eoshos.console.simple.bean.po.HosTradeCoinToChain;
import io.eoshos.console.simple.bean.po.HosTradeList;

public interface IHosTradeCoinToChainService extends IBaseService<HosTradeCoinToChainBo, HosTradeCoinToChainDto>{
	
	/**
	 * 
	* @Title: listWithdrawApply 
	* @Description: 分页查询
	* @param @param hosTradeCoinToChainDto
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带对象信息)
	* @throws
	 */
	CKResponse listWithdrawApply(HosTradeCoinToChainDto hosTradeCoinToChainDto) ;
	
	/**
	 * 
	* @Title: withdraw 
	* @Description: 提币审核
	* @param @param bo
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带删除是否成功信息)
	 * @throws SQLException 
	* 
	 */
	CKResponse withdraw(HosTradeCoinToChain hosTradeCoinToChain, HosTradeList hosTradeList) throws SQLException;
}
