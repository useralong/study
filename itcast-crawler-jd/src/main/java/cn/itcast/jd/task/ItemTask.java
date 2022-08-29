package cn.itcast.jd.task;

import cn.itcast.jd.pojo.Item;
import cn.itcast.jd.service.ItemService;
import cn.itcast.jd.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    //设置ObjectMapper工具类实例
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 定时执行：当下载任务完成后，间隔多长时间进行下次任务
     * 100 * 1000 = 100秒执行一次
     */
    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        //声明需要解析的初始地址(将page参数移动到url的最后,方面自动给page赋值)
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&pvid=45f546d344d44897b15b03a9fa497b5b"
                + "&s=57&click=0&page=";
        //按照页面对手机的搜索结果，进行分页遍历解析，获取内容
        //根据规则从第1页开始，每间隔1页，page参数值从1开始叠加增2;限制循环page值在10以内进行页面爬取
        for (int i = 1; i < 10; i = i + 2) {
            String html = httpUtils.doGetHtml(url + i);
            //解析页面，获取商品数据，并存储
            parse(html);
        }
        System.out.println("手机数据抓取完成！(目前京东网站抓取数据被限制，需要登陆2021-06-26日)");
    }

    /**
     * 解析页面，获取商品数据，并存储
     *
     * @param html
     */
    private void parse(String html) throws Exception {
        //解析html获取Document对象
        Document doc = Jsoup.parse(html);
        //获取spu集合
        Elements spuEles = doc.select("div#J_goodsList > ul > li");
        //循环获取单个spu(整个商品，如iPhone8)
        for (Element spuEle : spuEles) {
            //通过属性选择器，获取spu
            Long spu = Long.parseLong(spuEle.attr("data-spu"));
            //获取sku信息
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle :
                    skuEles) {
                //通过属性选择获取dom，然后通过attr获取sku属性的值，sku(商品中的属性，如红色的iPhone8)
                Long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));
                //根据sku查询商品数据
                Item item = new Item();
                item.setSku(sku);
                List<Item> list = itemService.findAll(item);
                if (list.size() > 0) {
                    //如果该sku商品属性存在，进行下一个循环，该商品不进行保存
                    continue;
                }
                //设置商品spu
                item.setSpu(spu);
                //获取商品详情的url(是根据sku商品属性的详情地址保存的)
                String skuItemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(skuItemUrl);
                //获取商品的图片
                String picUrl = "https:"+skuEle.select("img[data-sku]").first().attr("src");
                //将图片路径中的n7替换成n1使图片变大
                picUrl = picUrl.replace("/n7/","/n1/");
                //下载图片，重命名，保存本地，存储本地路径
                String imageUrl = httpUtils.doGetImage(picUrl);
                //设置商品的图片
                item.setPic(imageUrl);
                //根据sku查询商品价格(返回json集合字符串)
                String priceJsonStr = httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                //通过ObjectMapper工具类获取json集合字符串的第一个元素中的key=p的值(价格)
                Double price = MAPPER.readTree(priceJsonStr).get(0).get("p").asDouble();
                //设置商品的价格
                item.setPrice(price);
                //通过商品的详情页进入商品详情页获取商品标题
                String infoHtml = httpUtils.doGetHtml(skuItemUrl);
                Document itemDoc = Jsoup.parse(infoHtml);
                String title = itemDoc.select("div.sku-name").first().text();
                //设置商品的标题
                item.setTitle(title);
                //
                item.setCreatetime(new Date());
                item.setUpdatetime(new Date());
                //保存商品数据到数据库中
                itemService.save(item);
            }
        }

    }
}
