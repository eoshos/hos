package io.eoshos.console.simple.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.eoshos.console.simple.service.IHosUserAccountTransService;
import io.eoshos.console.simple.service.IHosUserService;
import io.eoshos.console.simple.service.IHosUserTaskAttachmentService;

//@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")  
@Controller
@RequestMapping("/withdraw")  
public class WithdrawController {
	
	private static Logger logger = LoggerFactory.getLogger(WithdrawController.class);
	
	//@Value("${upload.folder}")  
	//private String uploadFolder; 
	
	@Autowired
	protected HttpServletResponse response;	
    
    @Autowired
    IHosUserService hosUserService; 
    
    @Autowired
    IHosUserTaskAttachmentService hosUserTaskAttachmentService;    
    
    @Autowired
    IHosUserAccountTransService hosUserAccountTransService;   
    
    @RequestMapping(value = "/auditing", method = RequestMethod.GET)
    public String auditing(HttpServletRequest request, Model model) {
    	return "withdraw/auditing";
    }  
    
    @RequestMapping(value = "/audited", method = RequestMethod.GET)
    public String audited(HttpServletRequest request, Model model) {
    	return "withdraw/audited";
    }     
    
    @RequestMapping(value = "/auditfail", method = RequestMethod.GET)
    public String auditfail(HttpServletRequest request, Model model) {
    	return "withdraw/auditfail";
    }     
    
  //----------------------------以上page,以下data----------------------------------------------------------------------
    
	


}