package com.dinosaur.plat.modeler.web;

import com.dinosaur.core.util.JsonResultUtil;
import com.dinosaur.core.util.entity.JsonObject;
import com.dinosaur.module.flowable.modeler.ModelerService;
import com.dinosaur.module.system.construction.Construction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * modeler Controller
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Controller
@RequestMapping(value = "/modeler")
public class ModelController implements ModelDataJsonConstants{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelerService modelerService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * model列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("models",modelerService.getByPage(Construction.PAGE_SIZE,Construction.PAGE_NO));
        return "view/model/list";
    }

    /**
     * 创建model
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(){
        return  "view/model/create";
    }

    /**
     * 创建一个新的model定义
     * @param name model的名称
     * @param key model的key
     * @param description model的描述
     * @param request
     * @param response
     */
    // TODO 重复提交处理未完成，同名同key处理未完成
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                       HttpServletRequest request, HttpServletResponse response){
        String id = modelerService.create(name,key,description);
        if (id != null){
            try {
                response.sendRedirect(request.getContextPath() + "/themes/default/modeler/modeler.html?modelId=" + id);
            } catch (IOException e) {
                logger.error("资源访问错误："+e.getMessage());
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public JsonObject upload(String name, String description, MultipartFile file){
        if (!file.isEmpty()&&file != null){
            if (FilenameUtils.getExtension(file.getOriginalFilename()).equals(".zip")){
                InputStream in = null;
                try {
                    in = file.getInputStream();
                } catch (IOException e) {
                    logger.error("deploy error:"+e.getMessage());
                    return JsonResultUtil.getErrorJson("流程部署失败！");
                }
                if (modelerService.deploy(name,description,in)){
                    return JsonResultUtil.getSuccessJson("外部流程部署成功");
                } else {
                    return JsonResultUtil.getErrorJson("流程部署失败！");
                }
            } else {
                return JsonResultUtil.getErrorJson("文件类型不正确，请上传zip格式文件");
            }
        } else {
            return JsonResultUtil.getErrorJson("请选取文件！");
        }
    }

    /**
     * 保存流程定义数据
     * @param modelId 流程定义id
     * @param values 流程定义的数据集合
     */
    @RequestMapping(value="/{modelId}/save", method = RequestMethod.PUT)
    public void save(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values, HttpServletResponse response){
        if (modelerService.save(modelId,values)){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 获取编辑流程图是需要的数据
     * @param modelId 流程定义id
     * @return 流程定义的ObjectNode对象
     */
    @RequestMapping(value="/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ObjectNode edit(@PathVariable String modelId){
        return  modelerService.edit(modelId);
    }

    /**
     * 流程部署，使用model id
     * @param modelId
     * @return
     */
    @RequestMapping(value = "/deploy",method = RequestMethod.GET)
    @ResponseBody
    public JsonObject deploy(String modelId){
        if (modelerService.deploy(modelId)){
            return JsonResultUtil.getSuccessJson("流程部署成功");
        } else {
            return JsonResultUtil.getErrorJson("流程部署失败");
        }
    }

}
