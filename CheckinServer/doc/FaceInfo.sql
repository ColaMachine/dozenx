


CREATE TABLE `FaceInfo` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '编号',
 `userId` BIGINT(13) NOT NULL   COMMENT '用户Id',
 `face` VARCHAR(1000) NOT NULL   COMMENT '人脸特征数组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人脸信息';


CREATE TABLE `CheckinOut` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(13) NOT NULL COMMENT '用户Id',
  `checkType` int(3) NOT NULL COMMENT '考勤类型',
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1229 DEFAULT CHARSET=utf8 COMMENT='考勤';


CREATE TABLE `CheckinLate` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(13) NOT NULL COMMENT '用户Id',
  `userName` varchar(20) NOT NULL COMMENT '用户名',
  `checkType` int(3) NOT NULL COMMENT '考勤类型',
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '检查时间',
  `kqUserId` bigint(13) NOT NULL COMMENT '考勤机用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='考勤迟到表';

CREATE TABLE `FaceCheckinOut` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` bigint(13) NOT NULL COMMENT '用户Id',
  `userName` varchar(20) NOT NULL COMMENT '用户姓名',
  `camera` varchar(20) NOT NULL COMMENT '摄像机编号',
  `checkType` int(3) NOT NULL COMMENT '考勤类型',
  `checkTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `score` float(5,3) DEFAULT NULL COMMENT '人脸匹配度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='考勤';










