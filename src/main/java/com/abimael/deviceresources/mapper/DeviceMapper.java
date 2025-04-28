package com.abimael.deviceresources.mapper;

import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.entity.Device;

/**
 * Utility class for mapping between {@link Device} entities and {@link DeviceDto} objects.
 */
public class DeviceMapper {

    /**
     * Maps a {@link Device} entity to a {@link DeviceDto} object.
     *
     * @param device the device entity containing the data
     * @param deviceDto the device data transfer object to be populated
     * @return the populated device data transfer object
     */
    public static DeviceDto mapToDeviceDto(Device device, DeviceDto deviceDto) {
        deviceDto.setName(device.getName());
        deviceDto.setBrand(device.getBrand());
        deviceDto.setState(device.getState());
        return deviceDto;
    }

    /**
     * Maps a {@link DeviceDto} to a {@link Device} object.
     *
     * @param deviceDto the device data transfer object
     * @param device the device entity
     * @return the device entity with the data populated
     */
    public static Device mapToDevice(DeviceDto deviceDto, Device device) {
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());
        device.setState(deviceDto.getState());
        return device;
    }
}
