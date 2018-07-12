package io.eoshos.console.simple.dao.mapper;

import java.sql.SQLException;

import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.po.HosUserAccount;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;

/**  
* 
* @ClassName: HosUserAccountMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosUserAccountMapper extends BaseMapper<HosUserAccount, HosUserAccountDto, HosUserAccountVo>{

	int updateCoin(HosUserAccount hosUserAccount) throws SQLException;
	
	//int updateByTrade(HosUserAccount hosUserAccount) throws SQLException;
	
}
