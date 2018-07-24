package io.eoshos.console.simple.dao.mapper;

import java.sql.SQLException;
import java.util.List;

import io.eoshos.console.simple.bean.dto.HosTradeCoinToChainDto;
import io.eoshos.console.simple.bean.po.HosTradeCoinToChain;
import io.eoshos.console.simple.bean.vo.HosTradeCoinToChainVo;

/**  
* 
* @ClassName: HosTradeCoinToChainMapper
* @Description: TODO
* @author hehongjian
* @date 2018年5月31日 下午2:39:44
*
*/
public interface HosTradeCoinToChainMapper extends BaseMapper<HosTradeCoinToChain, HosTradeCoinToChainDto, HosTradeCoinToChainVo>{
	
	List<HosTradeCoinToChainVo> listWithdrawApply(HosTradeCoinToChainDto hosTradeCoinToChainDto) throws SQLException;
	
	int countWithdrawApply(HosTradeCoinToChainDto hosTradeCoinToChainDto);
}
