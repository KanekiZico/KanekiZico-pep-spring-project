package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import javassist.NotFoundException;

@Service
public class MessageService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public Message postMessage(Message message) 
    {
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255)
        {
            throw new IllegalArgumentException("Invalid Input for message text!");
        }
        if(accountRepository.findAccountByAccountId(message.getPostedBy()) == null)
        {
            throw new IllegalArgumentException("Invalid user!");
        }
        return messageRepository.save(message);
    }

    public List<Message> getMessages() 
    {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) 
    {
        return messageRepository.findById(messageId).orElse(null);
    }

    public String deleteMessageById(int messageId) 
    {
        Message message = messageRepository.findById(messageId).orElse(null);
        long countBefore = messageRepository.count();
        if(message != null)
        {
            messageRepository.deleteById(messageId);
            long countAfter = messageRepository.count();
            if(countBefore-countAfter == 1)
                return "1";
        }
        return "";
    }

    public String updateMessageById(int messageId, Message message) 
    {
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255)
        {
            throw new IllegalArgumentException("Message should not be blank or over 255 characters!");
        }
        Message found = messageRepository.findById(messageId).orElse(null);
        if(found != null)
        {
            found.setMessageText(message.getMessageText());
            messageRepository.save(found);
            return "1";
        }
        throw new IllegalArgumentException("Message not found, update not successful!");
    }

    public List<Message> getMessagesByUser(int accountId) 
    {
        return messageRepository.findMessagesByPostedBy(accountId);
    }
}
