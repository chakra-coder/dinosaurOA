package com.dinosaur.module.flowable.modeler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.NativeModelQuery;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import static org.activiti.editor.constants.ModelDataJsonConstants.*;

/**
 * modeler Service
 * @Author Alcott Hawk
 * @Date 12/2/2016
 */
@Service
@Transactional
public class ModelerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 查询一个model通过给定的唯一id
     * @param id model的唯一id
     * @return
     */
    public Model getById(String id){
        return repositoryService.getModel(id);
    }

    /**
     * 获取model的分页列表
     * @param pageSize 页面尺寸
     * @param pageNo 页码
     * @return
     */
    public List<Model> getByPage(int pageSize,int pageNo){
        List<Model> models = this.getModelQuery().orderByCreateTime().desc().listPage(pageNo-1,pageSize);
        return models;
    }

    /**
     * 创建model
     * @param name 模型名称
     * @param key 模型key
     * @param description 描述信息
     * @return 创建的模型的id
     */
    public String create(String name, String key, String description){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("id", "canvas");
            objectNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            objectNode.set("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), objectNode.toString().getBytes("utf-8"));
            return modelData.getId();
        } catch (IOException e) {
            logger.error("创建模型错误："+e.getMessage());
            return null;
        }
    }

    /**
     * 获取一个可供编辑的model的objectNode对象
     * @param id model的唯一id
     * @return
     */
    public ObjectNode edit(String id){
        ObjectNode modelNode = null;
        Model model = this.getById(id);
        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.set("model", editorJsonNode);

            } catch (Exception e) {
                logger.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    /**
     * 保存一个model使用给定的流程定义数据
     * @param modelId model 的唯一id
     * @param values 包含model的数据的map集合
     * @return
     */
    public boolean save(String modelId, MultiValueMap<String,String> values){
        try {
            Model model = repositoryService.getModel(modelId);
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            modelJson.put(MODEL_NAME, values.getFirst("name"));
            modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
            model.setMetaInfo(modelJson.toString());
            model.setName(values.getFirst("name"));
            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));
            InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);
            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);
            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception e){
            logger.error("保存模型出错："+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 通过model的唯一id部署流程
     * @param modelId model的唯一id
     * @return
     */
    public boolean deploy(String modelId){
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);
            return false;
        }
        return true;
    }

    /**
     * 部署流程通过外部文件
     * @param name 流程名称
     * @param description 流程描述
     * @param in 输入流
     * @return <p>部署消息，如果成功则返回true，负责返回false</p>
     */
    public boolean deploy(String name,String description,InputStream in){
        try {
            ZipInputStream zipInputStream = new ZipInputStream(in);
            repositoryService.createDeployment().name(name).addZipInputStream(zipInputStream).deploy();
            return true;
        } catch (Exception e){
            logger.error("deploy error"+e.getMessage());
            return false;
        }

    }

    /**
     * 获取model的本地查询实例
     * @return NativeModelQuery
     */
    private NativeModelQuery getNativeModelQuery(){
        return  repositoryService.createNativeModelQuery();
    }

    /**
     * 获取model的查询实例
     * @return ModelQuery
     */
    private ModelQuery getModelQuery(){
        return repositoryService.createModelQuery();
    }

}
