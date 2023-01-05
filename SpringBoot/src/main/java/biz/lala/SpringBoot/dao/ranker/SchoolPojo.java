package biz.lala.SpringBoot.dao.ranker;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school")
public class SchoolPojo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String label;

    private String value;

    private Integer provinceId;
}
