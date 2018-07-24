package io.eoshos.pc.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.octo.captcha.service.CaptchaServiceException;

import io.eoshos.core.api.bean.ExceptionCommonBean;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.pc.util.DateUtil;
import io.eoshos.pc.util.ExceptionUtil;
import io.eoshos.pc.util.NumberUtil;
import io.eoshos.pc.util.StringUtil;
import io.eoshos.pc.util.jcaptcha.CaptchaServiceSingleton;

@Controller
public class BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(BaseController.class);
	
	private static String AUTH_USER = "auth_user";
	
	/* 每页显示条数 */
	protected int pageLimit = 15;

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected HttpSession session;

	protected ModelAndView view;

	public BaseController() {
		view = new ModelAndView();
		this.init();
	}

	/**
	 * 返回请求数据(未过滤)
	 * 
	 * @param key
	 * @return String
	 */
	protected String getRequestOriginalData(String key) {
		return request.getParameter(key);
	}

	/**
	 * 返回请求数据(html标签过滤)
	 * 
	 * @param key
	 * @return
	 */
	protected String getRequestData(String key) {
		return filterDangerString(request.getParameter(key));
	}

	/**
	 * 过滤html标签
	 * 
	 * @param value
	 * @return
	 */
	private String filterDangerString(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = value.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		return str.replaceAll("[(/>)<]", "");
	}

	/**
	 * 设置顶部选中
	 * 
	 * @param menu
	 */
	protected void setTopMenu(String menu) {
		// logger.info("--------------------");
		view.addObject("topmenu", menu);
	}

	/**
	 * 设置左边选中
	 * 
	 * @param menu
	 */
	protected void setLeftMenu(String menu) {
		view.addObject("leftmenu", menu);
	}

	public void init() {
		//System.out.println("x");
	}

	/**
	 * 返回ModelAndView对象
	 * 
	 * @return
	 */
	protected ModelAndView getViewModel() {
		return view;
	}

	/**
	 * @Description: 输出JSON
	 * @param json
	 * @throws IOException
	 * @date 2016年1月11日 下午5:45:25
	 */
	protected void printJson(String json) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	protected int paramInt(String str) {
		return NumberUtil.getInt(request.getParameter(str));
	}

	protected long paramLong(String str) {
		return NumberUtil.getLong(request.getParameter(str));
	}

	protected double paramDouble(String str) {
		return NumberUtil.getDouble(request.getParameter(str));
	}

	protected String paramString(String str) {
		return StringUtil.isNull(request.getParameter(str));
	}

	/**
	 * 获取cookie信息
	 * 
	 * @param cookieName
	 * @return
	 */
	protected String getCookieValue(String cookieName) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	/**
	 * 将时间格式改为int类型
	 * 
	 * @param str
	 * @return
	 * @author zyx@chuangke18.com
	 * @date 2016年8月28日
	 */
	protected String parseDate2String(String str) {
		return NumberUtil.dateToInt(request.getParameter(str));
	}

	/**
	 * 
	 * @Description 将页面传来的时间格式转换为时间戳
	 * @Author zyx@chuangke18.com
	 * @Date 2016年8月29日 下午3:00:31
	 * @param str
	 * @param format
	 * @param flag
	 * @return
	 * @throws ParseException
	 */
	protected String paramUnixTimeStamp(String str, String format, int flag) throws ParseException {
		// 匹配时间类型，使用正则。主要是为 避免产生parseException的异常
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String dateStr = request.getParameter(str);
		if (dateStr != null && !"".equals(dateStr)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(dateStr);
			if (match.matches()) {
				dateStr += " 00:00:00";
			}
			return DateUtil.parseUnixTimeStampString(dateStr, format, flag);
		}
		return null;
	}

	/**
	 * 
	 * @Description 得到数字类型的参数
	 * @Author zyx@chuangke18.com
	 * @Date 2016年8月30日 下午2:57:29
	 * @param str
	 *            参数字段
	 * @return
	 */
	protected String paramNumber(String str) {
		if (StringUtil.assertNumber(request.getParameter(str))) {
			return str;
		}
		return null;
	}

	/**
	 * 
	 * @Description:如果得到的时间为空，则转化为当天时间
	 * @param str
	 * @return
	 * @throws ParseException
	 * @Author zyx@chuangke18.com
	 * @Date 2016年9月8日 下午2:40:21
	 */
	protected String parseToSimpleDate(String str) throws ParseException {
		String birthday = request.getParameter(str);
		if (birthday == null || "".equals(birthday)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.MMDD_DATE);
			Date date = new Date();
			birthday = sdf.format(date);
		}
		return birthday;
	}

	/**
	 * 如果输入的日期为空，则转化为一个特殊的标识
	 * 
	 * @param str
	 * @return
	 * @Author zyx@chuangke18.com
	 * @Date 2016年9月8日 下午7:14:02
	 */
	protected String parseToYMDDate(String str) {
		String registerday = request.getParameter(str);
		if (registerday == null || "".equals(registerday)) {
			registerday = "0";
		}
		return registerday;
	}

	protected void genernateCaptchaImage() throws IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		try {
			String captchaId = request.getSession(true).getId();
			BufferedImage challenge = (BufferedImage) CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId,
					request.getLocale());
			ImageIO.write(challenge, "jpg", out);
			out.flush();
		} catch (CaptchaServiceException e) {

		} finally {
			out.close();
		}
	}

	/**
	 * 得到session中的值
	 * 
	 * @return
	 */
	protected HosUserVo getUser() {
		return (HosUserVo) session.getAttribute(AUTH_USER);
	}

	protected void export(String infile, String downloadFile) throws Exception {
		File inFile = new File(infile);
		InputStream ins = new BufferedInputStream(new FileInputStream(infile));
		byte[] buffer = new byte[ins.available()];
		ins.read(buffer);
		ins.close();
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(downloadFile.getBytes()));
		response.addHeader("Content-Length", "" + inFile.length());
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		ous.write(buffer);
		ous.flush();
		ous.close();
	}

	/**
	 * 
	 * @param logger
	 * @param throwable
	 */
	protected void logError(Logger logger, Throwable throwable) {
		logger.error(throwable.getMessage(), throwable);
		ExceptionCommonBean exceptionCommonBean = ExceptionUtil.logException(throwable);
	}
	
