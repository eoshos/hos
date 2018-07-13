package io.eoshos.core.api.service;

import com.chuangke18.framework.api.response.CKResponse;

import io.eoshos.core.api.bean.bo.HosUserInviteBo;
import io.eoshos.core.api.bean.dto.HosUserInviteDto;

public interface IHosUserInviteService extends IBaseService<HosUserInviteBo, HosUserInviteDto>{
	CKResponse countObjects(HosUserInviteDto dto);
	CKResponse countNumberInvite(HosUserInviteDto dto);
}
