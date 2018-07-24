package io.eoshos.core.provider.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.response.CKResponse;
import com.chuangke18.framework.redis.util.RedisUtil;

import io.eoshos.core.api.bean.bo.HosUserBo;
import io.eoshos.core.api.bean.dto.HosUserAccountDto;
import io.eoshos.core.api.bean.dto.HosUserDto;
import io.eoshos.core.api.bean.po.HosUser;
import io.eoshos.core.api.bean.po.HosUserAccount;
import io.eoshos.core.api.bean.po.HosUserAccountTrans;
import io.eoshos.core.api.bean.po.HosUserInvite;
import io.eoshos.core.api.bean.vo.HosUserAccountVo;
import io.eoshos.core.api.bean.vo.HosUserVo;
import io.eoshos.core.api.common.ConstantApi;
import io.eoshos.core.api.service.IHosUserService;
import io.eoshos.core.provider.dao.HosUserAccountMapper;
import io.eoshos.core.provider.dao.HosUserAccountTransMapper;
import io.eoshos.core.provider.dao.HosUserInviteMapper;
import io.eoshos.core.provider.dao.HosUserMapper;
import io.eoshos.pc.util.AliSmsUtil;
import io.eoshos.pc.util.MD5;
import io.eoshos.pc.util.QrCodeUtil;
import io.eoshos.pc.util.StringUtil;

@Service("hosUserService")
@PropertySource(value = "classpath:config.properties",encoding = "utf-8")
public class HosUserServiceImpl implements IHosUserService {

	private static final Logger logger = LogManager.getLogger(HosUserServiceImpl.class);
	
    @Value("${number_enroll}")
    private String number_enroll;
    
    @Value("${number_invite}")
    private String number_invite;    

	@Autowired
	private HosUserMapper hosUserMapper;

	@Autowired
	private HosUserInviteMapper hosUserInviteMapper;

	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;
	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CKResponse save(HosUserBo bo) {
		CKResponse cKResponse = new CKResponse();
		HosUser hosUser = bo.getHosUser();

		HosUserDto hosUserDto = new HosUserDto();
		hosUserDto.setPhone(hosUser.getPhone());

		HosUserVo hosUserVo = null;
		int i = -1;
		String errorCode = "";
		String errorMsg = "";
		boolean bActionIsAdd = false;
		try {
			hosUserVo = hosUserMapper.getObject(hosUserDto);

			bActionIsAdd = hosUserVo == null;

			i = bActionIsAdd ? hosUserMapper.insert(hosUser) : hosUserMapper.update(hosUser);

			// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
			errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
			errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;

		} catch (SQLException e) {
			String str = i == -1 ? "查询用户信息异常:" : (bActionIsAdd ? "增加用户信息异常:" : "修改用户信息异常:");
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);

		return cKResponse;
	}

