package com.crio.stayEase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.stayEase.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    
}
