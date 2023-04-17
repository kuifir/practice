package com.kuifir.batis;

public interface SqlSessionFactory {
    SqlSession openSession();

    MapperNode getMapperNode(String name);
}
