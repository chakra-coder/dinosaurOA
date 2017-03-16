package com.dinosaur.core.controller;

import com.dinosaur.core.util.FileUtil;
import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件上传Controller
 * @Author Alcott Hawk
 * @Date 2/4/2017
 */
@Controller
@RequestMapping(value = "/uploadFactory")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject upload(HttpServletRequest request){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)){
            MultipartRequest multipartRequest = (MultipartRequest) request;
            Iterator<String> names = multipartRequest.getFileNames();
            List<String> urls = null;
            while (names.hasNext()){
                MultipartFile file = multipartRequest.getFile(names.next());
                if (null != file && StringUtils.isNotBlank(file.getOriginalFilename())){
                    urls = new LinkedList<>();
                    try {
                        urls.add(FileUtil.upload(file,"/themes/default/static/upload/"));
                    } catch (IOException e) {
                        logger.error("文件上传失败："+e.getMessage());
                        return JsonResultUtil.getErrorJson("文件上传失败："+e.getMessage());
                    }
                }
            }
            return JsonResultUtil.getObjectJson(urls);
        }
        return JsonResultUtil.getErrorJson("请求不包含文件");
    }

}