/*	*//**
	 * 生成校验码
	 * @param name
	 *//*
	protected String saveToken(String name){
		   String token=TokenHelper.generateGUID();
		   session.put(name, token);
		   return token;
	   }*/
	
    /**
     * 从国际化资源配置文件中根据key获取value  方法一
     * @param request
     * @param key
     * @return
     */
	protected static String getMessage(HttpServletRequest request, String key){
        Locale currentLocale = RequestContextUtils.getLocale(request);
        String lang = currentLocale.getLanguage();
        ResourceBundle bundle = ResourceBundle.getBundle("messages_"+lang, currentLocale);
        String message = bundle.getString(key);
        try {
			message = new String(message.getBytes("ISO-8859-1"), "UTF-8");  
		} catch (UnsupportedEncodingException e) {
			logger.error("读资源错误:" + e.getMessage());
		}  
        return message;
    }
    /**
     * 从国际化资源配置文件中根据key获取value  方法二
     * @param request
     * @param key
     * @return
     */
	protected static String getMessage2(HttpServletRequest request, String key){
        RequestContext requestContext = new RequestContext(request);
        String message = requestContext.getMessage(key);
        try {
			message = new String(message.getBytes("ISO-8859-1"), "UTF-8");  
		} catch (UnsupportedEncodingException e) {
			logger.error("读资源错误:" + e.getMessage());
		} 
        return message;
    }	
	
	/**
	 * 
	* @Title: setModelProperties1 
	* @Description: 设置公共的双语信息
	* @param @param model  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	protected void setModelProperties1(Model model){
		String common1 = getMessage(request, "common1");
		String common2 = getMessage(request, "common2");
		String common3_1 = getMessage(request, "common3_1");
		String common3_2 = getMessage(request, "common3_2");
		String common4 = getMessage(request, "common4");
		String common5_1 = getMessage(request, "common5_1");
		String common5_2 = getMessage(request, "common5_2");
		String common5_3 = getMessage(request, "common5_3");
		String common5_4 = getMessage(request, "common5_4");
		String common5_5 = getMessage(request, "common5_5");
		String common6_1 = getMessage(request, "common6_1");
		String common6_2 = getMessage(request, "common6_2");
		String common6_3 = getMessage(request, "common6_3");
		String common6_4 = getMessage(request, "common6_4");
		String common6_5 = getMessage(request, "common6_5");		
		String common7 = getMessage(request, "common7");		
		String common8 = getMessage(request, "common8");		
		String common9 = getMessage(request, "common9");		
		String common10 = getMessage(request, "common10");		
		String common11 = getMessage(request, "common11");		
		String common12 = getMessage(request, "common12");		
		String common12_1 = getMessage(request, "common12_1");		
		String common12_2 = getMessage(request, "common12_2");		
		String common13 = getMessage(request, "common13");		
		String common14 = getMessage(request, "common14");		
		String common15 = getMessage(request, "common15");		
		String common16 = getMessage(request, "common16");		
		String common17 = getMessage(request, "common17");		
		String common17_1 = getMessage(request, "common17_1");		
		String common18_1 = getMessage(request, "common18_1");		
		String common18_2 = getMessage(request, "common18_2");		
		String common18_3 = getMessage(request, "common18_3");		
		String common19 = getMessage(request, "common19");		
		String common20 = getMessage(request, "common20");		
		String common20_1 = getMessage(request, "common20_1");		
		String common21 = getMessage(request, "common21");		
		String common21_1 = getMessage(request, "common21_1");		
		String common21_2 = getMessage(request, "common21_2");		
		String common21_3 = getMessage(request, "common21_3");		
		String common22 = getMessage(request, "common22");		
		String common22_1 = getMessage(request, "common22_1");		
		String common22_2 = getMessage(request, "common22_2");		
		String common22_3 = getMessage(request, "common22_3");	
		String common30 = getMessage(request, "common30");
		String common31 = getMessage(request, "common31");
		String common32 = getMessage(request, "common32");
		String common33 = getMessage(request, "common33");
		String common34 = getMessage(request, "common34");
		String common35 = getMessage(request, "common35");
		String common36 = getMessage(request, "common36");
		String common37 = getMessage(request, "common37");
		String common38 = getMessage(request, "common38");
		String common39 = getMessage(request, "common39");
		String common40 = getMessage(request, "common40");
		String common41 = getMessage(request, "common41");
		String common42 = getMessage(request, "common42");
		String common43 = getMessage(request, "common43");
		String common44 = getMessage(request, "common44");
		String common45 = getMessage(request, "common45");
		
		model.addAttribute("common1", common1);
        model.addAttribute("common2", common2);
        model.addAttribute("common3_1", common3_1);
        model.addAttribute("common3_2", common3_2);
        model.addAttribute("common4", common4);
        model.addAttribute("common5_1", common5_1);
        model.addAttribute("common5_2", common5_2);
        model.addAttribute("common5_3", common5_3);
        model.addAttribute("common5_4", common5_4);
        model.addAttribute("common5_5", common5_5);
        model.addAttribute("common6_1", common6_1);
        model.addAttribute("common6_2", common6_2);        
        model.addAttribute("common6_3", common6_3);        
        model.addAttribute("common6_4", common6_4);        
        model.addAttribute("common6_5", common6_5);        
        model.addAttribute("common7", common7);        
        model.addAttribute("common8", common8);        
        model.addAttribute("common9", common9);        
        model.addAttribute("common10", common10);        
        model.addAttribute("common11", common11);        
        model.addAttribute("common12", common12);        
        model.addAttribute("common12_1", common12_1);        
        model.addAttribute("common12_2", common12_2);        
        model.addAttribute("common13", common13);        
        model.addAttribute("common14", common14);        
        model.addAttribute("common15", common15);        
        model.addAttribute("common16", common16);        
        model.addAttribute("common17", common17);        
        model.addAttribute("common17_1", common17_1);        
        model.addAttribute("common18_1", common18_1);        
        model.addAttribute("common18_2", common18_2);        
        model.addAttribute("common18_3", common18_3);        
        model.addAttribute("common19", common19);  
        model.addAttribute("common20", common20);        
        model.addAttribute("common20_1", common20_1);    
        model.addAttribute("common21", common21);        
        model.addAttribute("common21_1", common21_1);          
        model.addAttribute("common21_2", common21_2);          
        model.addAttribute("common21_3", common21_3);  
        model.addAttribute("common22", common22);        
        model.addAttribute("common22_1", common22_1);          
        model.addAttribute("common22_2", common22_2);          
        model.addAttribute("common22_3", common22_3);  
        model.addAttribute("common30", common30); 
        model.addAttribute("common31", common31); 
        model.addAttribute("common32", common32); 
        model.addAttribute("common33", common33); 
        model.addAttribute("common34", common34); 
        model.addAttribute("common35", common35); 
        model.addAttribute("common36", common36); 
        model.addAttribute("common37", common37); 
        model.addAttribute("common38", common38); 
        model.addAttribute("common39", common39); 
        model.addAttribute("common40", common40); 
        model.addAttribute("common41", common41);
        model.addAttribute("common42", common42);
        model.addAttribute("common43", common43);
        model.addAttribute("common44", common44);
        model.addAttribute("common45", common45);
	}
	
	/**
	 * 
	* @Title: setModelProperties2 
	* @Description: 设置主页的双语信息
	* @param @param model  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	protected void setModelProperties2(Model model){
		String homepage_1 = getMessage(request, "homepage_1");
		String homepage_1_1 = getMessage(request, "homepage_1_1");
		String homepage_1_2 = getMessage(request, "homepage_1_2");
		String homepage_1_3 = getMessage(request, "homepage_1_3");
		String homepage_2 = getMessage(request, "homepage_2");
		String homepage_2_1 = getMessage(request, "homepage_2_1");
		String homepage_2_2 = getMessage(request, "homepage_2_2");
		String homepage_2_3 = getMessage(request, "homepage_2_3");
		String homepage_3 = getMessage(request, "homepage_3");
		String homepage_4 = getMessage(request, "homepage_4");
		String homepage_5 = getMessage(request, "homepage_5");
		String homepage_6 = getMessage(request, "homepage_6");
		String homepage_7 = getMessage(request, "homepage_7");
		String homepage_8 = getMessage(request, "homepage_8");
		String homepage_9 = getMessage(request, "homepage_9");
		String homepage_9_1 = getMessage(request, "homepage_9_1");
		String homepage_9_2 = getMessage(request, "homepage_9_2");
		String homepage_9_3 = getMessage(request, "homepage_9_3");
		String homepage_9_4 = getMessage(request, "homepage_9_4");
		String homepage_10 = getMessage(request, "homepage_10");
		String homepage_11 = getMessage(request, "homepage_11");
		String homepage_12 = getMessage(request, "homepage_12");
		String homepage_12_1 = getMessage(request, "homepage_12_1");
		String homepage_13 = getMessage(request, "homepage_13");
		String homepage_14 = getMessage(request, "homepage_14");
		String homepage_15 = getMessage(request, "homepage_15");
		String homepage_16 = getMessage(request, "homepage_16");
		String homepage_17 = getMessage(request, "homepage_17");
		String homepage_17_1 = getMessage(request, "homepage_17_1");
		String homepage_18_1 = getMessage(request, "homepage_18_1");
		String homepage_18_2 = getMessage(request, "homepage_18_2");
		String homepage_18_3 = getMessage(request, "homepage_18_3");
		String homepage_18_4 = getMessage(request, "homepage_18_4");
		String homepage_18_5 = getMessage(request, "homepage_18_5");
		String homepage_18_6 = getMessage(request, "homepage_18_6");
		String homepage_18_7 = getMessage(request, "homepage_18_7");
		String homepage_19_1 = getMessage(request, "homepage_19_1");
		String homepage_19_2 = getMessage(request, "homepage_19_2");
		String homepage_20 = getMessage(request, "homepage_20");
		String homepage_21 = getMessage(request, "homepage_21");
		String homepage_22 = getMessage(request, "homepage_22");
		String homepage_23 = getMessage(request, "homepage_23");
		String homepage_24 = getMessage(request, "homepage_24");
		String homepage_25 = getMessage(request, "homepage_25");
		String homepage_26 = getMessage(request, "homepage_26");
		String homepage_27 = getMessage(request, "homepage_27");
		String homepage_28 = getMessage(request, "homepage_28");
		String homepage_29 = getMessage(request, "homepage_29");
		String homepage_30 = getMessage(request, "homepage_30");
		String homepage_31 = getMessage(request, "homepage_31");
		String homepage_32 = getMessage(request, "homepage_32");
		String homepage_33 = getMessage(request, "homepage_33");
		String homepage_34 = getMessage(request, "homepage_34");
		String homepage_35 = getMessage(request, "homepage_35");
		String homepage_36 = getMessage(request, "homepage_36");
		String homepage_37 = getMessage(request, "homepage_37");
		String homepage_38 = getMessage(request, "homepage_38");
		String homepage_39 = getMessage(request, "homepage_39");
		String homepage_40 = getMessage(request, "homepage_40");
		String homepage_41 = getMessage(request, "homepage_41");
		String taskpage_0 = getMessage(request, "taskpage_0");
		String taskpage_1_1 = getMessage(request, "taskpage_1_1");
		String taskpage_1_2 = getMessage(request, "taskpage_1_2");
		String taskpage_2_1 = getMessage(request, "taskpage_2_1");
		String taskpage_2_2_1 = getMessage(request, "taskpage_2_2_1");
		String taskpage_2_2_2 = getMessage(request, "taskpage_2_2_2");
		String taskpage_2_2_3 = getMessage(request, "taskpage_2_2_3");
		String taskpage_3_1 = getMessage(request, "taskpage_3_1");
		String taskpage_3_2 = getMessage(request, "taskpage_3_2");
		String taskpage_4_1 = getMessage(request, "taskpage_4_1");
		String taskpage_4_2 = getMessage(request, "taskpage_4_2");
		String taskpage_5_1 = getMessage(request, "taskpage_5_1");
		String taskpage_5_2 = getMessage(request, "taskpage_5_2");
		String invitationpage_1 = getMessage(request, "invitationpage_1");
		String invitationpage_2_1 = getMessage(request, "invitationpage_2_1");
		String invitationpage_2_2 = getMessage(request, "invitationpage_2_2");
		String invitationpage_2_3 = getMessage(request, "invitationpage_2_3");
		String invitationpage_3_1 = getMessage(request, "invitationpage_3_1");
		String invitationpage_3_2 = getMessage(request, "invitationpage_3_2");
		String invitationpage_3_3 = getMessage(request, "invitationpage_3_3");
		String invitationpage_3_4 = getMessage(request, "invitationpage_3_4");
		
		model.addAttribute("homepage_1", homepage_1);
		model.addAttribute("homepage_1_1", homepage_1_1);
		model.addAttribute("homepage_1_2", homepage_1_2);
		model.addAttribute("homepage_1_3", homepage_1_3);
		model.addAttribute("homepage_2", homepage_2);
		model.addAttribute("homepage_2_1", homepage_2_1);
		model.addAttribute("homepage_2_2", homepage_2_2);
		model.addAttribute("homepage_2_3", homepage_2_3);
		model.addAttribute("homepage_3", homepage_3);
		model.addAttribute("homepage_4", homepage_4);
		model.addAttribute("homepage_5", homepage_5);
		model.addAttribute("homepage_6", homepage_6);
		model.addAttribute("homepage_7", homepage_7);
		model.addAttribute("homepage_8", homepage_8);
		model.addAttribute("homepage_9", homepage_9);
		model.addAttribute("homepage_9_1", homepage_9_1);
		model.addAttribute("homepage_9_2", homepage_9_2);
		model.addAttribute("homepage_9_3", homepage_9_3);
		model.addAttribute("homepage_9_4", homepage_9_4);
		model.addAttribute("homepage_10", homepage_10);
		model.addAttribute("homepage_11", homepage_11);
		model.addAttribute("homepage_12", homepage_12);
		model.addAttribute("homepage_12_1", homepage_12_1);
		model.addAttribute("homepage_13", homepage_13);
		model.addAttribute("homepage_14", homepage_14);
		model.addAttribute("homepage_15", homepage_15);
		model.addAttribute("homepage_16", homepage_16);
		model.addAttribute("homepage_17", homepage_17);
		model.addAttribute("homepage_17_1", homepage_17_1);
		model.addAttribute("homepage_18_1", homepage_18_1);
		model.addAttribute("homepage_18_2", homepage_18_2);
		model.addAttribute("homepage_18_3", homepage_18_3);
		model.addAttribute("homepage_18_4", homepage_18_4);
		model.addAttribute("homepage_18_5", homepage_18_5);
		model.addAttribute("homepage_18_6", homepage_18_6);
		model.addAttribute("homepage_18_7", homepage_18_7);
		model.addAttribute("homepage_19_1", homepage_19_1);
		model.addAttribute("homepage_19_2", homepage_19_2);
		model.addAttribute("homepage_20", homepage_20);
		model.addAttribute("homepage_21", homepage_21);
		model.addAttribute("homepage_22", homepage_22);
		model.addAttribute("homepage_23", homepage_23);
		model.addAttribute("homepage_24", homepage_24);
		model.addAttribute("homepage_25", homepage_25);
		model.addAttribute("homepage_26", homepage_26);
		model.addAttribute("homepage_27", homepage_27);
		model.addAttribute("homepage_28", homepage_28);
		model.addAttribute("homepage_29", homepage_29);
		model.addAttribute("homepage_30", homepage_30);
		model.addAttribute("homepage_31", homepage_31);
		model.addAttribute("homepage_32", homepage_32);
		model.addAttribute("homepage_33", homepage_33);
		model.addAttribute("homepage_34", homepage_34);
		model.addAttribute("homepage_35", homepage_35);
		model.addAttribute("homepage_36", homepage_36);
		model.addAttribute("homepage_37", homepage_37);
		model.addAttribute("homepage_38", homepage_38);
		model.addAttribute("homepage_39", homepage_39);
		model.addAttribute("homepage_40", homepage_40);
		model.addAttribute("homepage_41", homepage_41);
		model.addAttribute("taskpage_0", taskpage_0);
		model.addAttribute("taskpage_1_1", taskpage_1_1);
		model.addAttribute("taskpage_1_2", taskpage_1_2);
		model.addAttribute("taskpage_2_1", taskpage_2_1);
		model.addAttribute("taskpage_2_2_1", taskpage_2_2_1);
		model.addAttribute("taskpage_2_2_2", taskpage_2_2_2);
		model.addAttribute("taskpage_2_2_3", taskpage_2_2_3);
		model.addAttribute("taskpage_3_1", taskpage_3_1);
		model.addAttribute("taskpage_3_2", taskpage_3_2);
		model.addAttribute("taskpage_4_1", taskpage_4_1);
		model.addAttribute("taskpage_4_2", taskpage_4_2);
		model.addAttribute("taskpage_5_1", taskpage_5_1);
		model.addAttribute("taskpage_5_2", taskpage_5_2);
		model.addAttribute("invitationpage_1", invitationpage_1);
		model.addAttribute("invitationpage_2_1", invitationpage_2_1);
		model.addAttribute("invitationpage_2_2", invitationpage_2_2);
		model.addAttribute("invitationpage_2_3", invitationpage_2_3);
		model.addAttribute("invitationpage_3_1", invitationpage_3_1);
		model.addAttribute("invitationpage_3_2", invitationpage_3_2);
		model.addAttribute("invitationpage_3_3", invitationpage_3_3);
		model.addAttribute("invitationpage_3_4", invitationpage_3_4);
	}
	
	
	protected void setModelProperties3(HttpServletRequest request, Model model){
        Locale currentLocale = RequestContextUtils.getLocale(request);
        String lang = currentLocale.getLanguage();
        model.addAttribute("localLang", lang);
        
        String homepage_1_1 = getMessage(request, "homepage_1_1");
        String homepage_1_2 = getMessage(request, "homepage_1_2");
        String homepage_1_3 = getMessage(request, "homepage_1_3");
        String homepage_37 = getMessage(request, "homepage_37");
        String withdraw_14 = getMessage(request, "withdraw_14");
        
        model.addAttribute("homepage_1_1", homepage_1_1);
        model.addAttribute("homepage_1_2", homepage_1_2);
        model.addAttribute("homepage_1_3", homepage_1_3);
        model.addAttribute("homepage_37", homepage_37);
        model.addAttribute("withdraw_14", withdraw_14);
	}    
	
	/**
	 * 
	* @Title: setModelProperties4 
	* @Description: 设置认证页的双语信息
	* @param @param model  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	protected void setModelProperties4(Model model){
		String verification_0 = getMessage(request, "verification_0");
		String verification_1 = getMessage(request, "verification_1");
		String verification_2 = getMessage(request, "verification_2");
		String verification_3 = getMessage(request, "verification_3");
		String verification_4 = getMessage(request, "verification_4");
		String verification_5 = getMessage(request, "verification_5");
		String verification_6 = getMessage(request, "verification_6");
		String verification_7 = getMessage(request, "verification_7");
		String verification_8 = getMessage(request, "verification_8");
		String verification_9 = getMessage(request, "verification_9");
		String verification_10 = getMessage(request, "verification_10");
		String verification_11 = getMessage(request, "verification_11");
		String verification_12 = getMessage(request, "verification_12");
		String verification_13 = getMessage(request, "verification_13");
		String verification_14 = getMessage(request, "verification_14");
		String verification_15 = getMessage(request, "verification_15");
		String verification_16 = getMessage(request, "verification_16");
		String verification_17 = getMessage(request, "verification_17");
		String verification_18 = getMessage(request, "verification_18");
		String verification_19 = getMessage(request, "verification_19");
		String verification_20 = getMessage(request, "verification_20");
		String verification_21 = getMessage(request, "verification_21");
		String verification_22 = getMessage(request, "verification_22");

		model.addAttribute("verification_0", verification_0);  
		model.addAttribute("verification_1", verification_1);  
		model.addAttribute("verification_2", verification_2);  
		model.addAttribute("verification_3", verification_3);  
		model.addAttribute("verification_4", verification_4);  
		model.addAttribute("verification_5", verification_5);  
		model.addAttribute("verification_6", verification_6);  
		model.addAttribute("verification_7", verification_7);  
		model.addAttribute("verification_8", verification_8);  
		model.addAttribute("verification_9", verification_9);  
		model.addAttribute("verification_10", verification_10);
		model.addAttribute("verification_11", verification_11);
		model.addAttribute("verification_12", verification_12);
		model.addAttribute("verification_13", verification_13);
		model.addAttribute("verification_14", verification_14);
		model.addAttribute("verification_15", verification_15);
		model.addAttribute("verification_16", verification_16);
		model.addAttribute("verification_17", verification_17);
		model.addAttribute("verification_18", verification_18);
		model.addAttribute("verification_19", verification_19);
		model.addAttribute("verification_20", verification_20);
		model.addAttribute("verification_21", verification_21);
		model.addAttribute("verification_22", verification_22);
	}
	
	/**
	 * 
	* @Title: setModelProperties5 
	* @Description: 设置提幣页的双语信息
	* @param @param model  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	protected void setModelProperties5(Model model){
		String withdraw_1 = getMessage(request, "withdraw_1");
		String withdraw_2 = getMessage(request, "withdraw_2");
		String withdraw_3 = getMessage(request, "withdraw_3");
		String withdraw_4 = getMessage(request, "withdraw_4");
		String withdraw_5 = getMessage(request, "withdraw_5");
		String withdraw_6 = getMessage(request, "withdraw_6");
		String withdraw_7 = getMessage(request, "withdraw_7");
		String withdraw_8 = getMessage(request, "withdraw_8");
		String withdraw_9 = getMessage(request, "withdraw_9");
		String withdraw_10 = getMessage(request, "withdraw_10");
		String withdraw_11 = getMessage(request, "withdraw_11");
		String withdraw_12 = getMessage(request, "withdraw_12");
		String withdraw_13 = getMessage(request, "withdraw_13");
		String withdraw_14 = getMessage(request, "withdraw_14");
		String withdraw_15 = getMessage(request, "withdraw_15");
		String withdraw_16 = getMessage(request, "withdraw_16");
		String withdraw_17 = getMessage(request, "withdraw_17");
		String withdraw_18 = getMessage(request, "withdraw_18");
		String withdraw_19 = getMessage(request, "withdraw_19");
		String withdraw_20 = getMessage(request, "withdraw_20");
		String withdraw_21 = getMessage(request, "withdraw_21");
		String withdraw_22 = getMessage(request, "withdraw_22");
		String withdraw_23 = getMessage(request, "withdraw_23");
		String withdraw_24 = getMessage(request, "withdraw_24");
		String withdraw_25 = getMessage(request, "withdraw_25");
		String withdraw_26 = getMessage(request, "withdraw_26");
		String withdraw_27 = getMessage(request, "withdraw_27");
		String withdraw_28 = getMessage(request, "withdraw_28");
		String withdraw_29 = getMessage(request, "withdraw_29");


		model.addAttribute("withdraw_1", withdraw_1);
		model.addAttribute("withdraw_2", withdraw_2);
		model.addAttribute("withdraw_3", withdraw_3);
		model.addAttribute("withdraw_4", withdraw_4);
		model.addAttribute("withdraw_5", withdraw_5);
		model.addAttribute("withdraw_6", withdraw_6);
		model.addAttribute("withdraw_7", withdraw_7);
		model.addAttribute("withdraw_8", withdraw_8);
		model.addAttribute("withdraw_9", withdraw_9);
		model.addAttribute("withdraw_10", withdraw_10);
		model.addAttribute("withdraw_11", withdraw_11);
		model.addAttribute("withdraw_12", withdraw_12);
		model.addAttribute("withdraw_13", withdraw_13);
		model.addAttribute("withdraw_14", withdraw_14);
		model.addAttribute("withdraw_15", withdraw_15);
		model.addAttribute("withdraw_16", withdraw_16);
		model.addAttribute("withdraw_17", withdraw_17);
		model.addAttribute("withdraw_18", withdraw_18);
		model.addAttribute("withdraw_19", withdraw_19);
		model.addAttribute("withdraw_20", withdraw_20);
		model.addAttribute("withdraw_21", withdraw_21);
		model.addAttribute("withdraw_22", withdraw_22);
		model.addAttribute("withdraw_23", withdraw_23);
		model.addAttribute("withdraw_24", withdraw_24);
		model.addAttribute("withdraw_25", withdraw_25);
		model.addAttribute("withdraw_26", withdraw_26);
		model.addAttribute("withdraw_27", withdraw_27);
		model.addAttribute("withdraw_28", withdraw_28);
		model.addAttribute("withdraw_29", withdraw_29);
	}
	
	/**
	 * 校验图形验证码是否正确
	 * @param valicode 图形验证码
	 * @param request request请求
	 * @return 图形验证码是否正确
	 */
	protected boolean valicode(String valicode, HttpServletRequest request){
		if(StringUtil.isEmpty(valicode)){
			valicode = request.getParameter("valicode");
		}

		// 校验验证码
		if (StringUtil.isBlank(valicode)) {
			return false;
		} else {
			HttpSession session = request.getSession();
			String v1 = valicode.toLowerCase();
			String vo = (String) session.getAttribute("verification");
			if (v1 != null && vo != null && !v1.equals(vo.toLowerCase())) {
				return false;
			} else if (v1 == null || vo == null) {
				return false;
			}
		}
		return true;
	}
	
}