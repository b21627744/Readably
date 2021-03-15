package com.readably.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readably.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{

}
