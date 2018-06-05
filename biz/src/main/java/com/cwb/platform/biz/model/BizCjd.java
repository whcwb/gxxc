package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "biz_cjd")
public class BizCjd implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 学员 ID
     */
    @Column(name = "XY_ID")
    private String xyId;

    /**
     * 教练 ID
     */
    @Column(name = "JL_ID")
    private String jlId;

    /**
     * 学员是否合格  0：不合格 1：合格
     */
    @Column(name = "XY_SFHG")
    private String xySfhg;

    /**
     * 科目编码
     */
    @Column(name = "KM_BM")
    private String kmBm;

    /**
     * 图片路径
     */
    @Column(name = "IMG_URL")
    private String imgUrl;

    /**
     * 评分来源  0：app  1：web
     */
    @Column(name = "PF_LY")
    private String pfLy;

    /**
     * 创建时间
     */
    @Column(name = "PF_CJSJ")
    private String pfCjsj;

    /**
     * 创建人
     */
    @Column(name = "PF_CJR")
    private String pfCjr;

    /**
     * 学员对教练评分  最大为5
     */
    @Column(name = "JL_PF")
    private String jlPf;

    /**
     * 评论
     */
    @Column(name = "JL_PL")
    private String jlPl;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return ID - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取学员 ID
     *
     * @return XY_ID - 学员 ID
     */
    public String getXyId() {
        return xyId;
    }

    /**
     * 设置学员 ID
     *
     * @param xyId 学员 ID
     */
    public void setXyId(String xyId) {
        this.xyId = xyId;
    }

    /**
     * 获取教练 ID
     *
     * @return JL_ID - 教练 ID
     */
    public String getJlId() {
        return jlId;
    }

    /**
     * 设置教练 ID
     *
     * @param jlId 教练 ID
     */
    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    /**
     * 获取学员是否合格  0：不合格 1：合格
     *
     * @return XY_SFHG - 学员是否合格  0：不合格 1：合格
     */
    public String getXySfhg() {
        return xySfhg;
    }

    /**
     * 设置学员是否合格  0：不合格 1：合格
     *
     * @param xySfhg 学员是否合格  0：不合格 1：合格
     */
    public void setXySfhg(String xySfhg) {
        this.xySfhg = xySfhg;
    }

    /**
     * 获取科目编码
     *
     * @return KM_BM - 科目编码
     */
    public String getKmBm() {
        return kmBm;
    }

    /**
     * 设置科目编码
     *
     * @param kmBm 科目编码
     */
    public void setKmBm(String kmBm) {
        this.kmBm = kmBm;
    }

    /**
     * 获取图片路径
     *
     * @return IMG_URL - 图片路径
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片路径
     *
     * @param imgUrl 图片路径
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取评分来源  0：app  1：web
     *
     * @return PF_LY - 评分来源  0：app  1：web
     */
    public String getPfLy() {
        return pfLy;
    }

    /**
     * 设置评分来源  0：app  1：web
     *
     * @param pfLy 评分来源  0：app  1：web
     */
    public void setPfLy(String pfLy) {
        this.pfLy = pfLy;
    }

    /**
     * 获取创建时间
     *
     * @return PF_CJSJ - 创建时间
     */
    public String getPfCjsj() {
        return pfCjsj;
    }

    /**
     * 设置创建时间
     *
     * @param pfCjsj 创建时间
     */
    public void setPfCjsj(String pfCjsj) {
        this.pfCjsj = pfCjsj;
    }

    /**
     * 获取创建人
     *
     * @return PF_CJR - 创建人
     */
    public String getPfCjr() {
        return pfCjr;
    }

    /**
     * 设置创建人
     *
     * @param pfCjr 创建人
     */
    public void setPfCjr(String pfCjr) {
        this.pfCjr = pfCjr;
    }

    /**
     * 获取学员对教练评分  最大为5
     *
     * @return JL_PF - 学员对教练评分  最大为5
     */
    public String getJlPf() {
        return jlPf;
    }

    /**
     * 设置学员对教练评分  最大为5
     *
     * @param jlPf 学员对教练评分  最大为5
     */
    public void setJlPf(String jlPf) {
        this.jlPf = jlPf;
    }

    /**
     * 获取评论
     *
     * @return JL_PL - 评论
     */
    public String getJlPl() {
        return jlPl;
    }

    /**
     * 设置评论
     *
     * @param jlPl 评论
     */
    public void setJlPl(String jlPl) {
        this.jlPl = jlPl;
    }

    public enum InnerColumn {
        id("ID"),
        xyId("XY_ID"),
        jlId("JL_ID"),
        xySfhg("XY_SFHG"),
        kmBm("KM_BM"),
        imgUrl("IMG_URL"),
        pfLy("PF_LY"),
        pfCjsj("PF_CJSJ"),
        pfCjr("PF_CJR"),
        jlPf("JL_PF"),
        jlPl("JL_PL");

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