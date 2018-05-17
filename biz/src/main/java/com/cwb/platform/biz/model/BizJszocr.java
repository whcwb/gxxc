package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_jszocr")
public class BizJszocr implements Serializable {
    /**
     * C证号
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
     * 驾驶证号
     */
    @Column(name = "JSZ_ID")
    private String jszId;

    /**
     * 姓名
     */
    @Column(name = "JSZ_XM")
    private String jszXm;

    /**
     * 性别
     */
    @Column(name = "JSZ_XB")
    private String jszXb;

    /**
     * 国籍
     */
    @Column(name = "JSZ_GJ")
    private String jszGj;

    /**
     * 住址
     */
    @Column(name = "JSZ_ZZ")
    private String jszZz;

    /**
     * 出生日期
     */
    @Column(name = "JSZ_CSJQ")
    private String jszCsjq;

    /**
     * 初次领证日期
     */
    @Column(name = "JSZ_LZRQ")
    private String jszLzrq;

    /**
     * 准驾车型
     */
    @Column(name = "JSZ_ZJCX")
    private String jszZjcx;

    /**
     * 有效期限
     */
    @Column(name = "JSZ_YXQ")
    private String jszYxq;

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
     * 获取驾驶证号
     *
     * @return JSZ_ID - 驾驶证号
     */
    public String getJszId() {
        return jszId;
    }

    /**
     * 设置驾驶证号
     *
     * @param jszId 驾驶证号
     */
    public void setJszId(String jszId) {
        this.jszId = jszId;
    }

    /**
     * 获取姓名
     *
     * @return JSZ_XM - 姓名
     */
    public String getJszXm() {
        return jszXm;
    }

    /**
     * 设置姓名
     *
     * @param jszXm 姓名
     */
    public void setJszXm(String jszXm) {
        this.jszXm = jszXm;
    }

    /**
     * 获取性别
     *
     * @return JSZ_XB - 性别
     */
    public String getJszXb() {
        return jszXb;
    }

    /**
     * 设置性别
     *
     * @param jszXb 性别
     */
    public void setJszXb(String jszXb) {
        this.jszXb = jszXb;
    }

    /**
     * 获取国籍
     *
     * @return JSZ_GJ - 国籍
     */
    public String getJszGj() {
        return jszGj;
    }

    /**
     * 设置国籍
     *
     * @param jszGj 国籍
     */
    public void setJszGj(String jszGj) {
        this.jszGj = jszGj;
    }

    /**
     * 获取住址
     *
     * @return JSZ_ZZ - 住址
     */
    public String getJszZz() {
        return jszZz;
    }

    /**
     * 设置住址
     *
     * @param jszZz 住址
     */
    public void setJszZz(String jszZz) {
        this.jszZz = jszZz;
    }

    /**
     * 获取出生日期
     *
     * @return JSZ_CSJQ - 出生日期
     */
    public String getJszCsjq() {
        return jszCsjq;
    }

    /**
     * 设置出生日期
     *
     * @param jszCsjq 出生日期
     */
    public void setJszCsjq(String jszCsjq) {
        this.jszCsjq = jszCsjq;
    }

    /**
     * 获取初次领证日期
     *
     * @return JSZ_LZRQ - 初次领证日期
     */
    public String getJszLzrq() {
        return jszLzrq;
    }

    /**
     * 设置初次领证日期
     *
     * @param jszLzrq 初次领证日期
     */
    public void setJszLzrq(String jszLzrq) {
        this.jszLzrq = jszLzrq;
    }

    /**
     * 获取准驾车型
     *
     * @return JSZ_ZJCX - 准驾车型
     */
    public String getJszZjcx() {
        return jszZjcx;
    }

    /**
     * 设置准驾车型
     *
     * @param jszZjcx 准驾车型
     */
    public void setJszZjcx(String jszZjcx) {
        this.jszZjcx = jszZjcx;
    }

    /**
     * 获取有效期限
     *
     * @return JSZ_YXQ - 有效期限
     */
    public String getJszYxq() {
        return jszYxq;
    }

    /**
     * 设置有效期限
     *
     * @param jszYxq 有效期限
     */
    public void setJszYxq(String jszYxq) {
        this.jszYxq = jszYxq;
    }

    public enum InnerColumn {
        sfzId("SFZ_ID"),
        wjId("WJ_ID"),
        jszId("JSZ_ID"),
        jszXm("JSZ_XM"),
        jszXb("JSZ_XB"),
        jszGj("JSZ_GJ"),
        jszZz("JSZ_ZZ"),
        jszCsjq("JSZ_CSJQ"),
        jszLzrq("JSZ_LZRQ"),
        jszZjcx("JSZ_ZJCX"),
        jszYxq("JSZ_YXQ");

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