package com.cwb.platform.biz.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.cwb.platform.biz.mapper.BizBillContrastMapper;
import com.cwb.platform.biz.model.BizBillContrast;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.mapper.common.Mapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



/** 支付宝 对账方法
 * Created by Administrator on 2018/7/30.
 */
public class AlipayBillServiceImpl extends BaseServiceImpl {

    Logger logger = LoggerFactory.getLogger("access_info");

    @Override
    protected Mapper getBaseMapper() {
        return null;
    }

    //  应用id(app_id)
    @Value("${alipay.app_id}")
    private static String alipayAppId;
    //APPA应用私钥(private_key)
    @Value("${alipay.app_privaie_key}")
    private static String appPrivaieKey;
    //编码格式(charset)
    @Value("${alipay.charset}")
    private static String charset;
    //    支付宝公钥
    @Value("${alipay.alipay_public_key}")
    private static String alipayPublicKey;
    //    网关(gateway)请求支付的接口
    @Value("${alipay.gateway}")
    private static String gateway;
    //  签名类型  默认为：RSA2
    @Value("${alipay.sign_type}")
    private static String signType;
    @Value("${alipay.notify_url}")//支付宝回调接口
    private String alipayNotifyUrl;
    @Value("${alipay.bill_file_path}")
    private static String billFilePath;

    @Autowired
    private BizBillContrastMapper billContrastMapper;

    /**
     * 实现支付宝对账
     * @param billDate 对账时间 格式是 yyyy-mm-dd
     * @return response
     */
    public Boolean alipayBill(String billDate) {
        Boolean retType=false;
        logger.info("==================向支付宝发起对账请求");
        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型）
            AlipayClient alipayClient = new DefaultAlipayClient(gateway, alipayAppId, appPrivaieKey, "json", charset,
                    alipayPublicKey,signType);
            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
            request.setBizContent("{" +
                    "\"bill_type\":\"trade\"," +
                    "\"bill_date\":\""+billDate+"\"}"); //设置业务参数
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);

            String billDownloadUrl=response.getBillDownloadUrl();
            logger.info("==================支付宝返回文件下载地址："+billDownloadUrl);
            try {
                downloadAlipayBills(billDownloadUrl,billDate);
                retType = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return retType;
    }


//    public static void main(String[] args) {
////        try {
//////            readZipCvsFile(new File("C:\\Users\\Administrator\\Desktop\\2003\\20886126836996110156_20160906.zip"));
////            downloadAlipayBills("","","");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//       String csvRowContent="支付宝交易号,商户订单号,业务类型,商品名称,创建时间,完成时间,交易门店,操作员,终端号,对账账户,订单金额,交易金额,红包抵扣,集分宝抵扣,优惠汇总,卡消费金额,卷核销金额,商家红包,退款批次号,服务费,分润,备注";
//        String [] csvColumnArray = csvRowContent.split(",");
//        String totalFee = String.valueOf(Math.abs(Double.parseDouble(csvColumnArray[12].trim())));//红包抵扣
//    }

