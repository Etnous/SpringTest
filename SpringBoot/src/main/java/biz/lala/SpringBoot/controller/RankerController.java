package biz.lala.SpringBoot.controller;

import biz.lala.SpringBoot.config.redis.AnnouncementMessage;
import biz.lala.SpringBoot.dao.ranker.CollegePojo;
import biz.lala.SpringBoot.dao.ranker.MajorPojo;
import biz.lala.SpringBoot.dao.ranker.ProvincePojo;
import biz.lala.SpringBoot.dao.ranker.SchoolPojo;
import biz.lala.SpringBoot.mapper.ranker.CollegeMapper;
import biz.lala.SpringBoot.mapper.ranker.MajorMapper;
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

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private MajorMapper majorMapper;

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
        String url = "http://api.kaoyanvip.cn/wx/v1/rank/%s/school/";
        // TODO: 发布url
        List<ProvincePojo> provincePojos = provinceMapper.selectList(null);
        provincePojos.forEach( item -> {
            String schoolUrl = String.format(url, item.getCode());
            JSONObject object = new JSONObject();
            object.put("schoolUrl", schoolUrl);
            object.put("code", item.getId());


            redisTemplate.opsForList().leftPush("schoolQueue", object.toJSONString());
        });

        return Result.success("发布完成");
    }

    @RequestMapping(value = "/getCollege", method = RequestMethod.GET)
    public Result getCollege() {
        String url = "https://api.kaoyanvip.cn/wx/v1/rank/%s/department/";
        //  发布college url
        List<SchoolPojo> schoolPojos = schoolMapper.selectList(null);
        schoolPojos.forEach( item -> {
            String collegeUrl = String.format(url, item.getValue());
            JSONObject object = new JSONObject();
            object.put("collegeUrl", collegeUrl);
            object.put("code", item.getId());


            redisTemplate.opsForList().leftPush("collegeQueue", object.toJSONString());
        });

        return Result.success("发布完成");
    }

    @RequestMapping(value = "/getMajor", method = RequestMethod.GET)
    public Result getMajor() {
        String url = "https://api.kaoyanvip.cn/wx/v1/rank/%s/major/";
        //  发布major url
        long startTime = System.currentTimeMillis();
        List<CollegePojo> collegePojos = collegeMapper.selectList(null);
        collegePojos.forEach( item -> {
            String majorUrl = String.format(url, item.getValue());
            JSONObject object = new JSONObject();
            object.put("majorUrl", majorUrl);
            object.put("code", item.getId());
            redisTemplate.opsForList().leftPush("majorQueue", object.toJSONString());
        });
        long endTime = System.currentTimeMillis();

        return Result.success("发布完成, 用时:" + (endTime - startTime) + "毫秒");
    }

    @RequestMapping(value = "/getSubject", method = RequestMethod.GET)
    public Result getSubject() {
        String url = "https://api.kaoyanvip.cn/wx/v1/rank/%s/subject/";
        //  发布subject url
        long startTime = System.currentTimeMillis();
        List<MajorPojo> majorPojos = majorMapper.selectList(null);
        majorPojos.forEach( item -> {
            String subjectUrl = String.format(url, item.getValue());
            JSONObject object = new JSONObject();
            object.put("subjectUrl", subjectUrl);
            object.put("code", item.getId());
            redisTemplate.opsForList().leftPush("subjectQueue", object.toJSONString());
        });
        long endTime = System.currentTimeMillis();

        return Result.success("发布完成, 用时:" + (endTime - startTime) + "毫秒");
    }
}
