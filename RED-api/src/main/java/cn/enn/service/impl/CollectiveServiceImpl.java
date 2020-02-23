package cn.enn.service.impl;

import cn.enn.dao.CollectiveMapper;
import cn.enn.entity.CollectiveEntity;
import cn.enn.service.CollectiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 协同伙伴关联表 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Service("CollectiveService")
public class CollectiveServiceImpl extends ServiceImpl<CollectiveMapper, CollectiveEntity> implements CollectiveService {

}
