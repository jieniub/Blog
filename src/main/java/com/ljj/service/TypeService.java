package com.ljj.service;

import com.ljj.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> ListType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    List<Type> ListTypeTop(Integer size);

    Type getTypeByName(String name);

    List<Type> listType();
}
