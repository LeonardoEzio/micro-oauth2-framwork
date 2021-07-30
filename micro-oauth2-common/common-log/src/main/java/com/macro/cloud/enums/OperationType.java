package com.macro.cloud.enums;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-30 14:28
 *
 * 操作类型枚举类
 *
 */
public enum OperationType {

    /**
     * 其它
     **/
    OTHER(0, "其它"),

    /**
     * 添加
     **/
    ADD(1, "添加"),

    /**
     * 修改
     **/
    UPDATE(2, "修改"),

    /**
     * 删除
     **/
    DELETE(3, "删除"),

    /**
     * 详情查询
     **/
    INFO(4, "详情查询"),
    /**
     * 列表查询
     **/
    LIST(5, "列表查询"),
    /**
     * 分页列表
     **/
    PAGE(6, "分页列表"),
    /**
     * 其它查询
     **/
    OTHER_QUERY(7, "其它查询"),

    /**
     * 文件上传
     **/
    UPLOAD(8, "文件上传"),

    /**
     * 文件下载
     **/
    DOWNLOAD(9, "文件下载"),

    /**
     * Excel导入
     **/
    EXCEL_IMPORT(10, "Excel导入"),

    /**
     * Excel导出
     **/
    EXCEL_EXPORT(11, "Excel导出");


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
