package cn.enn.dao;

import cn.enn.base.dto.DataSourceDTO;
import cn.enn.entity.DatasourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 数据源 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Mapper
public interface DataSourceMapper extends BaseMapper<DatasourceEntity> {
    /**
     * 级联查找数据源
     * @param datasourceId
     * @return
     */
    DataSourceDTO queryCascadeDataSource(Long datasourceId);

    List<DatasourceEntity> queryDataSource();
    IPage<DatasourceEntity> queryDataSource(Page page);

    /**
     * 条件查询我的收藏
     * @return
     */
    @Select("<script>"+
            "SELECT "+
            " datasource_id,datasource_name,datasource_show_name,datasource_type,datasource_describe"+
            " FROM t_datasource"+
            " WHERE 1=1 "+
            "<if test='searchKeyWord != null and searchKeyWord !=\"\"'>"+
            " and (datasource_name REGEXP '${searchKeyWord}' or datasource_show_name REGEXP '${searchKeyWord}')"+
            "</if>"+
            " order by datasource_id desc"+
            "</script>")
    Page<DatasourceEntity> searchDatasourcelList(Page page, String searchKeyWord);

}
