package com.cwb.platform.biz.api;

import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.biz.util.ShoreCode;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.SnowflakeIdWorker;
import com.cwb.platform.util.exception.RuntimeCheck;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * 业务系统对外开放的接口
 * 
 */

@RestController
@RequestMapping("/pub/")
public class MessageApi {
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;
    @Autowired
    private BizPtyhMapper entityMapper;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private ZhService zhService;
    @Autowired
    private KsjfService ksjfService;
    @Autowired
    private SubSchoolService schoolService;

    @Autowired
    private YjmxService yjmxService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @GetMapping("/test")
    public ApiResponse<String> test(String id) throws WxErrorException, IOException {


        String yhZsyqm = "";
        boolean flag = true;
        while (flag) {
            yhZsyqm = ShoreCode.createShareCode();
            List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yhZsyqm);
            if (CollectionUtils.isEmpty(ptyhs)) {
                flag = false;
            }
        }
        String filepath = "/QRCode/" + DateTime.now().toString("yyyyMMdd") + "/" + yhZsyqm + ".png";
        BizPtyh bizPtyh = entityMapper.selectByPrimaryKey(id);
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(bizPtyh.getId());
        String qrCode = ticket.getTicket();
        String pictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(qrCode);
        URL u = new URL(pictureUrl);

        FileUtils.copyURLToFile(u,new File(qrCodeFileUrl+ filepath),5000,5000);
        bizPtyh.setQrcode(ticket.getUrl());
        bizPtyh.setYhZsyqm(yhZsyqm);
        bizPtyh.setYhZsyqmImg(filepath);
        ptyhService.update(bizPtyh);
        return ApiResponse.success();
    }

    @GetMapping("/getUserInfo")
    public ApiResponse<BizPtyh> getUserInfo(String id){
        RuntimeCheck.ifBlank(id, "需要上传用户id");
        BizPtyh bizPtyh = entityMapper.selectByPrimaryKey(id);
        RuntimeCheck.ifNull(bizPtyh,"未找到用户信息");
        BizPtyh ptyh = new BizPtyh();
        ptyh.setYhZsyqm(bizPtyh.getYhZsyqm());
        ptyh.setYhZsyqmImg("http://www.520xclm.com:8001/" + bizPtyh.getYhZsyqmImg());
        ptyh.setYhTx("http://www.520xclm.com:8001/" + bizPtyh.getYhTx());
        ptyh.setYhXm(bizPtyh.getYhXm());
        return ApiResponse.success(ptyh);

    }

    @GetMapping("/getName")
    public ApiResponse<String> getUserName(String code){
        List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, code);
        if(CollectionUtils.isNotEmpty(ptyhs)){
            return ApiResponse.success(ptyhs.get(0).getYhXm());
        }else{
            return ApiResponse.fail("");
        }
    }

    @GetMapping("/updateQrcode")
    public ApiResponse<String> udpateQrcode() throws WxErrorException {
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.and().andIsNotNull(BizPtyh.InnerColumn.yhZsyqm.name());
        List<BizPtyh> ptyhs = ptyhService.findByCondition(condition);
        for (BizPtyh ptyh : ptyhs) {
            WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(ptyh.getId());
            String url = ticket.getUrl();
            ptyh.setQrcode(url);
            ptyhService.update(ptyh);
        }
        return ApiResponse.success();
    }

    @GetMapping("/bindWx")
    public ApiResponse<String> bindWx(String yqm, String openid){
        RuntimeCheck.ifBlank(yqm, "请填写邀请码");
        RuntimeCheck.ifBlank(openid, "请填写openid");
        List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yqm);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(ptyhs), "未找到用户信息,请确认邀请码是否正确");
        BizPtyh ptyh = ptyhs.get(0);
        RuntimeCheck.ifTrue(StringUtils.isNotBlank(ptyh.getYhOpenId()), "您已绑定微信号,请勿重复绑定");
        ptyh.setYhOpenId(openid);
        ptyhService.update(ptyh);
        List<BizSubSchool> sub = schoolService.findEq(BizSubSchool.InnerColumn.yhId, ptyh.getId());
        if(CollectionUtils.isNotEmpty(sub)){
            sub.forEach(bizSubSchool -> {
                bizSubSchool.setSubOpenid(openid);
                schoolService.update(bizSubSchool);
            });
        }
        return ApiResponse.success();
    }

    /**
     * 更新一波 账户余额
     */
    @GetMapping("/updateAllZh")
    public ApiResponse<String> updateAllZh(){
        List<BizPtyh> all = ptyhService.findAll();
        List<String> list = all.stream().map(BizPtyh::getId).collect(Collectors.toList());
        zhService.userAccountUpdate(list);
        return ApiResponse.success();
    }

    @GetMapping("/updateMxlx")
    public ApiResponse<String> updateMxlx(){
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhLx,  "1");
        List<BizPtyh> ptyhs = ptyhService.findByCondition(condition);
        ptyhs.forEach(bizPtyh -> {
            SimpleCondition cond = new SimpleCondition(BizYjmx.class);
            cond.eq(BizYjmx.InnerColumn.yhId, bizPtyh.getId());
            cond.eq(BizYjmx.InnerColumn.zjJe, 20000);
            cond.and().andIsNull(BizYjmx.InnerColumn.zjId.name());
            List<BizYjmx> yjmxes = yjmxService.findByCondition(cond);
            if(CollectionUtils.isNotEmpty(yjmxes)){
                List<String> list = yjmxes.stream().map(BizYjmx::getId).collect(Collectors.toList());
                list.forEach(s -> yjmxService.remove(s));
            }
        });
        return ApiResponse.success();
    }

    /**
     * 更新 现有学员的待缴考试费
     */
    @GetMapping("/updateJf")
    public ApiResponse<String> updateJfType(){
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhLx,  "1");
        List<BizPtyh> ptyhs = ptyhService.findByCondition(condition);
        ptyhs.forEach(bizPtyh -> {
            // 根据当前已交考试费来填
            SimpleCondition con = new SimpleCondition(BizKsJf.class);
            con.eq(BizKsJf.InnerColumn.yhId.name(),bizPtyh.getId());
//            con.eq(BizKsJf.InnerColumn.kmId.name(), "2");
            List<BizKsJf> jfs = ksjfService.findByCondition(con);
            if(CollectionUtils.isEmpty(jfs)){
                    bizPtyh.setYhXyJfType("1");
            }else{
                Set<String> set = jfs.stream().map(BizKsJf::getKmId).collect(Collectors.toSet());
                if(set.contains("1")){
                    if(set.contains("2")){
                        bizPtyh.setYhXyJfType("3");
                    }else{
                        bizPtyh.setYhXyJfType("2");
                    }
                }else{
                    bizPtyh.setYhXyJfType("1");
                }
            }
            ptyhService.update(bizPtyh);
        });
        return ApiResponse.success();
    }

}
