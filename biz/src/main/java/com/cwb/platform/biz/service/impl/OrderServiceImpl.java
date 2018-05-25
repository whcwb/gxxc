package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class OrderServiceImpl extends BaseServiceImpl<BizOrder,java.lang.String> implements OrderService{
    Logger payInfo = LoggerFactory.getLogger("access_info");
    @Autowired
    private BizOrderMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }

    /**
     * 支付成功后，调用这个方法，修改数据库
     * oracle订单表      1、支付凭证     2、订单状态 2     3、支付状态 1成功 2 失败
     * user 用户表     DD_SFJX  是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
     * @param l
     * @return
     */
    public ApiResponse<String> updateOrderPayTpye(BizOrder l){
        payInfo.info("订单编号："+l.getDdId()+"*****************************************************************");
        BizOrder order=findById(l.getDdId());
        if(StringUtils.equals(order.getDdZt(),"2")||StringUtils.equals(order.getDdZfzt(),"1")){
            payInfo.info("订单编号："+l.getDdId()+"已完成支付，不需要操作数据");
            return ApiResponse.success("该订单已完成支付，不需要操作数据");
        }

        BizOrder newBizOrder=new BizOrder();
        newBizOrder.setDdId(l.getDdId());
        newBizOrder.setDdZt("2");//2、订单状态 2
        newBizOrder.setDdZfzt("1");//支付状态 1成功 2 失败
//        newBizOrder.setDdZftd(l.getDdZftd());//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        newBizOrder.setDdZfpz(l.getDdZfpz());//支付凭证ID(保存支付通道返回的CODE)
        newBizOrder.setPayMoney(l.getPayMoney());//订单支付成功后，将实际支付的金额回写到这里。用于验证订单支付是否异常

        this.update(newBizOrder);

        BizPtyh bizPtyh=new BizPtyh();
        bizPtyh.setId(order.getYhId());
        bizPtyh.setDdSfjx("1");
        ptyhService.update(bizPtyh);

        return ApiResponse.success();
    }
}
