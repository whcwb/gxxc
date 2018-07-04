package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizKsSl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 学员考试受理信息表
 */
public interface BizKsSlMapper extends Mapper<BizKsSl> {
    /**
     * 受理成功后，自动补齐，前面未完成的流程
     * @param list
     * @return
     */
    @Select({ "<script> " +
            " INSERT INTO biz_ks_sl (ID, YH_ID, CODE, NAME, SL_TYPE, SL_SJ, YH_XM, YH_ZJHM,CJR,CJSJ,LSH) VALUES  "+
            " <foreach collection='list' item='item' open='(' close=')' separator='),('> " +
            "  #{item.id} ,#{item.yhId} ,#{item.code} ,#{item.name} ,#{item.slType} ,#{item.slSj} ,#{item.yhXm} ,#{item.yhZjhm},#{item.cjr} ,#{item.cjsj} ,#{item.lsh} " +
            " </foreach> " +
            " </script>"})
    void insertBatchKsSl(@Param("list") List<BizKsSl> list);
}