    /**
     *
     * 方法描述：请求支付宝对账单下载连接，解析zip包，读取、解析csv文件数据入库
     * @param   alipayBillDownloadUrl 对账文件下载的地址
     * @param tradeDate 对账时间 格式是 yyyy-mm-dd
     * @return
     * @version 1.0
     * @author zhouwen
     * 2017年7月18日 上午11:44:10
     */
    @SuppressWarnings("unchecked")
    public void downloadAlipayBills(String alipayBillDownloadUrl, String tradeDate) throws Exception {
        URL url = new URL(alipayBillDownloadUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        // 1 读取压缩文件流
        InputStream inputStream = conn.getInputStream();
        if(null==inputStream){
            logger.info("对账日期:"+tradeDate+",获取对账单文件流为空！");
            return;
        }
        File tempFile = new File("temp");
        FileOutputStream fos = new FileOutputStream(tempFile);
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = inputStream.read(b)) > -1) {
            fos.write(b, 0, len);
        }
        fos.close();
        inputStream.close();
        // 2 解压
        ZipFile zipFile = new ZipFile(tempFile, "gbk");
//        ZipFile zipFile = new ZipFile(new File("C:\\Users\\Administrator\\Desktop\\2003\\222\\20886126836996110156_20160906-2.zip"), "gbk");
        Enumeration<ZipEntry> zList=zipFile.getEntries();
        while(zList.hasMoreElements()){
            ZipEntry ze =(ZipEntry)zList.nextElement();
            if(ze.isDirectory() || !ze.getName().endsWith("业务明细.csv")){//为空的文件夹什么都不做 将汇总的文件也给删除掉
            }else{
                List<String> csvContentList = new ArrayList<String>();
//                logger.info("文件:"+ze.getName()+",大小:"+ze.getSize()+"bytes");
                System.out.println("文件:"+ze.getName()+",大小:"+ze.getSize()+"bytes");
                if(ze.getSize()>0){
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(ze), "gbk"));
                        String line=null;
                        while((line=reader.readLine())!=null){
                            csvContentList.add(line);
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        reader.close();
                    }
//                    logger.info("文件:"+ze.getName()+",条数:"+csvContentList.size());
                    System.out.println("文件:"+ze.getName()+",条数:"+csvContentList.size());
                    if(null!=csvContentList && !csvContentList.isEmpty()){//账单的文件是否存在。
                        saveAlipayBills(csvContentList,tradeDate); // 按读取出来的内容存库
                    }
                }
            }
        }
        zipFile.close();
        tempFile.delete();

    }

    /**
     *
     * 方法描述：根据读取的csv内容保存支付宝数据
     * @param   csvContentList 读取出来的账单列表
     * @param tradeDate 对账日期 yyyy-mm-dd
     * @return
     * @version 1.0
     * @author zhouwen
     * 2017年8月1日 下午2:43:36
     */
    private void saveAlipayBills(List<String> csvContentList,String tradeDate) throws Exception{
        List<BizBillContrast> addBizBillList=new ArrayList<BizBillContrast>();
        for(int i=5;i<csvContentList.size();i++){
            String csvRowContent = csvContentList.get(i);
            if(csvRowContent.indexOf("业务明细列表结束")!=-1){
                break;
            }
            String tradeState="1";//设置交易状态  1-支付成功  2-退款
            BizBillContrast bizBill= new BizBillContrast();
            bizBill.setId(genId());
            bizBill.setOriginalMessage(csvRowContent);//对账文件的原始报文
            bizBill.setTradeTime(tradeDate.replaceAll("-",""));
            bizBill.setPatType("1");//支付通道(1、支付宝 2、微信)

            String [] csvColumnArray = csvRowContent.split(",");
            String totalFee = String.valueOf(Math.abs(Double.parseDouble(csvColumnArray[12].trim())));//账单金额

            if("退款".equals(csvColumnArray[2].trim()) && Double.parseDouble(csvColumnArray[12].trim())<0){
                tradeState="2";
                totalFee= String.valueOf(Double.parseDouble(csvColumnArray[12].trim())*-1);//支付宝是负数，我们平台是记正数
                bizBill.setRefundId(csvColumnArray[18].trim());//支付宝退款批次号-退款单号
                bizBill.setRefundChannel(null);//退款类型
                bizBill.setRefundFee(totalFee);//退款金额
            }

            bizBill.setTransactionId(csvColumnArray[0].trim());//支付凭证ID
            bizBill.setOrderId(csvColumnArray[1].trim());//订单ID

            bizBill.setTradeState(tradeState);//交易状态
            bizBill.setTotalFee(totalFee);//总金额(元)  交易金额

            addBizBillList.add(bizBill);
        }

//        批量插入账单表
        if(addBizBillList!=null && addBizBillList.size()>0){
            billContrastMapper.insertBatch(addBizBillList);
        }
    }



}
