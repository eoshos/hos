package io.eoshos.pc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.eoshos.core.api.bean.vo.HosUserVo;

@Controller
@RequestMapping("/browser")
public class BrowserController extends BaseController {

	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(BrowserController.class);
	
	private static String AUTH_USER = "auth_user";
	
	/**
	 * 
	* @Title: browser 
	* @Description: 区块浏览器 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String browser(Model model, HttpServletRequest request) {
		setModelProperties3(request, model);
		setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		model.addAttribute("cardId", hosUserVo.getCardId());
		model.addAttribute("realName", hosUserVo.getRealName());
		
		String devtype = request.getParameter("devtype");
		//String url = StringUtils.equals("wap", devtype) ? "wap/extract" : "admin/extract";
        return "browser/index";			
	}
	
}
