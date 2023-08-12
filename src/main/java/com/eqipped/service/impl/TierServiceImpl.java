package com.eqipped.service.impl;

import com.eqipped.entities.Tier;
import com.eqipped.repositories.TierRepository;
import com.eqipped.service.TierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierServiceImpl implements TierService {

    @Autowired
    private TierRepository tierRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Tier> getAllTier() {
        return tierRepository.findAll();
    }

    @Override
    public List<Tier> getAllTierByTierCode(String tierCode) {

        Query query = new Query();
        query.addCriteria(Criteria.where("tierCode").is(tierCode));

        return mongoTemplate.find(query,Tier.class);
    }

    @Override
    public Tier createTier(Tier tier) {
        tierRepository.save(tier);
        return tier;
    }

    @Override
    public Tier updateTier(Tier tier) {
        Tier tier1 = tierRepository.findById(tier.getId()).get();
        if(tier1 != null){
            tier1 = tier;
            tierRepository.save(tier1);
        }
        return tier1;
    }

    @Override
    public void deleteTier(String id) {

        tierRepository.deleteById(id);

    }
}
