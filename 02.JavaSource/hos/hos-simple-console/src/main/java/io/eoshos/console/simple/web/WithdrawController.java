package io.eoshos.console.simple.web;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.dto.HosTradeCoinToChainDto;
import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.po.HosTradeCoinToChain;
import io.eoshos.console.simple.bean.po.HosTradeList;
import io.eoshos.console.simple.bean.vo.HosTradeCoinToChainVo;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;
import io.eoshos.console.simple.service.IHosTradeCoinToChainService;
import io.eoshos.console.simple.service.IHosUserAccountService;
import io.eoshos.console.simple.service.IHosUserService;
import io.eoshos.console.simple.service.IHosUserTaskAttachmentService;
import io.eoshos.console.simple.util.DateUtil;

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
    IHosUserAccountService hosUserAccountService; 
    
    @Autowired
    IHosTradeCoinToChainService hosTradeCoinToChainService;
    
    @RequestMapping(value = "/auditing", method = RequestMethod.GET)
    public String auditing(HttpServletRequest request, Model model) {
    	HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();
    	if(!StringUtils.isEmpty(request.getParameter("id"))){
			hosTradeCoinToChainDto.setId(Long.valueOf(request.getParameter("id")));
		} 
    	HosTradeCoinToChainVo hosTradeCoinToChainVo = (HosTradeCoinToChainVo) hosTradeCoinToChainService.getObject(hosTradeCoinToChainDto).getObj();
    	HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
    	hosUserAccountDto.setUserId(hosTradeCoinToChainVo.getUserId());
    	HosUserAccountVo hosUserAccountVo = (HosUserAccountVo) hosUserAccountService.getObject(hosUserAccountDto).getObj();
    	
    	model.addAttribute("id", "" + hosTradeCoinToChainVo.getId());
    	model.addAttribute("phone", hosTradeCoinToChainVo.getPhone());
    	model.addAttribute("applyTimeStr", hosTradeCoinToChainVo.getApplyTimeStr());
    	model.addAttribute("coinNumber", "" + hosTradeCoinToChainVo.getCoinNumber());
    	model.addAttribute("coinTotal", "" + hosUserAccountVo.getCoinTotal());
    	model.addAttribute("coinAvailable", "" + hosUserAccountVo.getCoinAvailable());
    	
    	String account = "2".equals(hosTradeCoinToChainVo.getAccountType()) ? 
    			hosTradeCoinToChainVo.getEosAccount() + "&emsp;&emsp;&emsp;交易所tag&#9;" + hosTradeCoinToChainVo.getExchangeAccount() : hosTradeCoinToChainVo.getEosAccount();
    	
    	model.addAttribute("eosAccount", account);
    	model.addAttribute("accountType", hosTradeCoinToChainVo.getAccountType());
    	model.addAttribute("exchangeAccount", hosTradeCoinToChainVo.getExchangeAccount());
    	
    	return "withdraw/auditing";
    }  
    
    @RequestMapping(value = "/audited", method = RequestMethod.GET)
    public String audited(HttpServletRequest request, Model model) {
    	HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();
    	if(!StringUtils.isEmpty(request.getParameter("id"))){
			hosTradeCoinToChainDto.setId(Long.valueOf(request.getParameter("id")));
		} 
    	HosTradeCoinToChainVo hosTradeCoinToChainVo = (HosTradeCoinToChainVo) hosTradeCoinToChainService.getObject(hosTradeCoinToChainDto).getObj();
    	HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
    	hosUserAccountDto.setUserId(hosTradeCoinToChainVo.getUserId());
    	HosUserAccountVo hosUserAccountVo = (HosUserAccountVo) hosUserAccountService.getObject(hosUserAccountDto).getObj();
    	
    	model.addAttribute("id", "" + hosTradeCoinToChainVo.getId());
    	model.addAttribute("phone", hosTradeCoinToChainVo.getPhone());
    	model.addAttribute("applyTimeStr", hosTradeCoinToChainVo.getApplyTimeStr());
    	model.addAttribute("coinNumber", "" + hosTradeCoinToChainVo.getCoinNumber());
    	model.addAttribute("coinTotal", "" + hosUserAccountVo.getCoinTotal());
    	model.addAttribute("coinAvailable", "" + hosUserAccountVo.getCoinAvailable());
    	model.addAttribute("auditDesc", hosTradeCoinToChainVo.getAuditDesc());
    	
    	String account = "2".equals(hosTradeCoinToChainVo.getAccountType()) ? 
    			hosTradeCoinToChainVo.getEosAccount() + "&emsp;&emsp;&emsp;交易所tag&#9;" + hosTradeCoinToChainVo.getExchangeAccount() : hosTradeCoinToChainVo.getEosAccount();
    	
    	model.addAttribute("eosAccount", account);
    	model.addAttribute("accountType", hosTradeCoinToChainVo.getAccountType());
    	model.addAttribute("exchangeAccount", hosTradeCoinToChainVo.getExchangeAccount());
    	return "withdraw/audited";
    }     
    
    @RequestMapping(value = "/auditfail", method = RequestMethod.GET)
    public String auditfail(HttpServletRequest request, Model model) {
    	HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();
    	if(!StringUtils.isEmpty(request.getParameter("id"))){
			hosTradeCoinToChainDto.setId(Long.valueOf(request.getParameter("id")));
		} 
    	HosTradeCoinToChainVo hosTradeCoinToChainVo = (HosTradeCoinToChainVo) hosTradeCoinToChainService.getObject(hosTradeCoinToChainDto).getObj();
    	HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
    	hosUserAccountDto.setUserId(hosTradeCoinToChainVo.getUserId());
    	HosUserAccountVo hosUserAccountVo = (HosUserAccountVo) hosUserAccountService.getObject(hosUserAccountDto).getObj();
    	
    	model.addAttribute("id", "" + hosTradeCoinToChainVo.getId());
    	model.addAttribute("phone", hosTradeCoinToChainVo.getPhone());
    	model.addAttribute("applyTimeStr", hosTradeCoinToChainVo.getApplyTimeStr());
    	model.addAttribute("coinNumber", "" + hosTradeCoinToChainVo.getCoinNumber());
    	model.addAttribute("coinTotal", "" + hosUserAccountVo.getCoinTotal());
    	model.addAttribute("coinAvailable", "" + hosUserAccountVo.getCoinAvailable());
    	
    	
    	String account = "2".equals(hosTradeCoinToChainVo.getAccountType()) ? 
    			hosTradeCoinToChainVo.getEosAccount() + "&emsp;&emsp;&emsp;交易所tag&#9;" + hosTradeCoinToChainVo.getExchangeAccount() : hosTradeCoinToChainVo.getEosAccount();
    	
    	model.addAttribute("eosAccount", account);
    	model.addAttribute("accountType", hosTradeCoinToChainVo.getAccountType());
    	model.addAttribute("exchangeAccount", hosTradeCoinToChainVo.getExchangeAccount());
    	model.addAttribute("auditDesc", hosTradeCoinToChainVo.getAuditDesc());
    	return "withdraw/auditfail";
    }     
    
    //----------------------------以上page,以下data----------------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
    @ResponseBody
    public Page<HosTradeCoinToChainVo> listUsers(HttpServletRequest request) {
    	HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();
		if(!StringUtils.isEmpty(request.getParameter("phone"))){
			hosTradeCoinToChainDto.setPhone(request.getParameter("phone"));
		} 
		if(!StringUtils.isEmpty(request.getParameter("stat"))){
			hosTradeCoinToChainDto.setStat(request.getParameter("stat"));
		}	
		
		if(!StringUtils.isEmpty(request.getParameter("beginDate"))){
			String s = request.getParameter("beginDate") + " 00:00:00";
			hosTradeCoinToChainDto.setBeginDate(DateUtil.getTime(s));
		}	
		if(!StringUtils.isEmpty(request.getParameter("endDate"))){
			String s = request.getParameter("endDate") + " 00:00:00";
			hosTradeCoinToChainDto.setEndDate(DateUtil.getTime(s));
		}		
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit")); 
		hosTradeCoinToChainDto.setLimit(limit);
		hosTradeCoinToChainDto.setOffset(offset);
		Page<HosTradeCoinToChainVo> page = null;
    	page = (Page<HosTradeCoinToChainVo>) hosTradeCoinToChainService.listWithdrawApply(hosTradeCoinToChainDto).getObjPage();
    	return page;
    }     
	
    @RequestMapping(value="/withdraw", method = RequestMethod.POST)
	@ResponseBody
	public CKResponse withdraw(HttpServletRequest request){
    	CKResponse cKResponse = new CKResponse();
    	HosTradeCoinToChainVo hosTradeCoinToChainVo = null;
    	HosTradeCoinToChain hosTradeCoinToChain = new HosTradeCoinToChain();
    	HosTradeList hosTradeList = new HosTradeList();
    	//调用上链接口
    	//根据上链结果：如果成功更新表HOS币上链、用户账户信息表，新增用户账户交易信息表、HOS币链上交易明细；
    	//如果失败，更新HOS币上链、用户账户信息表，新增用户账户交易信息表
    	
    	//查询上链信息
    	Long id = Long.valueOf(request.getParameter("id"));
    	HosTradeCoinToChainDto hosTradeCoinToChainDto = new HosTradeCoinToChainDto();
    	hosTradeCoinToChainDto.setId(id);
    	cKResponse = hosTradeCoinToChainService.getObject(hosTradeCoinToChainDto);
    	if(!ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
    		return cKResponse;
    	}
    	hosTradeCoinToChainVo = (HosTradeCoinToChainVo) cKResponse.getObj();
    	
    	if(!"1".equals(hosTradeCoinToChainVo.getStat())){
    		cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
    		cKResponse.setErrorMsg("该申请已处理，请刷新后查看！");
    		return cKResponse;
    	}
    	
    	boolean success = false;
    	
    	//TODO 调用上链接口
    	
    	/*
    	 * if 上链成功
    	 * 		更新
    	 * 
    	 */
    	String FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
		String dateStr = new SimpleDateFormat(FORMAT_STR).format(new Date());
		long auditTime = DateUtil.getTime(dateStr)/1000;		
    	if(success){
    		//修改上链信息为批准
    		hosTradeCoinToChain = new HosTradeCoinToChain();
    		hosTradeCoinToChain.setId(hosTradeCoinToChainVo.getId());
    		hosTradeCoinToChain.setUserId(hosTradeCoinToChainVo.getUserId());
    		hosTradeCoinToChain.setCoinNumber(hosTradeCoinToChainVo.getCoinNumber());
    		hosTradeCoinToChain.setStat("9");//1申请8拒绝9批准
    		hosTradeCoinToChain.setAuditTime(auditTime);//审批时间
    		hosTradeCoinToChain.setAuditDesc("");//审批结果
    		
    		//新增HOS币链上交易明细
    		hosTradeList.setUserId(hosTradeCoinToChainVo.getUserId());
    		hosTradeList.setUserAccountId(hosTradeCoinToChainVo.getUserAccountId());
    		hosTradeList.setTxHash("");//TxHash
    		hosTradeList.setBlockId("");//区块ID
    		hosTradeList.setTradeTime(auditTime);//交易时间
    		hosTradeList.setAddrFrom("");//开始地址
    		hosTradeList.setAddrTo("");//结束地址
    		hosTradeList.setCoinNumber(hosTradeCoinToChainVo.getCoinNumber());//交易币个数
    		hosTradeList.setTag(hosTradeCoinToChainVo.getExchangeAccount());//标签
    		hosTradeList.setNotes("");//备注
    		
    		try {
    			cKResponse = hosTradeCoinToChainService.withdraw(hosTradeCoinToChain, hosTradeList);
    			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
    			cKResponse.setErrorMsg("上链成功！");
    		} catch (SQLException e) {
    			e.printStackTrace();
    			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
    			cKResponse.setErrorMsg("上链成功，但处理用户提币申请异常，请联系技术人员!");
    		}
    	}else{
    		//修改上链信息为拒绝
    		hosTradeCoinToChain = new HosTradeCoinToChain();
    		hosTradeCoinToChain.setId(hosTradeCoinToChainVo.getId());
    		hosTradeCoinToChain.setUserId(hosTradeCoinToChainVo.getUserId());
    		hosTradeCoinToChain.setCoinNumber(hosTradeCoinToChainVo.getCoinNumber());
    		hosTradeCoinToChain.setStat("8");//1申请8拒绝9批准
    		hosTradeCoinToChain.setAuditTime(auditTime);//审批时间
    		hosTradeCoinToChain.setAuditDesc("");//审批结果
    		
    		try {
    			cKResponse = hosTradeCoinToChainService.withdraw(hosTradeCoinToChain, null);
    			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
    			cKResponse.setErrorMsg("上链失败！" + "");//TODO上链失败信息
    		} catch (SQLException e) {
    			e.printStackTrace();
    			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
    			cKResponse.setErrorMsg("上链失败且处理用户提币申请异常，请联系技术人员!");
    		}
    	}
    	
    	return cKResponse;
    }

}