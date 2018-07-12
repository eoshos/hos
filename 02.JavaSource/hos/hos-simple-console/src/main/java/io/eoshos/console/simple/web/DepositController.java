package io.eoshos.console.simple.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;
import com.google.common.base.Joiner;

import io.eoshos.console.simple.bean.bo.HosUserTaskAttachmentBo;
import io.eoshos.console.simple.bean.dto.HosUserAccountTransDto;
import io.eoshos.console.simple.bean.dto.HosUserDto;
import io.eoshos.console.simple.bean.po.HosUser;
import io.eoshos.console.simple.bean.po.HosUserTaskAttachment;
import io.eoshos.console.simple.bean.vo.HosUserAccountTransVo;
import io.eoshos.console.simple.bean.vo.HosUserVo;
import io.eoshos.console.simple.service.IHosUserAccountTransService;
import io.eoshos.console.simple.service.IHosUserService;
import io.eoshos.console.simple.service.IHosUserTaskAttachmentService;
import io.eoshos.console.simple.util.ConstantSimpleConsole;
import io.eoshos.console.simple.util.DateUtil;
import io.eoshos.console.simple.util.ExceptionNullUpdate;
import net.coobird.thumbnailator.Thumbnails;


@Controller
@RequestMapping("/deposit")  
public class DepositController {
	
	private static Logger logger = LoggerFactory.getLogger(DepositController.class);
	
	@Autowired
	protected HttpServletResponse response;	
    
    @Autowired
    IHosUserService hosUserService; 
    
    @Autowired
    IHosUserTaskAttachmentService hosUserTaskAttachmentService;    
    
    @Autowired
    IHosUserAccountTransService hosUserAccountTransService;  
    

    
    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(HttpServletRequest request, Model model) {
    	String userId = request.getParameter("userId");
    	model.addAttribute("userId", userId);
    	return "deposit/deposit";
    }   
    
    @RequestMapping(value = "/deposits", method = RequestMethod.GET)
    public String deposits(HttpServletRequest request, Model model) {
    	return "deposit/deposits";
    }     
    
    @RequestMapping(value = "/depositlist", method = RequestMethod.GET)
    public String depositlist(HttpServletRequest request, Model model) {
    	String userId = request.getParameter("userId");
    	model.addAttribute("userId", userId);
    	return "deposit/depositlist";
    }    
    
    @RequestMapping(value = "/authing", method = RequestMethod.GET)
    public String authing(HttpServletRequest request, Model model) {
    	String userId = request.getParameter("userId");
    	String cardId = request.getParameter("cardId");
    	String realName = request.getParameter("realName");
    	String gmtModified = request.getParameter("gmtModified");
    	model.addAttribute("userId", userId);
    	model.addAttribute("cardId", cardId);
    	model.addAttribute("realName", realName);
    	model.addAttribute("gmtModified", gmtModified);
    	return "deposit/authing";
    }    
       

    @RequestMapping(value = "/authed", method = RequestMethod.GET)
    public String authed(HttpServletRequest request, Model model) {
    	String userId = request.getParameter("userId");
    	String cardId = request.getParameter("cardId");
    	String realName = request.getParameter("realName");
    	model.addAttribute("userId", userId);
    	model.addAttribute("cardId", cardId);
    	model.addAttribute("realName", realName);
    	return "deposit/authed";
    }
    
    @RequestMapping(value = "/authfail", method = RequestMethod.GET)
    public String authfail(HttpServletRequest request, Model model) {
    	String userId = request.getParameter("userId");
    	String cardId = request.getParameter("cardId");
    	String realName = request.getParameter("realName");
    	String notes = request.getParameter("notes");
    	model.addAttribute("userId", userId);
    	model.addAttribute("cardId", cardId);
    	model.addAttribute("realName", realName);
    	model.addAttribute("notes", notes);
    	return "deposit/authfail";
    }   
    
