package com.abimael.deviceresources.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * {@code ResponseDto} A standard structure for successful API responses, and contains
 * a status code and a status message providing details about the result of the request.
 *
 * @author Abimael R Sergio
 * @version 1.0
 */
@Schema(
        name = "Response",
        description = "Successful response comes here"
)
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code"
    )
    private String code;

    @Schema(
            description = "Status message"
    )
    private String message;

}
