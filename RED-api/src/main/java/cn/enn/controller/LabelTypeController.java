package cn.enn.controller;

import cn.enn.common.utils.Result;
import cn.enn.entity.LabelTypeEntity;
import cn.enn.service.LabelConditionService;
import cn.enn.service.LabelTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author kongliang
 * @since 2019-10-29
 */
@RestController
@RequestMapping("labeltype")
@Api(tags = "标签类型")
@Slf4j
public class LabelTypeController {

    @Autowired
    private LabelTypeService labelTypeService;

    @Autowired
    private LabelConditionService labelConditionService;

    @GetMapping("queryLabelStructure")
    @ApiOperation("结构化查询标签")
    public Result<List<String[]>> queryLabelStructure(){
        List<String[]> labelStructure = this.labelTypeService.queryLabelStructure();
        return Result.rstSuccess(labelStructure);
    }

    @GetMapping("getDetailByName")
    @ApiOperation("根据标签名称查看详情")
    public Result<LabelTypeEntity> getDetailByName(String labelName){
        LabelTypeEntity labelTypeEntity = new LabelTypeEntity();
        labelTypeEntity.setLabelName(labelName);
        //this.labelConditionService.findLabelDetailByName();
        return Result.rstSuccess(labelTypeEntity);
    }

}
