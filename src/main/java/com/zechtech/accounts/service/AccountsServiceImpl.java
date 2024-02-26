package com.zechtech.accounts.service;

import com.zechtech.accounts.constants.AccountsConstants;
import com.zechtech.accounts.dto.AccountsDto;
import com.zechtech.accounts.dto.CustomerDto;
import com.zechtech.accounts.entity.Accounts;
import com.zechtech.accounts.entity.Customer;
import com.zechtech.accounts.exeption.CustomerAlreadyExistsExeption;
import com.zechtech.accounts.exeption.ResourceNotFoundException;
import com.zechtech.accounts.mapper.AccountsMapper;
import com.zechtech.accounts.mapper.CustomerMapper;
import com.zechtech.accounts.repository.AccountsRepository;
import com.zechtech.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
private AccountsRepository accountsRepository;
private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());

if(optionalCustomer.isPresent()){
    throw new CustomerAlreadyExistsExeption("Customer already registered with the given Number"+ customerDto.getMobileNumber());
}
Customer savedCustomer=customerRepository.save(customer);
accountsRepository.save(createNewAccount(savedCustomer));
    }



    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount= new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        Long randomAccNumber= 1000000000L + new Random().nextInt(900000000);

    newAccount.setAccountNumber(randomAccNumber);
newAccount.setAccountType(AccountsConstants.SAVINGS);
newAccount.setBranchAddress(AccountsConstants.ADDRESS);

return newAccount;

    }
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts= accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account", "mobileNumber", customer.getCustomerId().toString())
        );
        CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto= customerDto.getAccountsDto();
        if(accountsDto!=null){
          Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber())
                  .orElseThrow(()
                  -> new ResourceNotFoundException("Account","AccountNumber", accountsDto.getAccountNumber().toString()));

accountsDto.setAccountType(accountsDto.getAccountType()!=null ? accountsDto.getAccountType():accounts.getAccountType());
accountsDto.setBranchAddress(accountsDto.getBranchAddress()!=null ? accountsDto.getBranchAddress():accounts.getBranchAddress());

        AccountsMapper.mapToAccounts(accountsDto, accounts);
        accounts=accountsRepository.save(accounts);

        Long customerId= accounts.getCustomerId();
        Customer customer= customerRepository.findById(customerId).orElseThrow(
                ()->new ResourceNotFoundException("Customer","CustomerID", customerId.toString()));
        customerDto.setMobileNumber(customerDto.getMobileNumber()!=null?customerDto.getMobileNumber():customer.getMobileNumber());
        customerDto.setName(customerDto.getName()!=null?customerDto.getName():customer.getName());
        customerDto.setEmail(customerDto.getEmail()!=null?customerDto.getEmail():customer.getEmail());


        CustomerMapper.mapToCustomer(customerDto, customer);
     customerRepository.save(customer);
     isUpdated=true;

        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
