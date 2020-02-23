package cn.enn.controller;

import cn.enn.common.utils.Constant;
import cn.enn.common.utils.Result;
import cn.enn.entity.LabelTempEntity;
import cn.enn.service.LabelTempService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("temp")
@Slf4j
public class LabelTempController {
    @Autowired
    private LabelTempService labelTempService;

    /**
     * 临时保存标签的方法
     * @param labelTempEntity 临时标签类
     * @return 保存的标签id
     */
    @PostMapping("saveLabelTemp")
    @ApiOperation("保存标签")
    public Result saveLabelTemp(@RequestBody LabelTempEntity labelTempEntity){
        try {
            this.labelTempService.save(labelTempEntity);
        } catch (Exception e) {
            log.error("Context:",e);
            return Result.fail(500,"保存失败");
        }
        return Result.rstSuccess(labelTempEntity.getTempid());
    }

    /**
     * 根据ids查询标签规则
     * @param Ids id列表
     * @return  Collection<LabelTempEntity>
     */
    @PostMapping("queryLabelTemp")
    @ApiOperation("根据ids查询")
    public Result<Collection<LabelTempEntity>> queryLabelTemp(@RequestParam(value = "Ids") List<String> Ids){

        Collection<LabelTempEntity> labelTempEntityList;
        try {
            labelTempEntityList = this.labelTempService.listByIds(Ids);
        } catch (Exception e) {
            log.error("Context:",e);
            return Result.fail(500,"查询失败");
        }
        return Result.rstSuccess(labelTempEntityList);
    }

    /**
     * 根据id删除
     * @param id 规则id
     * @return 状态
     */
    @DeleteMapping("deleteById")
    @ApiOperation("根据id删除")
    public Result deleteById(@RequestParam(value = "id") Integer id){
        try {
            this.labelTempService.removeById(id);
        } catch (Exception e) {
            log.error("Context:",e);
            return Result.fail(500,"删除失败");
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

}
