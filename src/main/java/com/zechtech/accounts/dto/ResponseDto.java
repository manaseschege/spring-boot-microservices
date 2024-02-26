package com.zechtech.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Response"
)
public class ResponseDto {
    @Schema(
             description = "status code in the response"
    )
    private String statusCode;
    @Schema(
            description = "status message in the response"
    )
    private String statusMsg;
}
