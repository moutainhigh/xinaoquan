package cn.enn.service.impl;

import cn.enn.dao.LabelTempMapper;
import cn.enn.entity.LabelTempEntity;
import cn.enn.service.LabelTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("LabelTempService")
public class LabelTempServiceImpl extends ServiceImpl<LabelTempMapper, LabelTempEntity> implements LabelTempService {
}
