package com.abimael.deviceresources.util;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enumeration representing the possible states of a device.
 */
@Schema(description = "Enumeration of possible device states")
public enum State {

    @Schema(description = "Device is available for use")
    AVAILABLE,

    @Schema(description = "Device is currently in use")
    IN_USE,

    @Schema(description = "Device is inactive")
    INACTIVE
}
