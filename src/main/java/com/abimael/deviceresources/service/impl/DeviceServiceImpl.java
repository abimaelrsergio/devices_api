package com.abimael.deviceresources.service.impl;

import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.entity.Device;
import com.abimael.deviceresources.mapper.DeviceMapper;
import com.abimael.deviceresources.repository.DeviceRepository;
import com.abimael.deviceresources.service.IDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements IDeviceService {

    private DeviceRepository deviceRepository;

    /**
     * Create a new device using the given information.
     *
     * @param deviceDto device information
     */
    @Override
    public void createDevice(DeviceDto deviceDto) {
        Device device = DeviceMapper.mapToDevice(deviceDto, new Device());
        deviceRepository.save(device);
    }
}
