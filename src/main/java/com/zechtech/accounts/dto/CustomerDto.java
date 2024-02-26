package com.zechtech.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = " Schema to hold Customer and Account information"
)
public class CustomerDto {
    @Schema(
            description = "Name of the Customer",example = "Manasses Maina"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30")
    private String name;
    @Schema(
            description = "Email of the Customer",example = "manaseschege0@gmail.com"
    )
    @NotEmpty(message = " Email address cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;
    @Schema(
            description = "PhoneNumber of the Customer",example = "0792805686"
    )
   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
