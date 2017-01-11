package com.dinosaur.core.message.impl;

import com.dinosaur.core.message.IMessageFactory;

/**
 * 电子邮件消息
 * @Author Alcott Hawk
 * @Date 1/11/2017
 */
public class EmailMessage implements IMessageFactory{

    @Override
    public boolean sendMessage(String content) {
        // TODO 电子邮件消息
        return false;
    }
}
