package com.lwk.myspring.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author kai
 * @date 2020-09-02 23:06
 */
@RestController
@Slf4j
public class FileController {

    @GetMapping("/file/upload")
    public JSONObject upload() {
        JSONObject result = new JSONObject();
        result.put("msg", "success");
        return result;
    }

    @PostMapping("/file/upload")
    public JSONObject queryHive1(@RequestParam("file") MultipartFile file) {
        // curl -F "file=@C:\Users\kai\Desktop\abc-test1.zip" http://localhost:8080/file/upload
        JSONObject result = new JSONObject();
        if (file.isEmpty()) {
            log.info("file is empty");
            result.put("msg", "上传内容为空");
        }
        // 解析zip文件
        String zip = readZip(file);

        // 保存zip
        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/kai/Desktop/test/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info("文件已保存该目录: {}", dest.getName());
            result.put("msg", "上传成功");
        } catch (IOException e) {
            log.info("upload failed: {}", e.getMessage());
            result.put("msg", "上传失败");
        }
        result.put("zip", zip);
        return result;
    }

    /**
     * 解析zip文件
     * @param file 压缩包
     * @return 处理结果
     */
    private String readZip(MultipartFile file) {
        String result = "ok";
        try {
            InputStream in = file.getInputStream();
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(in), Charset.forName("GBK"));
            ZipEntry ze;
            while((ze = zin.getNextEntry()) != null){
                String fileName = ze.getName();
                if (fileName != null && fileName.endsWith("txt")) {
                    if (fileName.contains("/")) {
                        fileName = fileName.split("/")[1];
                    }
                    log.info("文件名称：" + fileName + " 文件大小：" + ze.getSize() + " bytes");
                    log.info("文件内容：");
                    //读取
                    BufferedReader br = new BufferedReader(new InputStreamReader(zin, Charset.forName("UTF-8")));
                    String line;
                    //内容不为空，输出
                    while ((line = br.readLine()) != null) {
                        log.info("{}: {}", fileName, line);
                    }
                } else {
                    log.info("OTH:{}", ze.toString());
                }
                log.info("============");
            }
            zin.closeEntry();
        } catch (FileNotFoundException e) {
            result = "文件不存在";
        } catch (IOException e) {
            result = "文件解析异常" + e.getMessage();
        }
        return result;
    }
}
