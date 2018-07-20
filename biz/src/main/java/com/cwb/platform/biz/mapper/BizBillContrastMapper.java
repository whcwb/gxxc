package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizBillContrast;
import com.cwb.platform.biz.model.BizOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizBillContrastMapper extends Mapper<BizBillContrast> {
//                bizBill.setRefundFee(l.getSettlementRefundFee());//退款金额
//                bizBill.setOrderId(l.getOutTradeNo());
    @Update({ "<script> " +
            " INSERT INTO biz_bill_contrast (ID,TRADE_TIME,OPEN_ID,TRADE_TYPE,TRADE_STATE,TOTAL_FEE,REFUND_ID,REFUND_CHANNEL,REFUND_STATE,PAT_TYPE,DISPOSE_TYPE,DISPOSE_MESSAGE,ORIGINAL_MESSAGE,ORDER_ID,REFUND_FEE) VALUES  "+
            " <foreach collection='list' item='item' open='(' close=')' separator='),('> " +
            "  #{item.id} ,#{item.tradeTime} ,#{item.openId} ,#{item.tradeType} ,#{item.tradeState} ,#{item.totalFee} ,#{item.refundId} ,#{item.refundChannel} ,#{item.refundState} ,#{item.patType} ,#{item.disposeType} ,#{item.disposeMessage} ,#{item.originalMessage} ,#{item.orderId} ,#{item.refundFee} " +
            " </foreach> " +
            " </script>"})
    void insertBatch(@Param("list") List<BizBillContrast> list);

    /**
     * 按交易日期，删除对账表数据
     * @param billDate
     */
    @Delete({ "<script> " +
            " DELETE FROM BIZ_BILL_CONTRAST WHERE TRADE_TIME= #{billDate}"+
            " </script>"})
    void delHandcraftBill(@Param("billDate") String billDate);

    /**
     * 重置订单表中对账字段
     *BILL_CONTRAST_TYPE    对账状态  [ZDCLK0072]：0未对账     1、已对账     2、对账异常
     *BILL_CONTRAST_MSG  对账结果描述
     * @param billDate
     */
    @Update({ "<script> " +
            " UPDATE BIZ_ORDER SET  BILL_CONTRAST_TYPE = '0', BILL_CONTRAST_MSG = null WHERE date_format(str_to_date(DD_ZFSJ,'%Y-%m-%d'),'%Y%m%d') = #{billDate}"+
            " </script>"})
    void resettingOrderBillContrast(@Param("billDate") String billDate);

    /**
     * 查询支付日期符合的订单
     * @param billDate
     * @return
     */
    @Select({ "<script> " +
            " SELECT * FROM BIZ_ORDER "+
            " WHERE ((date_format(str_to_date(DD_ZFSJ,'%Y-%m-%d'),'%Y%m%d') = #{billDate}) or (" +
            " DD_ZT='1' and ( DATE_FORMAT(STR_TO_DATE(CJSJ,'%Y-%m-%d %T'),'%Y%m%d')= #{billDate} or DATE_FORMAT(date_sub(STR_TO_DATE(CJSJ,'%Y-%m-%d %T'),interval -1 day),'%Y%m%d')= #{billDate} ) " +
            " ) )  AND BILL_CONTRAST_TYPE=0"+
            " </script>"})
    List<BizOrder> getPayDateOrder(@Param("billDate") String billDate);

    @Select({ "<script> " +
            " SELECT * FROM BIZ_BILL_CONTRAST WHERE TRADE_TIME= #{billDate} AND  TRADE_STATE in ('1','2') and DISPOSE_TYPE is NULL"+
            " </script>"})
    List<BizBillContrast> getPayDateBillContrast(@Param("billDate") String billDate);

    /**
     * 更新订单表的对账字段为成功
     * @param successOrderList
     */
    @Update({ "<script> " +
            " UPDATE BIZ_ORDER  "+
            " SET BILL_CONTRAST_TYPE = '1', BILL_CONTRAST_MSG = '对账成功'  "+
            " WHERE DD_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " </script>"})
    void updateOrderContrastType(@Param("list") List<String> successOrderList);

    /**
     * 更新账单表的对账字段为成功
     * @param successOrderList
     */
    @Update({ "<script> " +
            " UPDATE BIZ_BILL_CONTRAST  "+
            " SET DISPOSE_TYPE = '1', DISPOSE_MESSAGE = '对账成功'  "+
            " WHERE ORDER_ID IN " +
            " <foreach collection='list' item='item' open='(' close=')' separator=','>" +
            " #{item} " +
            "</foreach>" +
            " </script>"})
    void updateBillContrastType(@Param("list") List<String> successOrderList);

    @Update({ "<script> " +
            " UPDATE BIZ_ORDER  "+
            " SET BILL_CONTRAST_TYPE = '2', BILL_CONTRAST_MSG = '对账失败'  "+
            " WHERE date_format(str_to_date(left(DD_ZFSJ,10),'%Y-%m-%d'),'%Y%m%d') = #{billDate} " +
            " AND BILL_CONTRAST_TYPE='0' " +
            " </script>"})
    void updateNotOrderContrastType( @Param("billDate")  String billDate);


    @Update({ "<script> " +
            " UPDATE BIZ_BILL_CONTRAST  "+
            " SET DISPOSE_TYPE = '2', DISPOSE_MESSAGE = '对账失败'  "+
            " WHERE " +
            " TRADE_TIME= #{billDate} AND  TRADE_STATE in ('1','2') " +
            " AND DISPOSE_TYPE is NULL" +
            " </script>"})
    void updateNotBillContrastType( @Param("billDate")  String billDate);

}