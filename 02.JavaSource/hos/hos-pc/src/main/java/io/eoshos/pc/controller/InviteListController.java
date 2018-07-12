package io.eoshos.pc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.dto.HosUserInviteDto;
import io.eoshos.core.api.bean.vo.HosUserInviteVo;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.service.IHosUserInviteService;
import io.eoshos.pc.util.StringUtil;

/***
 * 
 * @ClassName InviteListController
 * @Description
 * @author hhj@chuangke18.com
 * @Date 2017年06月05日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/invite")
public class InviteListController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(InviteListController.class);
	
	@Autowired
	private IHosUserInviteService hosUserInviteService;
	
	private static String AUTH_USER = "auth_user";

	/**
	 * 
	* @Title: index 
	* @Description: 邀请记录页面
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		return null;
	}
	

	
	/**
	 * 
	* @Title: listInvite 
	* @Description: 邀请记录
	* @param @param request
	* @param @return  参数说明 
	* @return Page<HosUserInviteVo>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/listinvite", method = RequestMethod.POST)
	@ResponseBody
	public Page<HosUserInviteVo> listInvite(HttpServletRequest request){
		Page<HosUserInviteVo> page = new Page<HosUserInviteVo>();
		HosUserInviteDto hosUserInviteDto = new HosUserInviteDto();
		HosUserVo hosUserVo = (HosUserVo) session.getAttribute(AUTH_USER);
		Long userId = hosUserVo.getId();
		
		if(userId != null){
			hosUserInviteDto.setUserId(userId);
			//调用邀请表的不分页查询
			CKResponse cKResponse = hosUserInviteService.listAllObjects(hosUserInviteDto);
			
			if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
				List<HosUserInviteVo> hosUserInviteVoList = (List<HosUserInviteVo>) cKResponse.getObjList();
				// 隐藏被邀请人手机号码中间四位
				for(HosUserInviteVo item : hosUserInviteVoList){
					item.setInvitePhone(StringUtil.hidePhone(item.getInvitePhone()));
				}
				page.setRows(hosUserInviteVoList);
				page.setTotal(hosUserInviteVoList.size());
			}
		}
		return page;
	}

}
