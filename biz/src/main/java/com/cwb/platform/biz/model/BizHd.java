package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 活动表
 * 作用：首页信息列表展现。
 */
@Table(name = "biz_hd")
public class BizHd implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 标题
     */
    @Column(name = "HD_BT")
    private String hdBt;

    /**
     * 正文
     */
    @Column(name = "HD_ZW")
    private String hdZw;

    /**
     * 创建人
     */
    @Column(name = "HD_CJR")
    private String hdCjr;

    /**
     * 图片地址
     */
    @Column(name = "HD_TPDZ")
    private String hdTpdz;

    /**
     * 活动热点推荐
     */
    @Column(name = "HD_TJ")
    private String hdTj;

    /**
     * 属性（字典表：ZDCLK0036 1、驾校  2、训练场）
     */
    @Column(name = "HD_SX")
    private String hdSx;

    /**
     * 修改时间
     */
    @Column(name = "HD_XGSJ")
    private String hdXgsj;

    /**
     * 修改人
     */
    @Column(name = "HD_XGR")
    private String hdXgr;

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
     * 获取标题
     *
     * @return HD_BT - 标题
     */
    public String getHdBt() {
        return hdBt;
    }

    /**
     * 设置标题
     *
     * @param hdBt 标题
     */
    public void setHdBt(String hdBt) {
        this.hdBt = hdBt;
    }

    /**
     * 获取正文
     *
     * @return HD_ZW - 正文
     */
    public String getHdZw() {
        return hdZw;
    }

    /**
     * 设置正文
     *
     * @param hdZw 正文
     */
    public void setHdZw(String hdZw) {
        this.hdZw = hdZw;
    }

    /**
     * 获取创建人
     *
     * @return HD_CJR - 创建人
     */
    public String getHdCjr() {
        return hdCjr;
    }

    /**
     * 设置创建人
     *
     * @param hdCjr 创建人
     */
    public void setHdCjr(String hdCjr) {
        this.hdCjr = hdCjr;
    }

    /**
     * 获取图片地址
     *
     * @return HD_TPDZ - 图片地址
     */
    public String getHdTpdz() {
        return hdTpdz;
    }

    /**
     * 设置图片地址
     *
     * @param hdTpdz 图片地址
     */
    public void setHdTpdz(String hdTpdz) {
        this.hdTpdz = hdTpdz;
    }

    /**
     * 获取活动热点推荐
     *
     * @return HD_TJ - 活动热点推荐
     */
    public String getHdTj() {
        return hdTj;
    }

    /**
     * 设置活动热点推荐
     *
     * @param hdTj 活动热点推荐
     */
    public void setHdTj(String hdTj) {
        this.hdTj = hdTj;
    }

    /**
     * 获取属性（字典表：ZDCLK0036 1、驾校  2、训练场）
     *
     * @return HD_SX - 属性（字典表：ZDCLK0036 1、驾校  2、训练场）
     */
    public String getHdSx() {
        return hdSx;
    }

    /**
     * 设置属性（字典表：ZDCLK0036 1、驾校  2、训练场）
     *
     * @param hdSx 属性（字典表：ZDCLK0036 1、驾校  2、训练场）
     */
    public void setHdSx(String hdSx) {
        this.hdSx = hdSx;
    }

    /**
     * 获取修改时间
     *
     * @return HD_XGSJ - 修改时间
     */
    public String getHdXgsj() {
        return hdXgsj;
    }

    /**
     * 设置修改时间
     *
     * @param hdXgsj 修改时间
     */
    public void setHdXgsj(String hdXgsj) {
        this.hdXgsj = hdXgsj;
    }

    /**
     * 获取修改人
     *
     * @return HD_XGR - 修改人
     */
    public String getHdXgr() {
        return hdXgr;
    }

    /**
     * 设置修改人
     *
     * @param hdXgr 修改人
     */
    public void setHdXgr(String hdXgr) {
        this.hdXgr = hdXgr;
    }

    public enum InnerColumn {
        id("ID"),
        cjsj("CJSJ"),
        hdBt("HD_BT"),
        hdZw("HD_ZW"),
        hdCjr("HD_CJR"),
        hdTpdz("HD_TPDZ"),
        hdTj("HD_TJ"),
        hdSx("HD_SX"),
        hdxgsj("HD_XGSJ"),
        hdxgr("HD_XGR");

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