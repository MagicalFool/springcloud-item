package item.vip.dao;

import item.api.vip.entity.VipUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VipUserDAO {
    @Select("select  id,username,password,phone,email,created,updated from mb_user where id =#{userId}")
    VipUserEntity findByID(@Param("userId") Long userId);

    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(VipUserEntity userEntity);

    @Select("select  id,username,password,phone,email,created,updated from mb_user where username=#{username} and password=#{password}")
    VipUserEntity login(@Param("username")String userName,@Param("password")String passWord);
}
