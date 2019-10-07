package com.cwb.platform.biz.model;

import com.cwb.platform.sys.model.BizPtyh;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 学员考试约考表
 */
@Table(name = "biz_ks_yk")
public class BizKsYk implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 平台用户ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 科目编码 [ZDCLK0067]
     */
    @Column(name = "KM_CODE")
    private String kmCode;

    /**
     * 考场名称
     */
    @Column(name = "SCHOOL_NAME")
    private String schoolName;

    /**
     * 考场代码
     */
    @Column(name = "EXAM_PLACE_ID")
    private String examPlaceId;

    /**
     * 预约考试时间
     */
    @Column(name = "YK_SJ")
    private String ykSj;
    /**
     * 第一次考试成绩
     */
    @Column(name = "CJ1")
    private Integer cj1;
    /**
     * 第二次考试成绩
     */
    @Column(name = "CJ2")
    private Integer cj2;
    /**
     * 第一次考试成绩单
     */
    @Column(name = "CJD1")
    private String cjd1;
    /**
     * 第二次考试成绩单
     */
    @Column(name = "CJD2")
    private String cjd2;

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

    @Column(name = "exam_place_lat")
    private Double examPlaceLat;
    @Column(name = "exam_place_lng")
    private Double examPlaceLng;

    @Transient
    private BizPtyh ptyh;

    public BizPtyh getPtyh() {
        return ptyh;
    }

    public void setPtyh(BizPtyh ptyh) {
        this.ptyh = ptyh;
    }

    public Double getExamPlaceLat() {
        return examPlaceLat;
    }

    public void setExamPlaceLat(Double examPlaceLat) {
        this.examPlaceLat = examPlaceLat;
    }

    public Double getExamPlaceLng() {
        return examPlaceLng;
    }

    public void setExamPlaceLng(Double examPlaceLng) {
        this.examPlaceLng = examPlaceLng;
    }

    public String getCjd1() {
        return cjd1;
    }

    public void setCjd1(String cjd1) {
        this.cjd1 = cjd1;
    }

    public String getCjd2() {
        return cjd2;
    }

    public void setCjd2(String cjd2) {
        this.cjd2 = cjd2;
    }

    private static final long serialVersionUID = 1L;

    public String getExamPlaceId() {
        return examPlaceId;
    }

    public void setExamPlaceId(String examPlaceId) {
        this.examPlaceId = examPlaceId;
    }

    public Integer getCj1() {
        return cj1;
    }

    public void setCj1(Integer cj1) {
        this.cj1 = cj1;
    }

    public Integer getCj2() {
        return cj2;
    }

    public void setCj2(Integer cj2) {
        this.cj2 = cj2;
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
     * 获取平台用户ID
     *
     * @return YH_ID - 平台用户ID
     */
    public String getYhId() {
        return yhId;
    }

    /**
     * 设置平台用户ID
     *
     * @param yhId 平台用户ID
     */
    public void setYhId(String yhId) {
        this.yhId = yhId;
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
     * 获取预约考试时间
     *
     * @return YK_SJ - 预约考试时间
     */
    public String getYkSj() {
        return ykSj;
    }

    /**
     * 设置预约考试时间
     *
     * @param ykSj 预约考试时间
     */
    public void setYkSj(String ykSj) {
        this.ykSj = ykSj;
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
        kmCode("KM_CODE"),
        schoolName("SCHOOL_NAME"),
        ykSj("YK_SJ"),
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
