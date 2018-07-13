package io.eoshos.console.simple.dao.mapper;

import java.sql.SQLException;

import io.eoshos.console.simple.bean.dto.HosUserTaskAttachmentDto;
import io.eoshos.console.simple.bean.po.HosUserTaskAttachment;
import io.eoshos.console.simple.bean.vo.HosUserTaskAttachmentVo;

/**  
* 
* @ClassName: HosUserTaskAttachmentMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosUserTaskAttachmentMapper extends BaseMapper<HosUserTaskAttachment, HosUserTaskAttachmentDto, HosUserTaskAttachmentVo>{

	int updateTradeId(HosUserTaskAttachment hosUserTaskAttachment) throws SQLException;
	
}
