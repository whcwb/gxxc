package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizCp;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BizCpMapper extends Mapper<BizCp> {
    @Select({ "<script> " +
            " SELECT ID AS ID,CP_MC AS CPMC,CP_TYPE AS CPTYPE,CP_JL AS CPJL,CP_YJ AS CPYJ,CP_YJYJ AS CPYJYJ,CP_RJYJ AS CPRJYJ,CP_YX AS CPYX,CJSJ AS CJSJ,CJR AS CJR FROM ( " +
            "  SELECT * " +
            "  FROM BIZ_CP " +
            "  WHERE CP_TYPE = #{cpType} AND CP_YX = '1' " +
            "  ORDER BY ID DESC " +
            " ) C limit 1 " +
            " </script>"})
    BizCp getCpTyetList(String cpType);

    @Update("<script>" +
            " UPDATE BIZ_CP SET CP_YX = '0' " +
            " where CP_TYPE = #{cpType} " +
            "</script>")
    void updateTypeDelete(String cpType);
}