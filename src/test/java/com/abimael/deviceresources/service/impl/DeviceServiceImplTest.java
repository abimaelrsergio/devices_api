package com.abimael.deviceresources.service.impl;

import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.dto.UpdateDeviceDto;
import com.abimael.deviceresources.entity.Device;
import com.abimael.deviceresources.exception.ResourceNotFoundException;
import com.abimael.deviceresources.exception.DeviceInUseException;
import com.abimael.deviceresources.repository.DeviceRepository;
import com.abimael.deviceresources.util.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

/**
 * Unit tests for {@link DeviceServiceImpl} using Mockito.
 * Verifies creation, retrieval, updating, and deletion of devices.
 * Mocks the {@link DeviceRepository} to isolate service layer behavior.
 */
class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private AutoCloseable autoCloseable;

    /**
     * Initialize the Mockito annotations and store the closeable object.
     */
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    /**
     * Clean up resources after each test by closing the AutoCloseable instance.
     */
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * This test verifies that a device can be created with a given set of properties.
     */
    @Test
    void shouldCreateDevice() {
        DeviceDto dto = new DeviceDto();
        dto.setName("Device name");
        dto.setBrand("Device brand");
        dto.setState(State.AVAILABLE);

        Device device = new Device();
        device.setName("Device name");
        device.setBrand("Device brand");
        device.setState(State.AVAILABLE);
        device.setCreatedBy("TEST_USER");
        device.setCreatedAt(LocalDateTime.now());

        when(deviceRepository.save(any(Device.class))).thenReturn(device);
        DeviceDto createdDevice = deviceService.createDevice(dto);
        assertNotNull(createdDevice);
        assertEquals(dto.getName(), createdDevice.getName(), "Device name does not match");
        assertEquals(dto.getBrand(), createdDevice.getBrand(), "Device brand does not match");
        assertEquals(dto.getState(), createdDevice.getState(), "Device state does not match");
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    /**
     * Tests that the fetchDeviceById method successfully retrieves a device
     * when it exists in the repository.
     */
    @Test
    void shouldFetchDeviceByIdWhenExists() {
        Device device = new Device();
        device.setId(1L);
        device.setName("Device name");
        device.setBrand("Device brand");
        device.setState(State.AVAILABLE);
        device.setCreatedBy("TEST_USER");
        device.setCreatedAt(LocalDateTime.now());

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        DeviceDto result = deviceService.fetchDeviceById(1L);
        assertNotNull(result);
        assertEquals(device.getName(), result.getName(), "Device name does not match");
        assertEquals(device.getBrand(), result.getBrand(), "Device brand does not match");
        assertEquals(device.getState(), result.getState(), "Device state does not match");
        verify(deviceRepository, times(1)).findById(1L);
    }

    /**
     * This test verifies that a {@link ResourceNotFoundException} is thrown.
     */
    @Test
    void shouldThrowExceptionWhenDeviceNotFoundById() {
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deviceService.fetchDeviceById(1L));
        verify(deviceRepository, times(1)).findById(1L);
    }

    /**
     * This test verifies that all devices are fetched from the repository when
     * no brand or state filter is provided.
     */
   @Test
    void shouldFetchDevicesWhenNoFilter() {
       Specification<Device> specification = Specification.where(null);
       when(deviceRepository.findAll(specification)).thenReturn(buildDevices());
       List<DeviceDto> devices = deviceService.fetchDevices(null, null);
       assertNotNull(devices);
       verify(deviceRepository, times(1)).findAll(specification);
    }

    /**
     * This test verifies that a device can be deleted by its ID when the state of
     * the device is not IN_USE.
     */
    @Test
    void shouldDeleteDeviceWhenNotInUse() {
        Device device = new Device();
        device.setId(1L);
        device.setName("Device name");
        device.setBrand("Device brand");
        device.setState(State.AVAILABLE);
        device.setCreatedBy("TEST_USER");
        device.setCreatedAt(LocalDateTime.now());
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        deviceService.deleteById(1L);
        verify(deviceRepository, times(1)).deleteById(1L);
    }

    /**
     * This test verifies that attempting to delete a device with a state of IN_USE
     * results in a DeviceInUseException being thrown.
     */
    @Test
    void shouldThrowDeviceInUseExceptionWhenDeleteDeviceInUse() {
        Device device = new Device();
        device.setId(1L);
        device.setName("Device name");
        device.setBrand("Device brand");
        device.setCreatedBy("TEST_USER");
        device.setCreatedAt(LocalDateTime.now());
        device.setState(State.IN_USE);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        assertThrows(DeviceInUseException.class, () -> deviceService.deleteById(1L));
        verify(deviceRepository, never()).deleteById(1L);
    }

    /**
     * This test verifies that the {@code updateDevice} method successfully updates
     * a device if the device is not IN_USE.
     */
    @Test
    void shouldUpdateDeviceWhenValid() {
        Device device = new Device();
        device.setId(1L);
        device.setName("OldName");
        device.setBrand("OldBrand");
        device.setState(State.AVAILABLE);

        Device deviceExpected = new Device();
        deviceExpected.setId(1L);
        deviceExpected.setName("NewName");
        deviceExpected.setBrand("NewBrand");
        deviceExpected.setState(State.IN_USE);

        UpdateDeviceDto updateDto = new UpdateDeviceDto();
        updateDto.setId(1L);
        updateDto.setName("NewName");
        updateDto.setBrand("NewBrand");
        updateDto.setState(State.IN_USE);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(deviceExpected);
        DeviceDto updatedDevice = deviceService.updateDevice(updateDto);
        assertNotNull(updatedDevice);
        assertEquals(deviceExpected.getName(), updatedDevice.getName(), "Device name does not match");
        assertEquals(deviceExpected.getBrand(), updatedDevice.getBrand(), "Device brand does not match");
        assertEquals(deviceExpected.getState(), updatedDevice.getState(), "Device state does not match");
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    /**
     * Tests that the {@code updateDevice} method throws a {@link ResourceNotFoundException}
     * if the device to be updated does not exist.
     */
    @Test
    void shouldThrowExceptionWhenUpdateNonExistingDevice() {
        UpdateDeviceDto updateDto = new UpdateDeviceDto();
        updateDto.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> deviceService.updateDevice(updateDto));
    }

    /**
     * Verifies that an {@link IllegalArgumentException} is thrown
     * when attempting to fetch a device by a null ID.
     */
    @Test
    void shouldThrowIllegalArgumentExceptionWhenFetchDeviceByIdWithNull() {
        assertThrows(IllegalArgumentException.class, () -> deviceService.fetchDeviceById(null));
    }

    // Returns a list of devices used in unit tests.
    private static List<Device> buildDevices() {
        Device device = new Device();
        device.setId(1L);
        device.setName("Device name");
        device.setBrand("Device brand");
        device.setState(State.AVAILABLE);
        device.setCreatedBy("TEST_USER");
        device.setCreatedAt(LocalDateTime.now());

        Device device2 = new Device();
        device2.setId(2L);
        device2.setName("Device name");
        device2.setBrand("Device brand");
        device2.setState(State.AVAILABLE);
        device2.setCreatedBy("TEST_USER");
        device2.setCreatedAt(LocalDateTime.now());

        Device device3 = new Device();
        device3.setId(3L);
        device3.setName("Device name");
        device3.setBrand("Device brand");
        device3.setState(State.AVAILABLE);
        device3.setCreatedBy("TEST_USER");
        device3.setCreatedAt(LocalDateTime.now());

        return List.of(device, device2, device3);
    }
}
