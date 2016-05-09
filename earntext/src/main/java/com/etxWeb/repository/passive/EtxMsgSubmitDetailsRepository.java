package com.etxWeb.repository.passive;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.etxWeb.entity.passive.EtxMsgSubmitDetails;

public interface EtxMsgSubmitDetailsRepository extends Repository<EtxMsgSubmitDetails, Long> {
		
		public List<EtxMsgSubmitDetails> findByCreatedTimeIST(String createdTimeIST);
		
		@Query("select distinct(createdTimeIST) from EtxMsgSubmitDetails order by createdTimeIST desc")
		public List<String> findDistinctCreatedTimeIST();
}
