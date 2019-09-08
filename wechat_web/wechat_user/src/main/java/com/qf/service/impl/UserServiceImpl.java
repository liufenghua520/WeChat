package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.PinyinUtils;
import com.qf.util.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/30 10:23
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * -1 表示用户名存在
     * -2 其他的错误
     * 1 注册成功
     * @param user
     * @return
     */
    @Override
    public int registerUser(User user) {

        //确保用户名唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        Integer count = userMapper.selectCount(queryWrapper);

        if(count > 0){
            //用户名已经存在
            return -1;
        }

        //TODO 生成用户的二维码名片
        File file = null;
        try {
            //生成二维码的内容
            String content = "txcc:" + user.getUsername();
            //创建一个临时的File对象
            file = File.createTempFile("qrcode_file_" + user.getUsername(), "png");
            //生成二维码
            boolean flag = QRCodeUtils.createQRCode(file, content);
            if(flag){
                //将file对象上传到fastDFS
                StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(new FileInputStream(file), file.length(), "png", null);
                //将二维码的图片路径写入数据库
                user.setCard(storePath.getFullPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(file.exists()){
                file.delete();
            }
        }


        //TODO 将用户的 昵称转换成 拼音
        String pinyin = PinyinUtils.str2Pinyin(user.getNickname());
        user.setPinyin(pinyin);

        //进行注册
        int result = userMapper.insert(user);
        return result;
    }

    /**
     * 登录
     * @param username
     * @return
     */
    @Override
    public User queryByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateHeader(String header, String headerCrm, Integer uid) {

        User user = userMapper.selectById(uid);
        if(user != null){
            user.setHeader(header);
            user.setHeaderCrm(headerCrm);
            return userMapper.updateById(user);
        }

        return 0;
    }

    @Override
    public User queryById(Integer uid) {
        return userMapper.selectById(uid);
    }
}
