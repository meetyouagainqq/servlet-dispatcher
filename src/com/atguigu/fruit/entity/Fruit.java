package com.atguigu.fruit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月27日 22:21
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fruit {
    private Integer fid;
    private String fname;
    private Integer price;
    private Integer fcount;
    private String remark;
}
