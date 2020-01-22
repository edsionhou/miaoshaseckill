package org.sec.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sec.entity.Seckill;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 閰嶇疆spring鍜宩unit鏁村悎锛宩unit鍚姩鏃跺姞杞絪pring瀹瑰櫒
 * spring-test,junit
 * 
 * @author ym
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
//鍛婅瘔junit spring閰嶇疆鏂囦欢
@ContextConfiguration({"classpath:/conf/spring-dao.xml"})
public class SeckillDaoTest {
	
	//娉ㄥ叆Dao瀹炵幇绫讳緷璧�
	@Resource
	private SeckillDao seckilldao;
	
	
	@Test
	public void testReduceNumber() {
		Date date = new Date();
		
		int reduceNumber = seckilldao.reduceNumber(1000,date);
		System.out.println(reduceNumber);
	
	}

	@Test
	public void testQueryById() {
		long id=1000;
		Seckill seckill = seckilldao.queryById(id);
		System.out.println(seckill.getName2());
		System.out.println(seckill);
	
	}
	/**
	 * 1000鍏冪鏉�iphone 11 
Seckill [seckillId=1000, name=1000鍏冪鏉�iphone 11 , 
number=100, startTime=Mon Nov 11 00:00:00 CST 2019, 
endTime=Tue Nov 12 00:00:00 CST 2019, createTime=Sat Jan 18 11:17:12 CST 2020]
	 	
	 	Date 鏄� TimeStamp鐨勭埗绫伙紝鎵�浠� 鍚庤�呭彲浠ュ悜鍓嶈�呰浆鎹紝浣� date涓嶈兘杞崲鍚庤�咃紝闇�瑕乼oString(date.getTime())
	 *
	 */
	

	@Test
	public void testQueryAll() {
		List<Seckill> queryAll = seckilldao.queryAll(0, 2);
		for(Seckill s:queryAll){
			System.out.println(s);
		}
	
	}

}
