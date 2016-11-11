package com.zyx.rpc.system;

import java.util.List;
import com.zyx.entity.system.Version;

/**
 * Created by MrDeng on 2016/11/10.
 * App版本管理
 */
public interface VersionFacade {
    /**
     * 获取最新版本
     * @param type 正式发布后该参数固定为1-正式发布版本
     * @param platform
     * @return
     */
    public Version getNewVersion(Integer type, Integer platform);

    /**
     * 获取历史版本 包括最新版本
     * @param type 正式发布后该参数固定为1-正式发布版本
     * @param platform
     * @return
     */
    public List<Version> getHistoryVersion(Integer type, Integer platform);
}
