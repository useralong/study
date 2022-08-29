package com.southwind.controller;

import com.southwind.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class RestHandler {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        /**
         * 调用服务提供者接口时，可以直接return返回 可以使用restTemplate.getForEntity()获取接口数据  [getForEntity 通过get请求获取Entity]
         * restTemplate.getForEntity(Url url,Class<T> responseType)
         * url 是你想调用服务提供者接口的路径
         * responseType  是你想要获取数据后返回的类型
         * 以上步骤写完后，restTemplate.getForEntity 会报错 红线
         * 原因：getForEntity方法 底层的返回类型是 ResponseEntity<T>  定义的返回类型不一致
         * 解决：restTemplate.getForEntity("url",Collection.class).getBody()  使用.getBody()
         */
        return  restTemplate.getForEntity("http://localhost:8010/student/findAll",Collection.class).getBody();
    }

    @GetMapping("/findAll2")
    public Collection<Student> findAll2(){
        /**
         * restTemplate.getForObject(Url url,Class<T> responseType)
         * 参数如上findAll方法
         * getForObject()方法就不用.getBody()方法也不会报错
         * 因为getForObject()的返回类型就是集合 也可以是 T 泛型
         */
        return  restTemplate.getForObject("http://localhost:8010/student/findAll",Collection.class);
    }

    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") long id){
        return  restTemplate.getForEntity("http://localhost:8010/student/findById/{id}",Student.class,id).getBody();
    }

    @GetMapping("/findById2/{id}")
    public Student findById2(@PathVariable("id") long id){
        return  restTemplate.getForObject("http://localhost:8010/student/findById/{id}",Student.class,id);
    }

    @PostMapping("/save")
    public void save(@RequestBody Student student){
        /**
         * 当前添加没有返回类型，所以不需要返回类型，只需要按照方法传参即可
         * 接口路径，post的参数，返回类型没有 null 即可
         */
        restTemplate.postForEntity("http://localhost:8010/student/save",student,null).getBody();
    }

    @PostMapping("/save2")
    public void save2(@RequestBody Student student){
        restTemplate.postForObject("http://localhost:8010/student/save",student,null);
    }

    @PutMapping("/update")
    public void update(@RequestBody Student student){
        restTemplate.put("http://localhost:8010/student/update",student);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") long id){
        restTemplate.delete("http://localhost:8010/student/deleteById/{id}",id);
    }

}
