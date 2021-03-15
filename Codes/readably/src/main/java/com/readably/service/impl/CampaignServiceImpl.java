package com.readably.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readably.model.Campaign;
import com.readably.repository.CampaignRepository;
import com.readably.service.CampaignService;

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
