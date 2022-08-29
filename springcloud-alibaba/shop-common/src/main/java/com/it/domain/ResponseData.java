package com.it.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @AllArgsConstructor 全参构造
 * @NoArgsConstructor  无参构造
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData implements Serializable {

    /**
     * 再次自动生成serialVersionUID的值与之前的不同，会导致反序列化失败
     * 所以要定义该值
     */
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
}
