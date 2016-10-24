package com.zyx.controller.common;

import com.zyx.constants.Constants;
import com.zyx.utils.FileUploadUtils;
import com.zyx.utils.ImagesVerifyUtils;
import com.zyx.utils.MapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by wms on 2016/6/28.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title CommonController.java
 */
@RestController
@RequestMapping("/v1")
@Api(description = "公共接口API。1、上传一张图片。2、上传多张图片")
public class UploadCommonController {
    // 创建一个线程池
    ExecutorService pool = Executors.newFixedThreadPool(100);
    ExecutorService pool_delete = Executors.newFixedThreadPool(100);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传一张图片", notes = "上传图片到服务器返回图片地址")
    public ModelAndView upload(@RequestPart(name = "avatar") MultipartFile avatar) throws ExecutionException, InterruptedException {
        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (avatar == null) {
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                System.out.println("avatar  :  " + avatar);
                System.out.println("avatar.getName()  :  " + avatar.getName());

                Callable c = new MyCallable(avatar);
                // 执行任务并获取Future对象
                Future f = pool.submit(c);

                String avatarId = f.get().toString();
                Map<String, Object> map = ImagesVerifyUtils.verify(avatarId);
                if (map != null) {
                    jsonView.setAttributesMap(map);
                } else {
                    map = new HashMap<>();
                    map.put(Constants.STATE, Constants.SUCCESS);
                    map.put(Constants.SUCCESS_MSG, "图片上传成功");
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("url", avatarId);
                    map.put(Constants.DATA, map2);
                    jsonView.setAttributesMap(map);
                }
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    @ApiOperation(value = "上传多张图片，图片大的时候慎用", notes = "上传图片到服务器返回图片地址")
    public ModelAndView uploads(@RequestPart(name = "avatars") MultipartFile[] avatars) throws ExecutionException, InterruptedException {

        AbstractView jsonView = new MappingJackson2JsonView();

        if (avatars == null || avatars.length == 0) {
            jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
        } else {
            // 创建一个线程池
            ExecutorService pool = Executors.newFixedThreadPool(9);
            // 创建多个有返回值的任务
            List<Future> list = new ArrayList<>();
            for (int i = 0; i < avatars.length; i++) {
                Callable c = new MyCallable(avatars[i]);
                // 执行任务并获取Future对象
                Future f = pool.submit(c);
                list.add(i, f);
            }
            // 关闭线程池
            pool.shutdown();

            List<String> _temp = new ArrayList<>();

            // 获取所有并发任务的运行结果
            for (Future f : list) {
                // 从Future对象上获取任务的返回值，并输出到控制台
                _temp.add(f.get().toString());
            }

            Map<String, Object> map = new HashMap<>();
            if (_temp.contains("901")) {
                map = MapUtils.buildErrorMap(Constants.AUTH_ERROR_901, "图片大小不能超过5MB");
            } else if (_temp.contains("902")) {
                map = MapUtils.buildErrorMap(Constants.AUTH_ERROR_902, "图片上传失败,请重试");
            } else if (_temp.contains("903")) {
                map = MapUtils.buildErrorMap(Constants.AUTH_ERROR_903, "文件格式错误");
            } else {
                map.put(Constants.STATE, Constants.SUCCESS);
                map.put(Constants.SUCCESS_MSG, "图片上传成功");
                map.put(Constants.DATA, _temp);
            }
            jsonView.setAttributesMap(map);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除文件", notes = "根据文件地址删除服务器文件")
    public ModelAndView deleteImage(@RequestParam(name = "fileUri") String fileUri) throws ExecutionException, InterruptedException {

        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (fileUri == null) {
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                System.out.println("fileUri  :  " + fileUri);

                Callable c = new MyDeleteCallable(fileUri);
                // 执行任务并获取Future对象
                Future f = pool.submit(c);

                String avatarId = f.get().toString();
                jsonView.setAttributesMap(MapUtils.buildSuccessMap(avatarId));
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除文件", notes = "根据文件地址删除服务器文件")
    public ModelAndView deleteImage(@RequestParam(name = "fileUri") String[] fileUri) throws ExecutionException, InterruptedException {

        AbstractView jsonView = new MappingJackson2JsonView();
        try {
            if (fileUri == null) {
                jsonView.setAttributesMap(Constants.MAP_PARAM_MISS);
            } else {
                if (fileUri.length > 10) {
                    jsonView.setAttributesMap(MapUtils.buildErrorMap(Constants.ERROR, "长度限制10"));
                    return new ModelAndView(jsonView);
                }

                System.out.println("fileUri  :  " + fileUri);

                // 创建一个线程池
                ExecutorService pool = Executors.newFixedThreadPool(10);
                // 创建多个有返回值的任务
                List<Future> list = new ArrayList<>();
                for (int i = 0; i < fileUri.length; i++) {
                    Callable c = new MyDeleteCallable(fileUri[i]);
                    // 执行任务并获取Future对象
                    Future f = pool.submit(c);
                    list.add(i, f);
                }
                // 关闭线程池
                pool.shutdown();

                List<String> _temp = new ArrayList<>();
                List<String> _temp_error = new ArrayList<>();
                // 获取所有并发任务的运行结果
                for (Future f : list) {
                    // 从Future对象上获取任务的返回值，并输出到控制台
                    _temp.add(f.get().toString());
                    if (!f.get().toString().equals("0")) {
                        _temp_error.add(fileUri[list.indexOf(f)]);
                    }
                }
                if (_temp_error.size() != 0) {
                    jsonView.setAttributesMap(MapUtils.buildErrorMap(Constants.ERROR, _temp_error.toString()));
                } else {
                    jsonView.setAttributesMap(MapUtils.buildSuccessMap(_temp));
                }
            }
        } catch (Exception e) {
            jsonView.setAttributesMap(Constants.MAP_500);
        }
        return new ModelAndView(jsonView);
    }

}

class MyCallable implements Callable<Object> {
    private MultipartFile file;

    MyCallable(MultipartFile file) {
        this.file = file;
    }

    public Object call() throws Exception {
        return FileUploadUtils.uploadFile(file);
    }
}

class MyDeleteCallable implements Callable<Object> {
    private String fileId;

    MyDeleteCallable(String fileId) {
        this.fileId = fileId;
    }

    public Object call() throws Exception {
        return FileUploadUtils.deleteFile(fileId);
    }
}
