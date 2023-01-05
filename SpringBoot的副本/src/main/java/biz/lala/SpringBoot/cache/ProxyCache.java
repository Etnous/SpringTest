package biz.lala.SpringBoot.cache;

import biz.lala.SpringBoot.entity.ProxyEntity;
import biz.lala.SpringBoot.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ProxyCache {

    private  String url = "https://dps.kdlapi.com/api/getdps/?secret_id=ouvt85jj9dvahoa0eyvx&num=20&signature=opw03tm6lve7amoennfj5ttybm&pt=1&format=json&sep=1";

    private  List<ProxyEntity> proxyEntityList = new ArrayList<>();


    public ProxyCache() {
        try {
            String s = HttpUtil.httpGetRequest(url, null, null);
            String proStr = JSON.parseObject(s).getJSONObject("data").getString("proxy_list");
            List<String> s1 = JSONObject.parseArray(proStr, String.class);
            s1.forEach( proxyStr -> {
                String[] split = proxyStr.split(":");
                proxyEntityList.add(new ProxyEntity()
                        .setProxyIp(split[0])
                        .setProxyPort(Integer.valueOf(split[1])).setProxyType("http"));
            });

            System.out.println(proxyEntityList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public  ProxyEntity getProxy() {
        int size = proxyEntityList.size();
        return proxyEntityList.get(new Random().nextInt(size));
    }

    static {

    }
}
