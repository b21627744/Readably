package com.readably.service;

import java.util.List;

import com.readably.model.Campaign;
import com.readably.model.Product;

public interface CampaignService {

	List<Campaign> listAll();
	
	void save (Campaign campaign);
	
	Campaign getCampaign(Long id);
}
