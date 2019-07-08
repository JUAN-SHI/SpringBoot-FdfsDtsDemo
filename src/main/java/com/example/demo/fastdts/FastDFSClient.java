package com.example.demo.fastdts;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import java.io.BufferedOutputStream;
import java.io.IOException;


/**
 * Created by shijuan on 2019/6/29.
 * FastDFS工具类【实现文件上传、下载、删除、查询】
 */
@Slf4j
public class FastDFSClient {

   static TrackerServer trackerServer = null;
   static StorageServer storageServer = null;
    static StorageClient1 storageClient1 = null;
    TrackerClient trackerClient = null;

    static {
        try {
           // String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();(打包到服务器上用该方法加载资源)
            ClientGlobal.init("fdss_client.conf");
            trackerServer= getTrackerServer();
            // 获取存储器客户端对象
            storageClient1 = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            log.error("FastDFS Client Init Fail!",e);
        }
    }


    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) {
        String result=null;
        try {


            // 获取存储器客户端对象
            //storageClient1 = new StorageClient1(trackerServer, storageServer);
            result = storageClient1.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传文件,传fileName
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @return null为失败
     */
    public String uploadFile(String fileName) {
        return uploadFile(fileName, null, null);
    }
    /**
     *
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @param extName 文件的扩展名 如 txt jpg等
     * @return null为失败
     */
    public  String uploadFile(String fileName, String extName) {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件扩展名
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) {
        String result=null;
        try {
            result = storageClient1.upload_file1(fileContent, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 上传文件
     * @param fileContent 文件的字节数组
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    /**
     * 上传文件
     * @param fileContent  文件的字节数组
     * @param extName  文件的扩展名 如 txt  jpg png 等
     * @return null为失败
     */
    public String uploadFile(byte[] fileContent, String extName) {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 文件下载到磁盘
     * @param path 图片路径
     * @param output 输出流 中包含要输出到磁盘的路径
     * @return -1失败,0成功
     */
    public int download_file(String path,BufferedOutputStream output) {
        int result=-1;
        try {
            byte[] b = storageClient1.download_file1(path);
            try{
                if(b != null){
                    output.write(b);
                    result=0;
                }
            }catch (Exception e){} //用户可能取消了下载
            finally {
                if (output != null){
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取文件数组
     * @param path 文件的路径 如group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public byte[] download_bytes(String path) {
        byte[] b=null;
        try {
            // 获取存储器客户端对象
           // storageClient1 = new StorageClient1(trackerServer, storageServer);
            b = storageClient1.download_file1(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 删除文件
     * @param group 组名 如：group1
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     */
    public Integer delete_file(String group ,String storagePath){
        int result=-1;
        try {
            result = storageClient1.delete_file(group, storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return  result;
    }
    /**
     *
     * @param storagePath  文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     * @throws IOException
     * @throws Exception
     */
    public Integer delete_file(String storagePath){
        int result=-1;
        try {
            result = storageClient1.delete_file1(storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 获取远程服务器文件资源信息
     * @param groupName   文件组名 如：group1
     * @param remoteFileName M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public FileInfo getFile(String groupName,String remoteFileName){
        try {
            return storageClient1.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private  static TrackerServer getTrackerServer() throws IOException {
        // 创建跟踪器客户端对象
        TrackerClient trackerClient = new TrackerClient();
        // 获取跟踪器连接
        TrackerServer trackerServer = trackerClient.getConnection();
        return  trackerServer;
    }


    public  String getTrackerUrl() throws IOException {
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
    }

}
