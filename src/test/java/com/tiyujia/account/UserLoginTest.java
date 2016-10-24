package com.tiyujia.account;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by skmbg on 2016/6/20.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.tiyujia.account
 */

public class UserLoginTest {
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
//        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/account/login");
//        try {
//            CloseableHttpClient client = HttpClients.createDefault();
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("phone", "18502826672"));
//            params.add(new BasicNameValuePair("pwd", "7788119"));
//            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity( params, "UTF-8");
//            httpPost.setEntity(httpEntity );
//            CloseableHttpResponse response = client.execute(httpPost);
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        } finally {
//            httpPost.releaseConnection();
//        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(650697901065l);
        System.out.println(calendar.getTime());
    }
}
