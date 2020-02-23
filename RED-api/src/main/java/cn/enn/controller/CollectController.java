package cn.enn.controller;


import cn.enn.base.dto.CollectDTO;
import cn.enn.base.vo.CollectVO;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.Result;
import cn.enn.service.CollectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 收藏关联表 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/Collect")
@Api(tags = "收藏接口")
@Slf4j
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 收藏搜索的两种方法之第一种
     * 不同点在于获取总数的方法，第一种使用分页插件统计总数，第二种单独统计总数
     * @param pageNum
     * @param pageSize
     * @param searchKeyWord
     * @param status
     * @return
     */
    @GetMapping("searchCollect")
    @ApiOperation("搜索收藏规则方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "当前用户ID",dataType = "String",paramType = "query",defaultValue="10043017",required = true),
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "searchKeyWord",value = "多个关键字以,隔开例如：测,试",dataType="String",paramType = "query",defaultValue="测,试"),
            @ApiImplicitParam(name = "status",value = "草稿状态值为1",dataType = "int",paramType = "query",required = true)
    })
    public Result<Page<CollectVO>> searchCollect(String userId, int pageNum, int pageSize, String searchKeyWord, Integer status){
        Page page = new Page(pageNum,pageSize);
        Page<CollectVO> collectList = this.collectService.searchCollect(page,userId,searchKeyWord,status);
        return Result.rstSuccess(collectList);
    }

    /**
     * 搜索收藏的两种方法之第二种
     * 第一种使用分页插件统计总数，第二种单独统计总数
     * @param pageNum
     * @param pageSize
     * @param searchKeyWord
     * @param status
     * @return
     */
    @GetMapping("searchCollect2")
    @ApiOperation("搜索收藏规则方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",dataType = "int",paramType = "query",defaultValue="1",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",dataType = "int",paramType = "query",defaultValue="10",required = true),
            @ApiImplicitParam(name = "searchKeyWord",value = "多个关键字以,隔开例如：测,试",dataType="String",paramType = "query",defaultValue="测,试"),
            @ApiImplicitParam(name = "status",value = "草稿状态值为1",dataType = "int",paramType = "query",required = true)
    })
    @Deprecated
    public Result<Page<CollectDTO>> searchCollect2(int pageNum, int pageSize, String searchKeyWord, Integer status){
        Page page = new Page(pageNum,pageSize);
        Page<CollectDTO> collectList = this.collectService.searchCollect2(page,10043017l,searchKeyWord,status);
        Long count = this.collectService.serachCollect2Count(10043017l,searchKeyWord,status);
        collectList.setTotal(count);
        return Result.rstSuccess(collectList);
    }



    /**
     * 根据id批量删除
     * @param collectIds 收藏id
     * @return Result<String>
     */
    @GetMapping("deleteByIds")
    @ApiOperation("收藏删除")
    public Result<String> deleteByIds(@RequestParam(value = "collectIds") List<Long> collectIds){
        try {
            if(null == collectIds){
                return Result.fail(400,"id不能为空");
            }
            this.collectService.removeByIds(collectIds);
        } catch (Exception e) {
            log.error(Constant.ERROR,e);
            return Result.fail(500,"删除出错");
        }
        return Result.rstSuccess(Constant.SUCCESS);
    }


}
