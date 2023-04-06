package com.ljj.service;

import com.ljj.NotFoundException;
import com.ljj.dao.TypeRepository;
import com.ljj.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{


    @Autowired
    private TypeRepository repository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return repository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return repository.getById(id);
    }

    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = repository.getById(id);
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return repository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Type> ListTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return repository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public List<Type> listType() {
        return repository.findAll();
    }
}
