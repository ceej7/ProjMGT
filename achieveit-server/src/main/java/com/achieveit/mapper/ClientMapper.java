package com.achieveit.mapper;

import com.achieveit.entity.Client;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMapper {
    @Select("Select * from client where cid = #{cid}")
    Client getByCid(int id);
}
