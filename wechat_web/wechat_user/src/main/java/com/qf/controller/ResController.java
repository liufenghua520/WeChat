package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.UUID;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/30 15:12
 */
@RestController
@RequestMapping("/res")
public class ResController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private IUserService userService;

    /**
     * 头像上传
     * @return
     */
    @RequestMapping("/uploaderHeader")
    public Map<String, String> uploaderHeader(MultipartFile file, Integer uid){
        //System.out.println("接收到上传的文件信息了！" + file.getOriginalFilename());

        Map<String, String> map = new HashMap();

        //获得上传的文件名
        String filename = UUID.randomUUID().toString();

        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), "PNG", null);

            //获得上传的路径
            String uploadPath = storePath.getFullPath();
            String uploadCrmPath = uploadPath.replace(".", "_80x80.");
            map.put("header", uploadPath);
            map.put("headerCrm", uploadCrmPath);

            //更新数据库
            userService.updateHeader(uploadPath, uploadCrmPath, uid);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
