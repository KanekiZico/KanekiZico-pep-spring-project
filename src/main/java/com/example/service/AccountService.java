package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private List<Account> users = new ArrayList<>();
    private MessageService messageService;

    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }
    public Account register(Account newUser)  
    {
        if(newUser.getUsername().isBlank() || newUser.getPassword().length() < 4)
        {
            throw new IllegalArgumentException("Invalid Input!");
        }
        if(accountRepository.findAccountByUsername(newUser.getUsername()) != null)
        {
            throw new RuntimeException("Duplicate Username!");
        }
        users.add(newUser); 
        return accountRepository.save(newUser);

    }
    public Account login(Account account) throws AuthenticationException 
    {
        Account login = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(login != null)
        {
            return login;
        } 
        throw new AuthenticationException("Could not find user!");

    }
    
}
