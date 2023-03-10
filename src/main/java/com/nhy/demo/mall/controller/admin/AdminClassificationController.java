package com.nhy.demo.mall.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nhy.demo.mall.entity.Classification;
import com.nhy.demo.mall.entity.pojo.ResultBean;
import com.nhy.demo.mall.service.ClassificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/classification")
public class AdminClassificationController {
    @Autowired
    private ClassificationService classificationService;

    //返回列表页面
    @RequestMapping("/toList.html")
    public String toList(int type) {
        return "admin/category/list";
    }

    //打开添加分类页面
    @RequestMapping("/toAdd.html")
    public String toAdd(int type) {
        return "admin/category/add";
    }

    //打开编辑页面
    @RequestMapping("/toEdit.html")
    public String toEdit(int id, int type, Map<String, Object> map) {
        Classification classification = classificationService.findById(id);
        map.put("cate", classification);
        return "admin/category/edit";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public ResultBean<Boolean> add(String cname, int type) {
        Classification classification = new Classification();
        classification.setCname(cname);
        classification.setType(type);
        classificationService.create(classification);
        return new ResultBean<>(true);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public ResultBean<Boolean> update(int id, String cname, int parentId, int type) {
        Classification classification = classificationService.findById(id);
        classification.setCname(cname);
        classification.setType(type);
        classificationService.update(classification);
        return new ResultBean<>(true);
    }

    @ResponseBody
    @RequestMapping("/del.do")
    public ResultBean<Boolean> del(int id) {
        classificationService.delById(id);
        return new ResultBean<>(true);
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public ResultBean<List<Classification>> findAll(int type,
                                                    int pageindex, @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) {
        List<Classification> list = new ArrayList<>();
        if (pageindex == -1)
            list = classificationService.findAll(type);
        else {
            Pageable pageable = PageRequest.of(pageindex, pageSize);
            list = classificationService.findAll(type, pageable).getContent();
        }
        return new ResultBean<>(list);
    }

    @ResponseBody
    @RequestMapping("/getTotal.do")
    public ResultBean<Integer> getTotal(int type) {
        Pageable pageable = PageRequest.of(1, 30);
        int count = (int) classificationService.findAll(type, pageable).getTotalElements();
        return new ResultBean<>(count);
    }
}