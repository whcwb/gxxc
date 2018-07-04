package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 产品表
 */
@Table(name = "biz_cp")
public class BizCp implements Serializable {
    /**
     * 产品ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 产品名称
     */
    @Column(name = "CP_MC")
    private String cpMc;

    /**
     * 产品类型（1、学费  2、补考费）必填
     */
    @Column(name = "CP_TYPE")
    private String cpType;

    /**
     * 产品总金额
     */
    @Column(name = "CP_JL")
    private String cpJl;

    /**
     * 是否分佣(0不分 1分佣)
     */
    @Column(name = "CP_YJ")
    private String cpYj;

    /**
     * 一级佣金
     */
    @Column(name = "CP_YJYJ")
    private Double cpYjyj;

    /**
     * 二级佣金
     */
    @Column(name = "CP_RJYJ")
    private Double cpRjyj;

    /**
     * 产品是否有效(0、无效 1、生效)
     */
    @Column(name = "CP_YX")
    private String cpYx;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 产品信息
     */
    @Column(name = "CP_XX")
    private String cpXx;
    /**
     * 产品验证状态(0待手机短信验证 1、已进行手机短信验证)  暂时不用
     */
    @Column(name = "CP_YZ")
    private String cpYz;

//
//    /**
//     * 审核人
//     */
//    @Column(name = "SHR")
//    private String shr;

//    /**
//     * 审核时间
//     */
//    @Column(name = "SHSJ")
//    private String shsj;

//    /**
//     * 产品审核(0待审核 1、审核通过 2、审核驳回)
//     */
//    @Column(name = "CP_SH")
//    private String cpSh;

    private static final long serialVersionUID = 1L;

    public String getCpYz() {
        return cpYz;
    }

    public void setCpYz(String cpYz) {
        this.cpYz = cpYz;
    }

    /**
     * 获取产品ID
     *
     * @return ID - 产品ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置产品ID
     *
     * @param id 产品ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取产品名称
     *
     * @return CP_MC - 产品名称
     */
    public String getCpMc() {
        return cpMc;
    }

    /**
     * 设置产品名称
     *
     * @param cpMc 产品名称
     */
    public void setCpMc(String cpMc) {
        this.cpMc = cpMc;
    }

    /**
     * 获取产品类型（1、学费  2、补考费）必填
     *
     * @return CP_TYPE - 产品类型（1、学费  2、补考费）必填
     */
    public String getCpType() {
        return cpType;
    }

    /**
     * 设置产品类型（1、学费  2、补考费）必填
     *
     * @param cpType 产品类型（1、学费  2、补考费）必填
     */
    public void setCpType(String cpType) {
        this.cpType = cpType;
    }

    /**
     * 获取产品总金额
     *
     * @return CP_JL - 产品总金额
     */
    public String getCpJl() {
        return cpJl;
    }

    /**
     * 设置产品总金额
     *
     * @param cpJl 产品总金额
     */
    public void setCpJl(String cpJl) {
        this.cpJl = cpJl;
    }

    /**
     * 获取是否分佣(0不分 1分佣)
     *
     * @return CP_YJ - 是否分佣(0不分 1分佣)
     */
    public String getCpYj() {
        return cpYj;
    }

    /**
     * 设置是否分佣(0不分 1分佣)
     *
     * @param cpYj 是否分佣(0不分 1分佣)
     */
    public void setCpYj(String cpYj) {
        this.cpYj = cpYj;
    }

    /**
     * 获取一级佣金
     *
     * @return CP_YJYJ - 一级佣金
     */
    public Double getCpYjyj() {
        return cpYjyj;
    }

    /**
     * 设置一级佣金
     *
     * @param cpYjyj 一级佣金
     */
    public void setCpYjyj(Double cpYjyj) {
        this.cpYjyj = cpYjyj;
    }

    /**
     * 获取二级佣金
     *
     * @return CP_RJYJ - 二级佣金
     */
    public Double getCpRjyj() {
        return cpRjyj;
    }

    /**
     * 设置二级佣金
     *
     * @param cpRjyj 二级佣金
     */
    public void setCpRjyj(Double cpRjyj) {
        this.cpRjyj = cpRjyj;
    }

    /**
     * 获取产品是否有效(0、无效 1、生效)
     *
     * @return CP_YX - 产品是否有效(0、无效 1、生效)
     */
    public String getCpYx() {
        return cpYx;
    }

    /**
     * 设置产品是否有效(0、无效 1、生效)
     *
     * @param cpYx 产品是否有效(0、无效 1、生效)
     */
    public void setCpYx(String cpYx) {
        this.cpYx = cpYx;
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
     * @return CJR - 创建人
     */
    public String getCjr() {
        return cjr;
    }

    /**
     * 设置创建人
     *
     * @param cjr 创建人
     */
    public void setCjr(String cjr) {
        this.cjr = cjr;
    }
//
//    /**
//     * 获取审核人
//     *
//     * @return SHR - 审核人
//     */
//    public String getShr() {
//        return shr;
//    }
//
//    /**
//     * 设置审核人
//     *
//     * @param shr 审核人
//     */
//    public void setShr(String shr) {
//        this.shr = shr;
//    }
//
//    /**
//     * 获取审核时间
//     *
//     * @return SHSJ - 审核时间
//     */
//    public String getShsj() {
//        return shsj;
//    }
//
//    /**
//     * 设置审核时间
//     *
//     * @param shsj 审核时间
//     */
//    public void setShsj(String shsj) {
//        this.shsj = shsj;
//    }
//
//    /**
//     * 获取产品审核(0待审核 1、审核通过 2、审核驳回)
//     *
//     * @return CP_SH - 产品审核(0待审核 1、审核通过 2、审核驳回)
//     */
//    public String getCpSh() {
//        return cpSh;
//    }
//
//    /**
//     * 设置产品审核(0待审核 1、审核通过 2、审核驳回)
//     *
//     * @param cpSh 产品审核(0待审核 1、审核通过 2、审核驳回)
//     */
//    public void setCpSh(String cpSh) {
//        this.cpSh = cpSh;
//    }

    public String getCpXx() {
        return cpXx;
    }

    public void setCpXx(String cpXx) {
        this.cpXx = cpXx;
    }

    public enum InnerColumn {
        id("ID"),
        cpMc("CP_MC"),
        cpType("CP_TYPE"),
        cpJl("CP_JL"),
        cpYj("CP_YJ"),
        cpYjyj("CP_YJYJ"),
        cpRjyj("CP_RJYJ"),
        cpYx("CP_YX"),
        cjsj("CJSJ"),
        cjr("CJR"),
        shr("SHR"),
        shsj("SHSJ"),
        cpSh("CP_SH"),
        cpXx("CP_XX");

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