package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizWj;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizWjMapper extends Mapper<BizWj> {
    @Select({ "<script> " +
    " INSERT INTO gxxc.biz_wj (ID, YH_ID, WJ_TPDZ, WJ_SX, WJ_SBZT, WJ_BW, CJSJ, WJ_SFYX) VALUES  "+
    " <foreach collection='list' item='item' open='(' close=')' separator='),('> " +
    "  #{item.id} ,#{item.yhId} ,#{item.wjTpdz} ,#{item.wjSx} ,#{item.wjSbzt} ,'' ,#{item.cjsj} ,#{item.wjSfyx} " +
    " </foreach> " +
    " </script>"})
    void insertBatch(List<BizWj> list);
}
