package com.cwb.platform.biz.mapper;

import com.cwb.platform.biz.model.BizBillContrast;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BizBillContrastMapper extends Mapper<BizBillContrast> {
//                bizBill.setRefundFee(l.getSettlementRefundFee());//退款金额
//                bizBill.setOrderId(l.getOutTradeNo());
    @Select({ "<script> " +
            " INSERT INTO biz_bill_contrast (ID,TRADE_TIME,OPEN_ID,TRADE_TYPE,TRADE_STATE,TOTAL_FEE,REFUND_ID,REFUND_CHANNEL,REFUND_STATE,PAT_TYPE,DISPOSE_TYPE,DISPOSE_MESSAGE,ORIGINAL_MESSAGE,ORDER_ID,REFUND_FEE) VALUES  "+
            " <foreach collection='list' item='item' open='(' close=')' separator='),('> " +
            "  #{item.id} ,#{item.tradeTime} ,#{item.openId} ,#{item.tradeType} ,#{item.tradeState} ,#{item.totalFee} ,#{item.refundId} ,#{item.refundChannel} ,#{item.refundState} ,#{item.patType} ,#{item.disposeType} ,#{item.disposeMessage} ,#{item.originalMessage} ,#{item.orderId} ,#{item.refundFee} " +
            " </foreach> " +
            " </script>"})
    void insertBatch(@Param("list") List<BizBillContrast> list);
}