package io.eoshos.core.provider.dao;

import java.sql.SQLException;

import io.eoshos.core.api.bean.dto.HosUserAccountDto;
import io.eoshos.core.api.bean.po.HosUserAccount;
import io.eoshos.core.api.bean.vo.HosUserAccountVo;

/**  
* 
* @ClassName: HosUserMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosUserAccountMapper extends BaseMapper<HosUserAccount, HosUserAccountDto, HosUserAccountVo>{
	int updateByTrade(HosUserAccount hosUserAccount) throws SQLException;
}
