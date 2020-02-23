package cn.enn.service.impl;

import cn.enn.base.dto.DataSourceDTO;
import cn.enn.dao.DataSourceMapper;
import cn.enn.entity.DatasourceEntity;
import cn.enn.service.DatasourceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据源 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Service("DatasourceService")
public class DatasourceServiceImpl extends ServiceImpl<DataSourceMapper, DatasourceEntity> implements DatasourceService {


    /**
     * 级联查找数据源
     *
     * @param datasourceId
     * @return
     */
    @Override
    public DataSourceDTO queryCascadeDataSource(Long datasourceId) {
        return this.baseMapper.queryCascadeDataSource(datasourceId);
    }

    @Override
    public List<DatasourceEntity> queryDataSource() {
        return this.baseMapper.queryDataSource();
    }

    @Override
    public IPage<DatasourceEntity> queryDataSource(Page page) {
        return this.baseMapper.queryDataSource(page);
    }

    @Override
    public IPage<DatasourceEntity> searchDatasourceList(Page page,String searchKeyWord){
        return this.baseMapper.searchDatasourcelList(page,searchKeyWord);
    }
}
