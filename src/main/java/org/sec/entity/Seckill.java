package org.sec.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Seckill {
	private long seckillId;
	
	private String name2;
	private int number;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	
	
	public long getSeckillId() {
		return seckillId;
	}


	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}


	


	public String getName2() {
		return name2;
	}


	public void setName2(String name2) {
		this.name2 = name2;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name2 + ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}
	
	
	
	
	
}
