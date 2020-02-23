package cn.enn.service;

import cn.enn.entity.RuleExpressionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 规则-详情 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
public interface RuleExpressionService extends IService<RuleExpressionEntity> {

    List<RuleExpressionEntity> checkLabelName(String labelName);
}