	@Override
	public CKResponse delete(HosUserBo bo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CKResponse getObject(HosUserDto dto) {
		CKResponse cKResponse = new CKResponse();
		HosUserVo hosUserVo = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserVo = hosUserMapper.getObject(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = hosUserVo == null ? ConstantApi.ERROR_CODE.ERROR : ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = hosUserVo == null ? ConstantApi.ERROR_MSG.ERROR : ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObj(hosUserVo);
		return cKResponse;
	}

	@Override
	public CKResponse listAllObjects(HosUserDto dto) {
		CKResponse cKResponse = new CKResponse();
		List<HosUserVo> hosUserVoList = null;
		String errorCode = "";
		String errorMsg = "";
		try {
			hosUserVoList = hosUserMapper.listAllObjects(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			logger.error("查询用户信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjList(hosUserVoList);
		return cKResponse;
	}

	@Override
	public CKResponse listPagingObjects(HosUserDto dto) {
		CKResponse cKResponse = new CKResponse();
		Page<HosUserVo> hosUserVoPage = new Page<HosUserVo>();
		// 查询用户信息列表
		List<HosUserVo> hosUserVoList = null;
		String errorCode = "";
		String errorMsg = "";
		int total = 0;
		try {
			hosUserVoList = hosUserMapper.listPagingObjects(dto);
			// 查询总数
			total = hosUserMapper.countObjects(dto);

			// SQL执行正常,返回的对象不为空则操作成功、为空则操作失败
			errorCode = CollectionUtils.isEmpty(hosUserVoList) ? ConstantApi.ERROR_CODE.ERROR
					: ConstantApi.ERROR_CODE.SUCCESS;
			errorMsg = CollectionUtils.isEmpty(hosUserVoList) ? ConstantApi.ERROR_MSG.ERROR
					: ConstantApi.ERROR_MSG.SUCCESS;
		} catch (SQLException e) {
			String str = hosUserVoList == null ? "查询用户信息异常:" : "查询用户信息总数异常:";
			logger.error(str + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		hosUserVoPage.setTotal(total);
		hosUserVoPage.setRows(hosUserVoList);

		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		cKResponse.setObjPage(hosUserVoPage);
		return cKResponse;
	}

	@Override
	public CKResponse applyPhoneValidationCode(HosUserDto hosUserDto) {
		// 向手机发条短信
		// 把短信保存到redis
		// 返回短信

		CKResponse cKResponse = new CKResponse();

		if (StringUtil.isEmpty(hosUserDto.getPhone())) {
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9998);
			cKResponse.setErrorMsg("手机号不能为空");
			return cKResponse;
		}
		
		if(!StringUtil.isPhone(hosUserDto.getPhone())){
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9993);
			cKResponse.setErrorMsg("手机号非法");
			return cKResponse;
		}

		int len = 4;// 手机验证码长度
		String phoneValidationCode = generateVerification(len);// 手机验证码

		//TODO 向短信平台发条短信
		String retCode = AliSmsUtil.sendSmsValidateCode(hosUserDto.getPhone(), phoneValidationCode);

		if ("OK".equals(retCode)) {
			// 发送手机验证码成功，将手机验证码保存到redis
			redisUtil.set(hosUserDto.getPhone(), phoneValidationCode, 1800);
			cKResponse.setObj(phoneValidationCode);
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
			cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);

		} else {
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg(retCode);
		}

		return cKResponse;
	}

	@Override
	public CKResponse validateInfo(HosUserBo hosUserBo) {
		// 从redis中取得短信
		// 短信检查
		// 按手机号做唯一性检查
		// 密码合规则检查
		// 生成唯一邀请码

		CKResponse ckResponse = new CKResponse();
		HosUser hosUser = hosUserBo.getHosUser();
		String phone = hosUser.getPhone();// 手机号
		String password = hosUser.getPassword();// 密码
		String phoneValidationCode = hosUser.getPhoneValidationCode();// 手机验证码

		// 密码复杂检查
		if (StringUtil.isEmpty(password) || !passwordCheck(password)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9990);
			ckResponse.setErrorMsg("密码过于简单，请重新设置！");
			return ckResponse;
		}

		// 密码MD5加密
		MD5 md5 = new MD5();
		hosUser.setPassword(md5.getMD5ofStr(password));

		// 从redis中获取短信验证码
		String tmp = (String) redisUtil.get(phone);// redis中保存的验证码

		if (StringUtil.isEmpty(tmp) || !tmp.equals(phoneValidationCode)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9991);
			ckResponse.setErrorMsg("短信验证码输入错误！");
			return ckResponse;
		}

		// 检查手机号是否已经注册过
		HosUserDto hosUserDto = new HosUserDto();
		hosUserDto.setPhone(phone);
		HosUserVo hosUserVo = null;
		try {
			hosUserVo = hosUserMapper.getObject(hosUserDto);
		} catch (SQLException e) {
			logger.error("查询用户信息异常:" + e.getMessage());

			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
			ckResponse.setErrorMsg(e.getSQLState() + ":" + e.getMessage());
			return ckResponse;
		}

		if (hosUserVo != null) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9994);
			ckResponse.setErrorMsg("该手机号已经注册过！");
			return ckResponse;
		}

		// 生成唯一邀请码
		int inviteLen = 6;// 邀请码长度

		// 生成唯一邀请码
		String inviteCode = checkInviteCode(inviteLen);
		if (StringUtil.isEmpty(inviteCode)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
			ckResponse.setErrorMsg("生成唯一邀请码失败！");
			return ckResponse;
		}
		hosUser.setInviteCode(inviteCode);

		//生成图片二维码
		//TODO 避免注册时重复点击，造成二维码被覆盖，二维码在登录时生成
//		int code = QrCodeUtil.qrcode(hosUser.getPhone(), hosUser.getInviteCode());
//		if(code != 1){
//			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
//			ckResponse.setErrorMsg("生成二维码失败！");
//			return ckResponse;
//		}
		
		// 新增用户信息和用户邀请信息
//		try {
//			ckResponse = saveUserInfo(hosUserBo);
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9986);
//			ckResponse.setErrorMsg(e.getMessage());
//		}
		
