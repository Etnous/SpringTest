package biz.lala.SpringBoot.controller;

import biz.lala.SpringBoot.dao.ranker.ProvincePojo;
import biz.lala.SpringBoot.mapper.ranker.ProvinceMapper;
import biz.lala.SpringBoot.mapper.ranker.SchoolMapper;
import biz.lala.SpringBoot.util.HttpUtil;
import biz.lala.SpringBoot.util.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranker")
public class RankerController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @RequestMapping(value = "/getProvince", method = RequestMethod.GET)
    public Result getProvince() throws Exception {
        String url = "https://yikaoyan-api.51easymaster.com/v1/rank/region/?year=2023";
        String s = HttpUtil.httpGetRequest(url, null, null);
        JSONObject retObj = JSON.parseObject(s);
        JSONArray retArray = retObj.getJSONArray("data");
        List<ProvincePojo> provincePojos = JSONObject.parseArray(retArray.toJSONString(), ProvincePojo.class);
        provincePojos.forEach( item -> provinceMapper.insert(item));
        return Result.success();
    }

    @RequestMapping(value = "/getSchool", method = RequestMethod.GET)
    public Result getSchool() {
//        String url = "https://yikaoyan-api.51easymaster.com/v1/rank/school/?regionId=";
//        // TODO: 发布url
//        List<ProvincePojo> provincePojos = provinceMapper.selectList(null);
//        provincePojos.forEach( item -> {
//            String finalUrl = url + item.getId();
//            redisTemplate.opsForList().leftPush("schoolQueue", finalUrl);
//        });
        redisTemplate.opsForList().leftPush("schoolQueue", "https://yikaoyan-api.51easymaster.com/v1/rank/school/?regionId=183");
        redisTemplate.opsForList().leftPush("schoolQueue", "https://yikaoyan-api.51easymaster.com/v1/rank/school/?regionId=198");

        return Result.success("发布完成");
    }
}
