package biz.lala.SpringBoot.cache;

import biz.lala.SpringBoot.entity.ProxyEntity;
import biz.lala.SpringBoot.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProxyCache {

    private  String url = "https://dps.kdlapi.com/api/getdps/?secret_id=olcegu0xt1n268kersn0&num=20&signature=onrv21n8ksj46013r217djdr9d&pt=1&format=json&sep=1";


    private Map<String, ProxyEntity> proxyMapList = new HashMap<>();

    public ProxyCache() {
        try {
            getIP();
            log.info("初始化代理池成功");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private synchronized void getIP() throws Exception {
        String s = HttpUtil.httpGetRequest(url, null, null);
        String proStr = JSON.parseObject(s).getJSONObject("data").getString("proxy_list");
        List<String> s1 = JSONObject.parseArray(proStr, String.class);
        s1.forEach( proxyStr -> {
            String[] split = proxyStr.split(":");
            proxyMapList.put(proxyStr, new ProxyEntity(split[0], Integer.valueOf(split[1]), "http"));
        });
    }

    public ProxyEntity getProxy() throws Exception {
        int size = proxyMapList.size();
        List<String> proxyKeys = new ArrayList<>(proxyMapList.keySet());
        String proxyStr = proxyKeys.get(new Random().nextInt(size));
        return proxyMapList.get(proxyStr);
    }

    public void updateProxy() throws Exception {
        String checkUrl = "https://dps.kdlapi.com/api/checkdpsvalid";
        HashMap<String , String> params = new HashMap<>();
        params.put("secret_id", "olcegu0xt1n268kersn0");
        List<String> proxyKeys = new ArrayList<>(proxyMapList.keySet());
        String proxyStr = StringUtils.join(proxyKeys, ',');
        params.put("proxy", proxyStr);
        params.put("signature", "onrv21n8ksj46013r217djdr9d");
        String s = HttpUtil.httpGetRequest(checkUrl, params, null);
        JSONObject resObj = JSON.parseObject(s);
        if (resObj.getIntValue("code") != 0 )
            throw new Exception("查询代理过期时间出错,错误码：" + resObj.getString("code") + " | 原因：" + resObj.getString("msg"));
        JSONObject resData = resObj.getJSONObject("data");
        proxyKeys.forEach( item -> {
            if (!resData.getBooleanValue(item)) {
                proxyMapList.remove(item);
            }
        });
        if (proxyMapList.size() < 20) {
            getIP();
            log.info("更新代理池成功");
        }
    }

}
