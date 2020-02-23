package cn.enn.service;

import cn.enn.base.dto.CollectDTO;
import cn.enn.base.dto.RuleDTO;
import cn.enn.base.vo.CollectVO;
import cn.enn.entity.CollectEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 收藏关联表 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
public interface CollectService extends IService<CollectEntity> {
    /**
     * 根据id查询收藏
     * @param ruleId
     * @return
     */
    CollectEntity getByCollectId(Long ruleId);

    /**
     * 搜索我的收藏,版本一，目前使用
     */
    Page<CollectVO> searchCollect(Page page, String userId, String searchKeyWord, Integer status);

    /**
     * 搜索我的收藏,版本二，可能即将使用
     * @param page
     * @param createId
     * @param searchKeyWord
     * @param status
     * @return
     */
    Page<CollectDTO> searchCollect2(Page page, Long createId, String searchKeyWord, Integer status);
    Long serachCollect2Count(Long createId, String searchKeyWord, Integer status);
     /** 根据ruleId批量删除
     * @param ruleIds 规则id
     */
    void removeByRuleIds(List<Long> ruleIds);
}

