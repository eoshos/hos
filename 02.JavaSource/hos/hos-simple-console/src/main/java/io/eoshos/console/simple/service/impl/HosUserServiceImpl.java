package io.eoshos.console.simple.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chuangke18.framework.api.bean.Page;
import com.chuangke18.framework.api.constant.ConstantApi;
import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.console.simple.bean.bo.HosUserBo;
import io.eoshos.console.simple.bean.dto.HosUserAccountDto;
import io.eoshos.console.simple.bean.dto.HosUserAccountTransDto;
import io.eoshos.console.simple.bean.dto.HosUserDto;
import io.eoshos.console.simple.bean.dto.HosUserInviteDto;
import io.eoshos.console.simple.bean.po.HosUser;
import io.eoshos.console.simple.bean.po.HosUserAccount;
import io.eoshos.console.simple.bean.po.HosUserAccountTrans;
import io.eoshos.console.simple.bean.vo.HosUserAccountTransVo;
import io.eoshos.console.simple.bean.vo.HosUserAccountVo;
import io.eoshos.console.simple.bean.vo.HosUserInviteVo;
import io.eoshos.console.simple.bean.vo.HosUserVo;
import io.eoshos.console.simple.dao.mapper.HosUserAccountMapper;
import io.eoshos.console.simple.dao.mapper.HosUserAccountTransMapper;
import io.eoshos.console.simple.dao.mapper.HosUserInviteMapper;
import io.eoshos.console.simple.dao.mapper.HosUserMapper;
import io.eoshos.console.simple.service.IHosUserService;
import io.eoshos.console.simple.util.ConstantSimpleConsole;
import io.eoshos.console.simple.util.CustomConfig;
import io.eoshos.console.simple.util.DateUtil;
//import io.eoshos.pc.util.AliSmsUtil;
//import io.eoshos.pc.util.MD5;
import io.eoshos.console.simple.util.ExceptionNullUpdate;

@Service("hosUserService")
public class HosUserServiceImpl implements IHosUserService {

	private static final Logger logger = LogManager.getLogger(HosUserServiceImpl.class);
	
    @Autowired
    private CustomConfig customConfig;	

	@Autowired
	private HosUserMapper hosUserMapper;
	
	@Autowired
	private HosUserAccountMapper hosUserAccountMapper;		

	@Autowired
	private HosUserInviteMapper hosUserInviteMapper;
	
	@Autowired
	private HosUserAccountTransMapper hosUserAccountTransMapper;	
	
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
		
