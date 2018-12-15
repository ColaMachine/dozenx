



CREATE TABLE `Goods` (
 `id` bigint(9)   AUTO_INCREMENT  COMMENT '编号',
 `shopId` bigint(9)    COMMENT '商户id',
 `name` VARCHAR(50) NOT NULL   COMMENT '名称',
 `subName` VARCHAR(50) NOT NULL   COMMENT '副标题',
 `detail` VARCHAR(500) NOT NULL   COMMENT '副标题',
 `address` VARCHAR(200)    COMMENT '地址',
 `telno` varchar(11)    COMMENT '手机号码',
 `img` varchar(100) NOT NULL   COMMENT '图片',
 `img1` varchar(100)    COMMENT '图片',
 `img2` varchar(100)    COMMENT '图片',
 `img3` varchar(100)    COMMENT '图片',
 `remark` varchar(200)    COMMENT '备注',
 `status` int(1) NOT NULL  DEFAULT 0  COMMENT '状态',
 `price` int(11)    COMMENT '价格',
 `tags` varchar(100)    COMMENT '标签',
 `priceDesc` varchar(50)    COMMENT '价格描述',
 `creator` bigint(11)    COMMENT '创建人id',
 `creatorName` varchar(50)    COMMENT '创建人姓名',
 `platform` varchar(50)    COMMENT '平台名称',
 `comments` int(11)   DEFAULT 0  COMMENT '评论数',
 `score` float(6,3)   DEFAULT 0  COMMENT '分数',
 `link` varchar(100)    COMMENT '外链',
 `up` int(11)   DEFAULT 0  COMMENT '顶',
 `down` int(11)   DEFAULT 0  COMMENT '踩',
 `createTime` timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '创建时间',
 `updateTime` timestamp   DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品';


INSERT INTO `goods` (`id`, `name`, `subName`, `detail`, `img`, `remark`, `status`, `price`, `priceDesc`, `creatorName`, `platform`, `comments`, `score`, `link`, `up`, `down`, `createTime`, `updateTime`) VALUES ('2', 'J.H.Longess 顺滑宝宝绒保暖星座系冬季四件套 1.5米床（被套200*230cm）', '', '<p>和路雪是英国著名的冰激凌企业，后被联合利华收购，是世界畅销冰淇淋品牌之一。</p>', 'https://y.zdmimg.com/201810/15/5bc43400258d19418.jpg', '和路雪是英国著名的冰激凌企业，后被联合利华收购，是世界畅销冰淇淋品牌之一。', '0', '100', '可优惠至19元', 'zhangsan', '天猫精选', '12', '0.000', 'https://go.smzdm.com/ee8008d56b674415/cb_aa_yh_95_11787341_10124_36035_111_0', '13', '1', '2018-12-02 20:05:51', '2018-12-02 12:05:02');










