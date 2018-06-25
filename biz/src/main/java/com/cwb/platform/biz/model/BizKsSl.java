package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 学员考试受理信息表
 */
@Table(name = "biz_ks_sl")
public class BizKsSl implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 受理单位ID
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 受理单位名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 受理类型(ZDCLK0071  1、医院体验 2、入网面签 3、档案采集 4、受理成功 )
     */
    @Column(name = "SL_TYPE")
    private String slType;

    /**
     * 受理时间
     */
    @Column(name = "SL_SJ")
    private String slSj;

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

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;
    /**
     * 流水号
     */
    @Column(name = "LSH")
    private String lsh;

    private static final long serialVersionUID = 1L;

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

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
     * 获取受理单位ID
     *
     * @return CODE - 受理单位ID
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置受理单位ID
     *
     * @param code 受理单位ID
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取受理单位名称
     *
     * @return NAME - 受理单位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置受理单位名称
     *
     * @param name 受理单位名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取受理类型(ZDCLK0071  1、医院体验 2、入网面签 3、档案采集 4、受理成功 )
     *
     * @return SL_TYPE - 受理类型(ZDCLK0071  1、医院体验 2、入网面签 3、档案采集 4、受理成功 )
     */
    public String getSlType() {
        return slType;
    }

    /**
     * 设置受理类型(ZDCLK0071  1、医院体验 2、入网面签 3、档案采集 4、受理成功 )
     *
     * @param slType 受理类型(ZDCLK0071  1、医院体验 2、入网面签 3、档案采集 4、受理成功 )
     */
    public void setSlType(String slType) {
        this.slType = slType;
    }

    /**
     * 获取受理时间
     *
     * @return SL_SJ - 受理时间
     */
    public String getSlSj() {
        return slSj;
    }

    /**
     * 设置受理时间
     *
     * @param slSj 受理时间
     */
    public void setSlSj(String slSj) {
        this.slSj = slSj;
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

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        code("CODE"),
        name("NAME"),
        slType("SL_TYPE"),
        slSj("SL_SJ"),
        yhXm("YH_XM"),
        yhZjhm("YH_ZJHM"),
        cjr("CJR"),
        cjsj("CJSJ");

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