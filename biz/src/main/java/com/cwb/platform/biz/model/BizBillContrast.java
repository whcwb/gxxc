package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_bill_contrast")
public class BizBillContrast implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 交易时间
     */
    @Column(name = "TRADE_TIME")
    private String tradeTime;

    /**
     * 用户标识
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 交易类型
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
RECHARGE_REFUND，返回当日充值退款订单
     */
    @Column(name = "TRADE_TYPE")
    private String tradeType;

    /**
     * 交易状态  1=支付成功  2 退款
     */
    @Column(name = "TRADE_STATE")
    private String tradeState;

    /**
     * 总金额(元)
     */
    @Column(name = "TOTAL_FEE")
    private String totalFee;

    /**
     * 微信退款单号
     */
    @Column(name = "REFUND_ID")
    private String refundId;

    /**
     * 退款类型
     */
    @Column(name = "REFUND_CHANNEL")
    private String refundChannel;

    /**
     * 退款状态
     */
    @Column(name = "REFUND_STATE")
    private String refundState;

    /**
     * 支付通道(1、支付宝 2、微信)
     */
    @Column(name = "PAT_TYPE")
    private String patType;

    /**
     * 处理状态 0处理失败  1 成功
     */
    @Column(name = "DISPOSE_TYPE")
    private String disposeType;

    /**
     * 处理结果
     */
    @Column(name = "DISPOSE_MESSAGE")
    private String disposeMessage;
    /**
     * 退款金额
     */
    @Column(name = "REFUND_FEE")
    private String refundFee;
    /**
     * 平台ID
     */
    @Column(name = "ORDER_ID")
    private String orderId;

    /**
     * 支付凭证(第三方支付平台的唯一订单号)
     */
    @Column(name = "TRANSACTION_ID")
    private String transactionId;


    /**
     * 对账文件的原始报文
        微信端的报文文档：
        https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
     支付宝的报文文档：
     https://docs.open.alipay.com/204/106262/
     */
    @Column(name = "ORIGINAL_MESSAGE")
    private String originalMessage;

    private static final long serialVersionUID = 1L;

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**8
     *
     * 获取交易时间
     *
     * @return TRADE_TIME - 交易时间
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * 设置交易时间
     *
     * @param tradeTime 交易时间
     */
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * 获取用户标识
     *
     * @return OPEN_ID - 用户标识
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置用户标识
     *
     * @param openId 用户标识
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取交易类型
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
RECHARGE_REFUND，返回当日充值退款订单
     *
     * @return TRADE_TYPE - 交易类型
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
RECHARGE_REFUND，返回当日充值退款订单
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 设置交易类型
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
RECHARGE_REFUND，返回当日充值退款订单
     *
     * @param tradeType 交易类型
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
RECHARGE_REFUND，返回当日充值退款订单
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取交易状态
     *
     * @return TRADE_STATE - 交易状态
     */
    public String getTradeState() {
        return tradeState;
    }

    /**
     * 设置交易状态  1-支付成功  2-退款
     *
     * @param tradeState 交易状态
     */
    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    /**
     * 获取总金额(元)
     *
     * @return TOTAL_FEE - 总金额(元)
     */
    public String getTotalFee() {
        return totalFee;
    }

    /**
     * 设置总金额(元)
     *
     * @param totalFee 总金额(元)
     */
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取微信退款单号
     *
     * @return REFUND_ID - 微信退款单号
     */
    public String getRefundId() {
        return refundId;
    }

    /**
     * 设置微信退款单号
     *
     * @param refundId 微信退款单号
     */
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    /**
     * 获取退款类型
     *
     * @return REFUND_CHANNEL - 退款类型
     */
    public String getRefundChannel() {
        return refundChannel;
    }

    /**
     * 设置退款类型
     *
     * @param refundChannel 退款类型
     */
    public void setRefundChannel(String refundChannel) {
        this.refundChannel = refundChannel;
    }

    /**
     * 获取退款状态
     *
     * @return REFUND_STATE - 退款状态
     */
    public String getRefundState() {
        return refundState;
    }

    /**
     * 设置退款状态
     *
     * @param refundState 退款状态
     */
    public void setRefundState(String refundState) {
        this.refundState = refundState;
    }

    /**
     * 获取支付通道(ZDCLK0038  1、支付宝 2、微信)
     *
     * @return PAT_TYPE - 支付通道(1、支付宝 2、微信)
     */
    public String getPatType() {
        return patType;
    }

    /**
     * 设置支付通道(1、支付宝 2、微信)
     *
     * @param patType 支付通道(1、支付宝 2、微信)
     */
    public void setPatType(String patType) {
        this.patType = patType;
    }

    /**
     * 获取处理状态 0处理成功
     *
     * @return DISPOSE_TYPE - 处理状态 0处理成功
     */
    public String getDisposeType() {
        return disposeType;
    }

    /**
     * 设置处理状态 0处理成功
     *
     * @param disposeType 处理状态 0处理成功
     */
    public void setDisposeType(String disposeType) {
        this.disposeType = disposeType;
    }

    /**
     * 获取处理结果
     *
     * @return DISPOSE_MESSAGE - 处理结果
     */
    public String getDisposeMessage() {
        return disposeMessage;
    }

    /**
     * 设置处理结果
     *
     * @param disposeMessage 处理结果
     */
    public void setDisposeMessage(String disposeMessage) {
        this.disposeMessage = disposeMessage;
    }

    /**
     * 获取对账文件的报文

微信端的报文：
https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
     *
     * @return ORIGINAL_MESSAGE - 对账文件的报文

微信端的报文：
https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
     */
    public String getOriginalMessage() {
        return originalMessage;
    }

    /**
     * 设置对账文件的报文

微信端的报文：
https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
     *
     * @param originalMessage 对账文件的报文

微信端的报文：
https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6
     */
    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public enum InnerColumn {
        id("ID"),
        tradeTime("TRADE_TIME"),
        openId("OPEN_ID"),
        tradeType("TRADE_TYPE"),
        tradeState("TRADE_STATE"),
        totalFee("TOTAL_FEE"),
        refundId("REFUND_ID"),
        refundChannel("REFUND_CHANNEL"),
        refundState("REFUND_STATE"),
        patType("PAT_TYPE"),
        disposeType("DISPOSE_TYPE"),
        disposeMessage("DISPOSE_MESSAGE"),
        originalMessage("ORIGINAL_MESSAGE");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}