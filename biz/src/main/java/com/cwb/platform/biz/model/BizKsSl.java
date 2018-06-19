package com.cwb.platform.biz.model;

import javax.persistence.Column;
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
    private String id;

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 驾校代码 BIZ_DRIVERS_SCHOOL 表的主键
     */
    @Column(name = "SCHOOL_CODE")
    private String schoolCode;

    /**
     * 受理时间
     */
    @Column(name = "SL_SJ")
    private String slSj;

    /**
     * 受理流水
     */
    @Column(name = "SL_LS")
    private String slLs;

    /**
     * 受理车型(车型字典项：ZDCLK0069  A1 A2……)
     */
    @Column(name = "SL_CX")
    private String slCx;

    /**
     * 考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)
     */
    @Column(name = "SL_KSYY")
    private String slKsyy;

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
     * 获取驾校代码 BIZ_DRIVERS_SCHOOL 表的主键
     *
     * @return SCHOOL_CODE - 驾校代码 BIZ_DRIVERS_SCHOOL 表的主键
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * 设置驾校代码 BIZ_DRIVERS_SCHOOL 表的主键
     *
     * @param schoolCode 驾校代码 BIZ_DRIVERS_SCHOOL 表的主键
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
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
     * 获取受理流水
     *
     * @return SL_LS - 受理流水
     */
    public String getSlLs() {
        return slLs;
    }

    /**
     * 设置受理流水
     *
     * @param slLs 受理流水
     */
    public void setSlLs(String slLs) {
        this.slLs = slLs;
    }

    /**
     * 获取受理车型(车型字典项：ZDCLK0069  A1 A2……)
     *
     * @return SL_CX - 受理车型(车型字典项：ZDCLK0069  A1 A2……)
     */
    public String getSlCx() {
        return slCx;
    }

    /**
     * 设置受理车型(车型字典项：ZDCLK0069  A1 A2……)
     *
     * @param slCx 受理车型(车型字典项：ZDCLK0069  A1 A2……)
     */
    public void setSlCx(String slCx) {
        this.slCx = slCx;
    }

    /**
     * 获取考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)
     *
     * @return SL_KSYY - 考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)
     */
    public String getSlKsyy() {
        return slKsyy;
    }

    /**
     * 设置考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)
     *
     * @param slKsyy 考试原因(考试原因 字典项：ZDCLK0070  1、初次申领  2、增驾申请)
     */
    public void setSlKsyy(String slKsyy) {
        this.slKsyy = slKsyy;
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
        schoolCode("SCHOOL_CODE"),
        slSj("SL_SJ"),
        slLs("SL_LS"),
        slCx("SL_CX"),
        slKsyy("SL_KSYY"),
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