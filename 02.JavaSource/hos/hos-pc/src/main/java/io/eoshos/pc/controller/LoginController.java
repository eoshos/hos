package io.eoshos.pc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosUserBo;
import io.eoshos.core.api.bean.dto.HosUserAccountTransDto;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.vo.HosUserAccountTransVo;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.request.UserLoginRequest;
import io.eoshos.core.api.response.UserLoginResponse;
import io.eoshos.core.api.service.IHosUserAccountTransService;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.pc.util.VerificationCodeUtil;

/***
 * 
 * @ClassName LoginController
 * @Description
 * @author hhj@chuangke18.com
 * @Date 2017年06月05日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	private static String AUTH_USER = "auth_user";
	
	private static String AUTH_LIST = "auth_list";

	@Autowired
	private IHosUserService hosUserService;
	
	@Autowired
	private IHosUserAccountTransService hosUserAccountTransService;

	/**
	 * 
	* @Title: index 
	* @Description: 登录页面 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/Bw8gJkU6YXj", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties3(request, model);
         
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/index" : "admin/index";
        return url;
	}
	
	/**
	 * 
	* @Title: login 
	* @Description: 登录页面
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties3(request, model);
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/login" : "admin/login";
        return url;	
		
	}	
	/**
	 * 
	* @Title: rePassword 
	* @Description: 重置密码页面
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/repassword", method = RequestMethod.GET)
	public String rePassword(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties3(request, model);
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/repassword" : "admin/repassword";
        return url;		
	}	
	
	@RequestMapping(value = "/validimg", method = RequestMethod.GET)
	public void verification( Model model, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			VerificationCodeUtil.getCoade(request.getSession(), response);
			String validCode = (String)session.getAttribute("verification");
			model.addAttribute("validCode", validCode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("获取系统验证码有误", e.getMessage());
		}
	}	
	


	/**
	 * 登录验证
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @author hhj@chuangke18.com
	 * @throws IOException
	 * @date 2018年06月10日 上午11:50:32
	 */
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	@ResponseBody
	public UserLoginResponse validate(Model model, HttpServletRequest request, UserLoginRequest loginRequest)
			throws IOException {
		HosUserDto hosUserDto = (HosUserDto)(loginRequest.getObj());
		String verification = hosUserDto.getValicode();
		UserLoginResponse userLoginResponse = new UserLoginResponse();
		
		// 校验验证码
		boolean valicode = valicode(verification, request);
		if (!valicode) {
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9992);
			userLoginResponse.setErrorMsg("验证码不正确！");
			return userLoginResponse;
		}
		// 登录操作
		try {
			// 调用service登录接口
			CKResponse cKResponse = hosUserService.login(hosUserDto);
			userLoginResponse.setErrorCode(cKResponse.getErrorCode());
			userLoginResponse.setErrorMsg(cKResponse.getErrorMsg());
			// 登录成功
			if (ConstantApi.ERROR_CODE.SUCCESS.equals(userLoginResponse.getErrorCode())) {
				HosUserVo hosUserVo = (HosUserVo) cKResponse.getObj();
				userLoginResponse.setHosUserVo(hosUserVo);

				session.setAttribute(AUTH_LIST, userLoginResponse.getAuthList());
				session.setAttribute(AUTH_USER, userLoginResponse.getHosUserVo());

				Cookie cookie = new Cookie("jforumUserInfo", URLEncoder.encode(hosUserDto.getPhone(), "utf-8"));
				cookie.setMaxAge(3600 * 24);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			return userLoginResponse;
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			userLoginResponse.setErrorMsg("数字转换错误");
			return userLoginResponse;
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			userLoginResponse.setErrorMsg("用户名或密码错误");
			return userLoginResponse;
		/*} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9999");
			userLoginResponse.setErrorMsg("用户名或密码错误");
			return userLoginResponse;*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			userLoginResponse.setErrorMsg("用户名或密码错误");
			return userLoginResponse;
		}
		
	}
	
	/**
	 * 忘记密码，修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ("/password"), method = RequestMethod.POST)
	@ResponseBody
	public CKResponse password(HosUserBo hosUserBo, HttpServletRequest request) {
		CKResponse cKResponse = new CKResponse();
		String valicode = hosUserBo.getValicode();
		
		// 校验验证码
		boolean success = valicode(valicode, request);
		if (!success) {
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9992);
			cKResponse.setErrorMsg("验证码不正确！");
			return cKResponse;
		}
		
		// 调用service修改密码接口
		cKResponse = hosUserService.password(hosUserBo);
		return cKResponse;
	}
	
	/**
	 * 查询交易信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ("/accountTrans"), method = RequestMethod.POST)
	@ResponseBody
	public Page<HosUserAccountTransVo> accountTrans(HttpServletRequest request) {
		Page<HosUserAccountTransVo> page = null;
		CKResponse cKResponse = new CKResponse();
		HosUserAccountTransDto dto = new HosUserAccountTransDto();
		HosUserVo hosUserVo = (HosUserVo) session.getAttribute(AUTH_USER);
		Long userId = hosUserVo.getId();
		if(userId != null){
			dto.setUserId(userId);
			int offset = Integer.parseInt(request.getParameter("offset"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			dto.setOffset(offset);
			dto.setLimit(limit);
			// 调用service查询交易记录
			cKResponse = hosUserAccountTransService.listPagingObjects(dto);
			if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
				page = (Page<HosUserAccountTransVo>) cKResponse.getObjPage();
			}
		}
		return page;
	}
	
	/**
	 * 
	* @Title: homepage 
	* @Description: 跳转到主页 
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	/*@RequestMapping(value = "homepage", method = RequestMethod.GET)
	public String homepage(Model model, HttpServletRequest request) {
		// model.addAttribute("a", 1);
		return "admin/homepage";
	}*/	

	/**
	 * 
	* @Title: logOut 
	* @Description: 登出
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	/*@RequestMapping(value = "logOut1", method = RequestMethod.GET)
	public ModelAndView logOut1(Model model,HttpServletRequest request) {
		session.removeAttribute(AUTH_LIST);
		session.removeAttribute(AUTH_USER);
		return new ModelAndView("redirect:/admin/Bw8gJkU6YXj");
	}*/
	@RequestMapping(value = "logOut", method = RequestMethod.GET)
	public String logOut(Model model,HttpServletRequest request) {
		session.removeAttribute(AUTH_LIST);
		session.removeAttribute(AUTH_USER);
		//此处要加wap参数
		setModelProperties1(model);
		setModelProperties3(request, model);
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/index" : "admin/index";
		return url;
	}	

	/**
	 * 
	* @Title: loginAgain 
	* @Description: 重新跳转到登录 
	* @param @return  参数说明 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping(value = "loginAgain", method = RequestMethod.GET)
	public ModelAndView loginAgain() {
		session.removeAttribute(AUTH_USER);
		//此处要加wap参数
		return new ModelAndView("redirect:/admin/Bw8gJkU6YXj");
	}
	
	/**
	 * 
	*Title: export
	*Description: 下载白皮书
	* @param infile
	* @param downloadFile
	* @throws Exception 
	* @see io.eoshos.pc.controller.BaseController#export(java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = "downloadwhitepaper", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadWhitePaper(HttpServletRequest request) throws Exception {
		String lang = request.getParameter("language");
		String fileName = StringUtils.equals(lang, "zh") ? "EOSHOS_WP_V1.0_Chinese.pdf" : "EOSHOS_WP_V1.0_English.pdf";
		String filePath = request.getSession().getServletContext().getRealPath("whitepaper") + File.separator;
		File file = new File(filePath + fileName);
		InputStream ins = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		ous.write(buffer);
		ous.flush();
		ous.close();
	}	
	
	@RequestMapping(value = "/validGraphicCode", method = RequestMethod.POST)
	@ResponseBody
	public int validGraphicCode(HttpServletRequest request){
		String validCode = request.getParameter("validCode");
		HttpSession session = request.getSession();    
		int result = StringUtils.equals(((String)session.getAttribute("verification")).toLowerCase(), 
				validCode.toLowerCase()) ? 1 : 0;
		return result;
	}
	
	/*
	//当浏览器出现兼容性问题时跳转页面
	@RequestMapping("/browserHit")
	public String browserHit(HttpServletRequest request){
		return "admin/browserHit";
	}
	
	@RequestMapping("/system/login/loginLog")
	public String loginLog(HttpServletRequest request){
		return "admin/systemInfo/logList";
	}*/
	

}
