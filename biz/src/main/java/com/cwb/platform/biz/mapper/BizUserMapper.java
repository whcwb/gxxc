package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizUserMapper extends Mapper<BizUser> {

    @Update("<script>" +
            " UPDATE BIZ_USER SET YH_JLID = #{jlId}" +
            " where YH_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    void updateJlid(@Param("list") List<String> list,@Param("jlId") String jlId);

    @Select("<script>" +
            " SELECT ID FROM biz_ptyh WHERE " +
            " YH_LX='1' AND YH_ZT='1' AND DD_SFJX='1' AND YH_IXY_SFFP='0' AND YH_SFSD='0' " +
            " AND ID IN " +
            "<foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<String> getYhIds(@Param("list") List<String> ids);

}