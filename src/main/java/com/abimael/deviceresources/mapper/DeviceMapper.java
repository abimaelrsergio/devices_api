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
     * @return the populated device data transfer object
     */
    public static DeviceDto mapToDeviceDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setName(device.getName());
        deviceDto.setBrand(device.getBrand());
        deviceDto.setState(device.getState());
        return deviceDto;
    }

    /**
     * Maps a {@link DeviceDto} to a {@link Device} object.
     *
     * @param deviceDto the device data transfer object
     * @return the device entity with the data populated
     */
    public static Device mapToDevice(DeviceDto deviceDto) {
        Device device = new Device();
        device.setName(deviceDto.getName());
        device.setBrand(deviceDto.getBrand());
        device.setState(deviceDto.getState());
        return device;
    }
}
