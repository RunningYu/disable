package com.life.square.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.life.square.pojo.UserCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCollectionMapper extends BaseMapper<UserCollection> {

    @Select("select * from tb_disable_date_user_collect WHERE user_id = #{userId} and liked_id = #{likedId}")
    UserCollection getUserCollection(@Param("userId") String userId,@Param("likedId") String likedId);
}
