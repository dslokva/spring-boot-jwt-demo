package kz.example.controller;

import kz.example.config.JwtTokenUtil;
import kz.example.dto.UserDto;
import kz.example.dto.AuthTokenDto;
import kz.example.model.User;
import kz.example.service.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/userauth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody @Valid UserDto user) throws Exception {
        return ResponseEntity.ok(userAuthService.saveUser(user));
    }

    @RequestMapping(value = "/get-token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid UserDto loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getName(), loginUser.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userAuthService.findUser(loginUser.getName());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthTokenDto(token));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> delete(@PathVariable("id") long userId) throws Exception {
        if (userAuthService.deleteUser(userId))
            return ResponseEntity.ok("User deleted");
        else
            return ResponseEntity.internalServerError().body("Processing error");
    }

}
