package cn.enn.service;

import cn.enn.base.dto.RuleDTO;
import cn.enn.base.vo.RuleVo;
import cn.enn.common.utils.Result;
import cn.enn.entity.RuleEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 标签规则 服务类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
public interface RuleService extends IService<RuleEntity> {
    /**
     * 查询总数
     * @param newKeyWord 关键词
     * @param status 状态
     * @return queryRuleViewCount
     */
    Long queryRuleViewCount(String newKeyWord,int status,String userId);
    /**
     * 搜索方法
     * @param page 分页参数
     * @param searchKeyWord 关键词
     * @param status 状态
     * @param userId 用户id
     * @return Page<RuleEntity>
     */
    Page<RuleEntity> queryRuleView(Page page,String searchKeyWord,int ruleType,int status,String userId);
    /**
     * 级联查询规则和规则详情
     * @param page 分页
     * @param createId 当前用户
     * @param status 我的、草稿、
     * @param searchKeyWord 关键字搜索
     * @return Page<RuleEntity>
     */
    Page<RuleEntity> getAllOfRuleAndExpression(Page page,Long createId,Integer status,String searchKeyWord);
    /**
     * 查询我的规则/我的草稿
     * @param page 分页
     * @param createId 用户id
     * @param status 状态
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> getQuestionByPagenByPage(Page page, Long createId,int status);

    /**
     * 查询我的收藏
     * @param page 分页
     * @param createId 用户id
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> getQuestionByCollectPage(Page page, Long createId);

    /**
     * 搜索
     * @param page 分页
     * @param searchKeyWord 关键字
     * @param userId 用户id
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> searchKeyWord(Page page,String searchKeyWord,String userId);


    /**
     * 查询和我的
     * @param page 分页
     * @param createId 创建人id
     * @param status 状态
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> queryRuleExpression(Page page, Long createId,int status);

    /**
     * 查询具体规则
     * @param ruleId 规则id
     * @return RuleEntity
     */
    RuleEntity specificExpression(Long ruleId);

    void saveRule(RuleVo ruleVo) throws Exception;

    /**
     * 更新规则new
     * @param ruleVo
     */
    void updateRuleDetail(RuleVo ruleVo) throws Exception;

    /**
     * 复制数据
     * @param ruleId
     */
    Result<String> copyRule(Long ruleId);

    /**
     * 引用数据
     * @param ruleId
     */
    Result<String> quoteRule(Long ruleId,String userId,String realName);

    /**
     * 查询规则
     * @param ruleId 规则id
     * @return RuleEntity
     */
    RuleEntity queryRuleById(Long ruleId);
}
