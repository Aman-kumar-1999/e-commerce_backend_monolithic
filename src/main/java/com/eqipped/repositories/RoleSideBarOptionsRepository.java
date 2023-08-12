package com.eqipped.repositories;

import com.eqipped.entities.RoleSideBarOptions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleSideBarOptionsRepository extends MongoRepository<RoleSideBarOptions, String> {
}
