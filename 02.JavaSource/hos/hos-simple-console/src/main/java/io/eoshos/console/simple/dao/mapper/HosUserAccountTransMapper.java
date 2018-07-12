package io.eoshos.console.simple.dao.mapper;

import java.sql.SQLException;
import java.util.List;

import io.eoshos.console.simple.bean.dto.HosUserAccountTransDto;
import io.eoshos.console.simple.bean.po.HosUserAccountTrans;
import io.eoshos.console.simple.bean.vo.HosUserAccountTransVo;

/**  
* 
* @ClassName: HosUserMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosUserAccountTransMapper extends BaseMapper<HosUserAccountTrans, HosUserAccountTransDto, HosUserAccountTransVo>{
	
	/**
	 * 
	* @Title: listPagingObjects1 
	* @Description: 带附件的分页查询
	* @param @param hosUserAccountTransDto
	* @param @return
	* @param @throws SQLException  参数说明 
	* @return List<HosUserAccountTransVo>    返回类型 
	* @throws
	 */
	List<HosUserAccountTransVo> listPagingObjects1(HosUserAccountTransDto hosUserAccountTransDto) throws SQLException;
	
	/**
	 * 
	* @Title: countObjects1 
	* @Description: 带附件的分页查询-总数
	* @param @param hosUserAccountTransDto
	* @param @return  参数说明 
	* @return int    返回类型 
	* @throws
	*/
	int countObjects1(HosUserAccountTransDto hosUserAccountTransDto);

	/**
	 * 
	* @Title: getFirstObject 
	* @Description: 取注册时的交易数据 
	* @param @param hosUserAccountTransDto
	* @param @return  参数说明 
	* @return HosUserAccountTransVo    返回类型 
	* @throws
	 */
	HosUserAccountTransVo getFirstObject(HosUserAccountTransDto hosUserAccountTransDto) throws SQLException;

}
