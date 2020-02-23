package cn.enn.service.impl;

import cn.enn.base.dto.CollectDTO;
import cn.enn.base.dto.RuleDTO;
import cn.enn.base.vo.CollectVO;
import cn.enn.common.utils.FullHalfAngleConverter;
import cn.enn.dao.CollectMapper;
import cn.enn.entity.CollectEntity;
import cn.enn.service.CollectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏关联表 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Service("CollectService")
public class CollectServiceImpl extends ServiceImpl<CollectMapper, CollectEntity> implements CollectService {

    /**
     * 根据id查询收藏
     *
     * @param ruleId
     * @return
     */
    @Override
    public CollectEntity getByCollectId(Long ruleId) {
        return this.baseMapper.getByCollectId(ruleId);
    }

    /**
     * 搜索收藏的两种方法之第一种
     * 第一种使用分页插件统计总数，第二种单独统计总数
     */
    @Override
    public Page<CollectVO> searchCollect(Page page, String createId, String searchKeyWord, Integer status){
        String newKeyWord = null;
        if(searchKeyWord != null){
            //全角转换为半角
            String halfAngleString = FullHalfAngleConverter.ToDBC(searchKeyWord);
            String[] newStr = halfAngleString.split(",");
            StringBuffer key = new StringBuffer();
            for(int i=0;i<newStr.length;i++) {
                //key.append(newStr[i].replaceAll("\\\\s*","")+".*");
                //改为查并集
                if(i+1 == newStr.length){
                    key.append(newStr[i].trim());
                }else {
                    key.append(newStr[i].trim()+"|");
                }
            }
            newKeyWord = key.toString();
        }
        return this.baseMapper.searchCollect(page,createId,status,newKeyWord);
    }

    /**
     * 搜索收藏的两种方法之第二种
     * 第一种使用分页插件统计总数，第二种单独统计总数
     */
    @Override
    public Page<CollectDTO> searchCollect2(Page page, Long createId, String searchKeyWord, Integer status){
        String newKeyWord = null;
        if(searchKeyWord != null){
            //全角转换为半角
            String halfAngleString = FullHalfAngleConverter.ToDBC(searchKeyWord);
            String[] newStr = halfAngleString.split(",");
            StringBuffer key = new StringBuffer();
            for(int i=0;i<newStr.length;i++) {
                key.append(newStr[i].replaceAll("\\\\s*","")+".*");
            }
            newKeyWord = key.toString();
        }
        return this.baseMapper.searchCollect2(page,createId,status,newKeyWord);
    }
    @Override
    public Long serachCollect2Count(Long createId, String searchKeyWord, Integer status){
        String newKeyWord = null;
        if(searchKeyWord != null){
            //全角转换为半角
            String halfAngleString = FullHalfAngleConverter.ToDBC(searchKeyWord);
            String[] newStr = halfAngleString.split(",");
            StringBuffer key = new StringBuffer();
            for(int i=0;i<newStr.length;i++) {
                key.append(newStr[i].replaceAll("\\\\s*","")+".*");
            }
            newKeyWord = key.toString();
        }
        return this.baseMapper.serachCollect2Count(createId,status,newKeyWord);
    }

     /** 根据ruleId批量删除
     *
     * @param ruleIds 规则id
     */
    @Override
    public void removeByRuleIds(List<Long> ruleIds) {
        this.baseMapper.removeByRuleIds(ruleIds);
    }

    /*public Page<RuleDTO> searchCollect(Page page,String searchKeyWord,Integer status){
        Map<String,Object> searchMap = new HashMap<String,Object>();
        searchMap.put("createId",10043017);
        searchMap.put("status",status);
        if(searchKeyWord != null){
            //全角转换为半角
            String halfAngleString = FullHalfAngleConverter.ToDBC(searchKeyWord);
            String[] newStr = halfAngleString.split(",");
            for(int i=0;i<newStr.length;i++) {
                searchMap.put("s"+i,newStr[i]);
            }
        }
        return this.baseMapper.searchCollect(page,searchMap,status);
    }*/
}
