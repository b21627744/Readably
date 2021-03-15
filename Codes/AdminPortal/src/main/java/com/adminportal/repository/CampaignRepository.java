package com.adminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.model.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long>{

}
