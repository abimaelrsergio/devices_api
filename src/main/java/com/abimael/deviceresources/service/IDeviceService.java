package com.abimael.deviceresources.service;

import com.abimael.deviceresources.dto.*;

import java.util.List;

public interface IDeviceService {

    DeviceDto createDevice(DeviceDto deviceDto);

    List<DeviceDto> fetchDevices(String brand, String state);

    DeviceDto fetchDeviceById(Long id);

    void deleteById(Long id);

    DeviceDto updateDevice(UpdateDeviceDto deviceDto);
}
