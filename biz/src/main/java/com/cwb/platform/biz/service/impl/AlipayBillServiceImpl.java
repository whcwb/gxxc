package com.cwb.platform.biz.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.cwb.platform.biz.model.BizBillContrast;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.commonUtil.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.mapper.common.Mapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 实现支付宝对账
     * @param
     * @return response
     */
    public void alipayBill(String billDate) {
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
            String downUrl=this.downBill(billDownloadUrl); //调用下载文件方法
            if(StringUtils.isNotEmpty(downUrl)){//文件下载成功
                try {
//                    readZipCvsFile(new File(""));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                //下载文件失败，请重新下载 todo
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }//通过alipayClient调用API，获得对应的response类

    }


    /**
     * 下载账单文件：
     * @param billDownloadUrl   账单地址
     * @return response
     */
    public String downBill(String billDownloadUrl){
        Boolean ret=false;
        long filename=new Date().getTime();
        //指定希望保存的文件路径
        String filePath = "";
        //拼装文件目录
        filePath=billFilePath;
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        filePath+=filename;
        FileUtil.fileExistsDir(filePath);
        filePath+="/"+filename+".zip";

        URL url = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            url = new URL(billDownloadUrl);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5 * 1000);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
            httpUrlConnection.connect();
            fis = httpUrlConnection.getInputStream();
            byte[] temp = new byte[1024];
            int b;
            fos = new FileOutputStream(new File(filePath));
            while ((b = fis.read(temp)) != -1) {
                fos.write(temp, 0, b);
                fos.flush();
            }
            ret=true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis!=null) fis.close();
                if(fos!=null) fos.close();
                if(httpUrlConnection!=null) httpUrlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(ret){
           return  filePath;
        }else{
            return null;
        }

    }

    public static void main(String[] args) {
//        try {
////            readZipCvsFile(new File("C:\\Users\\Administrator\\Desktop\\2003\\20886126836996110156_20160906.zip"));
//            downloadAlipayBills("","","");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

       String csvRowContent="支付宝交易号,商户订单号,业务类型,商品名称,创建时间,完成时间,交易门店,操作员,终端号,对账账户,订单金额,交易金额,红包抵扣,集分宝抵扣,优惠汇总,卡消费金额,卷核销金额,商家红包,退款批次号,服务费,分润,备注";
        String [] csvColumnArray = csvRowContent.split(",");
        String totalFee = String.valueOf(Math.abs(Double.parseDouble(csvColumnArray[12].trim())));//红包抵扣
    }

//    public static void readZipCvsFile(File file) throws Exception {
////获得输入流，文件为zip格式，
//        //支付宝提供
//        //20886126836996110156_20160906.csv.zip内包含
//        //20886126836996110156_20160906_业务明细.csv
//        //20886126836996110156_20160906_业务明细(汇总).csv
//        ZipInputStream in = new ZipInputStream(new FileInputStream(file));
//        //不解压直接读取,加上gbk解决乱码问题
//        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
//        ZipEntry zipFile;
////        zipFile = new ZipFile(zipFilePath,Charset.forName("GBK"));
//        //循环读取zip中的cvs文件，无法使用jdk自带，因为文件名中有中文
//        while ((zipFile=in.getNextEntry())!=null) {
//            if (zipFile.isDirectory()){
//                //如果是目录，不处理
//            }
//            //获得cvs名字
//            String fileName = zipFile.getName();
//            System.out.println("-----"+fileName);
//            //检测文件是否存在
//            if (fileName != null && fileName.indexOf(".") != -1) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
//        }
//        //关闭流
//        br.close();
//        in.close();
//    }



    /**
     *
     * 方法描述：请求支付宝对账单下载连接，解析zip包，读取、解析csv文件数据入库
     * @param
     * @return
     * @version 1.0
     * @author zhouwen
     * 2017年7月18日 上午11:44:10
     */
    @SuppressWarnings("unchecked")
    public static void downloadAlipayBills(String alipayBillDownloadUrl, String tradeDate, String mch) throws Exception {
//        URL url = new URL(alipayBillDownloadUrl);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(5 * 1000);
//        // 1 读取压缩文件流
//        InputStream inputStream = conn.getInputStream();
//        if(null==inputStream){
//            logger.info("商户号:"+mch+",获取对账单文件流为空！");
//            return;
//        }
//        File tempFile = new File("temp");
//        FileOutputStream fos = new FileOutputStream(tempFile);
//        int len = -1;
//        byte[] b = new byte[1024];
//        while ((len = inputStream.read(b)) > -1) {
//            fos.write(b, 0, len);
//        }
//        fos.close();
//        inputStream.close();
        // 2 解压
//        ZipFile zipFile = new ZipFile(tempFile, "gbk");
        ZipFile zipFile = new ZipFile(new File("C:\\Users\\Administrator\\Desktop\\2003\\222\\20886126836996110156_20160906-2.zip"), "gbk");
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
//                        List<String> oldList = alipayAccountDao.getAlipayAccountByDate(tradeDate);
//                        saveAlipayBills(csvContentList,oldList,mch);
                    }
                }
            }
        }
        zipFile.close();
//        tempFile.delete();
    }

    /**
     *
     * 方法描述：根据读取的csv内容保存支付宝数据
     * @param
     * @return
     * @version 1.0
     * @author zhouwen
     * 2017年8月1日 下午2:43:36
     */
    private void saveAlipayBills(List<String> csvContentList,List<String> oldList,String mch) throws Exception{
        for(int i=5;i<csvContentList.size();i++){
            String csvRowContent = csvContentList.get(i);
            if(csvRowContent.indexOf("业务明细列表结束")!=-1){
                break;
            }
            BizBillContrast bizBill= new BizBillContrast();
            bizBill.setId(genId());
            bizBill.setOriginalMessage(csvRowContent);
//            bizBill.setTradeTime(billDate);
//
//            AccountQueryAccountLogVO vo = new AccountQueryAccountLogVO();
//            vo.setAlipayID(UUIDGenerator.getUUID());
//            String [] csvColumnArray = csvRowContent.split(",");
//            String totalFee = String.valueOf(Math.abs(Double.parseDouble(csvColumnArray[12].trim())));//红包抵扣
//            vo.setSellerAccount(mch);
//            vo.setPartnerId(mch);
//            vo.setCurrency("156");
//            vo.setTradeNo(csvColumnArray[0].trim());
//            vo.setMerchantOutOrderNo(csvColumnArray[1].trim());
//            vo.setTransCodeMsg(csvColumnArray[2].trim());
//            vo.setGoodsTitle(csvColumnArray[3].trim());
//            vo.setTransDate(csvColumnArray[5].trim());
//            vo.setBuyerAccount(csvColumnArray[10].trim());
//            vo.setIncome(totalFee);
//            vo.setTotalFee(totalFee);
//            vo.setServiceFee(csvColumnArray[22].trim());
//            vo.setMemo(csvColumnArray[24].trim());
//            if("退款".equals(csvColumnArray[2].trim()) && Double.parseDouble(csvColumnArray[12].trim())<0){
//                vo.setAddRefundNum(csvColumnArray[21].trim());
//                vo.setAddRefundDate(csvColumnArray[5].trim());
//                vo.setAddRefundFee(totalFee);
//                vo.setTradeRefundAmount(totalFee);
//                boolean updated = alipayAccountDao.updateAlipayAccount(vo);
//                if (updated) {
//                    continue;
//                }
//            }
//            if (!oldList.contains(vo.getTradeNo())) {
//                alipayAccountDao.insertAlipayAccount(vo);
//            }
        }
    }



}
