package io.eoshos.console.simple.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;


@Controller
@RequestMapping("/")  
public class IndexController {
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
    	return "index";
    } 
    
    @RequestMapping(value = "/depositindex", method = RequestMethod.GET)
    public String depositindex(HttpServletRequest request, Model model) {
    	return "deposit/index";
    }   
    
    @RequestMapping(value = "/withdrawindex", method = RequestMethod.GET)
    public String withdrawindex(HttpServletRequest request, Model model) {
    	return "withdraw/index";
    }     
   
	@RequestMapping(value = "/login")
	@ResponseBody
	public CKResponse login(HttpServletRequest httpServletRequest)throws Exception {
		CKResponse response = new CKResponse();
		response.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		response.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);
		String name = httpServletRequest.getParameter("name");
		String pass = httpServletRequest.getParameter("pass");
		if (!(StringUtils.equals("eoser", name) && StringUtils.equals("hoser", pass))){
			response.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			response.setErrorMsg("密码错误");
		}
		return response;	
	}      
   
}