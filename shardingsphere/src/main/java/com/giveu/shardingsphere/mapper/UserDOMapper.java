package com.giveu.shardingsphere.mapper;

import com.giveu.shardingsphere.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDOMapper {
    UserDO selectByPrimaryKey(Long id);

    String selectNameById(Long id);
    void insert(UserDO userDO);
}