package com.travelsky.ypb.domain.service;

import java.util.List;

public interface FlightBaseInfoService {
	
	/**
	 * 根据出发地三字码查询出发机场三字码
	 * @param depCode
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getDepApcodeByCtcode(String depCode) throws Exception;
	/**
	 * 根据出发地三字码查询出发机场三字码
	 * @param desCode
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> getDesApcodeByCtcode(String desCode) throws Exception;

}
