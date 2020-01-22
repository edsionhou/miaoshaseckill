package org.sec.service;

import java.util.List;

import org.sec.dto.Exposer;
import org.sec.dto.SeckillExecution;
import org.sec.entity.Seckill;
import org.sec.exception.RepeatKillException;
import org.sec.exception.SeckillCloseException;
import org.sec.exception.SeckillException;

/**
 * 业务接口：站在使用者角度设计接口
 * 三个方面：
 * 1.方法定义粒度
 * 2.参数
 * 3.返回类型( 类型/异常)
 * @author ym
 *
 */
public interface SeckillService {
	
	/*
	 * 查询所有秒杀记录
	 */
	List<Seckill>  getSeckillList();
	
	/*
	 * 查询单个秒杀记录
	 */
	
	Seckill getById(long seckillId);
	
	
	/*
	 * 秒杀开启时输出秒杀开启地址，
	 * 否则输出系统时间和秒杀时间
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 秒杀
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
	throws SeckillException,SeckillCloseException,RepeatKillException;
}
