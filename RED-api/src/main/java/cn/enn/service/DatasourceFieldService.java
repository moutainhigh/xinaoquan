package cn.enn.service;

import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.entity.DatasourceFieldEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源-字段 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
public interface DatasourceFieldService extends IService<DatasourceFieldEntity> {

    //改为map条件，有字段转换
    List<DataSourceFieldVO> getListByDatasourceIdAndFieldOptions(Map datasourceFieldEntity);

    /**
     * 根据数据id查询该数据的字段信息
     * @param datasourceId
     * @return
     */
    List<DatasourceFieldEntity> getListByDatasourceId(Long datasourceId);

    /**
     * 查询数据源所有字段信息，没有字段转换
     * @return
     */
    List<DatasourceFieldEntity> getAllList();

}
