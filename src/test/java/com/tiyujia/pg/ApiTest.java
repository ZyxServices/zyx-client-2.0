package com.tiyujia.pg;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.junit.Test;

import java.io.IOException;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.tiyujia.pg
 * Create by XiaoWei on 2016/6/17
 */
public class ApiTest {

    @Test
    public void addCircle() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/circle/add");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "-1");
        postMethod.addParameter("title", "1");
        postMethod.addParameter("createId", "2");
        postMethod.addParameter("state", "0");
        postMethod.addParameter("type", "0");
        postMethod.addParameter("details", "3");
        postMethod.addParameter("headImgUrl", "4");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void meeting() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/circle/meetting");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "-1");
        postMethod.addParameter("circleId", "1");
        postMethod.addParameter("accountId", "2");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void addCern() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/cern/insert");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "1");
        postMethod.addParameter("userId", "1");
        postMethod.addParameter("type", "2");
        postMethod.addParameter("cernTitle", "我是个人1");
        postMethod.addParameter("content", "我是个人1内容");
        postMethod.addParameter("imgUrl", "5");
        postMethod.addParameter("videoUrl", "6");
        postMethod.addParameter("visible", "7");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void zan() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/zan/add");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "-1");
        postMethod.addParameter("bodyId", "1");
        postMethod.addParameter("bodyType", "2");
        postMethod.addParameter("accountId", "3");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void addMyConcern() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/cern/add");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "-1");
        postMethod.addParameter("concernId", "2");
        postMethod.addParameter("concernType", "2");
        postMethod.addParameter("accountId", "3");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void circleList() throws IOException {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8080/v1/circle/list/-1/10");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void random() throws IOException {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8080/v1/cern/random/-1/10");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void starRandom() throws IOException {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:8080/v1/cern/starRandom/-1/10");
        client.executeMethod(getMethod);
        //打印服务器返回的状态
        System.out.println(getMethod.getStatusLine());
        //打印返回的信息
        System.out.println(getMethod.getResponseBodyAsString());
        //释放连接
        getMethod.releaseConnection();
    }

    @Test
    public void addCircleItem() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/circleItem/add");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "1");
        postMethod.addParameter("circle_id", "1");
        postMethod.addParameter("create_id", "2");
        postMethod.addParameter("title", "我是个人1");
        postMethod.addParameter("content", "我是个人1内容");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void setMaster() throws IOException {
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod("http://localhost:8080/v1/circle/setMaster");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        postMethod.addParameter("token", "-1");
        postMethod.addParameter("circle_id", "21");
        postMethod.addParameter("master_id", "2");
        postMethod.addParameter("account_id", "3");
        client.executeMethod(postMethod);
        //打印服务器返回的状态
        System.out.println(postMethod.getStatusLine());
        //打印返回的信息
        System.out.println(postMethod.getResponseBodyAsString());
        //释放连接
        postMethod.releaseConnection();
    }

    @Test
    public void deleteCircle() throws IOException {
        HttpClient client = new HttpClient();
        DeleteMethod deleteMethod = new DeleteMethod("http://localhost:8080/v1/circle/delete/-1/21");
        client.executeMethod(deleteMethod);
        //打印服务器返回的状态
        System.out.println(deleteMethod.getStatusLine());
        //打印返回的信息
        System.out.println(deleteMethod.getResponseBodyAsString());
        //释放连接
        deleteMethod.releaseConnection();
    }

}
