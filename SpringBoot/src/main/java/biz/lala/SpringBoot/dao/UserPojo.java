package biz.lala.SpringBoot.dao;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("mp_user")
public class UserPojo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除，逻辑删除请用 @TableLogic
     */
    private Boolean deleted;

    /**
     * 乐观锁版本号，需要乐观锁请用 @Version
     * 支持的字段类型:
     * long,
     * Long,
     * int,
     * Integer,
     * java.util.Date,
     * java.sql.Timestamp,
     * java.time.LocalDateTime
     */
    private Integer version;
}
