package org.sec.dao;

import org.apache.ibatis.annotations.Param;
import org.sec.entity.SuccessKill;

public interface SuccessKillDao {
	
	//查询购买明细，可过滤重复
	//int>1，表示更新记录的行数
	int insertSuccessKill(@Param("seckillId") long seckillId,   @Param("userPhone")long userPhone);
	
	//根据id查询successKill并携带秒杀产品对象实体
	SuccessKill  queryByIdWithSeckill(@Param("seckillId")long seckillId ,@Param("userPhone") long userPhone);
}
