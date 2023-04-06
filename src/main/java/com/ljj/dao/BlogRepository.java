package com.ljj.dao;

import com.ljj.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id= ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.updateDate,'%Y') as year from Blog b group by function('date_format',b.updateDate,'%Y') order by function('date_format',b.updateDate,'%Y') desc")
    List<String> findGroupYear();


    @Query("select b from Blog b where function('date_format',b.updateDate,'%Y') = ?1")
    List<Blog> findByYear(String year);
}