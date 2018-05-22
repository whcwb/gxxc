package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizZh;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizZhMapper extends Mapper<BizZh> {

    @Select({ "<script> " +
            " DELETE FROM BIZ_ZH  WHERE YH_ID IN "+
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> " +
    " </script>"})
    void deleteUserDetail(List<String> list);

    @Select({ "<script> " +
            " INSERT INTO BIZ_ZH (YH_ID, YH_ZHYE, YH_TXDJ) " +
            " SELECT U.ID AS YH_ID " +
            "  ,(SELECT SUM(W.ZJ_JE*W.ZJ_FS) FROM BIZ_YJMX W WHERE W.YH_ID=U.ID AND W.ZJ_ZT!='2') AS YH_ZHYE " +
            "  ,(SELECT SUM(ZJ_JE) FROM BIZ_YJMX W WHERE W.YH_ID=U.ID AND W.ZJ_ZT='0' AND W.ZJ_FS='-1') AS YH_TXDJ " +
            " FROM BIZ_PTYH U WHERE U.ID IN  "+
            " <foreach collection='list' item='item' open='(' close=')' separator=','> " +
            "  #{item} " +
            " </foreach> " +
            " </script>"})
    void updatUserAccount(List<String> list);
}