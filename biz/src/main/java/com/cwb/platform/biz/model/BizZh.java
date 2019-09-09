package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 用户账户表
 * 仅统计本人当前时间可用的账户情况
 */
@Table(name = "biz_zh")
public class BizZh implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 账户余额(分)
     */
    @Column(name = "YH_ZHYE")
    private Double yhZhye;

    /**
     * 提现冻结金额(分)
     */
    @Column(name = "YH_TXDJ")
    private Double yhTxdj;

    @Column(name = "YH_YEDJ")
    private Double yhYedj;

    public Double getYhYedj() {
        return yhYedj;
    }

    public void setYhYedj(Double yhYedj) {
        this.yhYedj = yhYedj;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户id
     *
     * @return YH_ID - 用户id
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置用户id
     *
     * @param yhId 用户id
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取账户余额(分)
     *
     * @return YH_ZHYE - 账户余额(分)
     */
    public Double getYhZhye() {
        return yhZhye;
    }

    /**
     * 设置账户余额(分)
     *
     * @param yhZhye 账户余额(分)
     */
    public void setYhZhye(Double yhZhye) {
        this.yhZhye = yhZhye;
    }

    /**
     * 获取提现冻结金额(分)
     *
     * @return YH_TXDJ - 提现冻结金额(分)
     */
    public Double getYhTxdj() {
        return yhTxdj;
    }

    /**
     * 设置提现冻结金额(分)
     *
     * @param yhTxdj 提现冻结金额(分)
     */
    public void setYhTxdj(Double yhTxdj) {
        this.yhTxdj = yhTxdj;
    }

    public enum InnerColumn {
        yhId("YH_ID"),
        yhZhye("YH_ZHYE"),
        yhTxdj("YH_TXDJ");

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