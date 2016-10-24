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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skmbg on 2016/6/21.
 *
 * @author WeiMinSheng
 * @version V1.0
 *          Copyright (c)2016 tyj-版权所有
 * @title com.tiyujia.account
 */
public class AccountAddressTest {
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
    public void insertAccountAddressInfoTest() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/account/receiptAddress/insert");
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", "78602a40776a4bd88c5dbaefe9cee6cf"));
            params.add(new BasicNameValuePair("account_id", "5"));
            params.add(new BasicNameValuePair("name", "樱桃小丸子"));
            params.add(new BasicNameValuePair("phone", "13558971556"));
            params.add(new BasicNameValuePair("zipCode", "610000"));
            params.add(new BasicNameValuePair("content", "四川省成都市成华区成都理工大学"));
            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpPost.releaseConnection();
        }
    }
}
