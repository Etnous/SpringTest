package biz.lala.SpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author et
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ProxyEntity {

    private String proxyIp;

    private Integer proxyPort;

    private String proxyType;

}
