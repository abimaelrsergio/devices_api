package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.constants.*;
import com.abimael.deviceresources.dto.*;
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
     * Create a new device with the given information.
     *
     * @param deviceDto device information
     * @return a response with a status code of 201 and a message indicating that
     * the device was created successfully
     */
    @Operation(
            summary = "Create Device REST API",
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
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
        logger.debug("DeviceController.createDevice: {}", deviceDto);
        iDeviceService.createDevice(deviceDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
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
            summary = "Fetch Device REST API",
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
                            schema = @Schema(implementation = ErrorResponseDto.class)
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

    @GetMapping("/fetch/{id}")
    public ResponseEntity<DeviceDto> fetchDeviceById(@PathVariable Long id){
        DeviceDto device = iDeviceService.fetchDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(device);
    }
}
