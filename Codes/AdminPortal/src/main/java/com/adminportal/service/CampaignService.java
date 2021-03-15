package com.adminportal.service;

import java.util.List;

import com.adminportal.model.Campaign;
import com.adminportal.model.Product;

public interface CampaignService {

	List<Campaign> listAll();
	
	void save (Campaign campaign);
	
	Campaign getCampaign(Long id);
}
