package com.firattamur.spring_crawler.mapper;

public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);

}
