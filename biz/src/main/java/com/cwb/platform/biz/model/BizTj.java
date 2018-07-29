package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户统计表
 */
@Table(name = "biz_tj")
public class BizTj implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;
    /**
     * 统计时间
     */
    @Column(name = "TJ_SJ")
    private String tjSj;
    /**
     * 统计类型 20:订单请求 21:已支付 22:已退款  10:提现申请 11:提现审核通过 12:提现待打款
     */
    @Column(name = "TJ_LX")
    private String tjLx;

    /**
     * 时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 统计数量
     */
    @Column(name = "TJ_NUM")
    private Integer tjNum;

    /**
     * 金额
     */
    @Column(name = "TJ_JE")
    private String tjJe;

    /**
     * 统计订单支付通道 1、微信 2、支付宝
     */
    @Column(name = "TJ_TD")
    private String tjTd;

    private static final long serialVersionUID = 1L;

    public String getTjSj() {
        return tjSj;
    }

    public void setTjSj(String tjSj) {
        this.tjSj = tjSj;
    }

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取统计类型 20:订单请求 21:已支付 22:已退款  10:提现申请 11:提现审核通过 12:提现待打款
     *
     * @return TJ_LX - 统计类型 20:订单请求 21:已支付 22:已退款  10:提现申请 11:提现审核通过 12:提现待打款
     */
    public String getTjLx() {
        return tjLx;
    }

    /**
     * 设置统计类型 20:订单请求 21:已支付 22:已退款  10:提现申请 11:提现审核通过 12:提现待打款
     *
     * @param tjLx 统计类型 20:订单请求 21:已支付 22:已退款  10:提现申请 11:提现审核通过 12:提现待打款
     */
    public void setTjLx(String tjLx) {
        this.tjLx = tjLx;
    }

    /**
     * 获取时间
     *
     * @return CJSJ - 时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置时间
     *
     * @param cjsj 时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取统计数量
     *
     * @return TJ_NUM - 统计数量
     */
    public Integer getTjNum() {
        return tjNum;
    }

    /**
     * 设置统计数量
     *
     * @param tjNum 统计数量
     */
    public void setTjNum(Integer tjNum) {
        this.tjNum = tjNum;
    }

    /**
     * 获取金额
     *
     * @return TJ_JE - 金额
     */
    public String getTjJe() {
        return tjJe;
    }

    /**
     * 设置金额
     *
     * @param tjJe 金额
     */
    public void setTjJe(String tjJe) {
        this.tjJe = tjJe;
    }

    /**
     * 获取统计订单支付通道 1、微信 2、支付宝
     *
     * @return TJ_TD - 统计订单支付通道 1、微信 2、支付宝
     */
    public String getTjTd() {
        return tjTd;
    }

    /**
     * 设置统计订单支付通道 1、微信 2、支付宝
     *
     * @param tjTd 统计订单支付通道 1、微信 2、支付宝
     */
    public void setTjTd(String tjTd) {
        this.tjTd = tjTd;
    }

    public enum InnerColumn {
        id("ID"),
        tjLx("TJ_LX"),
        cjsj("CJSJ"),
        tjNum("TJ_NUM"),
        tjJe("TJ_JE"),
        tjTd("TJ_TD");

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