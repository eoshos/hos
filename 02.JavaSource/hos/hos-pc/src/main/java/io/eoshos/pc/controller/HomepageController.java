package io.eoshos.pc.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.dto.HosTradeListDto;
import io.eoshos.core.api.bean.dto.HosUserAccountDto;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.dto.HosUserInviteDto;
import io.eoshos.core.api.bean.vo.HosTradeListVo;
import io.eoshos.core.api.bean.vo.HosUserAccountVo;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.service.IHosTradeListService;
import io.eoshos.core.api.service.IHosUserAccountService;
import io.eoshos.core.api.service.IHosUserInviteService;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.pc.util.QrCodeUtil;
import io.eoshos.pc.util.StringUtil;

/***
 * 
 * @ClassName HomepageController
 * @Description
 * @author hhj@chuangke18.com
 * @Date 2017年06月05日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/homepage")
public class HomepageController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(HomepageController.class);
	
	@Autowired
	private IHosUserService hosUserService;
	
	@Autowired
	private IHosUserAccountService hosUserAccountService;
	
	@Autowired
	private IHosUserInviteService hosUserInviteService;
	
	@Autowired
	private IHosTradeListService hosTradeListService;

	private static String AUTH_USER = "auth_user";
	
	@Value("${withdraw_available}")
	private String withdraw_available;
	/**
	 * 
	* @Title: index 
	* @Description: 主页
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties2(model);
		setModelProperties3(request, model);
		
		model.addAttribute("withdraw_available", withdraw_available);//提币功能开关
      
		//session中数据不一定正确，实时查询为准
        HosUserVo hosUserVo = (HosUserVo) session.getAttribute(AUTH_USER);
        HosUserDto hosUserDto = new HosUserDto();
        hosUserDto.setPhone(hosUserVo.getPhone());
        hosUserVo = (HosUserVo)hosUserService.getObject(hosUserDto).getObj();
        session.setAttribute(AUTH_USER, hosUserVo);
        model.addAttribute("userId", hosUserVo.getId());//用户id
        model.addAttribute("userName", hosUserVo.getUserName());//用户名
        model.addAttribute("inviteCode", hosUserVo.getInviteCode());//邀请码
        model.addAttribute("realStat", hosUserVo.getRealStat());//实名认证状态
        // 取用户账户信息
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		hosUserAccountDto.setUserId(hosUserVo.getId());
		CKResponse cKResponse = hosUserAccountService.getObject(hosUserAccountDto);
		if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
			HosUserAccountVo hosUserAccountVo = (HosUserAccountVo) cKResponse.getObj();
			model.addAttribute("coinTotal", hosUserAccountVo.getCoinTotal());//币总数
			model.addAttribute("coinAvailable", hosUserAccountVo.getCoinAvailable());//可用币数
			model.addAttribute("coinFreeze", hosUserAccountVo.getCoinFreeze());//冻结币数
		}
		// 取用户邀请信息
		HosUserInviteDto hosUserInviteDto = new HosUserInviteDto();
		hosUserInviteDto.setUserId(hosUserVo.getId());
		cKResponse = hosUserInviteService.countObjects(hosUserInviteDto);
		if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
			int count = (int) cKResponse.getObj();
			model.addAttribute("numberInvite", count);//邀请人总数
		}
		cKResponse = hosUserInviteService.countNumberInvite(hosUserInviteDto);
		if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
			Double numberReward = (Double) cKResponse.getObj();
			model.addAttribute("numberReward", numberReward);//邀请奖励币总数
		}
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/homepage" : "admin/homepage";
        return url;			
        
	}
	
	
	/**
	 * 
	* @Title: auth 
	* @Description: 实名认证页面 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String auth(Model model, HttpServletRequest request) {
		setModelProperties3(request, model);
		setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/auth" : "admin/auth";
        return url;			
	}
	
	/**
	 * 
	* @Title: authing 
	* @Description: 实名认证中 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/authing", method = RequestMethod.GET)
	public String authing(Model model, HttpServletRequest request) {
		setModelProperties3(request, model);
		setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		model.addAttribute("cardId", hosUserVo.getCardId());
		model.addAttribute("realName", hosUserVo.getRealName());
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/authing" : "admin/authing";
        return url;			
	}	
	
	/**
	 * 
	* @Title: authed 
	* @Description: 实名认证完成 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/authed", method = RequestMethod.GET)
	public String authed(Model model, HttpServletRequest request) {
		setModelProperties3(request, model);
		setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		model.addAttribute("cardId", hosUserVo.getCardId());
		model.addAttribute("realName", hosUserVo.getRealName());		
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/authed" : "admin/authed";
        return url;			
	}	
	
	/**
	 * 
	* @Title: authfail 
	* @Description: 实名认证失败
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/authfail", method = RequestMethod.GET)
	public String authfail(Model model, HttpServletRequest request) {
		setModelProperties3(request, model);
		setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		// 重取用戶信息
		HosUserDto hosUserDto = new HosUserDto();
        hosUserDto.setPhone(hosUserVo.getPhone());
        hosUserVo = (HosUserVo)hosUserService.getObject(hosUserDto).getObj();
        session.setAttribute(AUTH_USER, hosUserVo);
		model.addAttribute("userName",hosUserVo.getPhone());
		model.addAttribute("cardId", hosUserVo.getCardId());
		model.addAttribute("realName", hosUserVo.getRealName());
		model.addAttribute("notes", hosUserVo.getNotes());
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/authfail" : "admin/authfail";
        return url;			
	}	
	
	/**
	 * 
	* @Title: asset 
	* @Description: 资产明细 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/asset", method = RequestMethod.GET)
	public String asset(Model model, HttpServletRequest request) {
		setModelProperties2(model);
		setModelProperties3(request, model);
		
		HosUserVo hosUserVo = (HosUserVo) session.getAttribute(AUTH_USER);
		model.addAttribute("userId", hosUserVo.getId());//用户id
        model.addAttribute("userName", hosUserVo.getUserName());//用户名
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/asset" : "admin/asset";
        return url;			
	}	

	
	/**
	 * 
	* @Title: extract 
	* @Description: 提币 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.GET)
	public String withdraw(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties5(model);
		setModelProperties3(request, model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		
		// 取用户账户信息
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		hosUserAccountDto.setUserId(hosUserVo.getId());
		CKResponse cKResponse = hosUserAccountService.getObject(hosUserAccountDto);
		if (ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())) {
			HosUserAccountVo hosUserAccountVo = (HosUserAccountVo) cKResponse.getObj();
			model.addAttribute("coinTotal", hosUserAccountVo.getCoinTotal());// 币总数
			model.addAttribute("coinAvailable", hosUserAccountVo.getCoinAvailable());// 可用币数
			model.addAttribute("coinFreeze", hosUserAccountVo.getCoinFreeze());// 冻结币数
		}
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/withdraw" : "admin/withdraw";
        return url;			
	}
	
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
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public String browser(Model model, HttpServletRequest request) {
		//setModelProperties3(request, model);
		//setModelProperties4(model);
		
		HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
		model.addAttribute("userName",hosUserVo.getPhone());
		model.addAttribute("cardId", hosUserVo.getCardId());
		model.addAttribute("realName", hosUserVo.getRealName());
		
		String devtype = request.getParameter("devtype");
		//String url = StringUtils.equals("wap", devtype) ? "wap/extract" : "admin/extract";
        return "browser/index";			
	}
	
	/**
	 * 
	* @Title: myTrades 
	* @Description: 我的交易 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	 */
	@RequestMapping(value="/myTrades", method = RequestMethod.POST)
	@ResponseBody
	public Page<HosTradeListVo> myTrades(HttpServletRequest request){
		Page<HosTradeListVo> page = new Page<HosTradeListVo>();
		List<HosTradeListVo> list = new ArrayList<HosTradeListVo>();
		page.setRows(list);
		page.setTotal(0);
		String userId = request.getParameter("userId");//用户id
		String offset =  request.getParameter("offset");//分页开始
		String limit =  request.getParameter("limit");//分页数量
		// 查询交易记录
		HosTradeListDto dto = new HosTradeListDto();
		dto.setUserId(StringUtil.isEmpty(userId) ? 0L : Long.valueOf(userId));
		dto.setOffset(StringUtil.isEmpty(offset) ? 0 : Integer.valueOf(offset));
		dto.setLimit(StringUtil.isEmpty(limit) ? 0 : Integer.valueOf(limit));
		CKResponse cKResponse = hosTradeListService.listPagingObjects(dto);
		if (ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())) {
			page = (Page<HosTradeListVo>) cKResponse.getObjPage();
		}
		return page;
	}
	
	/**
	 * 
	* @Title: getimage 
	* @Description: 取图片
	* @param @param response
	* @param @param request
	* @param @throws Exception  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/getimage")   
	public void getimage(HttpServletResponse response,HttpServletRequest request) throws Exception{ 
		String filePath = request.getSession().getServletContext().getRealPath("qrcode") + File.separator;
		String fileName = request.getParameter("phone") + ".png";//文件以电话号码命名
		//将图片输出给浏览器  
		String imgPath = filePath + fileName;  
		// 判断文件是否存在
		File file = new File(imgPath);
		// 二维码不存在则重新生成
		if(!file.exists()){
			// 查询用户信息
			String[] tmp = request.getParameter("phone").split("_");
			String phone = tmp[0];// 手机号码
			HosUserDto hosUserDto = new HosUserDto();
			hosUserDto.setPhone(phone);
			CKResponse cKResponse = hosUserService.getObject(hosUserDto);
			if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
				HosUserVo hosUserVo = (HosUserVo) cKResponse.getObj();
				// 生成二维码
				QrCodeUtil.qrcode(phone, hosUserVo.getInviteCode());
			}
		}
		BufferedImage image = ImageIO.read(new FileInputStream(imgPath));  
	    response.setContentType("image/png");  
	    OutputStream os = response.getOutputStream();  
	    ImageIO.write(image, "png", os);  
	}

}
