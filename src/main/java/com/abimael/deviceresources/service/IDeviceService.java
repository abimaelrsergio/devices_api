package com.abimael.deviceresources.service;

import com.abimael.deviceresources.dto.*;

public interface IDeviceService {

    /**
     * Create a new device using the given information.
     *
     * @param deviceDto device information
     */
    void createDevice(DeviceDto deviceDto);
}
