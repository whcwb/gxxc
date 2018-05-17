--已更新至 gxxc数据库
ALTER TABLE gxxc.biz_hd MODIFY HD_SX VARCHAR(2) COMMENT '属性（字典表：c 1、驾校  2、训练场）';
ALTER TABLE gxxc.biz_order MODIFY DD_ZT VARCHAR(2) COMMENT '订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)';
ALTER TABLE gxxc.biz_order MODIFY DD_ZFTD VARCHAR(32) COMMENT '支付通道(ZDCLK0038 1、支付宝  2、微信  3、银联  4、快钱……)';
ALTER TABLE gxxc.biz_order MODIFY DD_ZFZT VARCHAR(2) DEFAULT '0' COMMENT '支付状态（ZDCLK0039  0,待支付 1、支付成功  2、支付失败）';
ALTER TABLE gxxc.biz_order MODIFY DD_ZFJG VARCHAR(2) COMMENT '支付响应结果 ZDCLK0040 (1:成功 2:失败)';

ALTER TABLE gxxc.biz_ptyh MODIFY YH_LX VARCHAR(2) COMMENT '类型 ZDCLK0041(2、驾驶员、1、学员)';
ALTER TABLE gxxc.biz_ptyh MODIFY YH_XB VARCHAR(32) COMMENT '性别 ZDCLK0042(1、男;2、女)';
ALTER TABLE gxxc.biz_ptyh MODIFY YH_ZT VARCHAR(2) COMMENT '认证状态 ZDCLK0043(0 未认证、1 已认证)';
ALTER TABLE gxxc.biz_ptyh MODIFY DD_SFJX VARCHAR(2) COMMENT '是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)';
ALTER TABLE gxxc.biz_ptyh MODIFY YH_IXY_SFFP VARCHAR(2) COMMENT '学员是否已分配 ZDCLK0046 (0 否  1 是)';
ALTER TABLE gxxc.biz_ptyh MODIFY YH_SFSD VARCHAR(2) COMMENT '用户是否锁定 ZDCLK0046 (0 否  1 是)';

ALTER TABLE gxxc.biz_tx MODIFY TT_FS VARCHAR(2) COMMENT '提现方式 ZDCLK0047 (1、微信红包 2、人工转账)';
ALTER TABLE gxxc.biz_tx MODIFY TT_ZT VARCHAR(2) COMMENT '提现状态 ZDCLK0048 (0 待审核 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请)';
ALTER TABLE gxxc.biz_tx MODIFY TT_SHZT VARCHAR(2) DEFAULT '0' COMMENT '提现审核状态 ZDCLK0049 (0、 待审核 1、 审核通过 2、 审核拒绝)';

ALTER TABLE gxxc.biz_user MODIFY YH_SFJSZ VARCHAR(2) COMMENT '是否有驾驶证 ZDCLK0046 (0 否  1 是)';

ALTER TABLE gxxc.biz_wj MODIFY WJ_SX VARCHAR(2) COMMENT '文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)';
ALTER TABLE gxxc.biz_wj MODIFY WJ_SBZT VARCHAR(2) DEFAULT '0' COMMENT '图片是否ORC进行了识别 ZDCLK0052 （0、 待识别 1、 识别成功 2、 识别失败）';
ALTER TABLE gxxc.biz_wj MODIFY WJ_SFYX VARCHAR(2) COMMENT '有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。';

ALTER TABLE gxxc.biz_yjmx MODIFY ZJ_FS_ VARCHAR(2) COMMENT '费用方式 ZDCLK0053 (1 回扣 -1 提现)';
ALTER TABLE gxxc.biz_yjmx MODIFY ZJ_ZT VARCHAR(2) COMMENT '提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1  ';

