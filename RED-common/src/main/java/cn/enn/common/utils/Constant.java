package cn.enn.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
    public static final String USER_SESSION_KEY="realName";
    /** 当前用户假设id */
    public static final String REALNAME = "孟飞";
    public static final String USERID = "10043017";
    /** 用户中心系统组织根节点编码 */
    public static final String ORGCODE = "10000000";
    /** 用户中心系统令牌 */
    public static final String APPSECRET = "1122aa";
    /**
     * 用户认证
     */
    public static final String AUTH = "http://ennuser-api-zhihuiyun.icloud.enncloud.cn/ennuser-api/s/api/account/auth";
    /**
     * 当前组织下的角色
     */
    public static final String  ROLEURL = "http://ennuser-api-zhihuiyun.icloud.enncloud.cn/ennuser-api/s/api/org/post/";
    /**
     * 组织树
     */
    public static final String  ORGTREE = "http://ennuser-api-zhihuiyun.icloud.enncloud.cn/ennuser-api/s/api/org/dynamictree/";
    /**
     * 当前组织下的员工
     */
    public static final String  EMPTREE = "http://ennuser-api-zhihuiyun.icloud.enncloud.cn/ennuser-api/s/api/org/employee/";
    public static final String  PAGESTR = "?page=1&size=10";
    public static final String  ERROR = "ErrorContext:";
    /** 成功 */
    public static final String SUCCESS = "success";
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
