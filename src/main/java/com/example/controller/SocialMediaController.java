package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService)
    {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("register")
    // @RequestMapping(value = "register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account){
        Account created = accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK)
                .body(created);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handle(IllegalArgumentException e)
    {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handle(RuntimeException e)
    {
        return e.getMessage();
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException
    {
        Account login = accountService.login(account);
        return ResponseEntity.status(HttpStatus.OK).body(login);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handle(AuthenticationException e)
    {
        return e.getMessage();
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message message)
    {   Message posted = messageService.postMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(posted);
    }

    @GetMapping("messages")
    public @ResponseBody List<Message> getMessages()
    {
        return messageService.getMessages();
    }

    @GetMapping("messages/{messageId}")
    public @ResponseBody Message getMessageById(@PathVariable int messageId) 
    {
        return messageService.getMessageById(messageId);
        
    }

    @DeleteMapping("messages/{messageId}")
    public @ResponseBody String deleteMessageById(@PathVariable int messageId)
    {
        return messageService.deleteMessageById(messageId);
    } 

    @PatchMapping("messages/{messageId}")
    public @ResponseBody String updateMessageById(@PathVariable int messageId, @RequestBody Message message)
    {
        return messageService.updateMessageById(messageId, message);
    }

    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody List<Message> getMessagesByUser(@PathVariable int accountId)
    {
        return messageService.getMessagesByUser(accountId);
    }
    // @ExceptionHandler({RuntimeException.class, CustomException.class})
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public @ResponseBody String handleNotFound(RuntimeException ex) {
    //     return ex.getMessage();
    // }
    

    

}
