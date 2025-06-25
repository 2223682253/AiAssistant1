package com.example.gpt.controller;

import com.example.gpt.entity.ChatMessage;
import com.example.gpt.entity.Mask;
import com.example.gpt.repository.MaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(path="/mask")
public class MaskController {
    @Autowired
    private MaskRepository maskRepository;

    @PostMapping(path="/add_all")
    public @ResponseBody String addAllMasks (@RequestBody List<Mask> maskList) {

        for (Mask mask : maskList) {
            for (ChatMessage chatMessage : mask.getContext()) {
                chatMessage.setMask(mask);
            }
        }
        maskRepository.saveAll(maskList);
        return "SAVED";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Mask> getAllMasks() {
        return maskRepository.findAll();
    }
}
