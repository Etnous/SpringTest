package biz.lala.SpringBoot.dao.ranker;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("subject")
public class SubjectPojo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String value;

    private Integer majorId;
}
