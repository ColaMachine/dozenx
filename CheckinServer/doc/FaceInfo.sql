


CREATE TABLE `FaceInfo` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '编号',
 `userId` BIGINT(13) NOT NULL   COMMENT '用户Id',
 `face` VARCHAR(1000) NOT NULL   COMMENT '人脸特征数组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人脸信息';












