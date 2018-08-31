package com.jnshu.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.jnshu.model.Login;
import com.jnshu.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;


public class AliyunOSSAPI {
    private static Logger logger = LoggerFactory.getLogger(AliyunOSSAPI.class);
    private OSSClient ossClient;
    private Boolean flag = false;
    private String bucketName ;
    private String fileName;
    private String accessKeyId ;
    private String accessKeySecret ;
    private String endpoint ;


    @Autowired
    LoginService loginService;

    public OSSClient getOssClient() {
        try {
            return new OSSClient(endpoint, accessKeyId, accessKeySecret);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("OSSClient 初始化失败");
            return null;
        }

    }
    public boolean creatbucket(String bucketName) {
        ossClient = getOssClient();
        if (ossClient != null) {
            try {
                Bucket bucket = ossClient.createBucket(bucketName);
                flag = bucketName.equals(bucket.getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        }
        return flag;
    }
    public Boolean deleteBucket(String bucketName) {
        ossClient = getOssClient();
        if (ossClient != null) {
            try {
                ossClient.deleteBucket(bucketName);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ossClient.shutdown();
            }
        }
        return flag;
    }
    //multiparFile
    public boolean updateFile(Integer id, MultipartFile multipartFile, String fileName) {
        InputStream fi = null;
        try {
            //将multipartFile 转换成inputStreanm类型
            fi = MultFileToIoFile.multipartToInputStream(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件流转换失败");
        }
        return updateLFileReal(id, fi, fileName, multipartFile.getContentType());
    }
    //inputSteam 上传，选择上传格式
    public Boolean updateFile(Integer id, InputStream inputStream, String fileName, String fileType) {
        return updateLFileReal(id, inputStream, fileName, fileType);
    }
    //上传图片
    private Boolean updateLFileReal(Integer id, InputStream inputStream, String fileName, String fileType) {
        ossClient = getOssClient();
        if (ossClient!=null){
            try{
                int fileSize =inputStream.available();
                logger.info("fileName/fileSize:"+fileName+"/"+fileSize);
                //ObjectMetadate 是对上传对象的描述
                ObjectMetadata metadata =new ObjectMetadata();
                //设置文件长度，必选参数
                metadata.setContentLength(fileSize);
                //可选参数
                metadata.setCacheControl("no-cache");
                metadata.setHeader("Pragma","no-cache");
                metadata.setContentEncoding("UTF-8");
                //文件类型
                metadata.setContentType(fileType);
                metadata.setContentDisposition("fileName/fileSize:"+fileName+"/"+fileSize);
                //上传文件
                PutObjectResult putObjectResult =ossClient.putObject(bucketName,fileName,inputStream,metadata);
                //id等于null为文件复制，暂时不入库，改造为入库只存文件名不存地址，前端接受图片再做拼接，方便后期，无需修改数据库
                if (id!=null){
                    if(putObjectResult!=null){
                        fileName = fileName +fileSize;
                        logger.info("写入数据库的名字是："+fileName);
                        Login login = loginService.findIdLogin(id);
                        login.setFileName(fileName);
                        //写入数据库
                        logger.info("这里面有啥"+login);
                        loginService.updateLogin(login);
                        logger.info("写入数据库成功");
                        return true;
                    }
                }
                logger.info(putObjectResult.getETag());
                return false;
            }catch (Exception e){
                e.printStackTrace();
                logger.error("上传失败");
                return false;
            }finally {
                ossClient.shutdown();
            }
        }
        return false;
    }
    //下载
    public InputStream getFile(){
        ossClient = getOssClient();
        if (ossClient!=null){
            try {
                OSSObject ossObject =ossClient.getObject(bucketName,fileName);
                return  ossObject.getObjectContent();
            }catch (Exception e){
                e.printStackTrace();
                logger.error("下载失败");
            }finally {
                ossClient.shutdown();
            }
        }
        return null;
    }
    //删除
    public Boolean deleteFile(String fileName){
        ossClient = getOssClient();
        try {
            ossClient.deleteObject(bucketName,fileName);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除错误");
        }finally {
            ossClient.shutdown();
        }
        return false;
    }
    //获取链接
    public  String  getUrl(String fileName){
        StringBuffer sb = new StringBuffer();
        sb.append("http://");
        sb.append(bucketName);
        sb.append(".");
        sb.append(endpoint);
        sb.append("/");
        sb.append(fileName);
        //压缩图片，宽高为200，按长边优先，拼接在url后
        sb.append("?x-oss-process=image/resize,m_lfit,h_200,w_200");
        return sb.toString();
    }
    //获取连接
    public  String getUrl1(String fileName){
        //设置url过期时间为1小时
        Date expiration = new Date(new Date().getTime()+3600*1000);
        //生成URL
        URL url = ossClient.generatePresignedUrl(bucketName,fileName,expiration);
        return url.toString();
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}


