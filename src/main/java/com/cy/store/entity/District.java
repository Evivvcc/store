package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/** 省/市/区数据的实体类 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_dict_district")
public class District implements Serializable {
    @TableId
    private Integer id;
    private String parent;
    private String name;
    private String code;

}
