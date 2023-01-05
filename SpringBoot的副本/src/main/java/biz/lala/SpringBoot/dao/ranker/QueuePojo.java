package biz.lala.SpringBoot.dao.ranker;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("queue")
public class QueuePojo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String content;

    private Integer status;
}
