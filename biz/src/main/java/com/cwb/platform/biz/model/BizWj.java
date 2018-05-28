package com.cwb.platform.biz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 文件上传表
 * 用户身份证、驾驶证文件
 */
@Table(name = "biz_wj")
public class BizWj implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "YH_ID")
    private String yhId;

    /**
     * 图片地址
     */
    @Column(name = "WJ_TPDZ")
    private String wjTpdz;

    /**
     * 文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     */
    @Column(name = "WJ_SX")
    private String wjSx;

    /**
     * 图片是否ORC进行了识别 ZDCLK0052（0、 待识别 1、 识别成功 2、 识别失败）
     */
    @Column(name = "WJ_SBZT")
    private String wjSbzt;

    /**
     * 识别的报文内容
     */
    @Column(name = "WJ_BW")
    private String wjBw;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private String cjsj;

    /**
     * 有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。
     */
    @Column(name = "WJ_SFYX")
    private String wjSfyx;

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
     * 获取图片地址
     *
     * @return WJ_TPDZ - 图片地址
     */
    public String getWjTpdz() {
        return wjTpdz;
    }

    /**
     * 设置图片地址
     *
     * @param wjTpdz 图片地址
     */
    public void setWjTpdz(String wjTpdz) {
        this.wjTpdz = wjTpdz;
    }

    /**
     * 获取文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     *
     * @return WJ_SX - 文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     */
    public String getWjSx() {
        return wjSx;
    }

    /**
     * 设置文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     *
     * @param wjSx 文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     */
    public void setWjSx(String wjSx) {
        this.wjSx = wjSx;
    }

    /**
     * 获取图片是否ORC进行了识别 ZDCLK0052（0、 待识别 1、 识别成功 2、 识别失败）
     *
     * @return WJ_SBZT - 图片是否ORC进行了识别 ZDCLK0052（0、 待识别 1、 识别成功 2、 识别失败）
     */
    public String getWjSbzt() {
        return wjSbzt;
    }

    /**
     * 设置图片是否ORC进行了识别 ZDCLK0052（0、 待识别 1、 识别成功 2、 识别失败）
     *
     * @param wjSbzt 图片是否ORC进行了识别 ZDCLK0052（0、 待识别 1、 识别成功 2、 识别失败）
     */
    public void setWjSbzt(String wjSbzt) {
        this.wjSbzt = wjSbzt;
    }

    /**
     * 获取识别的报文内容
     *
     * @return WJ_BW - 识别的报文内容
     */
    public String getWjBw() {
        return wjBw;
    }

    /**
     * 设置识别的报文内容
     *
     * @param wjBw 识别的报文内容
     */
    public void setWjBw(String wjBw) {
        this.wjBw = wjBw;
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
     * 获取有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。
     *
     * @return WJ_SFYX - 有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。
     */
    public String getWjSfyx() {
        return wjSfyx;
    }

    /**
     * 设置有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。
     *
     * @param wjSfyx 有效状态位 ZDCLK0051（0、 删除 1、 有效) 默认为0  用户保存后创建用户资料表并更新此状态为有效。
     */
    public void setWjSfyx(String wjSfyx) {
        this.wjSfyx = wjSfyx;
    }

    public enum InnerColumn {
        id("ID"),
        yhId("YH_ID"),
        wjTpdz("WJ_TPDZ"),
        wjSx("WJ_SX"),
        wjSbzt("WJ_SBZT"),
        wjBw("WJ_BW"),
        cjsj("CJSJ"),
        wjSfyx("WJ_SFYX");

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