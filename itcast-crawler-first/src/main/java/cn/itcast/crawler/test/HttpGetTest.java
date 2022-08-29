package cn.itcast.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetTest {

    public static void main(String[] args) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建HttpGet对象，设置Url访问地址
        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");

        //使用HttpClient发起Get请求，获取respose
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

            //4.解析响应，获取数据
            //判断响应状态码是否是正常200
            if(response.getStatusLine().getStatusCode() == 200 ){
                //获取响应实体 response.getEntity()
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭response响应
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
