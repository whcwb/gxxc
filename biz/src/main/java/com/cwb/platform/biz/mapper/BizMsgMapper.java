package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizMsg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizMsgMapper extends Mapper<BizMsg> {
    /**
     * 根据创建时间查询用户id
     */
    @Select("<script> select b.* from biz_msg b inner join ( select max(cjsj) cjsj ,user_id from biz_msg <if test='userName !=null'> where user_name = #{userName} </if> group by user_id) a on a.user_id = b.user_id and a.cjsj = b.cjsj  where b.type = #{type}   order by b.cjsj desc </script>")
    List<BizMsg> getUserList(@Param("type") String type, @Param("userName") String userName);

}
