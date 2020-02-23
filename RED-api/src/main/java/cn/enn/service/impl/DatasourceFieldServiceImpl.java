package cn.enn.service.impl;

import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.dao.DataSourceMapper;
import cn.enn.dao.DatasourceFieldMapper;
import cn.enn.entity.DatasourceFieldEntity;
import cn.enn.entity.MapEntity;
import cn.enn.service.DatasourceFieldService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 数据源-字段 服务实现类
 * </p>
 *
 * @author kongliang
 * @since 2019-11-13
 */
@Service
public class DatasourceFieldServiceImpl extends ServiceImpl<DatasourceFieldMapper, DatasourceFieldEntity> implements DatasourceFieldService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public List<DataSourceFieldVO> getListByDatasourceIdAndFieldOptions(Map datasourceFieldEntity) {

        //数据库的字段列表
        List<DataSourceFieldVO> list = this.baseMapper.getListByDatasourceIdAndFieldOptions(datasourceFieldEntity);
        //需要返回的字段列表，需要进行字段处理，处理格式为“表名#列名”
        List<DataSourceFieldVO> voList = new ArrayList<DataSourceFieldVO>();
        //这是返回全部，不用二次查询
        if (datasourceFieldEntity.get("fieldOptions") == null) {
            for (int i = 0; i < list.size(); i++) {
                DataSourceFieldVO dataSourceFieldVO = new DataSourceFieldVO();
                BeanUtils.copyProperties(list.get(i), dataSourceFieldVO);
                voList.add(dataSourceFieldVO);
            }
        } else if ((Integer) datasourceFieldEntity.get("fieldOptions") == 2) {
            //查询条件，需要二次查询，动态执行sql
            for (int i = 0; i < list.size(); i++) {
                List<MapEntity> mapEntityList = baseMapper.excSql("key", list.get(i).getFieldSql());
                DataSourceFieldVO dataSourceFieldVO = new DataSourceFieldVO();
                BeanUtils.copyProperties(list.get(i), dataSourceFieldVO);
                dataSourceFieldVO.setAnswerList(mapEntityList);
                voList.add(dataSourceFieldVO);
            }
        } else {
            //这是查询选项
            for (int i = 0; i < list.size(); i++) {
                DataSourceFieldVO dataSourceFieldVO = new DataSourceFieldVO();
                BeanUtils.copyProperties(list.get(i), dataSourceFieldVO);
                voList.add(dataSourceFieldVO);
            }
        }

        return voList;
    }

    @Override
    public List<DatasourceFieldEntity> getListByDatasourceId(Long datasourceId) {
        return baseMapper.selectList(new QueryWrapper<DatasourceFieldEntity>().eq("datasource_id", datasourceId));
    }

    @Override
    public List<DatasourceFieldEntity> getAllList() {
        return this.list();
    }


}
