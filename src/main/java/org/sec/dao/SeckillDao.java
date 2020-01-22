package org.sec.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.sec.entity.Seckill;

public interface SeckillDao {
	
	//减库存
	//int>1，表示更新记录的行数
	
	//传多个参数的方法之一 @Param， xml里不必写 parameterType
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	//根据id查询秒杀商品
	Seckill  queryById(long seckillId);
	
	//根据偏移量查询秒杀列表
	/*
	 * //java没有保存形参的记录 queryAll(int a, int b) >> queryAll(arg0,arg1)
	 * 需要@param 指定参数
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