		return null;
	}

	@Override
	public CKResponse enroll(HosUserBo hosUserBo) {
		return null;
	}

	@Override
	public CKResponse login(HosUserDto hosUserDto) {
		return null;
	}

	@Override
	public CKResponse password(HosUserBo hosUserBo) {
		
		return null;
	}

	/**
	 * 密码合规则检查：1.长度 2.是否有数字、字母、特殊字符
	 * 
	 * @param password
	 *            密码
	 * @return
	 */
	private boolean passwordCheck(String password) {
		return false;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public CKResponse authentication(HosUser hosUser) throws ExceptionNullUpdate {
		CKResponse cKResponse = new CKResponse();
		cKResponse.setErrorCode(ConstantApi.ERROR_CODE.SUCCESS);
		cKResponse.setErrorMsg(ConstantApi.ERROR_MSG.SUCCESS);		
		int i = 0;
	
		//更改认证状态real_stat
		try {
			i = hosUserMapper.update(hosUser);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUser.toString());
			}
		} catch (SQLException e) {
			logger.error("更改认证状态失败:" + hosUser.toString() + e.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("更改认证状态失败");
			return cKResponse;
		}
		//如果认证失败则结束，否则继续
		if ((i > 0) & (ConstantSimpleConsole.REAL_STAT.AUTHFAIL.equals(hosUser.getRealStat()))){
			return cKResponse;
		}
		
		if (!ConstantSimpleConsole.REAL_STAT.AUTHED.equals(hosUser.getRealStat())){
			return cKResponse;
		}
		
		//取注册时送的奖励币数
		HosUserAccountTransDto hosUserAccountTransDto = new HosUserAccountTransDto();
		hosUserAccountTransDto.setUserId(hosUser.getId());
		HosUserAccountTransVo hosUserAccountTransVo = null;
		try {
			hosUserAccountTransVo = hosUserAccountTransMapper.getFirstObject(hosUserAccountTransDto);
			if (null == hosUserAccountTransVo){
				throw new ExceptionNullUpdate(hosUserAccountTransDto.toString());
			}
		} catch (SQLException e3) {
			logger.error("查询注册奖励币数失败:" + hosUserAccountTransDto.toString() + e3.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询注册奖励币数失败");
			return cKResponse;
		}
		Double dEnroll = hosUserAccountTransVo.getCoinNumber() + 0.0;
		
		Double authReward = dEnroll;//认证人奖励=1*100+n*50，需要计算			
		
		//查询认证人的账户
		HosUserAccountDto hosUserAccountDto = new HosUserAccountDto();
		hosUserAccountDto.setUserId(hosUser.getId());
		HosUserAccountVo hosUserAccountVo;
		try {
			hosUserAccountVo = hosUserAccountMapper.getObject(hosUserAccountDto);
			if (null == hosUserAccountVo){
				throw new ExceptionNullUpdate(hosUserAccountDto.toString());
			}
		} catch (SQLException e2) {
			logger.error("查询认证人失败:" + hosUserAccountDto.toString() + e2.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询认证人失败");
			return cKResponse;
		}
		
		//查询被邀请者
		HosUserInviteDto hosUserInviteDto = new HosUserInviteDto();
		hosUserInviteDto.setUserId(hosUser.getId());
		List<HosUserInviteVo> list = null;
		try {
			list = hosUserInviteMapper.listAllObjects(hosUserInviteDto);
		} catch (SQLException e) {
			logger.error("查询被邀请者出错:" + hosUserInviteDto.toString() + e.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询被邀请者出错");
			return cKResponse;
		}
		//遍历实名的被邀请者
		for (HosUserInviteVo e : list){
			if (ConstantSimpleConsole.REAL_STAT.AUTHED.equals(e.getInviteUserRealStat())){
				//实名被邀请者，认证人的账户中50币从冻结移到可用
				authReward += e.getCoinReward();
				//实名被邀请者，userAccountTrans中为认证人添加一笔冻结到可用的操作记录(50币)，备注被邀请者
				HosUserAccountTrans hosUserAccountTrans  = new HosUserAccountTrans();
				hosUserAccountTrans.setUserId(hosUser.getId());
				hosUserAccountTrans.setDcFlag(ConstantSimpleConsole.DC_FLAG.N);
				hosUserAccountTrans.setTransType(ConstantSimpleConsole.TRANS_TYPE.DEFREEZE);
				hosUserAccountTrans.setOtherUserId(hosUser.getId());
				hosUserAccountTrans.setCoinNumber(e.getCoinReward());
				hosUserAccountTrans.setCoinBal(hosUserAccountVo.getCoinTotal());//总币不变
				hosUserAccountTrans.setStat(ConstantSimpleConsole.STAT.ONE);
				hosUserAccountTrans.setNotes(e.getInviteUserId()+"");
				try {
					i = hosUserAccountTransMapper.insert(hosUserAccountTrans);
					if (i < 1){
						throw new ExceptionNullUpdate(hosUserAccountTrans.toString());
					}
				} catch (SQLException e1) {
					logger.error("新增账户交易明细出错:" + hosUserAccountTrans.toString() + e1.getMessage());
					cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
					cKResponse.setErrorMsg("新增账户交易明细出错");
					return cKResponse;
				}
			}
		}
		
		//userAccountTrans中添加一笔冻结到可用的操作记录(100币),备注认证人
		HosUserAccountTrans hosUserAccountTrans  = new HosUserAccountTrans();
		hosUserAccountTrans.setUserId(hosUser.getId());
		hosUserAccountTrans.setDcFlag(ConstantSimpleConsole.DC_FLAG.N);
		hosUserAccountTrans.setTransType(ConstantSimpleConsole.TRANS_TYPE.DEFREEZE);
		hosUserAccountTrans.setOtherUserId(hosUser.getId());
		hosUserAccountTrans.setCoinNumber(dEnroll);
		hosUserAccountTrans.setCoinBal(hosUserAccountVo.getCoinTotal());//总币不变
		hosUserAccountTrans.setStat(ConstantSimpleConsole.STAT.ONE);
		hosUserAccountTrans.setNotes(hosUser.getId()+"");
		try {
			i = hosUserAccountTransMapper.insert(hosUserAccountTrans);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUserAccountTrans.toString());
			}			
		} catch (SQLException e1) {
			logger.error("新增账户交易明细出错:" + hosUserAccountTrans.toString() + e1.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("新增账户交易明细出错");
			return cKResponse;
		}	
		//认证人userAccount的账户中奖励(1*100+n*50)从冻结移到可用
		HosUserAccount  hosUserAccount = new HosUserAccount();
		hosUserAccount.setId(hosUserAccountVo.getId());
		hosUserAccount.setUserId(hosUserAccountVo.getUserId());
		hosUserAccount.setGmtModified(DateUtil.getTime(hosUserAccountVo.getGmtModifiedStr()));
		hosUserAccount.setCoinTotal(hosUserAccountVo.getCoinTotal());
		hosUserAccount.setCoinAvailable(hosUserAccountVo.getCoinAvailable() + authReward);
		hosUserAccount.setCoinFreeze(hosUserAccountVo.getCoinFreeze() - authReward);
		try {
			i = hosUserAccountMapper.updateCoin(hosUserAccount);
			if (i < 1){
				throw new ExceptionNullUpdate(hosUserAccount.toString());
			}			
		} catch (SQLException e1) {
			logger.error("更新账户金额出错:" + hosUserAccount.toString() + e1.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("更新账户金额出错");
			return cKResponse;
		}
		
		//提取邀请者ID
		hosUserInviteDto.setUserId(null);
		hosUserInviteDto.setInviteUserId(hosUserAccount.getUserId());
		HosUserInviteVo hosUserInviteVo;
		try {
			hosUserInviteVo = hosUserInviteMapper.getObject(hosUserInviteDto);
		} catch (SQLException e3) {
			logger.error("查询邀请者出错:" + hosUserInviteDto.toString() + e3.getMessage());
			cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
			cKResponse.setErrorMsg("查询邀请者出错");
			return cKResponse;
		}
		//查询邀请者账户
		HosUserAccountVo hosUserAccountVoInvite = null;
		if (null != hosUserInviteVo){
			HosUserAccountDto hosUserAccountDtoInvite = new HosUserAccountDto();
			hosUserAccountDtoInvite.setUserId(hosUserInviteVo.getUserId());
			try {
				hosUserAccountVoInvite = hosUserAccountMapper.getObject(hosUserAccountDtoInvite);
			} catch (SQLException e1) {
				logger.error("查询邀请者账户出错:" + hosUserAccountDtoInvite.toString() + e1.getMessage());
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				cKResponse.setErrorMsg("查询邀请者账户出错");
				return cKResponse;
			}
		}
		
		
		if (null != hosUserAccountVoInvite  
				&& ConstantSimpleConsole.REAL_STAT.AUTHED.equals(hosUserAccountVoInvite.getRealStat())){
			//从邀请表中得到奖励币数
			//HosUserInviteDto hosUserInviteDto = new HosUserInviteDto();
			hosUserInviteDto.setUserId(hosUserAccountVoInvite.getUserId());
			hosUserInviteDto.setInviteUserId(hosUser.getId());
			//hosUserInviteVo = null;
			try {
				hosUserInviteVo = hosUserInviteMapper.getObject(hosUserInviteDto);
				if (null == hosUserInviteVo){
					throw new ExceptionNullUpdate(hosUserInviteDto.toString());
				}				
			} catch (SQLException e2) {
				logger.error("查询邀请者出错:" + hosUserInviteDto.toString() + e2.getMessage());
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				cKResponse.setErrorMsg("查询邀请者出错");
				return cKResponse;
			}
			Double dInvite = hosUserInviteVo.getCoinReward();
			//实名邀请者的账户中dInvite币从冻结移到可用
			HosUserAccount  hosUserAccountInvite = new HosUserAccount();
			hosUserAccountInvite.setId(hosUserAccountVoInvite.getId());
			hosUserAccountInvite.setUserId(hosUserAccountVoInvite.getUserId());
			hosUserAccountInvite.setGmtModified(DateUtil.getTime(hosUserAccountVoInvite.getGmtModifiedStr()));
			hosUserAccountInvite.setCoinAvailable(hosUserAccountVoInvite.getCoinAvailable() + dInvite);
			hosUserAccountInvite.setCoinFreeze(hosUserAccountVoInvite.getCoinFreeze() - dInvite);
			hosUserAccountInvite.setCoinTotal(hosUserAccountVoInvite.getCoinTotal());
			try {
				i = hosUserAccountMapper.updateCoin(hosUserAccountInvite);
				if (i < 1){
					throw new ExceptionNullUpdate(hosUserAccountInvite.toString());
				}				
			} catch (SQLException e1) {
				logger.error("更新邀请者账户金额出错:" + hosUserAccountInvite.toString() + e1.getMessage());
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				cKResponse.setErrorMsg("更新邀请者账户金额出错");
				return cKResponse;
			}
			//实名邀请者，userAccountTrans中为邀请者添加一笔 冻结到可用的操作记录(50币)，备注认证人
			hosUserAccountTrans  = new HosUserAccountTrans();
			hosUserAccountTrans.setUserId(hosUserAccountVoInvite.getUserId());
			hosUserAccountTrans.setDcFlag(ConstantSimpleConsole.DC_FLAG.N);
			hosUserAccountTrans.setTransType(ConstantSimpleConsole.TRANS_TYPE.DEFREEZE);
			hosUserAccountTrans.setOtherUserId(hosUserAccountVoInvite.getUserId());
			hosUserAccountTrans.setCoinNumber(dInvite);
			hosUserAccountTrans.setCoinBal(hosUserAccountVoInvite.getCoinTotal());
			hosUserAccountTrans.setStat(ConstantSimpleConsole.STAT.ONE);
			hosUserAccountTrans.setNotes(hosUser.getId()+"");
			try {
				i = hosUserAccountTransMapper.insert(hosUserAccountTrans);
				if (i < 1){
					throw new ExceptionNullUpdate(hosUserAccountTrans.toString());
				}				
			} catch (SQLException e1) {
				logger.error("新增账户交易明细出错(邀请者):" + hosUserAccountTrans.toString() + e1.getMessage());
				cKResponse.setErrorCode(ConstantApi.ERROR_CODE.ERROR);
				cKResponse.setErrorMsg("新增账户交易明细出错(邀请者)");
				return cKResponse;
			}			
		}
		
		return cKResponse;
	}	
	
}
