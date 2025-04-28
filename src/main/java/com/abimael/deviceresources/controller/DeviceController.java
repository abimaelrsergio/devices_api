package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.constants.*;
import com.abimael.deviceresources.dto.*;
import com.abimael.deviceresources.exception.ResourceNotFoundException;
import com.abimael.deviceresources.service.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;

import java.net.*;
import java.util.*;

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
    @PostMapping("/create")
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
    @GetMapping("/fetch")
    public ResponseEntity<List<DeviceDto>> fetchDevices(@RequestParam(required = false) String brand,
                                                        @RequestParam(required = false) String state){
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
    @GetMapping("/fetch/{id}")
    public ResponseEntity<DeviceDto> fetchDeviceById(@PathVariable(name = "id") Long id){
        DeviceDto device = iDeviceService.fetchDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(device);
    }

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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable(name = "id") Long id) {
        iDeviceService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseDto(DevicesConstants.STATUS_204, DevicesConstants.MESSAGE_204));
    }
}
