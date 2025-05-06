package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.constants.DevicesConstants;
import com.abimael.deviceresources.dto.ErrorDto;
import com.abimael.deviceresources.dto.ResponseDto;
import com.abimael.deviceresources.dto.DeviceDto;
import com.abimael.deviceresources.exception.ResourceNotFoundException;
import com.abimael.deviceresources.exception.DeviceInUseException;
import com.abimael.deviceresources.service.IDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing devices through CRUD operations.
 * Provides endpoints to create, fetch, update, and delete devices.
 */
@Tag(
        name = "CRUD REST APIs for device management",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE devices"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final IDeviceService iDeviceService;

    public DeviceController(IDeviceService iDeviceService) {
        this.iDeviceService = iDeviceService;
    }

    /**
     * REST API to create a new device.
     *
     * @param deviceDto contains the device information
     * @return ResponseEntity containing the HTTP status code and a ResponseDto
     *         with the status code and message
     */
    @Operation(
            summary = "Create device",
            description = "REST API to create a new device"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    }
    )
    @PostMapping("/devices")
    public ResponseEntity<ResponseDto> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
        logger.debug("DeviceController.createDevice: {}", deviceDto);
        deviceDto = iDeviceService.createDevice(deviceDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(deviceDto.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(new ResponseDto(DevicesConstants.STATUS_201, DevicesConstants.MESSAGE_201));
    }

    /**
     * Retrieve devices from the database filtered by the given brand and/or state.
     *
     * @param brand the brand of the devices to filter by, or null to include all brands
     * @param state the state of the devices to filter by, or null to include all states
     *
     * @return a list of {@link DeviceDto} containing the matching devices
     */
    @Operation(
            summary = "Fetch device",
            description = "REST API to fetch devices"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    }
    )
    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDto>> fetchDevices(@RequestParam(required = false) String brand,
                                                        @RequestParam(required = false) String state){
        logger.debug("DeviceController.fetchDevices: {}, {}", brand, state);
        List<DeviceDto> devices = iDeviceService.fetchDevices(brand, state);
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

    /**
     * Retrieve a device by its ID.
     *
     * @param id the ID of the device to retrieve
     *
     * @return a {@link DeviceDto} containing the matching device
     * @throws ResourceNotFoundException if the device is not found
     */
    @Operation(
            summary = "Fetch device by Id",
            description = "REST API to fetch a device by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    }
    )
    @GetMapping("/devices/{id}")
    public ResponseEntity<DeviceDto> fetchDeviceById(@PathVariable(name = "id") Long id){
        logger.debug("DeviceController.fetchDeviceById: {}", id);
        DeviceDto device = iDeviceService.fetchDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(device);
    }

    /**
     * Deletes a device by its ID using the REST API.
     *
     * @param id the ID of the device to be deleted
     * @return a ResponseEntity containing the HTTP status code and a ResponseDto
     *         with the status code and message
     * @throws DeviceInUseException if the device is currently in use
     * @throws ResourceNotFoundException if the device is not found
     */
    @Operation(
            summary = "Delete device by Id",
            description = "REST API to delete a device based on its id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable(name = "id") Long id) {
        logger.debug("DeviceController.deleteById: {}", id);
        iDeviceService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseDto(DevicesConstants.STATUS_204, DevicesConstants.MESSAGE_204));
    }

    /**
     * Updates an existing device.
     *
     * @param updateDeviceDto contains the updated device information
     * @return ResponseEntity containing the HTTP status code and a ResponseDto
     *         with the status code and message
     * @throws ResourceNotFoundException if the device is not found
     * @throws DeviceInUseException if the device is currently in use
     */
    @Operation(
            summary = "Update device",
            description = "REST API to update a device"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    }
    )
    @PutMapping("/devices/{id}")
    public ResponseEntity<ResponseDto> updateDevice(@Valid @RequestBody DeviceDto updateDeviceDto, @PathVariable(name = "id") Long id){
        logger.debug("DeviceController.updateDevice: {}", updateDeviceDto);
        DeviceDto deviceUpdated = iDeviceService.updateDevice(updateDeviceDto, id);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(deviceUpdated.getId()).toUri();

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, uri.toString())
                .body(new ResponseDto(DevicesConstants.STATUS_200, DevicesConstants.MESSAGE_200));
    }
}
