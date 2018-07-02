package com.cwb.platform.biz.bean;

import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.sys.model.BizPtyh;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusModel {
    private String id;
    private String yhXm;
    private String yhZh;
    private String yhZjhm;
    private String yhXyYkType;
    private String yhXySlType;

    private BizKsYk km1;
    private BizKsYk km2;
    private BizKsYk km3;
    private BizKsYk km4;

    private BizPtyh zy0; // 受理专员
    private BizPtyh zy1; // 一阶段专员
    private BizPtyh zy2; // 二阶段专员
    private BizPtyh zy3; // 三阶段专员
    private BizPtyh zy4; // 四阶段专员

    public StatusModel(BizPtyh ptyh){
        this.id = ptyh.getId();
        this.yhXm = ptyh.getYhXm();
        this.yhZh = ptyh.getYhZh();
        this.yhZjhm = ptyh.getYhZjhm();
        this.yhXyYkType = ptyh.getYhXyYkType();
        this.yhXySlType = ptyh.getYhXySlType();
    }

}
