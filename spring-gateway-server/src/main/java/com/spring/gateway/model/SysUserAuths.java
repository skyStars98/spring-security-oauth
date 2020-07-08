package com.spring.gateway.model;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: daiguoqing
 * @Date: 2020-06-13
 * @Time: 11:03
 * @Version: eoms-micro-service 1.0
 */
@Data
public class SysUserAuths{
    private static final long serialVersionUID = -6842219188757508197L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImage;

    /**
     * 所属系统id
     */
    private Long ownSystemId;

    /**
     * 职务id
     */
    private Long postId;

    /**
     * 工作范围
     */
    private String workScope;

    /**
     * 联系电话
     */
    private Long phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 聘用日期
     */
    private LocalDateTime hireDate;

    /**
     * 终止日期
     */
    private LocalDateTime endDate;

    /**
     * 详细信息
     */
    private String details;

    /**
     * 人员状态 0：正常 1：解雇（归档）
     */
    private Integer status;

    /**
     * 业务字段 角色
     */
    private String role;

}
