package cn.itcast.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import static sun.misc.PostVMInitHook.run;

public class JobProcessor implements PageProcessor {

    /**
     * site主要对爬虫进行一些配置
     * 如：编码、抓取间隔、超时时间、重复次数等
     * .setCharset("utf-8") 设置编码“utf-8”格式
     * .setTimeOut(10000) 设置超时时间，单位毫秒，最小1000毫秒
     * .setRetrySleepTime(1000) 设置重试间隔时间
     * .setSleepTime(3) 设置重试次数
     */
    private Site site = Site.me()
            .setCharset("utf-8")
            .setTimeOut(10000)
            .setRetrySleepTime(1000)
            .setSleepTime(3);

    public void process(Page page) {
        //page.getHtml() 返回的是一个HTML对象，它实现了Selectable接口
        //page.putField(key,value);key是自己定义名称，value是爬取的数据值
        //css表达式
        page.putField("div",page.getHtml().css("strong").all());
        //XPath解析
        page.putField("div2",page.getHtml().xpath("//*[@id=\"articleContent\"]/div[3]/div"));
        //css表达式+正则表达式 组合筛选
        page.putField("div3",page.getHtml().css("strong").regex(".*《财富》.*").all());
        //处理结果API
        page.putField("div4",page.getHtml().css("strong").regex(".*《财富》.*").get());
        page.putField("div5",page.getHtml().css("strong").regex(".*《财富》.*").toString());
        //获取链接(列表详情页的链接)
        page.putField("div6",page.getHtml().css("div.word-text-con p a").links().regex(".*.htm").all());
        page.addTargetRequests(page.getHtml().css("div.word-text-con p a").links().regex(".*.htm").all());
        //获取详情页的标题
        page.putField("div7",page.getHtml().css("div.title h1").all());

        //添加目标请求(不管添加多少目标请求，请求url只要是一样的，都会被去重过滤器进行去重，只执行一次)
        //去重原理：因为spider在Scheduler()对象中默认用的HashSet去重过滤器
        page.addTargetRequest("http://www.fortunechina.com/fortune500/c/2020-08/10/content_372148.htm");
        page.addTargetRequest("http://www.fortunechina.com/fortune500/c/2020-08/10/content_372148.htm");
        page.addTargetRequest("http://www.fortunechina.com/fortune500/c/2020-08/10/content_372148.htm");
    }

    public Site getSite() {
        return site;
    }

    /**
     * 主函数，执行爬虫
     * @param args
     */
    public static void main(String[] args) {
        //使用Spider创建解析器(将自己写的解析器new进去即可)，并加入解析的地址，执行爬虫。
        //.addPipeline(new FilePipeline("C://")) 将爬取得结果以文件得形式输出保存在自定义路径下
        //.thread(5)指定处理爬虫得线程数量
        //.addPipeline(new FilePipeline("C:\\Users\\Administrator\\Desktop\\image"))
        Spider spider = Spider.create(new JobProcessor())
                .addUrl("http://www.fortunechina.com/fortune500/c/2020-08/10/content_372148.htm")
                .thread(10)
                //调用Scheduler组件；使用队列Scheduler抓取；并设置BloomFilter去重过滤器，指定对1000万数据进行去重操作；
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)));

        //可以在Scheduler对象上打断点看见默认用的HashSet去重过滤器
        //在spider 中调用Scheduler组件；使用队列Scheduler抓取；并设置BloomFilter去重过滤器后再用断点查看，已经改为BloomFilter过滤器
        Scheduler scheduler = spider.getScheduler();

        spider.run();

    }
}
