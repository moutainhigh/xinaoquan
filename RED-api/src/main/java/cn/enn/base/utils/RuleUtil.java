package cn.enn.base.utils;

import cn.enn.base.vo.DataSourceFieldVO;
import cn.enn.common.utils.HttpContextUtils;
import cn.enn.common.utils.SpringContextUtils;
import cn.enn.service.DatasourceFieldService;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则处理工具类
 * kongliang
 * 2019/12/09
 */
public class RuleUtil {

    /**
     * 解析字符串
     * @param ruleStr
     * @return
     */
    public static String resolveRuleStr(String ruleStr) throws Exception{

        //第一步，先替换特殊符号，前面替换的，可以都去掉，后变处理规则表达式的基础
        String resultRule  = ruleStr.replace("×","*")
                .replace("÷","/")
                .replace("≦","<=")
                .replace("≧",">=");

        //第二步，定义正则关键字
        //1、定义流程处理关键字
        String keyStr = "如果|那么|且|或";
        //2、定义获取变量关键字
        String catchVariableKeys = "如果|那么|且|或|\\+|-|\\*|/|%|<=|>=|>|<|=|==| join | text join | in | not in ";
        //3、提前确定流程操作，后面判断加减乘除用到，最后处理语法也用到，firstList>0，说明有流程操作，否则没有
        Pattern pkey = Pattern.compile(keyStr);
        Matcher mkey = pkey.matcher(resultRule);
        List firstList = new ArrayList();
        while (mkey.find()){
            firstList.add(mkey.group());
        }

        //第三步，如果是+-*/%，则检测前后变量是不是数据，如果是除法，则检测除数不能为0
        //关键是找到运算符的上一个和下一个变量
        //1、捕获变量关键字，保存为List
        Pattern catchVKs = Pattern.compile(catchVariableKeys);
        Matcher vks = catchVKs.matcher(resultRule);
        List<String> vksList = new ArrayList<String>();
        while (vks.find()){
            vksList.add(vks.group());
        }
        //2、建立与关键字对应变量List，为了简单转换一下吧
        String[] checkStr =resultRule.split(catchVariableKeys);
        List<String> vvsList = new ArrayList<String>();
        for(int i=0;i<checkStr.length;i++){
            //如果第一个是空字符串，则说明有流程控制，如果没有流程控制，第一个一定不是空字符串
            if(!"".equals(checkStr[i])){
                vvsList.add(checkStr[i]);
            }
        }
        //3、经过前两步处理，关键字和关键字值是一一对应的，找到+-*/%，判断前后变量类型
        //判断一下是不是流程处理，对应关系不一样，差一位
        for(int j=0;j<vksList.size();j++){
            if(vksList.get(j).matches("\\+|-|\\*")){
                //加减乘的检测
                String preStr = null;
                String nextStr = null;
                if(firstList.size()>0){
                    preStr = vvsList.get(j-1);
                    nextStr = vvsList.get(j);
                }else {
                    preStr = vvsList.get(j);
                    nextStr = vvsList.get(j+1);
                }
                //检测这两个变量是不是数值，如果是数字，直接通过，如果是变量，1检验变量是否存在2变量类型是不是int
                if(!checkStringType(preStr)){
                    throw new Exception(preStr +"：不是可执行的数字变量或与预定义类型不匹配");
                }
                if(!checkStringType(nextStr)){
                    throw new Exception(nextStr +"：不是可执行的数字变量或与预定义类型不匹配");
                }
            }else if(vksList.get(j).matches("/|%")){
                //除法检测，要判断除数是不是0
                String preStr = null;
                String nextStr = null;
                if(firstList.size()>0){
                    preStr = vvsList.get(j-1);
                    nextStr = vvsList.get(j);
                }else {
                    preStr = vvsList.get(j);
                    nextStr = vvsList.get(j+1);
                }
                //检测这两个变量是不是数值，如果是数字或是空格，直接通过，如果是变量，1检验变量是否存在2变量类型是不是int
                if(!checkStringType(preStr)){
                    throw new Exception(preStr +"：不是可执行的数字变量或与预定义类型不匹配");
                }
                if("0".equals(nextStr)){
                    throw new Exception(nextStr +":被除数不能为0");
                }
                if(!checkStringType(nextStr)){
                    throw new Exception(nextStr +"：不是可执行的数字变量或与预定义类型不匹配");
                }
            }else{
                //校验其它变量，,
                //暂时先不检测
            }
        }

        //第四步，处理流程，只能按流程关键字处理
        //1、查找流程关键字，可以根据需要追加,firstList,放到前面处理

        //2、按照汉字流程关键字将字符串拆分成数组
        String[] ruleStrs = resultRule.split(keyStr);
        //为了简单再转换一下吧
        List secondList = new ArrayList();
        for (String rStr:ruleStrs) {
            if(!"".equals(rStr)){
                secondList.add(rStr);
            }
        }
        //定义最终的返回结果
        StringBuffer resultStr = new StringBuffer();
        //如果有指定关键词，进行解析
        if(firstList.size()>0){
            for(int i=0;i<firstList.size();i++){
                if(!"那么".equals(firstList.get(i))){
                    resultStr.append(firstList.get(i)).append("(");
                    resultStr.append(secondList.get(i)).append(")");
                }else{
                    resultStr.append(firstList.get(i)).append("{return ");
                    resultStr.append(secondList.get(i)).append(";}");
                }
            }
        }else{
            //直接替换特殊符号，已经替换过了，直接返回原结果
            return resultRule;
        }
        return resultStr.toString();
    }

