package io.eoshos.pc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.request.UserLoginRequest;
import io.eoshos.core.api.response.UserLoginResponse;
import io.eoshos.pc.util.StringUtil;
import io.eoshos.pc.util.VerificationCodeUtil;

/***
 * 
 * @ClassName SystemInfoController
 * @Description
 * @author zyx@chuangke18.com
 * @Date 2016年10月28日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class SystemController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(SystemController.class);
	
	private static String AUTH_USER = "auth_user";
	
	private static String AUTH_LIST = "auth_list";

	//@Autowired
	//private ISysUserService sysUserService;
	
	//@Autowired
	//private ISysLoginLog sysLoginLog;

	/**
	 * 进入登录界面
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @author zyx@chuangke18.com
	 * @date 2016年10月9日 下午4:11:41
	 */
	/*@RequestMapping(value = "/Bw8gJkU6YXj", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		
		return "admin/login";
	}*/


	/**
	 * 登录
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @author zyx@chuangke18.com
	 * @throws IOException
	 * @date 2016年10月10日 上午11:50:32
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public UserLoginResponse loginIndex(Model model, HttpServletRequest request, UserLoginRequest loginRequest)
			throws IOException {
		HosUserDto hosUserDto = (HosUserDto)(loginRequest.getObj());
		String verification = hosUserDto.getValicode();
		UserLoginResponse userLoginResponse = new UserLoginResponse();
		
		if (StringUtil.isBlank(verification)) {
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			userLoginResponse.setErrorMsg("验证码不能为空！");
			return userLoginResponse;
		} else {
			HttpSession session = request.getSession();
			String v1 = verification.toLowerCase();
			String vo = (String) session.getAttribute("verification");
			if (v1!=null &&vo !=null&& !v1.equals(vo.toLowerCase())) {
				userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				userLoginResponse.setErrorMsg("验证码不正确！");
				return userLoginResponse;
			} else if(v1==null || vo ==null){
				userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				userLoginResponse.setErrorMsg("验证码不正确！");
				return userLoginResponse;
			}
		}
		
		try {
			///String clientIp = getIp(request);
			///loginRequest.setClientIp(clientIp);
			////userLoginResponse = sysUserService.login(loginRequest);
			if ("0000".equals(userLoginResponse.getErrorCode())) {
				session.setAttribute(AUTH_LIST, userLoginResponse.getAuthList());
				session.setAttribute(AUTH_USER, userLoginResponse.getHosUserVo());
				
				Cookie cookie = new Cookie("jforumUserInfo", URLEncoder.encode(hosUserDto.getPhone(), "utf-8"));
				cookie.setMaxAge(3600 * 24);
				cookie.setPath("/");
				response.addCookie(cookie);
			} else if ("6666".equals(userLoginResponse.getErrorCode())) {
				session.setAttribute(AUTH_USER, userLoginResponse.getHosUserVo());
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
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR");
			userLoginResponse.setErrorMsg("用户名或密码错误");
			return userLoginResponse;*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			userLoginResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			userLoginResponse.setErrorMsg("用户名或密码错误");
			return userLoginResponse;
		}
		
	}

	@SuppressWarnings({ "null", "static-access" })
	private String getIp(HttpServletRequest request) {
		String clientIp =request.getHeader("x-forwarded-for");  
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) { 
			clientIp = request.getHeader("Proxy-Client-IP"); 
		    } 
		    if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) { 
		    	clientIp = request.getHeader("WL-Proxy-Client-IP"); 
		    } 
		    if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) { 
		    	clientIp = request.getHeader("HTTP_CLIENT_IP"); 
		    } 
		    if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) { 
		    	clientIp = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		    } 
		    if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) { 
		    	clientIp = request.getRemoteAddr(); 
		    } 
		return clientIp;
	}

	@RequestMapping(value = "logOut", method = RequestMethod.GET)
	//@ResponseBody
	public ModelAndView logOut(Model model,HttpServletRequest request) {
		session.removeAttribute(AUTH_LIST);
		session.removeAttribute(AUTH_USER);
		return new ModelAndView("redirect:/admin/Bw8gJkU6YXj");
	}

	@RequestMapping(value = "loginAgain", method = RequestMethod.GET)
	public ModelAndView loginAgain() {
		session.removeAttribute(AUTH_USER);
		return new ModelAndView("redirect:/admin/Bw8gJkU6YXj");
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 * @author zyx@chuangke18.com
	 * @date 2016年10月10日 上午11:50:32
	 */
	@RequestMapping(value = "indexInfo", method = RequestMethod.GET)
	public String indexInfo(Model model, HttpServletRequest request) {
		// model.addAttribute("a", 1);
		return "admin/adminIndex";
	}


	@RequestMapping(value = "/validimg", method = RequestMethod.GET)
	public void verification( Model model, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			VerificationCodeUtil.getCoade(request.getSession(), response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("获取系统验证码有误", e.getMessage());
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/file/download")
	public String download(String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String filePath = request.getParameter("path");
		fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		try {
			String path = request.getSession().getServletContext().getRealPath(filePath);
			System.out.println("\n~~~下载文件为:" + path + "~~~\n\n");
			InputStream inputStream = new FileInputStream(new File(path));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			logger.error("读取文件出错：" + e.getCause());
			logger.error(e.getMessage() ,e);
		} catch (IOException e) {
			logger.error(e.getMessage() ,e);
			logger.error("IO异常：" + e.getCause());
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;
	}

	@RequestMapping("/updatePwdIndex")
	public String updatePwdIndex(HttpServletRequest request) {
		return "admin/systemInfo/updatePwd";
	}

	@RequestMapping(value = "longConnect", method = RequestMethod.POST)
	@ResponseBody
	public String longConnect(HttpServletRequest request) {
		logger.info("------------每隔15分钟访问一次后台，保持session的有效时间------------------");
		return "connected";
	}

	/**
	 * 当浏览器出现兼容性问题时跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/browserHit")
	public String browserHit(HttpServletRequest request){
		return "admin/browserHit";
	}
	
	@RequestMapping("/system/login/loginLog")
	public String loginLog(HttpServletRequest request){
		return "admin/systemInfo/logList";
	}
	
	/*@RequestMapping(value="/login/logList")
	@ResponseBody
	public Page<LoginLogDto> listCustomer(HttpServletRequest request){
		LoginLogDto loginLogDto = new LoginLogDto();
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		if(StringUtil.isNotEmpty(beginDate)){
			loginLogDto.setBeginDate(beginDate);
		}
		if(StringUtil.isNotEmpty(endDate)){
			loginLogDto.setEndDate(endDate+" 23:59:59");
		}
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		
		Page<LoginLogDto> page = null;
		try {
			page = sysLoginLog.listLog(offset, limit, loginLogDto);
		} catch (SQLException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("查询登录日志失败：---:" );
			sb.append(e.getMessage());
			sb.append(e.getCause());
			sb.append("----:");
			sb.append(loginLogDto.toString());
			logger.error(sb.toString());
		}
		return page;
	}*/
}
