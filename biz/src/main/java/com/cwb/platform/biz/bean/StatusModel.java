package com.cwb.platform.biz.bean;

import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.sys.model.BizPtyh;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusModel extends BizPtyh {
    private BizKsYk km1;
    private BizKsYk km2;
    private BizKsYk km3;
    private BizKsYk km4;

    private BizPtyh zy0; // 受理专员
    private BizPtyh zy1; // 一阶段专员
    private BizPtyh zy2; // 二阶段专员
    private BizPtyh zy3; // 三阶段专员
    private BizPtyh zy4; // 四阶段专员

}
