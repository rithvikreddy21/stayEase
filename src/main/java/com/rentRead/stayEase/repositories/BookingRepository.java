package com.crio.stayEase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.stayEase.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
}
