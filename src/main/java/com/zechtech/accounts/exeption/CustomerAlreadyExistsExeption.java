package com.zechtech.accounts.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsExeption extends RuntimeException{
public CustomerAlreadyExistsExeption(String message){
    super(message);
}

}
