package io.eoshos.pc.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.dto.HosUserAccountTransDto;
import io.eoshos.core.api.bean.vo.HosUserAccountTransVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.service.IHosUserAccountTransService;
import io.eoshos.pc.util.StringUtil;

@Controller
@RequestMapping("/asset")
public class AssetController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(AssetController.class);
	
	@Autowired
	private IHosUserAccountTransService hosUserAccountTransService;
	
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
}
