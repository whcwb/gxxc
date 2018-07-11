package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_tk")
public class BizTk implements Serializable {
    /**
     * 主键
     */
    @Column(name = "PKID")
    private String pkid;

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 学员姓名
     */
    @Column(name = "YH_XM")
    private String yhXm;

    /**
     * 用户证件号码
     */
    @Column(name = "YH_ZJHM")
    private String yhZjhm;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 退款状态 (0已申请 1、退款成功 2、退款驳回 3、申请已经接收)
     */
    @Column(name = "TK_TYPE")
    private String tkType;

    /**
     * 退款描述
     */
    @Column(name = "TK_MESSAGE")
    private String tkMessage;

    /**
     * 经办人
     */
    @Column(name = "JBR")
    private String jbr;

    /**
     * 经办人姓名
     */
    @Column(name = "JBR_XM")
    private String jbrXm;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return PKID - 主键
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * 设置主键
     *
     * @param pkid 主键
     */
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取学员ID
     *
     * @return YH_ID - 学员ID
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置学员ID
     *
     * @param yhId 学员ID
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取学员姓名
     *
     * @return YH_XM - 学员姓名
     */
    public String getYhXm() {
        return yhXm;
    }

    /**
     * 设置学员姓名
     *
     * @param yhXm 学员姓名
     */
    public void setYhXm(String yhXm) {
        this.yhXm = yhXm;
    }

    /**
     * 获取用户证件号码
     *
     * @return YH_ZJHM - 用户证件号码
     */
    public String getYhZjhm() {
        return yhZjhm;
    }

    /**
     * 设置用户证件号码
     *
     * @param yhZjhm 用户证件号码
     */
    public void setYhZjhm(String yhZjhm) {
        this.yhZjhm = yhZjhm;
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
     * 获取退款状态 (0已申请 1、退款成功 2、退款驳回 3、申请已经接收)
     *
     * @return TK_TYPE - 退款状态 (0已申请 1、退款成功 2、退款驳回 3、申请已经接收)
     */
    public String getTkType() {
        return tkType;
    }

    /**
     * 设置退款状态 (0已申请 1、退款成功 2、退款驳回 3、申请已经接收)
     *
     * @param tkType 退款状态 (0已申请 1、退款成功 2、退款驳回 3、申请已经接收)
     */
    public void setTkType(String tkType) {
        this.tkType = tkType;
    }

    /**
     * 获取退款描述
     *
     * @return TK_MESSAGE - 退款描述
     */
    public String getTkMessage() {
        return tkMessage;
    }

    /**
     * 设置退款描述
     *
     * @param tkMessage 退款描述
     */
    public void setTkMessage(String tkMessage) {
        this.tkMessage = tkMessage;
    }

    /**
     * 获取经办人
     *
     * @return JBR - 经办人
     */
    public String getJbr() {
        return jbr;
    }

    /**
     * 设置经办人
     *
     * @param jbr 经办人
     */
    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    /**
     * 获取经办人姓名
     *
     * @return JBR_XM - 经办人姓名
     */
    public String getJbrXm() {
        return jbrXm;
    }

    /**
     * 设置经办人姓名
     *
     * @param jbrXm 经办人姓名
     */
    public void setJbrXm(String jbrXm) {
        this.jbrXm = jbrXm;
    }

    public enum InnerColumn {
        pkid("PKID"),
        yhId("YH_ID"),
        yhXm("YH_XM"),
        yhZjhm("YH_ZJHM"),
        cjsj("CJSJ"),
        tkType("TK_TYPE"),
        tkMessage("TK_MESSAGE"),
        jbr("JBR"),
        jbrXm("JBR_XM");

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