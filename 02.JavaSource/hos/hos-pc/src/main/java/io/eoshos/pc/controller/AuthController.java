package io.eoshos.pc.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosUserBo;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.po.HosUser;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.pc.util.Base64Utils;
import io.eoshos.pc.util.QrCodeUtil;
import io.eoshos.pc.util.StringUtil;
import net.coobird.thumbnailator.Thumbnails;

/***
 * 
 * @ClassName HomepageController
 * @Description
 * @author hhj@chuangke18.com
 * @Date 2017年06月05日 上午10:26:22
 * @version 1.0.0
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {
	/**
	 * log4j 日志管理
	 */
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	@Autowired
	private IHosUserService hosUserService;

	private static String AUTH_USER = "auth_user";

	 //处理文件上传
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public  CKResponse upload(@RequestParam(value="cardId",required=false) String cardId,
    		@RequestParam(value="realName",required=false) String realName,
    		@RequestParam(value="file1",required=false) String file1,
    		@RequestParam(value="file2",required=false) String file2,
    		@RequestParam(value="fileName1",required=false) String fileName1,
    		@RequestParam(value="fileName2",required=false) String fileName2,
    		HttpServletRequest request) {
    	CKResponse response = new CKResponse();
    	HosUserVo hosUserVo = (HosUserVo)session.getAttribute(AUTH_USER);
    	
    	// 查询证件号码是否已经认证过
    	HosUserDto hosUserDto = new HosUserDto();
    	hosUserDto.setCardId(cardId);
    	response = hosUserService.getObject(hosUserDto);
    	if(ConstantApi.ERROR_CODE.SUCCESS.equals(response.getErrorCode())){
    		HosUserVo hosUserVo1 = (HosUserVo) response.getObj();
    		if(!hosUserVo1.getPhone().equals(hosUserVo.getPhone())){
    			response.setErrorCode(ConstantApi.ERROR_CODE.CODE_9985);
    			response.setErrorMsg(ConstantApi.ERROR_MSG.MSG_9985);		
    			return response;
    		}
    	}
    	
    	//String filePath = System.getProperty("auth_upload") + File.separator;
    	String filePath = request.getSession().getServletContext().getRealPath("auth_upload") + File.separator;
		try {
			File newFile = new File(filePath);
			if (!newFile.isDirectory()) {
				newFile.mkdirs();
			}
			
			String fullFileName1 = fileName1 ;//正面
			String fullFileName2 = fileName2 ;//反面
			//缩略图名
			String strHead1 = fullFileName1.substring(0, fullFileName1.length() - 4);
			String strHead2 = fullFileName2.substring(0, fullFileName2.length() - 4);
			String strTail1 = fullFileName1.substring(fullFileName1.length() - 4, fullFileName1.length());
			String strTail2 = fullFileName2.substring(fullFileName2.length() - 4, fullFileName2.length());
			String thumbnailFileName1 = strHead1 + "_thumbnail" + strTail1;			
			String thumbnailFileName2 = strHead2 + "_thumbnail" + strTail2;			

			//保存文件
			String[] arr1 = file1.split(",");
			String[] arr2 = file2.split(",");
			Base64Utils.Base64ToImage(arr1[1], filePath + fullFileName1);
			Base64Utils.Base64ToImage(arr2[1], filePath + fullFileName2);
			//file.transferTo(new File(filePath + fullFileName));
			Thumbnails.of(filePath + fullFileName1).size(200, 300).toFile(filePath + thumbnailFileName1);
			Thumbnails.of(filePath + fullFileName1).size(200, 300).toFile(filePath + thumbnailFileName2);
			
			//保存文本
			HosUserBo hosUserBo = new HosUserBo();
			HosUser hosUser = new HosUser();
			hosUser.setId(hosUserVo.getId());
			hosUser.setPhone(hosUserVo.getPhone());
			hosUser.setRealName(realName);
			hosUser.setCardId(cardId);
			hosUser.setRealStat("1");
			hosUser.setPicPath1(fileName1);
			hosUser.setPicPath2(fileName2);
			
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			hosUser.setApplyAuthTime(calendar.getTimeInMillis()/1000);
			
			hosUserBo.setHosUser(hosUser);
			response = hosUserService.save(hosUserBo);
			return response;
		} catch (IllegalStateException | IOException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("上传文件失败:--" );
			sb.append(fileName1+fileName2);
			sb.append("----:");
			sb.append(e.getMessage());
			sb.append(e.getCause());
			logger.error(sb.toString());
			
			response.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			response.setErrorMsg("上传文件失败");			
			return response;
		}
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
		String filePath = request.getSession().getServletContext().getRealPath("auth_upload") + File.separator;
		String fileName = request.getParameter("fileName") + ".jpg";//文件以电话号码命名
		//将图片输出给浏览器  
		String imgPath = filePath + fileName;  
		BufferedImage image = ImageIO.read(new FileInputStream(imgPath));  
	    response.setContentType("image/png");  
	    OutputStream os = response.getOutputStream();  
	    ImageIO.write(image, "jpg", os);  
	}    

}
