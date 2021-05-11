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
 * 班级表
 * </p>
 *
 * @author zhangjiahui
 * @since 2021-05-05
 */
@Data
@Alias("Classes") //别名
public class Classes {

    @ApiModelProperty(value = "班级编号")
    private String classId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "二级学院编号")
    private String depId;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "年级")
    private String grade;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
