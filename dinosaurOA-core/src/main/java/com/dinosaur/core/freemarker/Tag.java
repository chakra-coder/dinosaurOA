package com.dinosaur.core.freemarker;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * freemarker标签创建基类
 * <p>创建自定义标签，</p>
 * @Author Alcott Hawk
 * @Date 12/30/2016
 */
public abstract class Tag implements TemplateMethodModelEx{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (!arguments.isEmpty() && arguments != null){
            String param = (String) arguments.get(0);
            if(param!=null){
                if(!param.startsWith("{")){
                    param="{"+param+"}";
                }
                //将json转换成MAP
                ObjectMapper mapper = new ObjectMapper();
                try {
                    Map map = mapper.readValue(param,Map.class);
                    return this.exec(map);
                } catch (IOException e) {
                    logger.error("data deserialize error"+e.getMessage());
                    return this.exec(new HashMap());
                }

            }else{
                return this.exec(new HashMap());
            }
        } else {
            return  this.exec(new HashMap());
        }
    }

    public abstract Object exec(Map map) throws TemplateModelException;
}
