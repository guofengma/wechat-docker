package com.mihoyo.hk4e.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这个组件提供其他的组件的引用
 */
@Component
public class DataGetter {

    @Autowired
    private ChainService chainService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PoemService poemService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WxCryptService wxCryptService;

    public ChainService getChainService() {
        return chainService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public PoemService getPoemService() {
        return poemService;
    }

    public SourceService getSourceService() {
        return sourceService;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public WxCryptService getWxCryptService() {
        return wxCryptService;
    }
}
