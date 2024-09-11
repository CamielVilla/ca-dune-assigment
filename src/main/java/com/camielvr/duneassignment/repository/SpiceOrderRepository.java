package com.camielvr.duneassignment.repository;

import com.camielvr.duneassignment.domain.entities.SpiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpiceOrderRepository extends JpaRepository<SpiceOrder, Long> {

}
