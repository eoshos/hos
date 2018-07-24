package io.eoshos.pc.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.response.CKResponse;
import com.chuangke18.framework.redis.util.RedisUtil;

import io.eoshos.core.api.bean.bo.HosTradeCoinToChainBo;
import io.eoshos.core.api.bean.dto.HosUserAccountDto;
import io.eoshos.core.api.bean.dto.HosUserAccountTransDto;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.po.HosTradeCoinToChain;
import io.eoshos.core.api.bean.po.HosUserAccount;
import io.eoshos.core.api.bean.vo.HosUserAccountTransVo;
import io.eoshos.core.api.bean.vo.HosUserAccountVo;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.service.IHosTradeCoinToChainService;
import io.eoshos.core.api.service.IHosUserAccountService;
import io.eoshos.core.api.service.IHosUserAccountTransService;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.pc.util.StringUtil;

@Controller
@RequestMapping("/asset")
public class AssetController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(AssetController.class);
	
	@Value("${withdraw_available}")
	private String withdraw_available;
	
	@Autowired
	private IHosUserAccountTransService hosUserAccountTransService;
	
	@Autowired
	private IHosUserService hosUserService;
	
	@Autowired
	private IHosUserAccountService hosUserAccountService;
	
	@Autowired
	private IHosTradeCoinToChainService hosTradeCoinToChainService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private static String AUTH_USER = "auth_user";
	
	/**
	 * 
	 * @Title: transList 
	 * @Description: 资产明细查询    只查询最近三个月的数据
	 * @param @param request
	 * @param @return  参数说明 
	 * @return Page<HosUserAccountTransVo>    返回类型 
	 * 
	 */
	@RequestMapping(value="/details", method = RequestMethod.POST)
	@ResponseBody
	public Page<HosUserAccountTransVo> details(HttpServletRequest request){
		Page<HosUserAccountTransVo> page = new Page<HosUserAccountTransVo>();
		page.setTotal(0);
		String userId = request.getParameter("userId");//用户id
		String offset =  request.getParameter("offset");//分页开始
		String limit =  request.getParameter("limit");//分页数量
		Long gmtStart = 0L;//查询起始时间
		Long gmtEnd = 0L;//查询终止时间
		
		// 只查询最近三个月的数据，计算查询起始时间和终止时间
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		gmtEnd = calendar.getTimeInMillis()/1000;
		calendar.add(2, -3);//月份减三
		gmtStart = calendar.getTimeInMillis()/1000;
		// 查询交易记录
		HosUserAccountTransDto dto = new HosUserAccountTransDto();
		dto.setUserId(StringUtil.isEmpty(userId)? 0L : Long.valueOf(userId));
		//dto.setGmtStart(gmtStart);
		//dto.setGmtEnd(gmtEnd);
		dto.setOffset(StringUtil.isEmpty(offset)? 0 : Integer.valueOf(offset));
		dto.setLimit(StringUtil.isEmpty(limit)? 0 : Integer.valueOf(limit));
		CKResponse cKResponse = hosUserAccountTransService.listPagingObjects(dto);
		if(ConstantApi.ERROR_CODE.SUCCESS.equals(cKResponse.getErrorCode())){
			page = (Page<HosUserAccountTransVo>) cKResponse.getObjPage();
		}
		return page;
	}
	
	/**
	 * 
	 * @Title: Withdraw 
	 * @Description: 提币
	 * @param @param request
	 * @param @return  参数说明 
	 * @return Page<HosUserAccountTransVo>    返回类型 
	 * 
	 */
	@RequestMapping(value="/withdraw", method = RequestMethod.POST)
	@ResponseBody
	public CKResponse withdraw(HosTradeCoinToChainBo hosTradeCoinToChainBo, HttpServletRequest request){
		//短信验证码校验
		//逻辑校验：是否实名、必输项非空、账户合法性、可用余额
		//更新账户余额，从可用转到冻结，插入资产明细
		CKResponse cKResponse = new CKResponse();
		
		if(!"1".equals(withdraw_available)){
			logger.error(ConstantApi.ERROR_MSG.ERROR);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("提币功能未打开！");
			return cKResponse;
		}
		
		String phonecode = hosTradeCoinToChainBo.getPhonecode();//短信验证码
		//获取用户信息
		HosUserVo hosUserVo = (HosUserVo) session.getAttribute(AUTH_USER);
		HosUserDto hosUserDto = new HosUserDto();
	    hosUserDto.setPhone(hosUserVo.getPhone());
	    hosUserVo = (HosUserVo)hosUserService.getObject(hosUserDto).getObj();
		// 从redis中获取短信验证码
		String tmp = (String) redisUtil.get(hosUserVo.getPhone());// redis中保存的验证码
		if (StringUtil.isEmpty(tmp) || !tmp.equals(phonecode)) {
			logger.error(ConstantApi.ERROR_MSG.MSG_9991);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9991);
			cKResponse.setErrorMsg("短信验证码输入错误！");
			return cKResponse;
		}
		
		//实名认证校验
		if(!"2".equals(hosUserVo.getRealStat())){
			logger.error(ConstantApi.ERROR_MSG.MSG_9982);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9982);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9982);
			return cKResponse;
		}
		
		// 提币数量校验
		Double coin = hosTradeCoinToChainBo.getHosTradeCoinToChain().getCoinNumber();
		if (coin.compareTo(10d) < 0) {
			logger.error(ConstantApi.ERROR_MSG.MSG_9981);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9981);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9981);
			return cKResponse;
		}
		
		//查询用户账户信息
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		hosUserAccountDto.setUserId(hosUserVo.getId());
		HosUserAccountVo hosUserAccountVo = 
				(HosUserAccountVo) hosUserAccountService.getObject(hosUserAccountDto).getObj();
		//判断可用余额是否足够
		if(coin.compareTo(hosUserAccountVo.getCoinAvailable()) > 0){
			logger.error(ConstantApi.ERROR_MSG.MSG_9980);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9980);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9980);
			return cKResponse;
		}
		
		//保存提币请求，分三步：1.冻结可用金额；2.保存交易记录；3.保存提币请求
		HosTradeCoinToChain hosTradeCoinToChain = hosTradeCoinToChainBo.getHosTradeCoinToChain();
		HosUserAccount hosUserAccount = new HosUserAccount();
		hosUserAccount.setId(hosUserAccountVo.getId());
		hosUserAccount.setUserId(hosUserAccountVo.getUserId());
		hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal());
		hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() - coin);
		hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze() + coin);
		hosUserAccount.setGmtModified(Long.valueOf(hosUserAccountVo.getGmtModifiedStr()));
		hosTradeCoinToChainBo.setHosUserAccount(hosUserAccount);
		
		hosTradeCoinToChain.setUserId(hosUserAccount.getUserId());
		hosTradeCoinToChain.setPhone(hosUserVo.getPhone());
		hosTradeCoinToChain.setUserAccountId(hosUserAccount.getId());
		hosTradeCoinToChain.setStat("1");
		hosTradeCoinToChainBo.setHosTradeCoinToChain(hosTradeCoinToChain);
		
		try {
			cKResponse = hosTradeCoinToChainService.withdraw(hosTradeCoinToChainBo);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9979);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9979);
		}
		
		return cKResponse;
	}
	
}
