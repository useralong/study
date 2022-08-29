package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

public class JsoupFirstTest {

    /**
     * 使用Jsoup解析URL
     * @throws Exception
     */
    @Test
    public void testUrl()throws Exception{
        //解析url地址；第一个参数是访问的url,第二个参数是访问时的超时时间（毫秒）
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);
        //使用标签选择器，获取title标签中的内容
        String title = doc.getElementsByTag("title").first().text();
        //打印通过标签选择器获取<title>标签中的内容
        System.out.println("标题testUrl："+title);
    }

    /**
     * 使用字符串解析
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testString()throws Exception{
        //使用工具类读取文件获取字符串
        String content = FileUtils.readFileToString(new File("C:\\Users\\Administrator\\Desktop\\Jsoup.html"), "utf-8");

        //使用Jsoup解析html文件转化的字符串
        Document doc = Jsoup.parse(content);

        //使用标签选择器，获取title标签中的内容
        String title = doc.getElementsByTag("title").first().text();

        //打印通过标签选择器获取<title>标签中的内容
        System.out.println("标题testString："+title);
    }

    /**
     * 使用文件解析
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testFile()throws Exception{
        //使用Jsoup直接解析文件
        Document doc = Jsoup.parse(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\Jsoup.html"),"utf-8");

        //使用标签选择器，获取title标签中的内容
        String title = doc.getElementsByTag("title").first().text();

        //打印通过标签选择器获取<title>标签中的内容
        System.out.println("标题testFile："+title);
    }

    /**
     * 通过Dom方法获取数据
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testDom()throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\Jsoup.html"),"utf-8");

        //获取元素
        //1.根据id查询元素getElementById;(只有通过id获取的是单个，其他都是多个)
        Element id_element = doc.getElementById("bjzx");
        System.out.println("打印通过id获取到的元素内容:"+id_element.text());
        //2.根据标签获取元素getElementsByTag;
        Element tag_element = doc.getElementsByTag("span").first();
        System.out.println("打印通过tag获取到的元素内容:"+tag_element.text());
        //3.根据class获取元素getElementsByClass;
        Element class_element = doc.getElementsByClass("beijing_class").first();
        System.out.println("打印通过class获取到的元素内容:"+class_element.text());
        //4.根据属性获取元素getElementsByAttribute;
        Element attr_element = doc.getElementsByAttribute("status").first();
        System.out.println("打印通过attr获取到的元素内容:"+attr_element.text());
        //4-1.根据属性获取元素getElementsByAttribute;
        Element attr_element_value = doc.getElementsByAttributeValue("href","shanghai.com").first();
        System.out.println("打印通过属性名称+属性值一起获取到的元素内容:"+attr_element_value.text());
    }

    /**
     * 从元素中获取数据
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testData()throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\Jsoup.html"),"utf-8");

        //根据id获取元素
        Element element = doc.getElementById("test");

        String str = "";
        //元素中获取数据
        //1.从元素中获取id
        str = element.id();
        //2.从元素中获取className
        str = element.className();
        //2-1.从元素中获取classNames  (将class中的值进行拆分成集合)
        Set<String> strings = element.classNames();
        for (String string : strings) {
            System.out.println(string);
        }
        //3.从元素中获取attr
        str = element.attr("class");
        //4.从元素中获取attributes
        Attributes attributes = element.attributes();
        System.out.println("获取所有属性："+attributes.toString());
        //5.从元素中获取文本内容
        String text = element.text();
        System.out.println("获取文本内容:"+text);

        System.out.println("从元素中获取数据:"+str);
    }

    /**
     * 通过选择器获取元素
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testSelect()throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\Jsoup.html"),"utf-8");

        //tag:通过标签查找元素，比如：span
        Elements elements_tag = doc.select("span");
        for (Element element : elements_tag) {
            System.out.println("tag:"+element.text());
        }
        //#id:同过ID查找元素，比如：#bjzx
        Elements elements_id = doc.select("#bjzx");
        System.out.println("#ID:"+elements_id.first().text());
        //.class:通过class名称查找元素，比如：.beijing_class
        Elements elements_class = doc.select(".beijing_class");
        System.out.println(".class:"+elements_class.first().text());
        //[attribute]:利用属性查找元素，比如：[status]
        Elements elements_attr = doc.select("[status]");
        System.out.println("[attr]:"+elements_attr.first().text());
        //[attr=value]:利用属性查找元素，比如：[href="shanghai.com"]
        Elements elements_attrValue = doc.select("[href=shanghai.com]");
        System.out.println("[attr=value]:"+elements_attrValue.first().text());
    }

    /**
     * 通过组合 选择器的方式获取元素
     * Jsoup.html文件请见有道笔记 Jsoup笔记文件中的：1-1-3 栏目
     * @throws Exception
     */
    @Test
    public void testSelectOr()throws Exception{
        //解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\Jsoup.html"),"utf-8");

        //el#id:元素+ID,比如：h3#bjzx
        Element element = doc.select("h3#bjzx").first();
        //el.class:元素+class,比如：li.class_a
        element = doc.select("li.class_a").first();
        //el[attr]:元素+属性名,比如： span[status]
        element = doc.select("span[status]").first();
        //任意组合:比如：span[status].c_name
        element = doc.select("span[status].c_name").first();
        //ancestor child: 查找某个元素下,比如：.city_con li 查找"city_con"下的所有li
        Elements elements = doc.select(".city_con li");
        //parent > child: 查找某个(父元素下的 直接 子元素)，比如：
        //.city_con > ul > li 查找 city_con 第一级(直接子元素)的ul，再找所有ul下的第一级li
        elements = doc.select(".city_con > ul > li");
        //parent > *:查找某个父元素下所有直接子元素
        elements = doc.select(".city_con > ul > *");
        elements = doc.select(".city_con > *");
        //打印
        System.out.println("获取的内容是:"+element.text());
        int i = 0;
        for (Element elem : elements) {
            i++;
            System.out.println(i+"遍历结果："+elem.text());
        }
    }
}
