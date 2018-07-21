package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_tj_dz")
public class BizTjDz implements Serializable {
    @Id
    @Column(name = "PKID")
    private String pkid;

    /**
     * 统计时间yyyymmdd
     */
    @Column(name = "DZ_SJ")
    private String dzSj;

    /**
     * 订单查询总数
     */
    @Column(name = "ORACLE_COUNT")
    private String oracleCount;

    /**
     * 订单查询交易额
     */
    @Column(name = "ORACLE_FEE")
    private String oracleFee;

    /**
     * 账单查询总数
     */
    @Column(name = "BILL_COUNT")
    private String billCount;

    /**
     * 账单查询金额
     */
    @Column(name = "BILL_FEE")
    private String billFee;

    /**
     * 成功笔数
     */
    @Column(name = "SUCCESS_COUNT")
    private String successCount;

    /**
     * 成功金额
     */
    @Column(name = "SUCCESS_FEE")
    private String successFee;

    /**
     * 对账成功的退款笔数
     */
    @Column(name = "SUCCESS_REFUND_COUNT")
    private String successRefundCount;

    /**
     * 对账成功的退款金额
     */
    @Column(name = "SUCCESS_REFUND_FEE")
    private String successRefundFee;

    /**
     * 订单失败笔数
     */
    @Column(name = "ORDER_FAIL_COUNT")
    private String orderFailCount;

    /**
     * 订单失败金额
     */
    @Column(name = "Bill_FAIL_COUNT")
    private String billFailCount;

    /**
     * 创建时间
     */
    @Column(name = "CREATION_TIME")
    private String creationTime;



    private static final long serialVersionUID = 1L;

    /**
     * @return PKID
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * @param pkid
     */
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取统计时间yyyymmdd
     *
     * @return DZ_SJ - 统计时间yyyymmdd
     */
    public String getDzSj() {
        return dzSj;
    }

    /**
     * 设置统计时间yyyymmdd
     *
     * @param dzSj 统计时间yyyymmdd
     */
    public void setDzSj(String dzSj) {
        this.dzSj = dzSj;
    }

    /**
     * 获取订单查询总数
     *
     * @return ORACLE_COUNT - 订单查询总数
     */
    public String getOracleCount() {
        return oracleCount;
    }

    /**
     * 设置订单查询总数
     *
     * @param oracleCount 订单查询总数
     */
    public void setOracleCount(String oracleCount) {
        this.oracleCount = oracleCount;
    }

    /**
     * 获取订单查询交易额
     *
     * @return ORACLE_FEE - 订单查询交易额
     */
    public String getOracleFee() {
        return oracleFee;
    }

    /**
     * 设置订单查询交易额
     *
     * @param oracleFee 订单查询交易额
     */
    public void setOracleFee(String oracleFee) {
        this.oracleFee = oracleFee;
    }

    /**
     * 获取账单查询总数
     *
     * @return BILL_COUNT - 账单查询总数
     */
    public String getBillCount() {
        return billCount;
    }

    /**
     * 设置账单查询总数
     *
     * @param billCount 账单查询总数
     */
    public void setBillCount(String billCount) {
        this.billCount = billCount;
    }

    /**
     * 获取账单查询金额
     *
     * @return BILL_FEE - 账单查询金额
     */
    public String getBillFee() {
        return billFee;
    }

    /**
     * 设置账单查询金额
     *
     * @param billFee 账单查询金额
     */
    public void setBillFee(String billFee) {
        this.billFee = billFee;
    }

    /**
     * 获取成功笔数
     *
     * @return SUCCESS_COUNT - 成功笔数
     */
    public String getSuccessCount() {
        return successCount;
    }

    /**
     * 设置成功笔数
     *
     * @param successCount 成功笔数
     */
    public void setSuccessCount(String successCount) {
        this.successCount = successCount;
    }

    /**
     * 获取成功金额
     *
     * @return SUCCESS_FEE - 成功金额
     */
    public String getSuccessFee() {
        return successFee;
    }

    /**
     * 设置成功金额
     *
     * @param successFee 成功金额
     */
    public void setSuccessFee(String successFee) {
        this.successFee = successFee;
    }

    /**
     * 获取对账成功的退款笔数
     *
     * @return SUCCESS_REFUND_COUNT - 对账成功的退款笔数
     */
    public String getSuccessRefundCount() {
        return successRefundCount;
    }

    /**
     * 设置对账成功的退款笔数
     *
     * @param successRefundCount 对账成功的退款笔数
     */
    public void setSuccessRefundCount(String successRefundCount) {
        this.successRefundCount = successRefundCount;
    }

    /**
     * 获取对账成功的退款金额
     *
     * @return SUCCESS_REFUND_FEE - 对账成功的退款金额
     */
    public String getSuccessRefundFee() {
        return successRefundFee;
    }

    /**
     * 设置对账成功的退款金额
     *
     * @param successRefundFee 对账成功的退款金额
     */
    public void setSuccessRefundFee(String successRefundFee) {
        this.successRefundFee = successRefundFee;
    }

    /**
     * 获取订单失败笔数
     *
     * @return ORDER_FAIL_COUNT - 订单失败笔数
     */
    public String getOrderFailCount() {
        return orderFailCount;
    }

    /**
     * 设置订单失败笔数
     *
     * @param orderFailCount 订单失败笔数
     */
    public void setOrderFailCount(String orderFailCount) {
        this.orderFailCount = orderFailCount;
    }

    public String getBillFailCount() {
        return billFailCount;
    }

    public void setBillFailCount(String billFailCount) {
        this.billFailCount = billFailCount;
    }

    /**
     * 获取创建时间
     *
     * @return CREATION_TIME - 创建时间
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     * 设置创建时间
     *
     * @param creationTime 创建时间
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public enum InnerColumn {
        pkid("PKID"),
        dzSj("DZ_SJ"),
        oracleCount("ORACLE_COUNT"),
        oracleFee("ORACLE_FEE"),
        billCount("BILL_COUNT"),
        billFee("BILL_FEE"),
        successCount("SUCCESS_COUNT"),
        successFee("SUCCESS_FEE"),
        successRefundCount("SUCCESS_REFUND_COUNT"),
        successRefundFee("SUCCESS_REFUND_FEE"),
        orderFailCount("ORDER_FAIL_COUNT"),
        orderFailFee("ORDER_FAIL_FEE"),
        creationTime("CREATION_TIME");

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