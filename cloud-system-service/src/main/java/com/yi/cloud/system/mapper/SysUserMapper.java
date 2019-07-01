package com.yi.cloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yi.cloud.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表持久层
 * @author chenguoyi
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