    //----------------------------以上page,以下data----------------------------------------------------------------------
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/listusers", method = RequestMethod.GET)
    @ResponseBody
    public Page<HosUserVo> listUsers(HttpServletRequest request) {
    	HosUserDto hosUserDto = new HosUserDto();
		if(!StringUtils.isEmpty(request.getParameter("phone"))){
			hosUserDto.setPhone(request.getParameter("phone"));
		} 
		if(!StringUtils.isEmpty(request.getParameter("realStat"))){
			hosUserDto.setRealStat(request.getParameter("realStat"));
		}	
		
		if(!StringUtils.isEmpty(request.getParameter("beginDate"))){
			String s = request.getParameter("beginDate") + " 00:00:00";
			hosUserDto.setBeginDate(DateUtil.getTime(s));
		}	
		if(!StringUtils.isEmpty(request.getParameter("endDate"))){
			String s = request.getParameter("endDate") + " 00:00:00";
			hosUserDto.setEndDate(DateUtil.getTime(s));
		}		
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit")); 
		hosUserDto.setLimit(limit);
		hosUserDto.setOffset(offset);
		Page<HosUserVo> page = null;
    	page = (Page<HosUserVo>) hosUserService.listPagingObjects(hosUserDto).getObjPage();
    	return page;
    }   
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/listtrade", method = RequestMethod.GET)
    @ResponseBody
    public Page<HosUserAccountTransVo> listTrade(HttpServletRequest request) {
    	HosUserAccountTransDto hosUserAccountTransDto = new HosUserAccountTransDto();
		if(!StringUtils.isEmpty(request.getParameter("userId"))){
			hosUserAccountTransDto.setUserId(Long.parseLong(request.getParameter("userId")));
		}    	
		int offset = Integer.parseInt(request.getParameter("offset"));
		int limit = Integer.parseInt(request.getParameter("limit")); 
		hosUserAccountTransDto.setLimit(limit);
		hosUserAccountTransDto.setOffset(offset);
		Page<HosUserAccountTransVo> page = null;
    	page = (Page<HosUserAccountTransVo>) hosUserAccountTransService.listPagingObjects1(hosUserAccountTransDto).getObjPage();
    	return page;
    }     
     
    
    //处理文件上传
    @RequestMapping(value="/uploadimg", method = RequestMethod.POST)
    @ResponseBody
    public  CKResponse uploadImg(@RequestParam(value="file",required=false) MultipartFile file,
    		@RequestParam(value="fileName",required=false) String fileName,
    		@RequestParam(value="userId",required=false) String userId,
    		@RequestParam(value="notes",required=false) String notes,
    		HttpServletRequest request) {
    	CKResponse response = new CKResponse();
    	
    	String filePath = System.getProperty("uploadFolder") + File.separator;
	    Calendar c = Calendar.getInstance();  
	    String localtime = new SimpleDateFormat("HH:mm:ss").format(c.getTime());     	
    	String time = localtime.toString().replaceAll(":", "").substring(0, 6);
		try {
			File newFile = new File(filePath);
			if (!newFile.isDirectory()) {
				newFile.mkdirs();
			}
			
			//String fullFileName = filePath + userId + "_"  + time + "_" + fileName ;
			String fullFileName = userId + "_"  + time + "_" + fileName ;
			//缩略图名
			String s1 = fullFileName.substring(0, fullFileName.length() - 4);
			String s2 = fullFileName.substring(fullFileName.length() - 4, fullFileName.length());
			String thumbnailFileName = s1 + "_thumbnail" + s2;			

			//保存文件
			file.transferTo(new File(filePath + fullFileName));
			Thumbnails.of(filePath + fullFileName).size(200, 300).toFile(filePath + thumbnailFileName);
			
			//保存文本
			HosUserTaskAttachment  hosUserTaskAttachment = new HosUserTaskAttachment();
			hosUserTaskAttachment.setFileName(fileName);
			hosUserTaskAttachment.setFullFileName(fullFileName.substring(fullFileName.lastIndexOf("\\")+1));
			hosUserTaskAttachment.setThumbnailFileName(thumbnailFileName.substring(thumbnailFileName.lastIndexOf("\\")+1));
			hosUserTaskAttachment.setUserId(Long.parseLong(userId));
			hosUserTaskAttachment.setNotes(notes);
			HosUserTaskAttachmentBo hosUserTaskAttachmentBo = new HosUserTaskAttachmentBo();
			hosUserTaskAttachmentBo.setHosUserTaskAttachment(hosUserTaskAttachment);
			hosUserTaskAttachmentService.save(hosUserTaskAttachmentBo);
			
			return response;
		} catch (IllegalStateException | IOException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("上传文件失败:--" );
			sb.append(fileName);
			sb.append("----:");
			sb.append(e.getMessage());
			sb.append(e.getCause());
			logger.error(sb.toString());
			
			response.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			response.setErrorMsg("上传文件失败");			
			return response;
		}
    } 
    
