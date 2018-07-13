package io.eoshos.console.simple.service;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserBo;
import io.eoshos.console.simple.bean.dto.HosUserDto;
import io.eoshos.console.simple.bean.po.HosUser;
import io.eoshos.console.simple.util.ExceptionNullUpdate;

public interface IHosUserService extends IBaseService<HosUserBo, HosUserDto>{
	
	/**
	 * 
	* @Title: applyPhoneValidationCode 
	* @Description: 获取手机验证码
	* @param @param hosUserDto
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse applyPhoneValidationCode(HosUserDto hosUserDto) ;
	
	/**
	 * 
	* @Title: enroll 
	* @Description: 用户注册
	* @param @param hosUserBo
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse enroll(HosUserBo hosUserBo) ;
	
	/**
	 * 
	* @Title: login 
	* @Description: 用户登录 
	* @param @param hosUserDto
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse login(HosUserDto hosUserDto) ;
	
	/**
	 * 
	* @Title: password 
	* @Description: 用户更改密码
	* @param @param hosUserBo
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse password(HosUserBo hosUserBo) ;
	
	/**
	 * 
	* @Title: authentication 
	* @Description: 实名认证
	* @param @param hosUser
	* @param @return  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse authentication(HosUser hosUser) throws ExceptionNullUpdate;	
	
}
