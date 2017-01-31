package com.dinosaur.core.util;

import com.dinosaur.core.context.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具类
 * @Author Alcott Hawk
 * @Date 1/30/2017
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 上传图片
     * @param multipartFile
     * @param subFolder 目标文件路径，请求路径为服务器相对路径入<b>/themes/default/static/image/</b>
     * @return
     * @throws IOException
     */
    public static String uploadImg(MultipartFile multipartFile, String subFolder) throws IOException {
        subFolder = subFolder + multipartFile.getOriginalFilename();
        String filePath = ApplicationContextHolder.getServletContext().getRealPath("/")+subFolder;
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            return subFolder;
        } catch (IOException e) {
            logger.error("文件写入异常："+e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

}
