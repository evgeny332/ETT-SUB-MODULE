package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserGameInfo;


@Repository("userGameInfoRepository")
public interface UserGameInfoRepository extends JpaRepository<UserGameInfo,Long> {
    UserGameInfo findByEttId(Long ettId);
}
