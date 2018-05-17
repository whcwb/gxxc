package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * OCR身份证识别
 */
@Table(name = "biz_sfzocr")
public class BizSfzocr implements Serializable {
    /**
     * 身份证号
     */
    @Id
    @Column(name = "SFZ_ID")
    private String sfzId;

    /**
     * 文件上传表(BIZ_WJ  PKID)
     */
    @Column(name = "WJ_ID")
    private String wjId;

    /**
     * 姓名
     */
    @Column(name = "SFZ_XM")
    private String sfzXm;

    /**
     * 性别
     */
    @Column(name = "SFZ_XB")
    private String sfzXb;

    /**
     * 民族
     */
    @Column(name = "SFZ_MZ")
    private String sfzMz;

    /**
     * 出生日期
     */
    @Column(name = "SFZ_CSJQ")
    private String sfzCsjq;

    /**
     * 住址
     */
    @Column(name = "SFZ_ZZ")
    private String sfzZz;

    /**
     * 有效期限
     */
    @Column(name = "SFZ_YXQ")
    private String sfzYxq;

    private static final long serialVersionUID = 1L;

    /**
     * 获取C证号
     *
     * @return SFZ_ID - C证号
     */
    public String getSfzId() {
        return sfzId;
    }

    /**
     * 设置C证号
     *
     * @param sfzId C证号
     */
    public void setSfzId(String sfzId) {
        this.sfzId = sfzId;
    }

    /**
     * 获取文件上传表(BIZ_WJ  PKID)
     *
     * @return WJ_ID - 文件上传表(BIZ_WJ  PKID)
     */
    public String getWjId() {
        return wjId;
    }

    /**
     * 设置文件上传表(BIZ_WJ  PKID)
     *
     * @param wjId 文件上传表(BIZ_WJ  PKID)
     */
    public void setWjId(String wjId) {
        this.wjId = wjId;
    }

    /**
     * 获取姓名
     *
     * @return SFZ_XM - 姓名
     */
    public String getSfzXm() {
        return sfzXm;
    }

    /**
     * 设置姓名
     *
     * @param sfzXm 姓名
     */
    public void setSfzXm(String sfzXm) {
        this.sfzXm = sfzXm;
    }

    /**
     * 获取性别
     *
     * @return SFZ_XB - 性别
     */
    public String getSfzXb() {
        return sfzXb;
    }

    /**
     * 设置性别
     *
     * @param sfzXb 性别
     */
    public void setSfzXb(String sfzXb) {
        this.sfzXb = sfzXb;
    }

    /**
     * 获取民族
     *
     * @return SFZ_MZ - 民族
     */
    public String getSfzMz() {
        return sfzMz;
    }

    /**
     * 设置民族
     *
     * @param sfzMz 民族
     */
    public void setSfzMz(String sfzMz) {
        this.sfzMz = sfzMz;
    }

    /**
     * 获取出生日期
     *
     * @return SFZ_CSJQ - 出生日期
     */
    public String getSfzCsjq() {
        return sfzCsjq;
    }

    /**
     * 设置出生日期
     *
     * @param sfzCsjq 出生日期
     */
    public void setSfzCsjq(String sfzCsjq) {
        this.sfzCsjq = sfzCsjq;
    }

    /**
     * 获取住址
     *
     * @return SFZ_ZZ - 住址
     */
    public String getSfzZz() {
        return sfzZz;
    }

    /**
     * 设置住址
     *
     * @param sfzZz 住址
     */
    public void setSfzZz(String sfzZz) {
        this.sfzZz = sfzZz;
    }

    /**
     * 获取有效期限
     *
     * @return SFZ_YXQ - 有效期限
     */
    public String getSfzYxq() {
        return sfzYxq;
    }

    /**
     * 设置有效期限
     *
     * @param sfzYxq 有效期限
     */
    public void setSfzYxq(String sfzYxq) {
        this.sfzYxq = sfzYxq;
    }

    public enum InnerColumn {
        sfzId("SFZ_ID"),
        wjId("WJ_ID"),
        sfzXm("SFZ_XM"),
        sfzXb("SFZ_XB"),
        sfzMz("SFZ_MZ"),
        sfzCsjq("SFZ_CSJQ"),
        sfzZz("SFZ_ZZ"),
        sfzYxq("SFZ_YXQ");

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