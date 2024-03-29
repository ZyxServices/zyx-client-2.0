package com.zyx.controller.system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wms on 2016/11/1.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/1
 */
public final class MsgPool {

    private static class SingletonHolder {
        /**
         * 单例变量 ,static的，在类加载时进行初始化一次，保证线程安全
         */
        private static MsgPool instance = new MsgPool();

        private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    }

    private static MsgPool instance = new MsgPool();

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

    /**
     * 私有化的构造方法，保证外部的类不能通过构造器来实例化。
     */
    private MsgPool() {

    }

    /**
     * 获取单例对象实例
     *
     * @return 单例对象
     */
    public static MsgPool getInstance() {
        return SingletonHolder.instance;
    }

    public static ExecutorService getMsgPool() {
        return fixedThreadPool;
    }
}
