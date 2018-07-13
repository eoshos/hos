package io.eoshos.core.api.response;

import java.util.List;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.vo.HosUserVo;

/**  
* 
* @ClassName: UserLoginResponse
* @Description: TODO
* @author hehongjian
* @date 2018年6月4日 下午3:37:03
*
*/
public class UserLoginResponse extends CKResponse{
	
	private HosUserVo hosUserVo;
	
	private List authList;

	public HosUserVo getHosUserVo() {
		return hosUserVo;
	}

	public List getAuthList() {
		return authList;
	}

	public void setAuthList(List authList) {
		this.authList = authList;
	}

	public void setHosUserVo(HosUserVo hosUserVo) {
		this.hosUserVo = hosUserVo;
	}

}
