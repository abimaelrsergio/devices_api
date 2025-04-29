package com.abimael.deviceresources.service.impl;

import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.dto.UpdateDeviceDto;
import com.abimael.deviceresources.entity.Device;
import com.abimael.deviceresources.exception.*;
import com.abimael.deviceresources.mapper.DeviceMapper;
import com.abimael.deviceresources.repository.DeviceRepository;
import com.abimael.deviceresources.service.IDeviceService;
import com.abimael.deviceresources.util.State;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements IDeviceService {

    private final DeviceRepository deviceRepository;

    /**
     * Create a new device using the given information.
     *
     * @param deviceDto device information
     */
    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = DeviceMapper.mapToDevice(deviceDto);
        try {
            deviceRepository.save(device);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Device");
        }
        return DeviceMapper.mapToDeviceDto(device);
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
                .map(DeviceMapper::mapToDeviceDto)
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
        checkArgument(isNotEmpty(id), "Id cannot be null");
        Device device = deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "id", id)
        );
        return DeviceMapper.mapToDeviceDto(device);
    }

    /**
     * Deletes a device by its ID using the service layer.
     *
     * @param id the ID of the device to be deleted
     * @throws ResourceNotFoundException if the device is not found
     * @throws DeviceInUseException if the device is currently in use
     */
    @Override
    public void deleteById(Long id) {
        DeviceDto deviceDto = fetchDeviceById(id);
        checkNotInUse(deviceDto.getState(), id);
        try {
            deviceRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Device");
        }
    }

    /**
     * Updates a device by its ID.
     *
     * @param deviceDto contains the updated device information
     * @return the updated device
     * @throws ResourceNotFoundException if the device is not found
     * @throws DeviceInUseException if the device is currently in use
     */
    @Override
    public DeviceDto updateDevice(UpdateDeviceDto deviceDto) {
        checkArgument(isNotEmpty(deviceDto.getId()), "Id cannot be null");
        Device device = deviceRepository.findById(deviceDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Device", "id", deviceDto.getId())
        );
        if (StringUtils.isNoneBlank(deviceDto.getName()) && !device.getName().equals(deviceDto.getName())) {
            checkNotInUse(device.getState(), deviceDto.getId());
            device.setName(deviceDto.getName());
        }
        if (StringUtils.isNoneBlank(deviceDto.getBrand()) && !device.getBrand().equals(deviceDto.getBrand())) {
            checkNotInUse(device.getState(), deviceDto.getId());
            device.setBrand(deviceDto.getBrand());
        }
        if (deviceDto.getState() != null && !device.getState().equals(deviceDto.getState())) {
            device.setState(deviceDto.getState());
        }
        try {
            deviceRepository.save(device);
        } catch (DataAccessException ex) {
            throw new DatabaseException("Device");
        }
        return DeviceMapper.mapToDeviceDto(device);
    }

     // Checks if the given device is in use and throws a DeviceInUseException if that's the case.
    private static void checkNotInUse(State device, Long deviceDto) {
        if (device == State.IN_USE) {
            throw new DeviceInUseException("Device", "id", deviceDto);
        }
    }

    // Retrieves a list of devices filtered by the specified brand and state.
    private List<Device> findByFiltersBrandAndState(String brand, String state) {
        Specification<Device> specification = Specification.where(null);
        if (StringUtils.isNotBlank(brand)) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("brand"), brand));
        }
        if (StringUtils.isNotBlank(state)) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("state"), state));
        }
        return deviceRepository.findAll(specification);
    }
}
