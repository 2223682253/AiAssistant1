package com.example.gpt.controller;

import com.example.gpt.entity.ChatMessage;
import com.example.gpt.entity.ChatSession;
import com.example.gpt.repository.ChatMessageRepository;
import com.example.gpt.repository.ChatSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path="/session")
public class ChatSessionController {
    @Autowired
    private ChatSessionRepository sessionRepository;
    @Autowired
    private ChatMessageRepository messageRepository;

    @GetMapping("/get")
    public @ResponseBody ChatSession getSessionById(@RequestParam String id){
        Optional<ChatSession> session = sessionRepository.findById(id);
        if( session.isPresent()){
            return session.get();
        }else {
            return null;
        }
    }
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addSession (@RequestBody ChatSession session) {
        sessionRepository.save(session);
        return "Saved";
    }

    @PostMapping(path="/message/add") // Map ONLY POST Requests
    public @ResponseBody String addMessage (@RequestParam String sessionId, @RequestBody ChatMessage message) {
        ChatSession session = sessionRepository.findById(sessionId).get();
        message.setSession(session);
        messageRepository.save(message);
        return "Saved";
    }

    @PostMapping(path="/delete") // Map ONLY POST Requests
    public @ResponseBody String deleteSession (@RequestParam String sessionId) {
        sessionRepository.deleteById(sessionId);
        return "Deleted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<ChatSession> getAllSessions() {
        return sessionRepository.findAll();
    }
}
