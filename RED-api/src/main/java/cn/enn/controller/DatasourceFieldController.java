package cn.enn.controller;

import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.HttpContextUtils;
import cn.enn.common.utils.Result;
import cn.enn.entity.DatasourceFieldEntity;
import cn.enn.service.DatasourceFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源-字段 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/datasourceField")
@Api(tags = "数据源字段")
@Slf4j
public class DatasourceFieldController {

    @Autowired
    private DatasourceFieldService datasourceFieldService;

    /**
     * 根据父级id保存子集数据
     *
     * @param datasourceFieldEntity
     * @return Result<String>
     */
    @PostMapping("saveDatasourceFieldById")
    @ApiOperation("修改或保存子集数据，传参数父级id")
    public Result<String> saveDatasourceFieldById(@RequestBody DatasourceFieldEntity datasourceFieldEntity) {

        try {
            //先清一下缓存数据,如果有单对象缓存，也应该清除单对象信息
            HttpContextUtils.getHttpServletRequest().getSession().getServletContext().removeAttribute("datasourceFieldVoMap");
            this.datasourceFieldService.saveOrUpdate(datasourceFieldEntity);
        } catch (Exception e) {
            log.error("错误",e);
            return Result.fail(500, e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    @DeleteMapping("deleteDatasourceFieldById")
    @ApiOperation("根据id删除子级数据源")
    public Result<String> deleteDatasourceFieldById(Long datasourceFieldId) {

        try {
            this.datasourceFieldService.removeById(datasourceFieldId);
        } catch (Exception e) {
            log.error("错误",e);
            return Result.fail(500, e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 查询数子集据源详情
     *
     * @param datasourceFieldId 数据源id
     * @return Result<DataSourceDTO>
     */
    @GetMapping("getDataSourceFieldById")
    @ApiOperation("根据id查询数据源")
    @ApiImplicitParam(name = "datasourceFieldId", value = "数据源-字段ID", dataType = "int", defaultValue = "3", paramType = "query", required = true)
    public Result<DatasourceFieldEntity> getDataSourceFieldById(Long datasourceFieldId) {
        DatasourceFieldEntity dataSourceFileDTO = null;
        try {
            dataSourceFileDTO = this.datasourceFieldService.getById(datasourceFieldId);
        } catch (Exception e) {
            log.error("错误",e);
            return Result.fail(500, e.getMessage());
        }
        return Result.rstSuccess(dataSourceFileDTO);
    }

    @GetMapping("queryByDatasourceIdAndFieldOptions")
    @ApiOperation("根据创值场景id(父id)字段类型选择相应子数据源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "datasourceId", value = "创值场景ID，可传多个,以逗号隔开，如(61,64）", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "fieldOptions", value = "查询选项，传1查询选项；传2查询条件；不传值查询所有", dataType = "Integer", paramType = "query")
    })
    public Result<List<DataSourceFieldVO>> queryByDatasourceIdAndFieldOptions(String datasourceId, Integer fieldOptions) {
        //参数验证
        if (null == datasourceId || "".equals(datasourceId)) {
            return Result.fail(500, "创值场景id不能为空");
        }
        Map map = new HashMap();//查询条件
        String[] arr = datasourceId.split(",");
        map.put("datasourceIds", Arrays.asList(arr));
        map.put("fieldOptions", fieldOptions);
        List<DataSourceFieldVO> list = this.datasourceFieldService.getListByDatasourceIdAndFieldOptions(map);

        return Result.rstSuccess(list);
    }

}
