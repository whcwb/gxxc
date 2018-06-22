package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizJl;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface BizJlMapper extends Mapper<BizJl> {
    @Select({ "<script> " +
            " UPDATE BIZ_JL SET JL_PF=(SELECT IFNULL(ROUND(AVG(YH_FZ),0),5) FROM BIZ_YHPF WHERE JL_ID=#{item} AND AUDIT_TYPE='1') WHERE YH_ID = #{item}  " +
            " </script>"})
    void updateJlPf(String list);
}