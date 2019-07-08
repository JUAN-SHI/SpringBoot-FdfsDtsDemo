package com.example.demo.fastdts;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传文件后的数据返回对象，便于前台获取数据.
 * Created by shijuan on 2019/6/29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FastResponseData {

        /**
         * * 返回状态编码
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String code;

        /**
         * 返回信息
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String message;

        /**
         * 成功标识
         */
        private boolean success = true;

        /**
         * 文件路径
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String filePath;

        /**
         * 文件名称
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String fileName;

        /**
         * 文件类型
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String fileType;

        /**
         * Http URL
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String httpUrl;

        /**
         * Http Token
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String token;

}
