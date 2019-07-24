package com.cwb.platform.biz.api;

import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.util.ShoreCode;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
        bizPtyh.setYhZsyqm(yhZsyqm);
        bizPtyh.setYhZsyqmImg(filepath);
        ptyhService.update(bizPtyh);
        return ApiResponse.success();
    }
}
