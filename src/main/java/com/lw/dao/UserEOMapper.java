package com.lw.dao;

import com.lw.model.entity.UserEO;
import org.apache.ibatis.annotations.Param;

public interface UserEOMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(UserEO record);

    int insertSelective(UserEO record);

    UserEO selectByPrimaryKey(String uuid);

    // @Parma中设置的name和password对应的是数据UserEO中的字段
    // 如果不设置@Param，UserEOMapper.xml中的selectByPrams这个sql语句中#{name,jdbcType=VARCHAR},name会找不到
    UserEO selectByPrams(@Param("name") String userName, @Param("password") String password);

    int updateByPrimaryKeySelective(UserEO record);

    int updateByPrimaryKey(UserEO record);
}