package com.cwb.platform.biz.app.bean;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Map;

/**
 * 教练页面-我的学员列表中的对象
 * Created by Administrator on 2018/6/5.
 */
public class StudentListModel implements Serializable {

    /**
     * 学员ID
     */
    @Column(name = "YH_ID")
    private String id;
    /**
     * 学员状态(0、完成学习  1、科目一 2、科目二 3、科目三 4、科目四)
     */
    @Column(name = "XY_ZT")
    private String xyZt;
    /**
     * 教练ID
     */
    @Column(name = "YH_JLID")
    private String yhJlid;

    @Transient
    private Map<String,Object> map;


    private static final long serialVersionUID = 2L;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getXyZt() {
        return xyZt;
    }

    public void setXyZt(String xyZt) {
        this.xyZt = xyZt;
    }

    public String getYhJlid() {
        return yhJlid;
    }

    public void setYhJlid(String yhJlid) {
        this.yhJlid = yhJlid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public enum InnerColumn {
        id("ID")
        ,xyZt("XY_ZT")
        ,yhJlid("YH_JLID")
        ;

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
