package com.abimael.deviceresources.dto;

import com.abimael.deviceresources.util.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * {@code UpdateDeviceDto} represents the data transfer object for device information.
 * It contains fields for device name, brand, and state.
 */
@Data
@Schema(
        name = "Device",
        description = "Device object"
)
public class UpdateDeviceDto {

    @Schema(
            description = "Device ID",
            accessMode = Schema.AccessMode.READ_WRITE
    )
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;

    @Schema(
            description = "Device name"
    )
    @Size(min = 3, max = 50,  message = "Device name must be between 3 and 50 characters")
    private String name;

    @Schema(
            description = "Device brand"
    )
    @Size(min = 3, max = 100,  message = "Device brand must be between 3 and 100 characters")
    private String brand;

    @Schema(
            description = "Device state",
            example = "AVAILABLE",
            allowableValues = {"AVAILABLE", "IN_USE", "INACTIVE"}
    )
    private State state;
}
