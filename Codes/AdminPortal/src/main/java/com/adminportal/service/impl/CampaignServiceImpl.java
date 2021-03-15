package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.model.Campaign;
import com.adminportal.repository.CampaignRepository;
import com.adminportal.service.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService{

	@Autowired
	private CampaignRepository campaignRepository;
	
	
	public List<Campaign> listAll() {
		return campaignRepository.findAll();
	}


	@Override
	public Campaign getCampaign(Long id) {
		return campaignRepository.findById(id).get();
	}


	@Override
	public void save(Campaign campaign) {
		campaignRepository.save(campaign);
	}
}
