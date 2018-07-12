package io.eoshos.core.api.service;

import java.sql.SQLException;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosUserBo;
import io.eoshos.core.api.bean.dto.HosUserDto;

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
	* @Title: validateInfo 
	* @Description: 信息校验，密码加密以及生成二维码
	* @param @param hosUserBo
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse validateInfo(HosUserBo hosUserBo) ;
	
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
	* @Title: saveUserInfo 
	* @Description: 保存用户数据
	* @param @param hosUserBo
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	CKResponse saveUserInfo(HosUserBo hosUserBo) throws SQLException;
}
