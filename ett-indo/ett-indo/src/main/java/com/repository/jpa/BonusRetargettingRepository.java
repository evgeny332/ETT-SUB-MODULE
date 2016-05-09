package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.BonusRetargetting;

@Repository("bonusRetargettingRepository")
public interface BonusRetargettingRepository extends JpaRepository<BonusRetargetting,String> {

	BonusRetargetting findByEttId(Long ettId);
}
