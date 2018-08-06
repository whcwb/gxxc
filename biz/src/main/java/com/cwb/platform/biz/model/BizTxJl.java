package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 提现记录表
 * 用于记录系统打款的所有记录。
 */
@Table(name = "biz_tx_jl")
public class BizTxJl implements Serializable {
    @Column(name = "PK_ID")
    private String pkId;

    /**
     * 提现表ID
     */
    @Column(name = "TX_ID")
    private String txId;

    /**
     * 提现方式 ZDCLK0047 (1、微信提现  2、银行卡提现 3、人工提现)
     */
    @Column(name = "TX_TYPE")
    private String txType;

    /**
     * 提现用户的ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 提现金额  (分)
     */
    @Column(name = "TX_MONEY")
    private String txMoney;

    /**
     * 1支付成功  2支付失败
     */
    @Column(name = "FRUIT")
    private String fruit;

    /**
     * 提现描述
     */
    @Column(name = "TX_DESC")
    private String txDesc;
    /**
     * 创建时间
     */
    @Column(name = "TX_CJSJ")
    private String cjsj;
    @Column(name = "WX_PAYMENT_NO")
    private String wxPaymentNo;

//
    private static final long serialVersionUID = 1L;

    public String getWxPaymentNo() {
        return wxPaymentNo;
    }

    public void setWxPaymentNo(String wxPaymentNo) {
        this.wxPaymentNo = wxPaymentNo;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * @return PK_ID
     */
    public String getPkId() {
        return pkId;
    }

    /**
     * @param pkId
     */
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    /**
     * 获取提现表ID
     *
     * @return TX_ID - 提现表ID
     */
    public String getTxId() {
        return txId;
    }

    /**
     * 设置提现表ID
     *
     * @param txId 提现表ID
     */
    public void setTxId(String txId) {
        this.txId = txId;
    }

    /**
     * 获取提现方式 ZDCLK0047 (1、微信提现  2、银行卡提现 3、人工提现)
     *
     * @return TX_TYPE - 提现方式 ZDCLK0047 (1、微信提现  2、银行卡提现 3、人工提现)
     */
    public String getTxType() {
        return txType;
    }

    /**
     * 设置提现方式 ZDCLK0047 (1、微信提现  2、银行卡提现 3、人工提现)
     *
     * @param txType 提现方式 ZDCLK0047 (1、微信提现  2、银行卡提现 3、人工提现)
     */
    public void setTxType(String txType) {
        this.txType = txType;
    }

    /**
     * 获取提现用户的ID
     *
     * @return YH_ID - 提现用户的ID
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置提现用户的ID
     *
     * @param yhId 提现用户的ID
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取提现金额  (分)
     *
     * @return TX_MONEY - 提现金额  (分)
     */
    public String getTxMoney() {
        return txMoney;
    }

    /**
     * 设置提现金额  (分)
     *
     * @param txMoney 提现金额  (分)
     */
    public void setTxMoney(String txMoney) {
        this.txMoney = txMoney;
    }

    /**
     * 获取1支付成功  2支付失败
     *
     * @return FRUIT - 1支付成功  2支付失败
     */
    public String getFruit() {
        return fruit;
    }

    /**
     * 设置1支付成功  2支付失败
     *
     * @param fruit 1支付成功  2支付失败
     */
    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    /**
     * 获取提现描述
     *
     * @return TX_DESC - 提现描述
     */
    public String getTxDesc() {
        return txDesc;
    }

    /**
     * 设置提现描述
     *
     * @param txDesc 提现描述
     */
    public void setTxDesc(String txDesc) {
        this.txDesc = txDesc;
    }

    public enum InnerColumn {
        pkId("PK_ID"),
        txId("TX_ID"),
        txType("TX_TYPE"),
        yhId("YH_ID"),
        txMoney("TX_MONEY"),
        fruit("FRUIT"),
        txDesc("TX_DESC");

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