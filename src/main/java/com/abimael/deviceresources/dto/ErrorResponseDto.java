package com.abimael.deviceresources.dto;

import io.swagger.v3.oas.annotations.media.*;
import lombok.*;
import org.springframework.http.*;

import java.time.*;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Error response comes here"
)
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private  String api;

    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus code;

    @Schema(
            description = "Error message representing the error happened"
    )
    private  String message;

    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime localDateTime;

}

