package com.abimael.deviceresources.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error",
        description = "Error response comes here"
)
public class ErrorDto {

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

