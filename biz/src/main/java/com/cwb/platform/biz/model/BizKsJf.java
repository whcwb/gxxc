package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 学员考试缴费记录表
 */
@Table(name = "biz_ks_jf")
public class BizKsJf implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 学员缴费时间
     */
    @Column(name = "JF_SJ")
    private String jfSj;

    /**
     * 缴费渠道
     */
    @Column(name = "JF_FS")
    private String jfFs;

    /**
     * 科目编码 [ZDCLK0067]
     */
    @Column(name = "KM_ID")
    private String kmId;

    /**
     * 操作人
     */
    @Column(name = "CZR")
    private String czr;

    /**
     * 缴费金额(单位分)
     */
    @Column(name = "JF_JL")
    private String jfJl;

    /**
     * 用户姓名
     */
    @Column(name = "YH_XM")
    private String yhXm;

    /**
     * 用户证件号码
     */
    @Column(name = "YH_ZJHM")
    private String yhZjhm;

    private static final long serialVersionUID = 1L;

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
     * 获取学员缴费时间
     *
     * @return JF_SJ - 学员缴费时间
     */
    public String getJfSj() {
        return jfSj;
    }

    /**
     * 设置学员缴费时间
     *
     * @param jfSj 学员缴费时间
     */
    public void setJfSj(String jfSj) {
        this.jfSj = jfSj;
    }

    /**
     * 获取缴费渠道
     *
     * @return JF_FS - 缴费渠道
     */
    public String getJfFs() {
        return jfFs;
    }

    /**
     * 设置缴费渠道
     *
     * @param jfFs 缴费渠道
     */
    public void setJfFs(String jfFs) {
        this.jfFs = jfFs;
    }

    /**
     * 获取科目编码 [ZDCLK0067]
     *
     * @return KM_ID - 科目编码 [ZDCLK0067]
     */
    public String getKmId() {
        return kmId;
    }

    /**
     * 设置科目编码 [ZDCLK0067]
     *
     * @param kmId 科目编码 [ZDCLK0067]
     */
    public void setKmId(String kmId) {
        this.kmId = kmId;
    }

    /**
     * 获取操作人
     *
     * @return CZR - 操作人
     */
    public String getCzr() {
        return czr;
    }

    /**
     * 设置操作人
     *
     * @param czr 操作人
     */
    public void setCzr(String czr) {
        this.czr = czr;
    }

    /**
     * 获取缴费金额(单位分)
     *
     * @return JF_JL - 缴费金额(单位分)
     */
    public String getJfJl() {
        return jfJl;
    }

    /**
     * 设置缴费金额(单位分)
     *
     * @param jfJl 缴费金额(单位分)
     */
    public void setJfJl(String jfJl) {
        this.jfJl = jfJl;
    }

    /**
     * 获取用户姓名
     *
     * @return YH_XM - 用户姓名
     */
    public String getYhXm() {
        return yhXm;
    }

    /**
     * 设置用户姓名
     *
     * @param yhXm 用户姓名
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

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        jfSj("JF_SJ"),
        jfFs("JF_FS"),
        kmId("KM_ID"),
        czr("CZR"),
        jfJl("JF_JL"),
        yhXm("YH_XM"),
        yhZjhm("YH_ZJHM");

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