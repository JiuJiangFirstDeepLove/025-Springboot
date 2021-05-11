package com.gxuwz.zjh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * <p>
 * 二级学院表
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Data
@Alias("Department") //别名
public class Department {

    @ApiModelProperty(value = "二级学院编号")
    private String depId;

    @ApiModelProperty(value = "二级学院名称")
    private String depName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
