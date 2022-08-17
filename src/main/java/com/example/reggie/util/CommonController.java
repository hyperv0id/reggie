package com.example.reggie.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @program: reggie
 * @description: 文件上传功能实现
 * @author: 超级虚空
 * @create: 2022-08-17 17:32
 **/
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value(value = "${reggie.upload-file-store-path}")
    private String BASE_PATH;


    /**
     * 文件上传功能实现
     * @param file 文件对象
     * @return 成功或异常
     */
    @PostMapping("/upload")
    public R<String> uploadFile(MultipartFile file){
        System.out.println("文件上传");
        String[] fileNames = file.getOriginalFilename().split("\\.");
        File dir = new File(BASE_PATH);
        if(!dir.exists()){
            // 目录不存在，创建目录
            dir.mkdir();
        }

        try{
            String targetFilePath = UUID.randomUUID()+"."+fileNames[fileNames.length-1];
            file.transferTo(new File(BASE_PATH+targetFilePath));
            log.info("文件成功上传到——{}", targetFilePath);
            return R.success(targetFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件下载
     * @param name 文件名
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        log.info("开始下载文件：{}", name);
        try {

            // 通过输入流获取文件内容
            FileInputStream fStream = new FileInputStream(BASE_PATH + name);
            response.setContentType("image/jpeg");
            // 输出流，相应文件内容
            OutputStream oStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len = fStream.read(bytes))!=-1){
                oStream.write(bytes, 0, len);
            }

            fStream.close();
            oStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
