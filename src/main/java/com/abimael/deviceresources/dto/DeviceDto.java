package com.abimael.deviceresources.dto;

import com.abimael.deviceresources.util.State;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * {@code DeviceDto} represents the data transfer object for device information.
 * It contains fields for device name, brand, and state, with validation constraints.
 * The {@code state} field must match one of the predefined values (AVAILABLE, IN_USE, INACTIVE).
 */
@Data
@Schema(
        name = "Device",
        description = "Device object"
)
public class DeviceDto {

    @Schema(
            description = "Device ID"
    )
    private Long id;

    @Schema(
            description = "Device name"
    )
    @NotEmpty(message = "Device name cannot be empty")
    @Size(min = 3, max = 50,  message = "Device name must be between 3 and 50 characters")
    private String name;

    @Schema(
            description = "Device brand"
    )
    @NotEmpty(message = "Device brand cannot be empty")
    @Size(min = 3, max = 100,  message = "Device brand must be between 3 and 100 characters")
    private String brand;

    @Schema(
            description = "Device state",
            example = "AVAILABLE",
            allowableValues = {"AVAILABLE", "IN_USE", "INACTIVE"}
    )
    @NotNull(message = "Device state cannot be null")
    private State state;
}
