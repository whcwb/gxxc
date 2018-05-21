package com.cwb.platform.biz.model;

import com.cwb.platform.sys.model.BizPtyh;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 系统订单表
 * 确定用户的缴费情况。
 */
@Table(name = "biz_order")
public class BizOrder implements Serializable {
    /**
     * 订单id
     */
    @Id
    @Column(name = "DD_ID")
    private String ddId;

    /**
     * 用户id
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 缴费状态(缴费是否成功)
     */
    @Column(name = "DD_SFJF")
    private String ddSfjf;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "YH_CJR")
    private String yhCjr;

    /**
     * 订单状态(1、待缴费 2、已缴费 3、已退费)
     */
    @Column(name = "DD_ZT")
    private String ddZt;

    /**
     * 支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     */
    @Column(name = "DD_ZFTD")
    private String ddZftd;

    /**
     * 支付时间
     */
    @Column(name = "DD_ZFSJ")
    private String ddZfsj;

    /**
     * 支付状态（0,待支付 1、支付成功  2、支付失败）
     */
    @Column(name = "DD_ZFZT")
    private String ddZfzt;

    /**
     * 支付金额(单位 分)
     */
    @Column(name = "DD_ZFJE")
    private Double ddZfje;

    /**
     * 支付凭证ID(保存支付通道返回的CODE)
     */
    @Column(name = "DD_ZFPZ")
    private String ddZfpz;

    /**
     * 支付响应结果(1:成功 2:失败)
     */
    @Column(name = "DD_ZFJG")
    private String ddZfjg;

    /**
     * 姓名
     */
    @Column(name = "YH_XM")
    private String yhXm;

    /**
     * 订单备注
     */
    @Column(name = "DD_BZ")
    private String ddBz;

    /**
     * 上级ID
     */
    @Column(name = "YH_SJID")
    private String yhSjid;

    /**
     * 上上级ID
     */
    @Column(name = "YH_SSJID")
    private String yhSsjid;
    /**
     * 用户等级
     * 1、一级用户   2、二级用户
     */
    @Transient
    private String userGrade;
    /**
     * 用户信息
     */
    @Transient
    private BizPtyh userDetail;


    private static final long serialVersionUID = 1L;

    public BizPtyh getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(BizPtyh userDetail) {
        this.userDetail = userDetail;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getYhSjid() {
        return yhSjid;
    }

    public void setYhSjid(String yhSjid) {
        this.yhSjid = yhSjid;
    }

    public String getYhSsjid() {
        return yhSsjid;
    }

    public void setYhSsjid(String yhSsjid) {
        this.yhSsjid = yhSsjid;
    }

    /**
     * 获取订单id
     *
     * @return DD_ID - 订单id
     */
    public String getDdId() {
        return ddId;
    }

    /**
     * 设置订单id
     *
     * @param ddId 订单id
     */
    public void setDdId(String ddId) {
        this.ddId = ddId;
    }

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
     * 获取缴费状态(缴费是否成功)
     *
     * @return DD_SFJF - 缴费状态(缴费是否成功)
     */
    public String getDdSfjf() {
        return ddSfjf;
    }

    /**
     * 设置缴费状态(缴费是否成功)
     *
     * @param ddSfjf 缴费状态(缴费是否成功)
     */
    public void setDdSfjf(String ddSfjf) {
        this.ddSfjf = ddSfjf;
    }

    /**
     * 获取创建时间
     *
     * @return CJSJ - 创建时间
     */
    public String getCjsj() {
        return cjsj;
    }

    /**
     * 设置创建时间
     *
     * @param cjsj 创建时间
     */
    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    /**
     * 获取创建人
     *
     * @return YH_CJR - 创建人
     */
    public String getYhCjr() {
        return yhCjr;
    }

    /**
     * 设置创建人
     *
     * @param yhCjr 创建人
     */
    public void setYhCjr(String yhCjr) {
        this.yhCjr = yhCjr;
    }

    /**
     * 获取订单状态(1、待缴费 2、已缴费 3、已退费)
     *
     * @return DD_ZT - 订单状态(1、待缴费 2、已缴费 3、已退费)
     */
    public String getDdZt() {
        return ddZt;
    }

    /**
     * 设置订单状态(1、待缴费 2、已缴费 3、已退费)
     *
     * @param ddZt 订单状态(1、待缴费 2、已缴费 3、已退费)
     */
    public void setDdZt(String ddZt) {
        this.ddZt = ddZt;
    }

    /**
     * 获取支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     *
     * @return DD_ZFTD - 支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     */
    public String getDdZftd() {
        return ddZftd;
    }

    /**
     * 设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     *
     * @param ddZftd 支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     */
    public void setDdZftd(String ddZftd) {
        this.ddZftd = ddZftd;
    }

    /**
     * 获取支付时间
     *
     * @return DD_ZFSJ - 支付时间
     */
    public String getDdZfsj() {
        return ddZfsj;
    }

    /**
     * 设置支付时间
     *
     * @param ddZfsj 支付时间
     */
    public void setDdZfsj(String ddZfsj) {
        this.ddZfsj = ddZfsj;
    }

    /**
     * 获取支付状态（0,待支付 1、支付成功  2、支付失败）
     *
     * @return DD_ZFZT - 支付状态（0,待支付 1、支付成功  2、支付失败）
     */
    public String getDdZfzt() {
        return ddZfzt;
    }

    /**
     * 设置支付状态（0,待支付 1、支付成功  2、支付失败）
     *
     * @param ddZfzt 支付状态（0,待支付 1、支付成功  2、支付失败）
     */
    public void setDdZfzt(String ddZfzt) {
        this.ddZfzt = ddZfzt;
    }

    /**
     * 获取支付金额(单位 分)
     *
     * @return DD_ZFJE - 支付金额(单位 分)
     */
    public Double getDdZfje() {
        return ddZfje;
    }

    /**
     * 设置支付金额(单位 分)
     *
     * @param ddZfje 支付金额(单位 分)
     */
    public void setDdZfje(Double ddZfje) {
        this.ddZfje = ddZfje;
    }

    /**
     * 获取支付凭证ID(保存支付通道返回的CODE)
     *
     * @return DD_ZFPZ - 支付凭证ID(保存支付通道返回的CODE)
     */
    public String getDdZfpz() {
        return ddZfpz;
    }

    /**
     * 设置支付凭证ID(保存支付通道返回的CODE)
     *
     * @param ddZfpz 支付凭证ID(保存支付通道返回的CODE)
     */
    public void setDdZfpz(String ddZfpz) {
        this.ddZfpz = ddZfpz;
    }

    /**
     * 获取支付响应结果(1:成功 2:失败)
     *
     * @return DD_ZFJG - 支付响应结果(1:成功 2:失败)
     */
    public String getDdZfjg() {
        return ddZfjg;
    }

    /**
     * 设置支付响应结果(1:成功 2:失败)
     *
     * @param ddZfjg 支付响应结果(1:成功 2:失败)
     */
    public void setDdZfjg(String ddZfjg) {
        this.ddZfjg = ddZfjg;
    }

    /**
     * 获取姓名
     *
     * @return YH_XM - 姓名
     */
    public String getYhXm() {
        return yhXm;
    }

    /**
     * 设置姓名
     *
     * @param yhXm 姓名
     */
    public void setYhXm(String yhXm) {
        this.yhXm = yhXm;
    }

    /**
     * 获取订单备注
     *
     * @return DD_BZ - 订单备注
     */
    public String getDdBz() {
        return ddBz;
    }

    /**
     * 设置订单备注
     *
     * @param ddBz 订单备注
     */
    public void setDdBz(String ddBz) {
        this.ddBz = ddBz;
    }

    public enum InnerColumn {
        ddId("DD_ID"),
        yhId("YH_ID"),
        ddSfjf("DD_SFJF"),
        cjsj("CJSJ"),
        yhCjr("YH_CJR"),
        ddZt("DD_ZT"),
        ddZftd("DD_ZFTD"),
        ddZfsj("DD_ZFSJ"),
        ddZfzt("DD_ZFZT"),
        ddZfje("DD_ZFJE"),
        ddZfpz("DD_ZFPZ"),
        ddZfjg("DD_ZFJG"),
        yhXm("YH_XM"),
        yhSjid("YH_SJID"),
        yhSsjid("YH_SSJID"),
        ddBz("DD_BZ");

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
