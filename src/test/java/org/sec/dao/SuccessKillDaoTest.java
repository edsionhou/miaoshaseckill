package org.sec.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sec.entity.SuccessKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring-dao.xml"})
public class SuccessKillDaoTest {

	@Resource
	private SuccessKillDao dao;
	
	@Test
	public void testInsertSuccessKill() {
		int a = dao.insertSuccessKill(1000, 1305469999);
		System.out.println(a);
	
	}

	@Test
	public void testQueryByIdWithSeckill() {
			SuccessKill queryByIdWithSeckill = dao.queryByIdWithSeckill(1000l, 1305469999l);
			System.out.println(queryByIdWithSeckill);
			System.out.println(queryByIdWithSeckill.getSeckill());
	
	}
	/**
	 * SuccessKill [seckillId=1000, userPhone=1305469999, state=-1, createTime=Sat Jan 18 17:31:53 CST 2020, 
	 * seckill=Seckill 
	 * [seckillId=0, name=1000鍏冪鏉�iphone 11 ,
	 *  number=0, 
	 *  startTime=null, 
	 *  endTime=null,
	 *   createTime=null]]
	 */

}
