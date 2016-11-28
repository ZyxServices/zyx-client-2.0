package com.zyx.annotation;

import java.lang.annotation.*;

/**
 * Created by wms on 2016/11/28.
 *
 * @author WeiMinSheng
 * @version V2.0
 *          Copyright (c)2016 tyj-版权所有
 * @since 2016/11/28
 */
@Documented//文档
@Retention(RetentionPolicy.RUNTIME)//在运行时可以获取
@Target({ElementType.TYPE, ElementType.METHOD})//作用到类，方法，接口上等
public @interface TokenVerify {
    //枚举类型
    enum VerifyEnum {
        NORMAL, MINE, OTHER
    }

    //实际的值
    VerifyEnum verifyType() default VerifyEnum.MINE;
}
