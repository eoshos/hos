package io.eoshos.pc.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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

import com.chuangke18.framework.api.response.CKResponse;
import com.chuangke18.framework.redis.util.RedisUtil;

import io.eoshos.core.api.bean.bo.HosUserBo;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.po.HosUserInvite;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.pc.util.StringUtil;
import io.eoshos.pc.util.VerificationCodeUtil;

/***
 * 
 * @ClassName EnrollController
 * @Description
 * @author hhj@chuangke18.com
 * @Date 2017年06月05日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/enroll")
public class EnrollController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(EnrollController.class);

	@Autowired
	private IHosUserService hosUserService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Value("${enroll_limit}")
    private int enroll_limit;

	/**
	 * 
	* @Title: index 
	* @Description: 显示注册的页面
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
		setModelProperties1(model);
		setModelProperties3(request, model);
		
		String devtype = request.getParameter("devtype");
		String url = StringUtils.equals("wap", devtype) ? "wap/enroll" : "admin/enroll";
        return url;			
	}
	
	/**
	 * 
	* @Title: validationcode 
	* @Description: 往指定手机号码发送验证码
	* @param @param model
	* @param @param request
	* @param @return  参数说明 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping(value = "validationcode", method = RequestMethod.POST)
	@ResponseBody
	public CKResponse getValidationCode(Model model, HttpServletRequest request) {
		CKResponse cKResponse = new CKResponse();
		String phone = request.getParameter("phone");

		// 校验验证码
		boolean success = valicode("", request);
		if (!success) {
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9992);
			cKResponse.setErrorMsg("验证码不正确！");
			return cKResponse;
		}
		
		// 调用service,得到验证码并发给WEB层
		HosUserDto hosUserDto = new HosUserDto();
		hosUserDto.setPhone(phone);
		cKResponse = hosUserService.applyPhoneValidationCode(hosUserDto);
		return cKResponse;
	}


	/**
	 * 
	* @Title: enroll 
	* @Description: 注册 
	* @param @param hosUserBo
	* @param @param httpServletRequest
	* @param @return  参数说明 
	* @return CKResponse    返回类型 
	* @throws
	 */
	@RequestMapping(value = "enroll", method = RequestMethod.POST)
	@ResponseBody
	public CKResponse enroll(HosUserBo hosUserBo, HttpServletRequest httpServletRequest) {
		CKResponse cKResponse = new CKResponse();
		HosUserVo hosUserVo = new HosUserVo();
		HosUserDto hosUserDto = new HosUserDto();
		HosUserInvite hosUserInvite = new HosUserInvite();
		String inviteCode = hosUserBo.getInviteCode();
		String ip = getIp(httpServletRequest);
		String valicode = hosUserBo.getValicode();
		
		// 校验验证码
		boolean success = valicode(valicode, request);
		if (!success) {
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9992);
			cKResponse.setErrorMsg("验证码不正确！");
			return cKResponse;
		}
		
		// 根据ip限制，超过规定次数注册失败
		int count = redisUtil.get(ip) == null ? 0 : (int)redisUtil.get(ip);
		if(count >= enroll_limit){
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9984);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9984);
			return cKResponse;
		}
		
		hosUserBo.getHosUser().setUserName(hosUserBo.getHosUser().getPhone());
		hosUserBo.getHosUser().setUserType("1");
		hosUserBo.getHosUser().setRealStat("0");
		hosUserBo.getHosUser().setStat("0");
		hosUserBo.getHosUser().setOperIp(ip);
		// 如果邀请码非空，根据邀请码查询邀请人信息
		if(StringUtil.isNotEmpty(inviteCode)){
			hosUserDto.setInviteCode(hosUserBo.getInviteCode());
			cKResponse = hosUserService.getObject(hosUserDto);
			if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
				hosUserBo.getHosUser().setInviteUserId(hosUserVo.getId());
				//查询邀请人信息
				hosUserVo = (HosUserVo) cKResponse.getObj();
				hosUserInvite.setInviteLevel(1L);
				hosUserInvite.setUserId(hosUserVo.getId());//邀请人UID
				hosUserInvite.setPhone(hosUserVo.getPhone());//邀请人手机号
				hosUserInvite.setInvitePhone(hosUserBo.getHosUser().getPhone());//被邀请人手机号
				hosUserInvite.setCoinReward(0d);//TODO 奖励币数
				hosUserInvite.setStat("1");//状态：1-生效，0-未生效
				hosUserInvite.setOperIp(ip);//用户IP
				hosUserBo.setHosUserInvite(hosUserInvite);
				
				cKResponse = hosUserService.validateInfo(hosUserBo);
			}else{
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9989);
			}
			
		}else{
			cKResponse = hosUserService.validateInfo(hosUserBo);
		}
		
		if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
			// 新增用户信息和用户邀请信息
			try {
				cKResponse = hosUserService.saveUserInfo(hosUserBo);
				//保存注册ip信息和次数
				count++;
				// 计算到凌晨0点的时间间隔
				Date nowDate = new Date();
				long now = nowDate.getTime() / 1000;//当前时间
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(nowDate);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				long future = calendar.getTimeInMillis() / 1000;//第二天凌晨
				success = count == 1 ? redisUtil.set(ip, count) : redisUtil.set(ip, count, future - now);
				if (!success) {
					cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
					cKResponse.setErrorMsg("保存ip信息到redis异常！");
					return cKResponse;
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
				cKResponse.setErrorMsg(e.getMessage());
			}
			
		}
		
		return cKResponse;
		
	}

	/**
	 * 
	* @Title: validimg 
	* @Description: 生成系统验证码 
	* @param @param model
	* @param @param response
	* @param @param request  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/validimg", method = RequestMethod.GET)
	public void validimg( Model model, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			VerificationCodeUtil.getCoade(request.getSession(), response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("获取系统验证码有误", e.getMessage());
		}
	}

	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
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

}
