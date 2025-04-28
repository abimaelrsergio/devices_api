package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.dto.*;
import com.abimael.deviceresources.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
        logger.debug("DeviceController.createDevice: {}", deviceDto);
        iDeviceService.createDevice(deviceDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("200", "Device created successfully"));
    }

}
