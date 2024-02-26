package com.zechtech.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
description = "Schema to hold Account information"
)
public class AccountsDto {
    @NotEmpty(message = "AccountNumber can not be a null or empty string")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
@Schema(
        description = "Account number of Zech Bank account",
        example = "1234567890"
)
    private Long accountNumber;
    @NotEmpty(message = "AccountType can not be a null or empty string")
    @Schema(
            description = "Account type of Zech Bank account", example = "Saving"
    )
    private String accountType;
    @NotEmpty(message = "BranchAddress can not be a null or empty string")
    @Schema(
            description = "Zech Bank address ",example ="123 NewYork"
    )
    private String branchAddress;
}
