package org.sec.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.sec.dao.SeckillDao;
import org.sec.dao.SuccessKillDao;
import org.sec.dto.Exposer;
import org.sec.dto.SeckillExecution;
import org.sec.entity.Seckill;
import org.sec.entity.SuccessKill;
import org.sec.enums.SeckillStatEnum;
import org.sec.exception.RepeatKillException;
import org.sec.exception.SeckillCloseException;
import org.sec.exception.SeckillException;
import org.sec.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@Transactional

/**
 * 使用注解控制事务方法的优点：
 * 1，开发团队达成一致的约定， 明确标注事务方法的编程风格
 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作  RPC/HTTP请求 或者剥离到方法事务外部
 * 3.不是所有的方法都需要事务，如 只有一条修改操作，或者是只读操作 不需要事务控制
 * @author ym
 *
 */
public class SeckillServiceImpl implements SeckillService {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Resource
	private SuccessKillDao successKillDao;
	
	//md5盐值字符串，用于混淆MD5
	private final String slat="123dwqdwqdr2rt34t43t45";
	
	public List<Seckill> getSeckillList() {
			List<Seckill> queryAll = seckillDao.queryAll(0, 5);
		return queryAll;
		
	}

	public Seckill getById(long seckillId) {
		Seckill queryById = seckillDao.queryById(seckillId);
		return queryById;
	}

	public Exposer exportSeckillUrl(long seckillId) {
		Seckill queryById = seckillDao.queryById(seckillId);
		System.out.println("queryByiD:"+queryById);
		if(queryById==null){
			return new Exposer(false, seckillId);
		}
		Date startTime =  queryById.getStartTime();
		Date endTime = queryById.getEndTime();
		//系统当前时间
		Date nowTime = new Date();
		if(nowTime.getTime() < startTime.getTime() 
				|| nowTime.getTime() > endTime.getTime()){
			return new Exposer(false, seckillId, nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		
		//转化特定字符串的过程，不可逆
		String md5 = getMD5(seckillId); //TODO
		return new Exposer(true, md5, seckillId);
	
	}
	
	
	private String getMD5(long seckillId){
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
	
			
		
		if(md5==null ||  !md5.equals(getMD5(seckillId))){
		throw new SeckillException("seckill data rewrite");
		}
		
		
		try {
			//执行秒杀逻辑： 减库存 + 记录购买行为
			
			//@transactionl 在 insertSuccessKill 重复秒杀后 把  reduceNumber 给回滚了
		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(seckillId, killTime);
		 if(updateCount <= 0){
			 //没有更新到记录,秒杀结束
			 throw new SeckillCloseException("seckill is closed");
		 }else{
			 //记录购买行为
			 int insertCount = successKillDao.insertSuccessKill(seckillId, userPhone);
			 if(insertCount <=0){
				 throw new RepeatKillException("重复秒杀");
			 }else{
				 //秒杀成功
				 SuccessKill sk = successKillDao.queryByIdWithSeckill(seckillId, userPhone);
				 return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			 }
		 }
		 
		} catch(SeckillCloseException e1){
			throw e1;
			
		}catch(RepeatKillException e2){
			throw e2;
			
		}catch (Exception e) {
			logger.error(e.getMessage());
			//所有编译期其他异常，转化为运行期异常
			throw new SeckillException("内部异常： "+e.getMessage());
		}
	
	
	}

}
