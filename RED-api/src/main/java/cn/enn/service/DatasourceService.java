package cn.enn.service;

import cn.enn.base.dto.DataSourceDTO;
import cn.enn.entity.DatasourceEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据源 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
public interface DatasourceService extends IService<DatasourceEntity> {
    /**
     * 级联查找数据源
     * @param dataSourceId
     * @return
     */
    DataSourceDTO queryCascadeDataSource(Long dataSourceId);

    List<DatasourceEntity> queryDataSource();
    IPage<DatasourceEntity> queryDataSource(Page page);

    IPage<DatasourceEntity> searchDatasourceList(Page page,String searchKeyWord);
}
