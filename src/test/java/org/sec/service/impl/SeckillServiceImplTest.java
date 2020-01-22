package org.sec.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sec.dto.Exposer;
import org.sec.dto.SeckillExecution;
import org.sec.entity.Seckill;
import org.sec.exception.RepeatKillException;
import org.sec.exception.SeckillCloseException;
import org.sec.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring-service.xml","classpath:conf/spring-dao.xml"})
public class SeckillServiceImplTest {
	//不懂
	private final Logger looger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private SeckillService service;
	
	
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = service.getSeckillList();
			looger.info("list={}",seckillList);
	}

	@Test
	public void testGetById() {
			Seckill byId = service.getById(1000);
			looger.info("seckill={}",byId);
	}

	//测试代码完整逻辑，注意可重复执行
	@Test
	public void testExportSeckillLogic() {
		long id=10005;
		Exposer exportSeckillUrl = service.exportSeckillUrl(id);
		
		//整合这俩方法
		if(exportSeckillUrl.isExposed()){
			looger.info("exposer={}",exportSeckillUrl);
			
			//long id = 1000;
			long phone = 13054269997L;
			String md5 = "e19249e9e6a2aa3dea303147aa9d8240";
			try {
				SeckillExecution executeSeckill = service.executeSeckill(id, phone, md5);
				looger.info("秒杀={}",executeSeckill);
				//插入第二次时，回滚了，这次失败
			} catch (RepeatKillException e) {
				//捕捉秒杀，不抛异常
				looger.error(e.getMessage());
			}catch (SeckillCloseException e) {
				//捕捉秒杀，不抛异常
				looger.error(e.getMessage());
			}
		
		}else{
			
			//秒杀未开启
			looger.warn("exposer222={}",exportSeckillUrl);
		}
		//looger.info("exposer={}",exportSeckillUrl);
	//exposer=Exposer [exposed=true, md5=e19249e9e6a2aa3dea303147aa9d8240, seckillId=1000, now=0, start=0, end=0]
	}

	@Test
	public void testExecuteSeckill() {
		
		
	}

}
