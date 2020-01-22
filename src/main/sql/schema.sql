create datebase seckill;

use seckill;

create table seckill(
`seckill_id` BIGINT not null AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(120) not null COMMENT '商品名称',
`number` int not null COMMENT '库存数量',
`start_time` datetime  NOT null COMMENT '秒杀开始时间',
`end_time` datetime not	 null COMMENT '秒杀结束时间',
`create_time` TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';


create table success_killed(
`seckill_id` BIGINT not null COMMENT '秒杀商品id',
`user_phone` BIGINT not null COMMENT '用户手机号',
`state` TINYINT not null default -1 COMMENT '状态 -1 无效，0 成功 1 已付款',
`create_time` TIMESTAMP not null COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone),   /*联合主键  */
KEY idx_create_time(create_time)

)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';



--连接数据库控制台
mysql -uroot -p123456


alter table seckill
drop index idx_create_time,
add index idx_c_s(start_time,create_time);