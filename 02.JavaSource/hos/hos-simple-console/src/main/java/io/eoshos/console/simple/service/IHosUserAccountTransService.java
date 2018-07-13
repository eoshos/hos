package io.eoshos.console.simple.service;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserAccountTransBo;
import io.eoshos.console.simple.bean.dto.HosUserAccountTransDto;
import io.eoshos.console.simple.util.ExceptionNullUpdate;

public interface IHosUserAccountTransService extends IBaseService<HosUserAccountTransBo, HosUserAccountTransDto>{
	
	/**
	 * 
	* @Title: trade 
	* @Description: 交易币数
	* @param @param hosUserAccountTransDto
	* @param @return  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse trade(HosUserAccountTransDto hosUserAccountTransDto) throws ExceptionNullUpdate;
	
	/**
	 * 
	* @Title: listPagingObjects 1
	* @Description: 分页查询-显示附件
	* @param @param dto
	* @param @return
	* @param @  参数说明 
	* @return CKResponse    返回类型 (带对象信息)
	* @throws
	 */
	CKResponse listPagingObjects1(HosUserAccountTransDto hosUserAccountTransDto) ;
	
}
