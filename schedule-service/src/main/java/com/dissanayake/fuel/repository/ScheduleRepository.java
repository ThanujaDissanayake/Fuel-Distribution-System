package com.dissanayake.fuel.repository;

import com.dissanayake.fuel.model.OrderEvent;
import com.dissanayake.fuel.model.ScheduledOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduledOrder,String> {
}
