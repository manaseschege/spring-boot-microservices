package com.zechtech.accounts.controller;

import com.zechtech.accounts.constants.AccountsConstants;
import com.zechtech.accounts.dto.CustomerDto;
import com.zechtech.accounts.dto.ErrorResponseDto;
import com.zechtech.accounts.dto.ResponseDto;
import com.zechtech.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "CRUD REST Apis for Account in ZechBank",
        description = "CRUD REST APIs to CREATE, UPDATE,FETCH AND DELETE account details"


)
@RestController
@RequestMapping(value = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;
@Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside ZechBank"
)
@ApiResponses(
        {
                @ApiResponse(
                        responseCode = "201",
                        description = "HTTP Status CREATED"
                ),
                @ApiResponse(
                       responseCode = "500",
                       description = "HTTP Status Internal Server Error",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponseDto.class
                                )
                        )
                )
        }
)

    @PostMapping("/create")
public ResponseEntity<ResponseDto>createAccount(@Valid @RequestBody CustomerDto customerDto){

    iAccountsService.createAccount(customerDto);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
}
    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API to Fetch  Customer & Account details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status PK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            ))
            )

    })
@GetMapping("/fetch")
public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern (regexp = "(^$|[0-9]{10})",message = "Mobile number must be a 10 digits") String mobileNumber){
CustomerDto customerDto=iAccountsService.fetchAccount(mobileNumber);
return ResponseEntity.status(HttpStatus.OK).body(customerDto);
}
    @Operation(
            summary = "Update Account REST API",
            description = "REST API to Update  Customer & Account details based on account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status PK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            ))
            )

    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to Delete  Customer & Account details based on phone number"
    )
    @ApiResponses({
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status PK"
    ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            ))
            )

    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|(0-9){10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
}