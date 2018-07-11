package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface BizOrderMapper extends Mapper<BizOrder> {
    /**
     * 将订单设置为退款。
     * @param userId
     * @param tkID
     */
    @Select({ "<script> " +
            " UPDATE biz_order SET DD_ZT = '2',TK_ID= #{tkID} WHERE DD_ID in ( "+
            " SELECT DD_ID FROM biz_order  "+
            " WHERE ( YH_ID = #{userId} ) and ( DD_ZT = '2' ) and ( DD_ZFZT = '1' ) and ( CP_ID IN (SELECT ID FROM biz_cp WHERE CP_TYPE='1') )   "+
            " ORDER BY DD_ID desc limit 1 ) "+
            "  "+
            " </script>"})
    void updateUserRefund(@Param("userId") String userId, @Param("tkID")  String tkID);
}