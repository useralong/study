package cn.itcast.crawler.test;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostParamTest {

    public static void main(String[] args) throws Exception {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建HttpGet对象，设置Url访问地址
        HttpPost httpPost = new HttpPost("http://yun.itheima.com/search");

        //声明List集合，封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //设置请求地址是：http://yun.itheima.com/search?keys=Java
        params.add(new BasicNameValuePair("keys","Java"));

        //创建表单的Entity对象,第一个参数就是封装好的参数表单，第二个参数就是编码
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params,"utf-8");

        //设置表单的Entity对象到Post请求中
        httpPost.setEntity(formEntity);

        //使用HttpClient发起Get请求，获取respose
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

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
