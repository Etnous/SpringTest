package biz.lala.SpringBoot.dao.ranker;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("school")
public class SchoolPojo {

    @TableId
    private Long id;

    private String code;

    private String name;

    private Long provinceId;
}
