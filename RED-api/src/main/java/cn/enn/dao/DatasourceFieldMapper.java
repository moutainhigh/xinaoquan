package cn.enn.dao;

import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.entity.DatasourceFieldEntity;
import cn.enn.entity.MapEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据源-字段 Mapper 接口
 * </p>
 *
 * @author kongliang
 * @since 2019-11-13
 */
@Mapper
public interface DatasourceFieldMapper extends BaseMapper<DatasourceFieldEntity> {

    //改为map条件
    //List<DatasourceFieldEntity> getListByDatasourceIdAndFieldOptions(DatasourceFieldEntity datasourceFieldEntity);
    List<DataSourceFieldVO> getListByDatasourceIdAndFieldOptions(Map map);

    //多传一下参数可以直接解析
    List<MapEntity> excSql(String key, String strSql);

}
