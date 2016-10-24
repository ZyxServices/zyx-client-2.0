package com.tiyujia.account;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skmbg on 2016/6/22.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.tiyujia.account
 */
public class UserRegisterTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMethod1() throws IOException {
//        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/account/register");
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpClient client1 = new HttpClient();
            PostMethod postMethod = new PostMethod("http://localhost:8080/v1/account/register");
            postMethod.setParameter("phone", "18502826672");
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("phone", "18502826672"));
//            params.add(new BasicNameValuePair("pwd", "7788119"));
//            params.add(new BasicNameValuePair("nickname", "我爱中国"));
//            MultipartEntity entity = new MultipartEntity();
//            entity.addPart("param1", new StringBody("中国", Charset.forName("UTF-8")));
//            entity.addPart("param2", new StringBody("value2", Charset.forName("UTF-8")));
//            entity.addPart("param3", new FileBody(new File("C:\\1.txt")));
            FilePart fp = new FilePart("avatar", new File("E:\\123.jpg"));
            Part[] parts = {fp};
            RequestEntity requestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(requestEntity);
            postMethod.setQueryString(new org.apache.commons.httpclient.NameValuePair[]{new org.apache.commons.httpclient.NameValuePair("phone", "18502826672"), new org.apache.commons.httpclient.NameValuePair("pwd", "7788119"), new org.apache.commons.httpclient.NameValuePair("nickname", "我爱中国")});
//            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
//            httpPost.setEntity(httpEntity);
//            CloseableHttpResponse response = client1.executeMethod(postMethod);
            int status = client1.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
            } else {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }
            }
        } finally {
//            httpPost.releaseConnection();
        }
    }
}
