package com.example.demo;


import com.example.demo.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FdfsApplicationTests {
//	// 本地文件位置
//	String local = "/Users/shijuan/Downloads/0.wav";
//	// 声明跟踪器客户端对象
//	TrackerClient trackerClient = null;
//	// 声明存储器客户端对象
//	StorageClient1 storageClient1 = null;
//	// 声明跟踪器服务对象
//	TrackerServer trackerServer = null;
//	// 声明存储器服务对象
//	StorageServer storageServer = null;

	@Test
	public void testUpload() {
//		try {
////			// 初始化配置文件
////			ClientGlobal.init("fdss_client.conf");
////			// 创建跟踪器客户端对象
////			trackerClient = new TrackerClient();
////			// 获取跟踪器连接
////			trackerServer = trackerClient.getConnection();
////			// 获取存储器客户端对象
////			storageClient1 = new StorageClient1(trackerServer, storageServer);
////			// 上传文件，返回文件标识
////			String index = storageClient1.upload_file1(local, null, null);
////			// 查看标识
//
//			FastDFSClient fastDFSClient = new FastDFSClient();
//			String index = fastDFSClient.uploadFile(local);
//			String url = fastDFSClient.getTrackerUrl();
//			FastResponseData fastResponseData = new FastResponseData();
//
//			fastResponseData.setCode("0");
//			fastResponseData.setSuccess(true);
//			fastResponseData.setFileName(local);
//			fastResponseData.setFileType("wav");
//			fastResponseData.setFilePath(index);
//			fastResponseData.setHttpUrl(fastDFSClient.getTrackerUrl());
//			System.out.println(index);
//			System.out.println(url);
//			System.out.println(fastResponseData.toString());
//			} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
//
//	@Test
//	public void testDownLoad() {
//
//
//
//		// 将数据写入输出流
//		try {
//			FastDFSClient fastDFSClient = new FastDFSClient();
//			// 根据文件标识下载文件
//			byte[] bytes = fastDFSClient.download_bytes("group1/M00/00/13/CoZSdF0XFECAZwcnAAAAvCIulAQ191.txt");
//			System.out.println("bytes"+bytes);
//			String s = new String(bytes);
//			System.out.println(s);
//			ClassPathResource classPathResource = new ClassPathResource("./tts/test.mp3");
//			String path = classPathResource.getFile().getPath();
//			System.out.println(path);
//			IOUtils.write(bytes, new FileOutputStream(path));
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
@Test
public  void testHeader() throws Exception {
	File f = new File("/Users/shijuan/Downloads/test.wav");
 RandomAccessFile rdf = null;
 rdf = new RandomAccessFile(f,"r");

 //System.out.println("audio size: " + Util.toInt(Util.read(rdf, 4, 4))); // 声音尺寸
 //System.out.println("audio format: " + Util.toInt(Util.read(rdf, 20, 2))); // 音频格式 1 = PCM

 //System.out.println("num channels: " + Util.toShort(Util.read(rdf, 22, 2))); // 1 单声道 2 双声道
	String hexString = DatatypeConverter.printHexBinary(Util.read(rdf, 24, 4));
	System.out.println(hexString);
	//System.out.println(Arrays);
	System.out.println(Arrays.toString(Util.read(rdf, 24, 4)));
 System.out.println("sample rate: " + Util.toInt(Util.read(rdf, 24, 4))); // 采样率、音频采样级别 8000 = 8KHz

 //System.out.println("byte rate: " + Util.toInt(Util.read(rdf, 28, 4))); // 每秒波形的数据量

 //System.out.println("block align: " + Util.toShort(Util.read(rdf, 32, 2))); // 采样帧的大小
 //System.out.println("bits per sample: " + Util.toShort(Util.read(rdf, 34, 2))); // 采样位数
 rdf.close();
 }








}
