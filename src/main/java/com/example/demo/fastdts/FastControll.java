package com.example.demo.fastdts;

import com.example.demo.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shijuan on 2019/6/29.
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FastControll {


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody//new annotation since 4.3
    public String singleFileUpload(MultipartFile file, String appkey)      {

        FastResponseData fastResponseData = new FastResponseData();
        FastDFSClient fastDFSClient = new FastDFSClient();
        String json =null;
        try {

            System.out.println(appkey);

             String path = fastDFSClient.uploadFile(file.getBytes(),"jpg");
             System.out.println("path="+path);
            System.out.println(file.getBytes());
           fastResponseData.setCode("0");
           fastResponseData.setSuccess(true);
           fastResponseData.setFileName(file.getName());
           fastResponseData.setFileType("jpg");
           fastResponseData.setFilePath(path);
           fastResponseData.setHttpUrl(fastDFSClient.getTrackerUrl());

            json = Util.getJsonFromBean(fastResponseData);

        } catch (Exception e) {
            log.error("upload file failed",e);
        }
        return json;
    }







}
