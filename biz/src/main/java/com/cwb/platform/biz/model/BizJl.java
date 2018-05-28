package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 教练表
 */
@Table(name = "biz_jl")
public class BizJl implements Serializable {
    /**
     * 用户id
     */
    @Id
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 姓名
     */
    @Column(name = "YH_XM")
    private String yhXm;

    /**
     * 身份证号
     */
    @Column(name = "YH_ZJHM")
    private String yhZjhm;

    /**
     * 手机号
     */
    @Column(name = "YH_SJHM")
    private String yhSjhm;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 教练驾龄
     */
    @Column(name = "JL_JL")
    private String jlJl;

    /**
     * 教练所属区域
     */
    @Column(name = "JL_QU")
    private String jlQu;

    /**
     * 教练-证明人
     */
    @Column(name = "JL_ZML")
    private String jlZml;

    /**
     * 教练-紧急联系人
     */
    @Column(name = "JL_JJLXR")
    private String jlJjlxr;

    /**
     * 教练-紧急联系人电话
     */
    @Column(name = "JL_JJLXRDH")
    private String jlJjlxrdh;

    /**
     * 教练-住地址
     */
    @Column(name = "JL_ZZ")
    private String jlZz;

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
     * 获取身份证号
     *
     * @return YH_ZJHM - 身份证号
     */
    public String getYhZjhm() {
        return yhZjhm;
    }

    /**
     * 设置身份证号
     *
     * @param yhZjhm 身份证号
     */
    public void setYhZjhm(String yhZjhm) {
        this.yhZjhm = yhZjhm;
    }

    /**
     * 获取手机号
     *
     * @return YH_SJHM - 手机号
     */
    public String getYhSjhm() {
        return yhSjhm;
    }

    /**
     * 设置手机号
     *
     * @param yhSjhm 手机号
     */
    public void setYhSjhm(String yhSjhm) {
        this.yhSjhm = yhSjhm;
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
     * 获取教练驾龄
     *
     * @return JL_JL - 教练驾龄
     */
    public String getJlJl() {
        return jlJl;
    }

    /**
     * 设置教练驾龄
     *
     * @param jlJl 教练驾龄
     */
    public void setJlJl(String jlJl) {
        this.jlJl = jlJl;
    }

    /**
     * 获取教练所属区域
     *
     * @return JL_QU - 教练所属区域
     */
    public String getJlQu() {
        return jlQu;
    }

    /**
     * 设置教练所属区域
     *
     * @param jlQu 教练所属区域
     */
    public void setJlQu(String jlQu) {
        this.jlQu = jlQu;
    }

    /**
     * 获取教练-证明人
     *
     * @return JL_ZML - 教练-证明人
     */
    public String getJlZml() {
        return jlZml;
    }

    /**
     * 设置教练-证明人
     *
     * @param jlZml 教练-证明人
     */
    public void setJlZml(String jlZml) {
        this.jlZml = jlZml;
    }

    /**
     * 获取教练-紧急联系人
     *
     * @return JL_JJLXR - 教练-紧急联系人
     */
    public String getJlJjlxr() {
        return jlJjlxr;
    }

    /**
     * 设置教练-紧急联系人
     *
     * @param jlJjlxr 教练-紧急联系人
     */
    public void setJlJjlxr(String jlJjlxr) {
        this.jlJjlxr = jlJjlxr;
    }

    /**
     * 获取教练-紧急联系人电话
     *
     * @return JL_JJLXRDH - 教练-紧急联系人电话
     */
    public String getJlJjlxrdh() {
        return jlJjlxrdh;
    }

    /**
     * 设置教练-紧急联系人电话
     *
     * @param jlJjlxrdh 教练-紧急联系人电话
     */
    public void setJlJjlxrdh(String jlJjlxrdh) {
        this.jlJjlxrdh = jlJjlxrdh;
    }

    /**
     * 获取教练-住地址
     *
     * @return JL_ZZ - 教练-住地址
     */
    public String getJlZz() {
        return jlZz;
    }

    /**
     * 设置教练-住地址
     *
     * @param jlZz 教练-住地址
     */
    public void setJlZz(String jlZz) {
        this.jlZz = jlZz;
    }

    public enum InnerColumn {
        yhId("YH_ID"),
        yhXm("YH_XM"),
        yhZjhm("YH_ZJHM"),
        yhSjhm("YH_SJHM"),
        cjsj("CJSJ"),
        jlJl("JL_JL"),
        jlQu("JL_QU"),
        jlZml("JL_ZML"),
        jlJjlxr("JL_JJLXR"),
        jlJjlxrdh("JL_JJLXRDH"),
        jlZz("JL_ZZ");

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