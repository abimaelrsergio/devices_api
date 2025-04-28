package com.abimael.deviceresources.repository;

import com.abimael.deviceresources.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
