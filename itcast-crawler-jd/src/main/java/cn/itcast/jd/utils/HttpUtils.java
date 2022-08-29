package cn.itcast.jd.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
public class HttpUtils {

    /**
     * 声明HttpClient连接池
     */
    private PoolingHttpClientConnectionManager cm;

    /**
     * 声明连接池的构造方法
     * 重新new一个新的HttpClient连接池
     */
    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();

        //设置连接池最大连接数
        cm.setMaxTotal(100);
        //设置连接池每个主机最大连接数
        cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     * @param url
     * @return
     */
    public String doGetHtml(String url){
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //设置HttpClient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息配置
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果
            if(response.getStatusLine().getStatusCode() == 200){
                //判断响应体Entity是否不为空，如果不为空就可使用EntityUtils
                if(response.getEntity() != null){
                    String content = EntityUtils.toString(response.getEntity());
                    return content;
                }
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
            ;
        }
        return "";
    }

    /**
     * 根据图片地址下载图片
     * @param url
     * @return
     */
    public String doGetImage(String url){
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //设置HttpClient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息配置
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果
            if(response.getStatusLine().getStatusCode() == 200){
                //判断响应体Entity是否不为空
                if(response.getEntity() != null){
                    //下载图片
                    //获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，图片重命名
                    String picName = UUID.randomUUID().toString() + extName;
                    //下载图片
                    //声明OutputStream 输出流,设置到输出流输出的文件地址
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\image"+picName));
                    //将响应体(图片),写出到,提前通过文件输出流设置好的本地文件夹中
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;
                }
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
            ;
        }
        return "";
    }

    /**
     * 设置请求信息配置
     * @return
     */
    private RequestConfig getConfig() {
        //.setConnectTimeout(1000)设置连接的最长时间
        //.setConnectionRequestTimeout(500)设置获取连接的最长时间
        //.setSocketTimeout(10000)设置数据传输的最长时间
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)
                .build();
        return config;
    }
}
