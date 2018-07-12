package io.eoshos.core.provider.dao;

import java.sql.SQLException;

import io.eoshos.core.api.bean.dto.HosUserInviteDto;
import io.eoshos.core.api.bean.po.HosUserInvite;
import io.eoshos.core.api.bean.vo.HosUserInviteVo;

/**  
* 
* @ClassName: HosUserMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosUserInviteMapper extends BaseMapper<HosUserInvite, HosUserInviteDto, HosUserInviteVo>{
	Double countNumberInvite(HosUserInviteDto hosUserInviteDto) throws SQLException;
}
