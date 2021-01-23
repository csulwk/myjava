CREATE TABLE `shedlock` (
  `name` varchar(64) NOT NULL COMMENT '分布式锁名称',
  `lock_until` timestamp(3) NOT NULL COMMENT '锁的结束时间',
  `locked_at` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '锁的开始时间',
  `locked_by` varchar(255) NOT NULL COMMENT '锁的拥有者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(32) NOT NULL DEFAULT '' COMMENT '更新用户',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='shedlock表';
