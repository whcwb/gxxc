package com.cwb.platform.sys.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 平台用户表
 * 目前仅支持学员、司机
 */
@Getter
@Setter
@Table(name = "biz_ptyh")
public class BizPtyh implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 账号(手机号)
     */
    @Column(name = "YH_ZH")
    private String yhZh;

    /**
     * 密码
     */
    @Column(name = "YH_MM")
    private String yhMm;

    /**
     * 创建人
     */
    @Column(name = "YH_CJR")
    private String yhCjr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 修改人
     */
    @Column(name = "YH_XGR")
    private String yhXgr;

    /**
     * 修改时间
     */
    @Column(name = "XGSJ")
    private String xgsj;

    /**
     * 姓名
     */
    @Column(name = "YH_XM")
    private String yhXm;

    /**
     * 类型 ZDCLK0041(2、教练、1、学员)
     */
    @Column(name = "YH_LX")
    private String yhLx;

    /**
     * 性别 ZDCLK0042(1、男;2、女)
     */
    @Column(name = "YH_XB")
    private String yhXb;

    /**
     * 身份证号码
     */
    @Column(name = "YH_ZJHM")
    private String yhZjhm;

    /**
     * 密码有效期
     */
    @Column(name = "YH_MMYXQ")
    private String yhMmyxq;

    /**
     * 认证状态 ZDCLK0043(0 未认证、1 已认证)
     */
    @Column(name = "YH_ZT")
    private String yhZt;

    /**
     * 是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
     */
    @Column(name = "DD_SFJX")
    private String ddSfjx;

    /**
     * 微信OPEN_ID
     */
    @Column(name = "YH_OPEN_ID")
    private String yhOpenId;

    /**
     * 支付宝ID
     */
    @Column(name = "YH_ALIPAY_ID")
    private String yhAlipayId;

    /**
     * 用户头像
     */
    @Column(name = "YH_TX")
    private String yhTx;

    /**
     * 用户别名
     */
    @Column(name = "YH_BM")
    private String yhBm;

    /**
     * 用户自身邀请码
     */
    @Column(name = "YH_ZSYQM")
    private String yhZsyqm;

    /**
     * 用户应邀邀请码
     */
    @Column(name = "YH_YYYQM")
    private String yhYyyqm;

    /**
     * 自身邀请码图片
     */
    @Column(name = "YH_ZSYQM_IMG")
    private String yhZsyqmImg;

    /**
     * 学员是否已分配 ZDCLK0046 (0 否  1 是)
     */
    @Column(name = "YH_IXY_SFFP")
    private String yhIxySffp;

    /**
     * 是否有驾照 ZDCLK0046 (0 否  1 是)
     */
    @Column(name = "YH_SFYJZ")
    private String yhSfyjz;

    /**
     * 学员分配描述
     */
    @Column(name = "YH_FPMS")
    private String yhFpms;

    /**
     * 用户认证状态失败描述
     */
    @Column(name = "YH_ZT_MS")
    private String yhZtMs;

    @Column(name = "YH_JL_MS")
    private String yhJlMs;
    /**
     * 用户是否锁定 ZDCLK0046 (0 否  1 是)  0是没有锁定 1是已锁定
     */
    @Column(name = "YH_SFSD")
    private String yhSfsd;
    /**
     * 科目三缴费状态  (0 否  1 是)
     */
    @Column(name = "K3_JFZT")
    private Integer k3jfzt;
    /**
     * 教练认证状态 ZDCLK0043(0 未认证、1 已认证 2、认证失败)
     */
    @Column(name = "YH_JLSH")
    private String yhJlsh;

    /**
     * 学员受理状态
     */
    @Column(name = "YH_XY_SL_TYPE")
    private String yhXySlType;
    /**
     * 学员约考状态
     * 10、 科目一已约考 11、 科目一已通过 12、 科目一未通过 20、 科目二已约考 21、 科目二已通过 22、 科目二未通过
     * 30、 科目三已约考 31、 科目三已通过 32、 科目三未通过 40、 科目四已约考 41、 科目四已通过 42、 科目四未通过
     */
    @Column(name = "YH_XY_YK_TYPE")
    private String yhXyYkType;
    /**
     * 学员分配专员情况
     0未分配
     1已分配受理专员
     2已分配科目一专员
     3已分配科目二专员
     4已分配科目三专员
     */
    @Column(name = "YH_XY_FPZY_TYPE")
    private String yhXyFpzyType;
    /**
     * 用户签名 (这个字段已经下放到订单表中，所以该字段已经无效)
     */
    @Column(name = "YH_AUTOGRAPH")
    private String yhAutograph;

    /**
     * 用户邀请码过期时间
     */
    @Column(name = "YH_YQMGQSJ")
    private String yhYqmgqsj;
    /**
     * 手机验证码
     */
    @Transient
    private String telIdentifying;

    /**
     * 注册类型  1、微信注册  2、支付宝注册 3、web页面注册
     */
    @Transient
    private String addType;
    /**
     * 上传个人文件，以","进行分隔。
     */
    @Transient
    private String imgList;
    /**
     * 上传属性，以","进行分隔。
     */
    @Transient
    private String imgTypeList;

    @Transient
    private String sysUserId;

    @Transient
    private String roleIds;

    @Transient
    private String roleNames;

    /**
     * 教练姓名
     */
    @Transient
    private String jlXm;

    /**
     * 教练手机号码
     */
    @Transient
    private String sjhm;
    /**
     * 教练简介
     */
    @Transient
    private String jljj;
    /**
     * 教练Id
     */
    @Transient
    private String jlId;

    /**
     * 用户当前状态
     * 0 处于受理状态 1科目1状态 2科目二状态  3科目三状态   4科目四状态
     */
    @Transient
    private String yhDqzt;

    /**
     * 用户邀请人数
     */
    @Transient
    private long userInviteCount;


    public Integer getK3jfzt() {
        return k3jfzt;
    }

    public void setK3jfzt(Integer k3jfzt) {
        this.k3jfzt = k3jfzt;
    }

    private static final long serialVersionUID = 1L;

    public enum InnerColumn {
        id("ID"),
        yhZh("YH_ZH"),
        yhMm("YH_MM"),
        yhCjr("YH_CJR"),
        k3jfzt("K3_JFZT"),
        cjsj("CJSJ"),
        yhXgr("YH_XGR"),
        xgsj("XGSJ"),
        yhXm("YH_XM"),
        yhLx("YH_LX"),
        yhXb("YH_XB"),
        yhZjhm("YH_ZJHM"),
        yhMmyxq("YH_MMYXQ"),
        yhZt("YH_ZT"),
        ddSfjx("DD_SFJX"),
        yhOpenId("YH_OPEN_ID"),
        yhAlipayId("YH_ALIPAY_ID"),
        yhTx("YH_TX"),
        yhBm("YH_BM"),
        yhZsyqm("YH_ZSYQM"),
        yhYyyqm("YH_YYYQM"),
        yhZsyqmImg("YH_ZSYQM_IMG"),
        yhIxySffp("YH_IXY_SFFP"),
        yhSfyjz("YH_SFYJZ"),
        yhFpms("YH_FPMS"),
        yhXySlType("YH_XY_SL_TYPE"),
        yhSfsd("YH_SFSD");

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
