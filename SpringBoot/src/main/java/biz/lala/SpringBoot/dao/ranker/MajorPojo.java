package biz.lala.SpringBoot.dao.ranker;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("major")
public class MajorPojo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String value;

    private String label;

    private Integer collegeId;
}
