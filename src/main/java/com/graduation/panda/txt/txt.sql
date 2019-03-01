-- auto-generated definition
CREATE TABLE sys_user
(
  id       BIGINT AUTO_INCREMENT
  COMMENT '编号'
    PRIMARY KEY,
  name     VARCHAR(50)  NOT NULL
  COMMENT '用户名',
  password VARCHAR(100) NOT NULL
  COMMENT '密码',
  salt     VARCHAR(40)  NULL
  COMMENT '盐',
  email    VARCHAR(100) NULL
  COMMENT '邮箱',
  mobile   VARCHAR(100) NULL
  COMMENT '手机号',
  kind     VARCHAR(100) NULL
  COMMENT '所属种类',
  CONSTRAINT name
  UNIQUE (name)
)
  COMMENT '用户'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE sys_user_token
(
  id               BIGINT AUTO_INCREMENT
  COMMENT '编号'
    PRIMARY KEY,
  user_id          BIGINT       NOT NULL,
  token            VARCHAR(100) NOT NULL
  COMMENT 'token',
  expire_time      DATETIME     NULL
  COMMENT '过期时间',
  create_by        VARCHAR(50)  NULL
  COMMENT '创建人',
  create_time      DATETIME     NULL
  COMMENT '创建时间',
  last_update_by   VARCHAR(50)  NULL
  COMMENT '更新人',
  last_update_time DATETIME     NULL
  COMMENT '更新时间',
  CONSTRAINT token
  UNIQUE (token)
)
  COMMENT '用户Token'
  ENGINE = InnoDB;


CREATE TABLE staff_info
(
  staff_id        VARBINARY(50) NOT NULL
  COMMENT '员工编号',
  PRIMARY KEY(staff_id),
  staff_number     VARCHAR(50)  NOT NULL
  COMMENT '身份证号',
  staff_name VARCHAR(100) NOT NULL
  COMMENT '员工名字',
  staf_sex     VARCHAR(40)  NULL
  COMMENT '员工性别',
  staff_phone    VARCHAR(100) NULL
  COMMENT '员工手机',
  staff_type   VARCHAR(100) NULL
  COMMENT '员工类型（0代购员，1是配送员）',
  CONSTRAINT staff_id
  UNIQUE (staff_id)
)
  COMMENT '员工'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE order_info
(
  order_id       VARCHAR(50) NOT NULL
  COMMENT '订单编号'
    PRIMARY KEY,
  order_type     VARCHAR(50)  NOT NULL
  COMMENT '订单托运类型',
  order_price VARCHAR(100) NOT NULL
  COMMENT '订单价格'
)
  COMMENT '订单'
  ENGINE = InnoDB;

-- auto-generated definition
CREATE TABLE user_info
(
  customer_id      VARCHAR(50)  NOT NULL
  COMMENT '顾客编码'
    PRIMARY KEY,
  customer_name    VARCHAR(50)  NOT NULL
  COMMENT '顾客真实姓名',
  customer_number  VARCHAR(100) NOT NULL
  COMMENT '顾客身份证号',
  customer_phone   VARCHAR(40)  NULL
  COMMENT '顾客电话',
  customer_address VARCHAR(100) NULL
  COMMENT '顾客收货地址',
  CONSTRAINT customer_name
  UNIQUE (customer_name)
)
  COMMENT '顾客'
  ENGINE = InnoDB;


  -- auto-generated definition
CREATE TABLE goods_info
(
  goods_id        VARCHAR(50)  NOT NULL
  COMMENT '商品唯一标识码'
    PRIMARY KEY,
  goods_name      VARCHAR(50)  NOT NULL
  COMMENT '商品名称',
  market_name     VARCHAR(100) NOT NULL
  COMMENT '所在超市名称',
  purchase_price  VARCHAR(40)  NULL
  COMMENT '采购价格',
  sell_price      VARCHAR(100) NULL
  COMMENT '出售价格',
  goods_introduce VARCHAR(100) NULL
  COMMENT '商品介绍',
  CONSTRAINT goods_name
  UNIQUE (goods_name)
)
  COMMENT '商品'
  ENGINE = InnoDB;



