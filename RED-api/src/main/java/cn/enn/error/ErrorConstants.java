package cn.enn.error;

/**
 * @Author: tiantao
 * @Date: 2019/10/11 9:09 AM
 * @Version 1.0
 */
public  enum ErrorConstants {
    AUTHENCATE_FAILURE(401, "登录失败，请检查用户名/密码是否正确!"),
    CONTEXT_FAILURE(101, "上下文错误"),
    DEFAULT_TYPE(103, "系统错误"),
    CONSTRAINT_VIOLATION_TYPE(104, "/contraint-violation"),
    PARAMETERIZED_TYPE(105, "参数不合法"),
    LOGIN_NOT_EXISTS(110, "账号不存在"),
    INVALID_PASSWORD_TYPE(111, "密码无效"),
    EMAIL_ALREADY_USED_TYPE(112, "邮箱已注册"),
    USERNAME_ALREADY_USED_TYPE(113, "用户名已注册"),
    EMAIL_NOT_FOUND_TYPE(114, "邮箱不存在"),
    PHONE_ALREADY_USED_TYPE(115, "手机号已注册"),
    USERID_EXISTS(116, "用户ID已存在"),
    PHONE_NOT_FOUND_TYPE(117, "手机号不存在"),
    REGISTER_USER_FAILURE(118, "注册用户失败"),
    CHANGE_PASSWORD_FAILURE(119, "当前用户不能修改密码!"),
    RESET_PASSWORD_FAILURE(120, "用户不存在或非自建用户,重置密码失败"),
    USERNAME_OR_PASSWOR_WRONG(121, "用户名或密码错误"),
    CHANGE_USERNAME_FAILURE(122, "用户名只能输入[a-z或A-Z或0-9或_]"),
    CURRENT_PASSWORD_WRONG(123, "当前密码错误"),
    DELETE_APP_USER_FAILURE(124, "用户不存在或非自建用户,自建用户删除失败"),
    NO_USER(125, "用户不存在"),
    ACCOUNT_ACTIVITED_DISABLE(126, "当前用户无访问权限,请联系管理员!"),
    INVALID_SMS_MESSAGE_FAILURE(127, "短信验证码错误"),
    DISABLE_USER_FAILURE(128, "禁用用户失败"),
    DISABLE_ORG_FAILURE(129, "禁用组织失败"),
    ORG_NAME_FAILURE(130, "组织名称不为空"),
    ORG_ALREADY_USED_TYPE(131, "组织已存在"),
    PARENT_ORG_NOT_EXIST(132, "父组织不存在"),
    COMPANY_NAME_FAILURE(133, "企业名称错误!"),
    COMPANY_CODE_FAILURE(134, "企业编码错误!"),
    COMPANY_CODE_NULL(135, "企业编码为空!"),
    ORG_SAVE_FAILURE(136, "组织保存失败"),
    ORG_NOT_EXIST(137, "组织不存在"),
    ROLE_AUDIT_FAILURE(138, "角色审批失败!"),
    ROLE_TRUST_FAILURE(139, "角色授权失败!"),
    CREATE_USER_FAILURE(140, "创建用户失败"),
    UPGRADE_USER_FAILURE(141, "企业认证失败"),
    NO_USER_FIND(142,"用户不存在"),
    POST_NOT_EXIST(143, "职位信息不存在"),
    COMPANY_NOT_EXIST(144, "公司信息不存在"),
    QUERY_USERLIST_FAILURE(150, "查询用户列表失败"),
    QUERY_USER_FAILURE(151, "查询用户失败"),
    UPDATE_USER_FAILURE(152, "修改用户失败"),
    DELETE_USER_FAILURE(153, "删除用户失败"),
    QUERY_USER_MENUS_FAILURE(171, "查询用户菜单列表失败"),
    QUERY_APPLICATION_FAILURE(181, "查询应用信息失败"),
    APPLICATION_NOT_EXIST(182, "应用不存在"),
    SYNC_RESOURCE_FAILURE(199, "同步资源失败"),
    IT_CODE_IS_NULL(201, "itCode不能为空"),
    EMP_NO_IS_NULL(202, "员工编号不能为空"),
    EMP_STATE_IS_NULL(203, "员工状态不能为空"),
    NAME_MISMATCH(204, "身份与姓名不匹配"),
    EMP_ADD_ERROR(903, "ad用户添加失败"),
    EMP_ADD_MAIL_OPEN_ERROR(906, "ad用户添加成功，邮箱开通失败"),
    OPEN_ERROR(908, "邮箱开通失败"),
    EMP_ALREADY_ADD(904, "用户已存在"),
    ACCOUNT_NOT_ONLY(905, "账号不唯一,请联系管理员"),
    UNKONWN_ERROR(999, "未知错误"),
    AD_ERROR(1999, "ad服务异常"),
    APP_SECRET_ERROR(4001, "appSecret错误"),
    ORG_NOT_EMPTY(4002, "登录失败，请补充企业code!"),
    ORG_NOT_FOUND_TYPE(4003, "登录失败，请检查企业code是否正确!"),
    TOKEN_INVALID(40001, "token无效"),
    MISSING_ARGS(10001, "缺少必填参数: {} => {}"),
    LDAP_OPERATION_FAILURE(20001, "ldap操作失败"),
    LDAP_CREATE_FAILURE(20002, "ldap编辑失败"),
    LDAP_CONNECT_FAILURE(20003, "ldap连接失败!"),
    QUERY_ROLES_FAILURE(60001, "查询角色列表失败"),
    APPID_NOT_FOUND_TYPE(60002, "接入系统信息不存在"),
    CREATE_ROLE_FAILURE(60003, "创建角色失败"),
    ROLE_EXISTS(60004, "角色编码已存在"),
    ROLE_NOT_FOUND_TYPE(60005, "角色编码不存在"),
    UPDATE_ROLE_FAILURE(60003, "创建角色失败"),
    QUERY_ROLE_RESOURCE_FAILURE(60006, "角色编码不存在"),
    DELETE_ROLE_FAILURE(60007, "角色被引用,删除失败!"),
    ROLE_DISTRIBUTION_FAILURE(60008, "角色分配失败!");
    private int code;

    private String message;

    private ErrorConstants(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
