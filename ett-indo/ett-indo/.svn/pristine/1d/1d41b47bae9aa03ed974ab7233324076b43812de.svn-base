package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.AppKeyMap;


@Repository("appKeyMapRepository")
public interface AppKeyMapRepository extends JpaRepository<AppKeyMap,String> {
    List<AppKeyMap> findByAppKeyIn(List<String> appKeys);
}