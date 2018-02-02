package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class FlightBaseInfoServiceImpl extends BaseDao implements FlightBaseInfoService {

	//根据出发地三字码查询出发机场三字码
		@Override
		public List<String> getDepApcodeByCtcode(String depCode)  {
			List<String> list = new ArrayList<>();
			try {
				if (depCode!=null&&depCode!="") {
					String codes = String.valueOf(sqlSessionTemplate.selectList(getNameSpace()+"getDepApcodeByCtcode",depCode).get(0));
					 list = getList(codes);
				}
			} catch (Exception e) {
				LogUtil.error("400", FlightBaseInfoServiceImpl.class, "根据出发地三字码查询出发机场三字码异常！", e);
			}
			return list;
		}
		//根据到达地三字码查询到达机场三字码
		@Override
		public List<String> getDesApcodeByCtcode(String desCode)  {
			List<String> list = new ArrayList<>();
			try {
				if (desCode!=null&&desCode!="") {
					String codes = String.valueOf(sqlSessionTemplate.selectList(getNameSpace()+"getDesApcodeByCtcode", desCode).get(0));
					list = getList(codes);
				}
			} catch (Exception e) {
				LogUtil.error("400", FlightBaseInfoServiceImpl.class, "根据出发地三字码查询出发机场三字码异常！", e);
			}
			return list;
		}
		
		public List<String> getList(String codes){
			List<String> list = new ArrayList<>();
			if (StringUtils.isNotEmpty(codes)) {
				if (codes.indexOf(",")>0) {
					list = Arrays.asList(codes.split(","));
				}else{
					list.add(codes);
				}
			}
			return list;
		}

	@Override
	protected String getNameSpace() {
		return "FlightBaseInfoMapper.";
	}
}
