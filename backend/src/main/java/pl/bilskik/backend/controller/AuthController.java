package pl.bilskik.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
@CrossOrigin
public class AuthController {

    @GetMapping()
    public String test() {
        return  "DZIALA?";
    }
}
