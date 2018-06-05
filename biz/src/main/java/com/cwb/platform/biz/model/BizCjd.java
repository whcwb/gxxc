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
     * 学员考试成绩
     */
    @Column(name = "XY_CJ")
    private String xyCj;

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

    public String getXyCj() {
        return xyCj;
    }

    public void setXyCj(String xyCj) {
        this.xyCj = xyCj;
    }

    public enum InnerColumn {
        id("ID"),
        xyId("XY_ID"),
        jlId("JL_ID"),
        xySfhg("XY_SFHG"),
        kmBm("KM_BM"),
        imgUrl("IMG_URL"),
        xyCj("XY_CJ");

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