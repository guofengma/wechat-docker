package com.mihoyo.hk4e.wechat.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Log {
    public static Logger errorLog = LoggerFactory.getLogger("com.mihoyo.hk4e.wechat.error");
    public static Logger requestLog = LoggerFactory.getLogger("com.mihoyo.hk4e.wechat.request");
}