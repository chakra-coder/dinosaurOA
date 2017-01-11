package com.dinosaur.core.message.impl;

import com.dinosaur.core.message.IMessageFactory;

/**
 * 平台消息
 * @Author Alcott Hawk
 * @Date 1/11/2017
 */
public class PlatMessage implements IMessageFactory{

    @Override
    public boolean sendMessage(String content) {
        // TODO 平台消息
        return false;
    }
}
