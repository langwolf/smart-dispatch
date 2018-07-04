package com.lioncorp.dispatch.dao.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lioncorp.dispatch.dao.db.MyBatisRepository;
import com.lioncorp.dispatch.model.Receive;

@MyBatisRepository
public interface ReceiveMapper {
	
	void saveOrUpdate(@Param("dataList") List<Receive> dataList);
}
