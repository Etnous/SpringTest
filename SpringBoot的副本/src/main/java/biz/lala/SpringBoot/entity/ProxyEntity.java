package biz.lala.SpringBoot.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author et
 */
@Data
@Accessors(chain = true)
public class ProxyEntity {

    private String proxyIp;

    private Integer proxyPort;

    private String proxyType;

}
