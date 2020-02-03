package com.easyfly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Location;
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

}
