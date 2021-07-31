package com.macro.cloud.enums;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 14:28
 *
 * 操作类型枚举类
 *
 */
public enum OperationType {


    /** 登录 */
    LOGIN(0,"登录"),

    /** 登出*/
    LOGOUT(1,"登出"),

    /**
     * 添加
     **/
    ADD(2, "添加"),

    /**
     * 修改
     **/
    UPDATE(3, "修改"),

    /**
     * 删除
     **/
    DELETE(4, "删除"),

    /**
     * 详情查询
     **/
    INFO(5, "详情查询"),
    /**
     * 列表查询
     **/
    LIST(6, "列表查询"),
    /**
     * 分页列表
     **/
    PAGE(7, "分页列表"),
    /**
     * 其它查询
     **/
    OTHER_QUERY(8, "其它查询"),

    /**
     * 文件上传
     **/
    UPLOAD(9, "文件上传"),

    /**
     * 文件下载
     **/
    DOWNLOAD(10, "文件下载"),

    /**
     * Excel导入
     **/
    EXCEL_IMPORT(11, "Excel导入"),

    /**
     * Excel导出
     **/
    EXCEL_EXPORT(12, "Excel导出"),

    /**
     * 其它
     **/
    OTHER(99, "其它");


    private final Integer code;

    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    OperationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
