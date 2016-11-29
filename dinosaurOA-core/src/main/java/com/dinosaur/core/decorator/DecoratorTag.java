package com.dinosaur.core.decorator;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * 自定义页面装饰标签
 * @Author Alcott Hawk
 * @Date 11/28/2016
 */
public class DecoratorTag implements TagRuleBundle{
    @Override
    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        defaultState.addRule("css",new ExportTagToContentRule(siteMeshContext,contentProperty.getChild("css"),false));
        defaultState.addRule("js",new ExportTagToContentRule(siteMeshContext,contentProperty.getChild("js"),false));
    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
