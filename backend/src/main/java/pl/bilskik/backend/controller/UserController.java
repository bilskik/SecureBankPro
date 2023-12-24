package pl.bilskik.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.data.dto.UserDetailsDTO;
import pl.bilskik.backend.service.UserService;

import java.security.Principal;

import static pl.bilskik.backend.controller.mapping.RequestPath.USER_DETAILS_PATH;
import static pl.bilskik.backend.controller.mapping.RequestPath.USER_PATH;

@RestController
@RequestMapping(value = USER_PATH)
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(Principal principal) {
        log.info(principal.getName());
        return "OK - sesja działa poprawnie";
    }
    @GetMapping()
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }
    @GetMapping(value = USER_DETAILS_PATH)
    public ResponseEntity<UserDetailsDTO> getUserDetails(Principal principal) {
        return ResponseEntity.ok(userService.getUserDetails(principal.getName()));
    }

}
