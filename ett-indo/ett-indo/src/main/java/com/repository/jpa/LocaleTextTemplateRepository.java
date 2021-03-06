package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.LocaleTextTemplate;

@Repository("localeTextTemplateRepository")
public interface LocaleTextTemplateRepository extends JpaRepository<LocaleTextTemplate,Long>{

	
}
