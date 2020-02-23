package cn.enn.controller;

import cn.enn.base.dto.DataSourceDTO;
import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.Result;
import cn.enn.entity.DatasourceEntity;
import cn.enn.entity.DatasourceFieldEntity;
import cn.enn.entity.LabelTypeEntity;
import cn.enn.service.DatasourceFieldService;
import cn.enn.service.DatasourceService;
import cn.enn.service.LabelTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@RestController
@RequestMapping("datasource")
@Api(tags = "数据源")
@Slf4j
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;
    @Autowired
    private DatasourceFieldService datasourceFieldService;
    @Autowired
    private LabelTypeService labelTypeService;

    @GetMapping("queryCreationScenarios")
    @ApiOperation("查询创值场景")
    @ApiImplicitParam(name = "searchKeyWord",value = "搜索关键字",dataType = "String",paramType = "query")
    public Result<List<DatasourceEntity>> queryCreationScenarios(String searchKeyWord){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(null != searchKeyWord ){
            queryWrapper.like("datasource_name",searchKeyWord);
        }
        List<DatasourceEntity> list = this.datasourceService.list(queryWrapper);
        return Result.rstSuccess(list);
    }

    /**
     * 保存父级数据源
     * @param datasourceEntity 数据源
     * @return Result<String>
     */
    @PostMapping("saveDatasource")
    @ApiOperation("保存或修改父级数据源")
    public Result<String> saveDatasource(@RequestBody DatasourceEntity datasourceEntity){
        try {
            this.datasourceService.saveOrUpdate(datasourceEntity);
        } catch (Exception e) {
            
            log.error("保存出错",e);
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    @ApiOperation("分页查询数据源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "searchKeyWord",value = "",dataType="String",paramType = "query",defaultValue="场景"),
    })
    @GetMapping("queryDataSource")
    public Result<IPage<DatasourceEntity>> queryDataSource(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize")int pageSize,String searchKeyWord){
        Page page = new Page(pageNum,pageSize);
        IPage<DatasourceEntity> pageList = null;
        //IPage<DatasourceEntity> pageList = this.datasourceService.page(page);
        //IPage<DatasourceEntity> pageList1 = this.datasourceService.queryDataSource(page);
        //List<DatasourceEntity>  pageList2 = this.datasourceService.queryDataSource();
        try{
            pageList = this.datasourceService.searchDatasourceList(page,searchKeyWord);
        }catch(Exception e){
            
            log.error("查询出错",e);
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(pageList);
    }

    @ApiOperation("全部数据源列表")
    @GetMapping("dataSourceList")
    public Result<List<DatasourceEntity>> dataSourceList(){
        List<DatasourceEntity> list = this.datasourceService.list();
        return Result.rstSuccess(list);
    }

    /**
     * 查询数据源根据ID
     * @param datasourceId 数据源id
     * @return Result<DataSourceDTO>
     */
    @GetMapping("getDataSourceById")
    @ApiOperation("根据id查询数据源")
    @ApiImplicitParam(name = "datasourceId",value = "数据源ID",dataType = "int",defaultValue = "3",paramType = "query",required = true)
    public Result<DataSourceDTO> getDataSourceById(Long datasourceId){
        DataSourceDTO dataSourceDTO = null;
        try {
            dataSourceDTO = this.datasourceService.queryCascadeDataSource(datasourceId);
        } catch (Exception e) {
            
            log.error("查询出错",e);
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(dataSourceDTO);
    }

    @GetMapping("queryDataSource2")
    @ApiOperation("查询数据源列表")
    @Deprecated
    public Result<IPage<DatasourceEntity>> queryDataSource2(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize")int pageSize){
        Page page = new Page(pageNum,pageSize);
        IPage<DatasourceEntity> pageList = this.datasourceService.page(page);
        IPage<DatasourceEntity> pageList1 = this.datasourceService.queryDataSource(page);
        List<DatasourceEntity>  pageList2 = this.datasourceService.queryDataSource();
        return Result.rstSuccess(pageList1);
    }

    /**
     * 保存数据源，暂时没有用
     * @param dataSourceDTO
     * @return
     */
    @PostMapping("saveDataSource")
    @ApiOperation("保存数据源")
    @Deprecated
    public Result<String> saveDataSource(@RequestBody DataSourceDTO dataSourceDTO){

        DatasourceEntity datasourceEntity = new DatasourceEntity();
        DatasourceFieldEntity datasourceFieldEntity = new DatasourceFieldEntity();

        BeanUtils.copyProperties(dataSourceDTO,datasourceEntity);
        BeanUtils.copyProperties(dataSourceDTO,datasourceFieldEntity);
        try {
            this.datasourceService.saveOrUpdate(datasourceEntity);
            datasourceFieldEntity.setDatasourceId(datasourceEntity.getDatasourceId());
            this.datasourceFieldService.saveOrUpdate(datasourceFieldEntity);
        }catch (Exception e){
            
            log.error("保存出错",e);
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 查找标签方法为什么会在这里，考虑删除
     * @param keyWord
     * @return
     */
    @GetMapping("queryLabelType")
    @ApiOperation("查询所有标签")
    @Deprecated
    public Result<List<LabelTypeEntity>> queryLabelType(String keyWord){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",0);
        if(null != keyWord){
            queryWrapper.like("label_name",keyWord);
        }
        List<LabelTypeEntity> labelJson = this.labelTypeService.list(queryWrapper);
        return Result.rstSuccess(labelJson);

    }

    /**
     * 好象暂时没有用，没有搜索功能
     * 查询创值场景方法
     * @param datasourceId
     * @return
     */
    @GetMapping("queryCreation")
    @ApiOperation("查询创值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "datasourceId",value = "创值场景ID",dataType = "String",paramType = "query",required = true),
            @ApiImplicitParam(name = "keyWord",value = "搜索关键字",dataType = "String",paramType = "query")
    })
    @Deprecated
    public Result<List<DatasourceFieldEntity>> queryCreation(String datasourceId,String keyWord){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("datasource_id",datasourceId);
        if(null != keyWord ){
            queryWrapper.like("field_show_name",keyWord);
        }
        List<DatasourceFieldEntity> list = this.datasourceFieldService.list(queryWrapper);
        return  Result.rstSuccess(list);
    }

    /**
     * 单表删除，不适用，删除父类时需要连同子类一块删除
     * @param datasourceId
     * @return
     */
    @DeleteMapping("deleteById")
    @ApiOperation("删除数据源")
    public Result<String> deleteById(String datasourceId){
        try {
            //先查数据源看看有没有子类数据
            //DatasourceFieldEntity datasourceFieldEntity = new DatasourceFieldEntity();
            //datasourceFieldEntity.setDatasourceId(datasourceId);
            Map datasourceFieldEntity = new HashMap<>();
            datasourceFieldEntity.put("datasourceIds",datasourceId.split(","));
            List<DataSourceFieldVO> list = this.datasourceFieldService.getListByDatasourceIdAndFieldOptions(datasourceFieldEntity);
            if(list.size()>0){
                return Result.fail(500,"有子数据不能删除");
            }
            this.datasourceService.removeById(datasourceId);
        } catch (Exception e) {
            
            log.error("删除错误",e);
            return Result.fail(500,e.getMessage());
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }

    /**
     * 好象没啥用
     * 查询标签对象方法
     * @return
     */
    @PostMapping("queryLabelObject")
    @ApiOperation("查询标签对象")
    @Deprecated
    public Result<List<DatasourceEntity>> queryLabelObject(){
        List<DatasourceEntity> datasourceEntity = this.datasourceService.list();
        return Result.rstSuccess(datasourceEntity);
    }

}