	@RequestMapping(value = "deposit")
	@ResponseBody
	public CKResponse deposit(HttpServletRequest httpServletRequest)throws Exception {
		String userId = httpServletRequest.getParameter("userId");
		String coinNumber = httpServletRequest.getParameter("coinNumber");
		String notes = httpServletRequest.getParameter("notes");
		HosUserAccountTransDto hosUserAccountTransDto = new HosUserAccountTransDto();
		hosUserAccountTransDto.setUserId(Long.parseLong(userId));
		hosUserAccountTransDto.setOtherUserId(1L);
		hosUserAccountTransDto.setCoinNumber(Double.parseDouble(coinNumber));
		hosUserAccountTransDto.setDcFlag(ConstantSimpleConsole.DC_FLAG.C);
		hosUserAccountTransDto.setNotes(notes);
		CKResponse response = hosUserAccountTransService.trade(hosUserAccountTransDto);
		return response;	
	}  
	
	@RequestMapping(value = "depositBatch", method = RequestMethod.POST)
	@ResponseBody
	public CKResponse depositBatch(HttpServletRequest request) {
		CKResponse response = new CKResponse();
		String phones = request.getParameter("phones"); 
		String coinNumber = request.getParameter("coinNumber"); 
		String notes = request.getParameter("notes"); 
		String fileName = MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss}", new Object[]{Calendar.getInstance().getTime()});
		fileName = fileName.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		String[] arr = phones.split(",");
		List<String> list = new ArrayList();
		list.add("-1");//给个初值
		for (int i = 0; i < arr.length; i++){
			String phone = arr[i];
			HosUserDto hosUserDto = new HosUserDto();
			hosUserDto.setPhone(phone);
			CKResponse response1 = hosUserService.getObject(hosUserDto);
			if (!StringUtils.equals("0000", response1.getErrorCode())){
				list.add(phone);
				continue;
			}
			Long userId = ((HosUserVo)response1.getObj()).getId();
			HosUserAccountTransDto hosUserAccountTransDto = new HosUserAccountTransDto();
			hosUserAccountTransDto.setUserId(userId);
			hosUserAccountTransDto.setOtherUserId(1L);
			hosUserAccountTransDto.setCoinNumber(Double.parseDouble(coinNumber));
			hosUserAccountTransDto.setDcFlag(ConstantSimpleConsole.DC_FLAG.C);
			hosUserAccountTransDto.setNotes(notes);
			try {
				response1 = hosUserAccountTransService.trade(hosUserAccountTransDto);
			} catch (ExceptionNullUpdate e) {
				logger.error("奖励失败:" + e.getMessage());
				list.add(phone);
				continue;
			}
			if (!StringUtils.equals("0000", response1.getErrorCode())){
				list.add(phone);
				continue;
			}
		}
		
		String str = Joiner.on(",").join(list);  
		response.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		response.setErrorMsg(str);
		return response;
	}
	
	@RequestMapping(value = "downLoadFailedFile", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadFailedFile(HttpServletRequest request) throws Exception {
		String content = request.getParameter("content"); 
		String fileName = MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss}", new Object[]{Calendar.getInstance().getTime()});
		fileName = fileName.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
        response.addHeader("Content-Length", "" + content.getBytes("UTF-8").length);
        OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		ous.write(content.getBytes("UTF-8"));
		ous.flush();
		ous.close();        
	}	
	
	@RequestMapping(value = "auth")
	@ResponseBody
	public CKResponse auth(HttpServletRequest httpServletRequest)throws Exception {
		String FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
		String dateStr = new SimpleDateFormat(FORMAT_STR).format(new Date());
		long authTime = DateUtil.getTime(dateStr);
		
		String userId = httpServletRequest.getParameter("userId");
		String realStat = httpServletRequest.getParameter("realStat");
		String gmtModified = httpServletRequest.getParameter("gmtModified");
		String notes = httpServletRequest.getParameter("notes");
		HosUser hosUser = new HosUser();
		hosUser.setId(Long.parseLong(userId));
		hosUser.setRealStat(realStat);
		hosUser.setNotes(notes);
		hosUser.setGmtModified(Long.parseLong(gmtModified));
		hosUser.setAuthTime(authTime);
		CKResponse response = new CKResponse();
		try {
			response = hosUserService.authentication(hosUser);
		} catch (ExceptionNullUpdate e) {
			response.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			response.setErrorMsg("认证未完成 ,请重试!");
			return response;
		}
		return response;	
	}	
	
}