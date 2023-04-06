package com.ljj.dao;

import com.ljj.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {
    public Type getByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