    /**
     * 检验字段类型，加减乘除两侧数值类型检测
     * 如果是数字，直接通过，如果是变量，1检验变量是否存在2变量类型是不是int,
     * @param str
     * @throws Exception
     */
    public static boolean checkStringType(String str){
        boolean flag = true;
        String pattern = "^\\d+(\\.\\d+)?";//匹配整数或小数
        if(str.replaceAll("\\\\s*","").equals("")){
            //1、如果是空字符串或空格，验证不通过
            flag = false;
        }else if(Pattern.matches(pattern, str.replaceAll("\\\\s*",""))){
            //如果是整数或小数，验证通过
            flag = true;
        }else{
            //从缓存里获取map集合，
            Map<String, DataSourceFieldVO> datasourceFieldVoMap = (Map) HttpContextUtils.getHttpServletRequest().getSession().getServletContext().getAttribute("datasourceFieldVoMap");
            //如果缓存没有，就调用service接口
            if(datasourceFieldVoMap==null){
                datasourceFieldVoMap = new HashMap<>();
                DatasourceFieldService datasourceFieldService = SpringContextUtils.getBean("datasourceFieldServiceImpl",DatasourceFieldService.class);
                //查询列表，转为Map，并放入缓存
                Map parameMap = new HashMap();
                parameMap.put("datasourceIds", null);
                parameMap.put("fieldOptions",null);
                List<DataSourceFieldVO> list  = datasourceFieldService.getListByDatasourceIdAndFieldOptions(parameMap);
                for (DataSourceFieldVO dataSourceFieldVO:list) {
                    datasourceFieldVoMap.put(dataSourceFieldVO.getFieldName(),dataSourceFieldVO);
                }
                HttpContextUtils.getHttpServletRequest().getSession().getServletContext().setAttribute("datasourceFieldVoMap",datasourceFieldVoMap);
            }
            //获得数据类型，进行比对
            if(datasourceFieldVoMap.get(str)!=null){
                if(datasourceFieldVoMap.get(str).getFieldType()!=null&&datasourceFieldVoMap.get(str).getFieldType().matches("int|tinyint|double|decimal")){
                    flag = true;
                }else{
                    flag = false;
                }
            }else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 测试表达式能否正确运行
     * @param ruleStr
     * @throws Exception
     */
    public static void testRunRule(String ruleStr)throws Exception{
        //表达式转换
        String result = resolveRuleStr(ruleStr);

        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("t_value_resource_trade#costPrice",1);
        context.put("b",2);
        context.put("c",3);
        String express = result;
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

    }

    public static void main(String[] args){

        String pattern = "in|not in|a";//匹配整数或小数
        String ceshiStr = "name in lisi,zhangsan or not in wangwu";
        String[] arr = ceshiStr.split(pattern);
        for (String a:arr) {
            System.out.println(a);
        }

    }
}
