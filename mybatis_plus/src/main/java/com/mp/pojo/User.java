package com.mp.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // 引入mybatis-plus的主键注解
    // AUTO 主键自增 @TableId(type = IdType.AUTO)
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //字段添加填充内容，引入mybatis-plus的填充注解
    @TableField(fill = FieldFill.INSERT)  // INSERT 插入的时候操作
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)  // INSERT_UPDATE 插入、更新的时候操作
    private Date updateTime;

    @Version //引入mybatis-plus的乐观锁 Version 注解
    private Integer version;

    @TableLogic //引入mybatis-plus的逻辑删除注解
    private Integer deleted;

}
