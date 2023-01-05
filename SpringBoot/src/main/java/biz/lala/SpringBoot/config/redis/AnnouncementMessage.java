package biz.lala.SpringBoot.config.redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnnouncementMessage implements Serializable {

    private static final long serialVersionUID = 8632296967087444509L;

    /*** 内容 */
    private String content;

    private Integer status;
}
