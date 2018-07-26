package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizTj;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BizTjMapper extends Mapper<BizTj> {
    @Update({ "<script> " +
            " " +
            "  INSERT INTO biz_tj (ID, TJ_SJ, TJ_LX, CJSJ, TJ_NUM, TJ_JE, TJ_TD)    "+

            " SELECT UUID(),#{tjsj} as tj_sj, '20'  as tj_lx, #{nowTime} as CJSJ,count(1) as TJ_NUM,SUM(DD_ZFSJ) ,DD_ZFTD as  tj_td  FROM biz_order  "+
            " date_format(str_to_date(left(cjsj,10),'%Y-%m-%d'),'%Y-%m-%d')= #{tjsj} GROUP BY DD_ZFTD "+
            "  "+
            "  "+


            " </script>"})
    void orderStatistics(String tjsj, String nowTime);
}