package com.fof.component.redis.service.impl;

import com.fof.common.util.Constants;
import com.fof.component.redis.service.IRedisDictionaryService;
import com.fof.component.redis.util.Code;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.dao.DictionaryInfoDao;
import com.fof.init.dao.DictionaryTypeDao;
import com.fof.init.entity.SysDictionaryInfoEntity;
import com.fof.init.entity.SysDictionaryTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisDictionaryServiceImpl implements IRedisDictionaryService {
	@Autowired
	private DictionaryTypeDao dictionaryTypeDao;
	@Autowired
	private DictionaryInfoDao dictionaryInfoDao;
	@Resource
	private CodeTableUtil codeTableUtil;
	/**缓存组标识*/
	private static final String SSI_CODE_TABLE = "SSI_CODE_TABLE";
	
	public void reloadCache() {
		codeTableUtil.delCacheByGroupKey(SSI_CODE_TABLE);
		/**业务缓存集合*/
		Map<String,Object> searchParams=new HashMap<String,Object>();
		searchParams.put("delete_flag", Constants.DELFLG_N);
		/**业务字典类型集合*/	 
		List<SysDictionaryTypeEntity> dictionaryTypelist=dictionaryTypeDao.getAll(searchParams);
		
		/**需要数据缓存*/
		if(!CollectionUtils.isEmpty(dictionaryTypelist)) {
			Map<String,Object> cacheMap=new HashMap<String,Object>();
			for (SysDictionaryTypeEntity sysDictionaryTypeEntity : dictionaryTypelist) {
				searchParams.put("foreignId", sysDictionaryTypeEntity.getId());
				
				List<SysDictionaryInfoEntity> dictionaryInfoList=dictionaryInfoDao.getAll(searchParams);
				List<Code> codeList=infoTranCode(dictionaryInfoList);
				cacheMap.put(sysDictionaryTypeEntity.getCode(), codeList);
			}
			codeTableUtil.setCodesByGroupKey(SSI_CODE_TABLE, cacheMap);
		}
	}

	public List<Code> infoTranCode(List<SysDictionaryInfoEntity> list){
		List<Code> codeList=new ArrayList<Code>();
		for(SysDictionaryInfoEntity entity : list) {
		        Code code = new Code();
	            code.setKey(entity.getCode());
	            code.setDisplay(entity.getName());
	            code.setBak1(entity.getBak1());
	            code.setBak2(entity.getBak2());
	            code.setBak3(entity.getBak3());
	            code.setBak4(entity.getBak4());
	            code.setBak5(entity.getBak5());
	            code.setBak6(entity.getBak6());
	            code.setBak7(entity.getBak7());
	            code.setBak8(entity.getBak8());
	            code.setBak9(entity.getBak9());
	            code.setBak10(entity.getBak10());
	            codeList.add(code);
		}
		return codeList;
	}

}
