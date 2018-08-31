package com.jnshu.util;//package com.jnshu.util;
//
//import com.jnshu.service.impl.LoginService;
//import com.qcloud.cos.COSClient;
//import com.qcloud.cos.ClientConfig;
//import com.qcloud.cos.auth.BasicCOSCredentials;
//import com.qcloud.cos.auth.COSCredentials;
//import com.qcloud.cos.model.Bucket;
//import com.qcloud.cos.model.COSObject;
//import com.qcloud.cos.model.PutObjectRequest;
//import com.qcloud.cos.region.Region;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class TencentCOSAPI {
//    private static Logger logger = LoggerFactory.getLogger(TencentCOSAPI.class);
//    private COSClient cosClient;
//    private Boolean flag = false;
//    private String bucketName;
//    private String fileName;
//    private String secretId;
//    private String secretKey;
//    private String clientConfig;
//    private String fileSize;
//
//    @Autowired
//    LoginService loginService;
//
//    public COSClient getCosClient() {
//        try {
//            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//            ClientConfig clientConfig = new ClientConfig(new Region(bucketName));
//            return new COSClient(cred, clientConfig);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.debug("COSClient 初始化失败");
//            return null;
//        }
//    }
//
//    //上传图片
//    private Boolean updateLFileReal1(Integer id, InputStream inputStream, String fileName, String fileType) {
//        cosClient = getCosClient();
//        if (cosClient != null) {
//            try {
//                int fileSize = inputStream.available();
//                logger.info("fileName/fileSize:" + fileName + "/" + fileSize);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //multiparFile
//    public boolean updateFile1(Integer id, MultipartFile multipartFile, String fileName) {
//        InputStream fi = null;
//        try {
//            //将multipartFile 转换成inputStreanm类型
//            fi = MultFileToIoFile.multipartToInputStream(multipartFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("文件流转换失败");
//        }
//        return updateLFileReal(id, fi, fileName, multipartFile.getContentType());
//
//    }
//
//    //inputSteam 上传，选择上传格式
//    public Boolean updateFile1(Integer id, InputStream inputStream, String fileName, String fileType) {
//        return updateLFileReal1(id, inputStream, fileName, fileType);
//    }
//    //下载
//    public InputStream getFile(){
//        cosClient = getCosClient();
//        if (cosClient!=null){
//            try {
//            COSObject cosObject =cosClient.getObject(bucketName,fileName);
//            return cosObject.getObjectContent();
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error("下载失败");
//    }finally {
//            cosClient.shutdown();
//            }
//    }
//    return null;
//    }
//    //删除
//    public Boolean deleteFile1(String fileName){
//
//    }
//}
