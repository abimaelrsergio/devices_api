package com.abimael.deviceresources.service.impl;

import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.entity.Device;
import com.abimael.deviceresources.exception.*;
import com.abimael.deviceresources.mapper.DeviceMapper;
import com.abimael.deviceresources.repository.DeviceRepository;
import com.abimael.deviceresources.service.IDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

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

    /**
     * Retrieve devices from the database filtered by the given brand and/or state.
     *
     * @param brand the brand of the devices to filter by, or null to include all brands
     * @param state the state of the devices to filter by, or null to include all states
     * @return a list of {@link DeviceDto} containing the matching devices
     */
    @Override
    public List<DeviceDto> fetchDevices(String brand, String state) {
        return Optional.ofNullable(findByFiltersBrandAndState(brand, state))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(device -> DeviceMapper.mapToDeviceDto(device, new DeviceDto()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a device by its ID.
     *
     * @param id the ID of the device to retrieve
     * @return a {@link DeviceDto} containing the matching device
     * @throws ResourceNotFoundException if the device is not found
     */
    @Override
    public DeviceDto fetchDeviceById(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "id", id)
        );
        return DeviceMapper.mapToDeviceDto(device, new DeviceDto());
    }

    private List<Device> findByFiltersBrandAndState(String brand, String state) {
        Specification<Device> specification = Specification.where(null);
        if (brand != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        }
        if (state != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("state"), state));
        }
        return deviceRepository.findAll(specification);
    }
}
