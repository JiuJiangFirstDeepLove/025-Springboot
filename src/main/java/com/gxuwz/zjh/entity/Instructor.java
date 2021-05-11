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
 * 辅导员表
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Data
@Alias("Instructor") //别名
public class Instructor {

    @ApiModelProperty(value = "辅导员工号")
    private String instId;

    @ApiModelProperty(value = "辅导员姓名")
    private String instName;

    @ApiModelProperty(value = "二级学院编号")
    private String depId;

    @ApiModelProperty(value = "联系电话")
    private String telephone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