		return ckResponse;
	}

	@Override
	public CKResponse login(HosUserDto hosUserDto) {

		CKResponse ckResponse = new CKResponse();
		String phone = hosUserDto.getPhone();// 手机号
		String password = hosUserDto.getPassword();// 密码

		if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(password)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9995);
			ckResponse.setErrorMsg("手机号或密码不能为空！");
			return ckResponse;
		}
		HosUserVo hosUserVo = null;

		//增加用户登录时将用户信息保存到redis中，以后登录时先查询redis，减轻数据库压力
		//从redis里取用户信息
		Map<Object, Object> map = redisUtil.hmget("userInfo" + phone);
		if(map == null || map.size() <= 0){
			map = new HashMap<Object, Object>();
			// 根据手机号查询用户信息
			try {
				hosUserVo = hosUserMapper.getObject(hosUserDto);
			} catch (SQLException e) {
				logger.error("查询用户信息异常:" + e.getMessage());
				
				ckResponse.setErrorCode(e.getErrorCode() + "");
				ckResponse.setErrorMsg(e.getSQLState() + ":" + e.getMessage());
				return ckResponse;
			}
			map.put("hosUser", hosUserVo);
			if(hosUserVo != null){
				//将用户信息保存到redis中
				redisUtil.hmset("userInfo" + phone, map, 86400);
			}
		}

		if (map.get("hosUser") == null) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9988);
			ckResponse.setErrorMsg("该手机号未注册，请先注册！");
			return ckResponse;
		}
		hosUserVo = (HosUserVo) map.get("hosUser");

		// 密码MD5加密
		MD5 md5 = new MD5();
		password = md5.getMD5ofStr(password);

		if (!hosUserVo.getPassword().equals(password)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9987);
			ckResponse.setErrorMsg("用户名或密码错误！");
			return ckResponse;
		}

		ckResponse.setObj(hosUserVo);
		ckResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		ckResponse.setErrorMsg("登录成功！");
		return ckResponse;
	}

	@Override
	public CKResponse password(HosUserBo hosUserBo) {
		// 从redis中取得短信
		// 短信检查

		CKResponse ckResponse = new CKResponse();
		HosUser hosUser = hosUserBo.getHosUser();
		HosUserDto hosUserDto = new HosUserDto();

		hosUserDto.setPhone(hosUser.getPhone());

		String password = hosUser.getPassword();// 密码

		// 密码复杂检查
		if (StringUtil.isEmpty(password) || !passwordCheck(password)) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9990);
			ckResponse.setErrorMsg("密码过于简单，请重新设置！");
			return ckResponse;
		}
		
		// 根据手机号查询用户信息
		HosUserVo hosUserVo = null;
		try {
			hosUserVo = hosUserMapper.getObject(hosUserDto);
		} catch (SQLException e) {
			logger.error("查询用户信息异常:" + e.getMessage());

			ckResponse.setErrorCode(e.getErrorCode() + "");
			ckResponse.setErrorMsg(e.getSQLState() + ":" + e.getMessage());
			return ckResponse;
		}

		if (hosUserVo == null) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9988);
			ckResponse.setErrorMsg("该手机号还未注册，请先注册！");
			return ckResponse;
		}

		// 从redis中获取短信验证码
		String tmp = (String) redisUtil.get(hosUser.getPhone());// redis中保存的验证码

		if (StringUtil.isEmpty(tmp) || !tmp.equals(hosUser.getPhoneValidationCode())) {
			ckResponse.setErrorCode(ConstantApi.ERROR_CODE.CODE_9991);
			ckResponse.setErrorMsg("短信验证码输入错误！");
			return ckResponse;
		}

		// 密码MD5加密
		MD5 md5 = new MD5();
		int i = 0;

		String errorCode = "";
		String errorMsg = "";

		// 更新用户密码
		hosUser.setPassword(md5.getMD5ofStr(hosUser.getPassword()));
		try {
			i = hosUserMapper.update(hosUser);

			// SQL执行正常,影响行数大于0则操作成功、等于0则操作失败
			errorCode = i > 0 ? ConstantApi.ERROR_CODE.SUCCESS : ConstantApi.ERROR_CODE.ERROR;
			errorMsg = i > 0 ? ConstantApi.ERROR_MSG.SUCCESS : ConstantApi.ERROR_MSG.ERROR;
			
			//删除之前登录保存到redis中的信息
			redisUtil.del("userInfo" + hosUser.getPhone());
		} catch (SQLException e) {
			logger.error("修改用户信息异常:" + e.getMessage());
			errorCode = e.getErrorCode() + "";
			errorMsg = e.getSQLState() + ":" + e.getMessage();
		}

		ckResponse.setErrorCode(errorCode);
		ckResponse.setErrorMsg(errorMsg);

		return ckResponse;
	}

	/**
	 * 密码合规则检查：1.长度 2.是否有数字、字母、特殊字符
	 * 
	 * @param password
	 *            密码
	 * @return
	 */
	private boolean passwordCheck(String password) {
		// 判断长度
		boolean a = password.length() >= 6 ? true : false;
		// 判断是否包含数字
		boolean b = password.matches(".*\\d+.*");
		// 判断是否包含字母
		boolean c = password.matches(".*[a-zA-Z]+.*");
		// 判断是否包含特殊字符
		//boolean d = password.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*");

		return a && b && c;
	}

	/**
	 * 检查邀请码是否已存在，存在重新生成，最多重新生成6次
	 * @param len 邀请码长度
	 * @return 唯一的邀请码
	 */
	private String checkInviteCode(int len){
		String inviteCode = "";
		int time = 0;
		HosUserDto hosUserDto = new HosUserDto();
		HosUserVo hosUserVo = null;
		while (true) {
			time++;
			if (time > 6) {
				logger.error("生成邀请码次数超限，请稍后重试！");
				return "";
			}

			inviteCode = generateInviteCode(len);

			// 检查邀请码是否已存在
			hosUserDto.setInviteCode(inviteCode);

			try {
				hosUserVo = hosUserMapper.getObject(hosUserDto);
			} catch (SQLException e) {
				logger.error("查询用户信息异常:" + e.getMessage());
				return "";
			}

			if (hosUserVo == null) {
				break;
			}

		}
		
		return inviteCode;
	}
	
	/**
	 * 生成指定长度的邀请码
	 * 
	 * @param len
	 *            邀请码长度
	 * @return 邀请码
	 */
	private String generateInviteCode(int len) {

		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };

		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");

		for (int i = 0; i < len; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}

		return shortBuffer.toString();
	}

	/**
	 * 生成指定长度的短信验证码
	 * 
	 * @param len
	 *            短信验证码长度
	 * @return 短信验证码
	 */
	private String generateVerification(int len) {

		String s = "0123456789";
		StringBuffer sb = new StringBuffer();

		Random random = new Random();// 创建一个随机类

		for (int i = 0; i < len; i++) {
			sb.append(String.valueOf(s.charAt(random.nextInt(s.length()))));
		}

		return sb.toString();
	}

	/**
	 * 新增用户信息和用户邀请信息，独立事务方便出错回滚，以免留下脏数据
	 * @param hosUserBo
	 * @return
	 * @throws SQLException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public CKResponse saveUserInfo(HosUserBo hosUserBo) throws SQLException{
		CKResponse cKResponse = new CKResponse();
		HosUser hosUser = hosUserBo.getHosUser();
		HosUserDto hosUserDto = new HosUserDto();
		HosUserVo hosUserVo = null;//邀请用户信息
		String inviteCode = hosUserBo.getInviteCode();
		int i = 0,j = 0;
		String errorCode = ConstantApi.ERROR_CODE.SUCCESS;
		String errorMsg = ConstantApi.ERROR_MSG.SUCCESS;
		HosUserAccount hosUserAccount = new HosUserAccount();
		HosUserAccountVo hosUserAccountVo = new HosUserAccountVo();
		HosUserAccountTrans hosUserAccountTrans = new HosUserAccountTrans();
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		double numberInvite = Double.valueOf(number_invite);
		double numberEnroll = Double.valueOf(number_enroll);

		// 新增用户信息
		i = hosUserMapper.insert(hosUser);
		j++;
		// 重取用户信息
		hosUserDto.setPhone(hosUser.getPhone());
		hosUserVo = hosUserMapper.getObject(hosUserDto);
		// 新增用户账户信息
		hosUserAccount.setUserId(hosUserVo.getId());
		hosUserAccount.setCoinTotal(numberEnroll);
		hosUserAccount.setCoinAvailable(0d);
		hosUserAccount.setCoinFreeze(numberEnroll);
		hosUserAccount.setStat("1");
		i += hosUserAccountMapper.insert(hosUserAccount);
		j++;
		if(numberEnroll > 0){
			// 按照奖励HOS币数扣减总币数
			logger.debug("按照奖励HOS币数扣减总币数");
			// 查询总币记录
			hosUserAccountDto.setUserId(1L);
			hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
			// 更新余额
			hosUserAccount = new HosUserAccount();
			hosUserAccount.setId(hosUserAccountVo.getId());
			hosUserAccount.setUserId(1L);
			hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal() - numberEnroll);
			hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() - numberEnroll);
			hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze());
			hosUserAccount.setGmtModified(Long.valueOf(hosUserAccountVo.getGmtModifiedStr()));
			i += hosUserAccountMapper.updateByTrade(hosUserAccount);
			j++;
			// 新增交易记录
			logger.debug("新增交易记录");
			hosUserAccountTrans = new HosUserAccountTrans();
			hosUserAccountTrans.setUserId(hosUserVo.getId());
			hosUserAccountTrans.setDcFlag("C");
			hosUserAccountTrans.setTransType("1");
			hosUserAccountTrans.setOtherUserId(1L);
			hosUserAccountTrans.setCoinNumber(numberEnroll);
			hosUserAccountTrans.setCoinBal(numberEnroll);
			hosUserAccountTrans.setStat("1");
			i += hosUserAccountTransMapper.insert(hosUserAccountTrans);
			j++;
		}
		
		// 判断邀请码是否为空，不为空新增用户邀请信息，按照奖励HOS币数扣减总币数，新增交易记录
		if(StringUtil.isNotEmpty(inviteCode)){
			if (hosUserVo != null) {
				// 获取id值赋值被邀请人uuid
				HosUserInvite hosUserInvite = hosUserBo.getHosUserInvite();
				hosUserInvite.setInviteUserId(hosUserVo.getId());//被邀请人UID
				hosUserInvite.setCoinReward(numberInvite);//奖励币数
				logger.debug("新增用户邀请信息");
				i += hosUserInviteMapper.insert(hosUserBo.getHosUserInvite());
				j++;
				
				if(numberInvite > 0){
					// 按照奖励HOS币数扣减总币数
					logger.debug("按照奖励HOS币数扣减总币数");
					// 查询总币记录
					hosUserAccountDto.setUserId(1L);
					hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
					// 更新余额
					hosUserAccount = new HosUserAccount();
					hosUserAccount.setId(hosUserAccountVo.getId());
					hosUserAccount.setUserId(1L);
					hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal() - numberInvite);
					hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() - numberInvite);
					hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze());
					hosUserAccount.setGmtModified(Long.valueOf(hosUserAccountVo.getGmtModifiedStr()));
					i += hosUserAccountMapper.updateByTrade(hosUserAccount);
					j++;
					// 增加邀请人账户余额
					logger.debug("按照奖励增加邀请人HOS币");
					// 查询邀请人账户信息
					hosUserAccountDto.setUserId(hosUserInvite.getUserId());
					hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
					// 更新余额
					hosUserAccount = new HosUserAccount();
					hosUserAccount.setId(hosUserAccountVo.getId());
					hosUserAccount.setUserId(hosUserAccountVo.getUserId());
					hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal() + numberInvite);
					hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable());
					hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze() + numberInvite);
					hosUserAccount.setGmtModified(Long.valueOf(hosUserAccountVo.getGmtModifiedStr()));
					i += hosUserAccountMapper.updateByTrade(hosUserAccount);
					j++;
					// 新增交易记录
					logger.debug("新增交易记录");
					hosUserAccountTrans = new HosUserAccountTrans();
					hosUserAccountTrans.setUserId(hosUserInvite.getUserId());
					hosUserAccountTrans.setDcFlag("C");
					hosUserAccountTrans.setTransType("1");
					hosUserAccountTrans.setOtherUserId(1L);
					hosUserAccountTrans.setCoinNumber(numberInvite);
					hosUserAccountTrans.setCoinBal(hosUserAccount.getCoinTotal());
					hosUserAccountTrans.setStat("1");
					hosUserAccountTrans.setNotes(hosUser.getPhone());
					i += hosUserAccountTransMapper.insert(hosUserAccountTrans);
					j++;
				}
				
			}
			if (i < j) {
				logger.error("新用户信息和用户邀请信息异常！");
				throw new SQLException("新用户信息和用户邀请信息异常！");
			}
		}else{
			if (i < j) {
				logger.error("新用户信息异常！");
				throw new SQLException("新用户信息异常！");
			}
		}
		
		cKResponse.setErrorCode(errorCode);
		cKResponse.setErrorMsg(errorMsg);
		return cKResponse;
	}	
	
}
