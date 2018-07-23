package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizTjDz;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BizTjDzMapper extends Mapper<BizTjDz> {
    @Update({ "<script> " +
            " INSERT INTO biz_tj_dz (PKID, DZ_SJ, ORACLE_COUNT, ORACLE_FEE, BILL_COUNT, BILL_FEE, SUCCESS_COUNT, SUCCESS_FEE, SUCCESS_REFUND_COUNT, SUCCESS_REFUND_FEE, ORDER_FAIL_COUNT, Bill_FAIL_COUNT, CREATION_TIME)    "+
            " SELECT  #{bizTjDz.pkid},#{bizTjDz.dzSj},#{bizTjDz.oracleCount},#{bizTjDz.oracleFee},#{bizTjDz.billCount},#{bizTjDz.billFee},#{bizTjDz.successCount},#{bizTjDz.successFee},#{bizTjDz.successRefundCount},#{bizTjDz.successRefundFee}" +
            ",(SELECT COUNT(1) FROM BIZ_ORDER WHERE BILL_CONTRAST_TYPE = '2' and date_format(str_to_date(left(DD_ZFSJ,10),'%Y-%m-%d'),'%Y%m%d') = #{billDate} )  " +
            ",(SELECT COUNT(1) FROM BIZ_BILL_CONTRAST WHERE DISPOSE_TYPE = '2' AND TRADE_TIME= #{billDate}) " +
            ",#{bizTjDz.creationTime}   " +
            " </script>"})
    void addStatistics(@Param("bizTjDz") BizTjDz bizTjDz, @Param("billDate") String billDate);
}