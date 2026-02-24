package com.crm.search.infra.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

public interface TestMyBatisMapper {

    String selectJdbcUrl();

    int insertName(@Param("name") String name);

    int countAll();
}
