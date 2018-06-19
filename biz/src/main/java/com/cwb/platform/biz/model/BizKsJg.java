package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 考试结果记录表
 */
@Table(name = "biz_ks_jg")
public class BizKsJg implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 学员用户id
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 成绩单图片地址
     */
    @Column(name = "CJD_IMG")
    private String cjdImg;

    /**
     * 科目编码 [ZDCLK0067]
     */
    @Column(name = "KM_CODE")
    private String kmCode;

    /**
     * 考试成绩
     */
    @Column(name = "KS_CJ")
    private String ksCj;

    /**
     * 学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）
     */
    @Column(name = "KS_SFHG")
    private String ksSfhg;

    /**
     * 考场名称
     */
    @Column(name = "SCHOOL_NAME")
    private String schoolName;

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
     * 获取学员用户id
     *
     * @return YH_ID - 学员用户id
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置学员用户id
     *
     * @param yhId 学员用户id
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
    }

    /**
     * 获取成绩单图片地址
     *
     * @return CJD_IMG - 成绩单图片地址
     */
    public String getCjdImg() {
        return cjdImg;
    }

    /**
     * 设置成绩单图片地址
     *
     * @param cjdImg 成绩单图片地址
     */
    public void setCjdImg(String cjdImg) {
        this.cjdImg = cjdImg;
    }

    /**
     * 获取科目编码 [ZDCLK0067]
     *
     * @return KM_CODE - 科目编码 [ZDCLK0067]
     */
    public String getKmCode() {
        return kmCode;
    }

    /**
     * 设置科目编码 [ZDCLK0067]
     *
     * @param kmCode 科目编码 [ZDCLK0067]
     */
    public void setKmCode(String kmCode) {
        this.kmCode = kmCode;
    }

    /**
     * 获取考试成绩
     *
     * @return KS_CJ - 考试成绩
     */
    public String getKsCj() {
        return ksCj;
    }

    /**
     * 设置考试成绩
     *
     * @param ksCj 考试成绩
     */
    public void setKsCj(String ksCj) {
        this.ksCj = ksCj;
    }

    /**
     * 获取学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）
     *
     * @return KS_SFHG - 学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）
     */
    public String getKsSfhg() {
        return ksSfhg;
    }

    /**
     * 设置学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）
     *
     * @param ksSfhg 学员考试是否合格 （字典表： ZDCLK0061  0：不合格 1：合格）
     */
    public void setKsSfhg(String ksSfhg) {
        this.ksSfhg = ksSfhg;
    }

    /**
     * 获取考场名称
     *
     * @return SCHOOL_NAME - 考场名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置考场名称
     *
     * @param schoolName 考场名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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
        cjdImg("CJD_IMG"),
        kmCode("KM_CODE"),
        ksCj("KS_CJ"),
        ksSfhg("KS_SFHG"),
        schoolName("SCHOOL_NAME"),
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