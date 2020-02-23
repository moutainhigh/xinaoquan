package cn.enn.service.impl;

import cn.enn.dao.LabelObjectMapper;
import cn.enn.entity.LabelObjectEntity;
import cn.enn.service.LabelObjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("LabelObjectService")
public class LabelObjectServiceImpl extends ServiceImpl<LabelObjectMapper, LabelObjectEntity> implements LabelObjectService {
}
