package com.southwind.repository.impl;

import com.southwind.entity.Student;
import com.southwind.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    /**
     * 私有化 静态 map集合 Map<Long,Student> Long代表map集合的key也是列表的ID，Student代表map集合的value同时也是代表实体的值
     */
    private static Map<Long,Student> studentMap;

    /**
     * 使用静态 及私有化后的map集合，创建构造参数值，模拟数据库结构：查询列表，添加，删除，编辑
     */
    static {
        studentMap = new HashMap<>();
        studentMap.put(1L,new Student(1L,"张三",20));
        studentMap.put(2L,new Student(2L,"里斯",21));
        studentMap.put(3L,new Student(3L,"小明",22));
        studentMap.put(4L,new Student(4L,"小红",15));
    }

    @Override
    public Collection<Student> findAll() {
        return studentMap.values();
    }

    @Override
    public Student findById(long id) {
        return studentMap.get(id);
    }

    @Override
    public void saveOrUpdate(Student student) {
        studentMap.put(student.getId(),student);
    }

    @Override
    public void deleteById(long id) {
        studentMap.remove(id);
    }
}
