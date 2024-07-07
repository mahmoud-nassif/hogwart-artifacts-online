package com.lak.hogwartartifactsonline.security;

import com.lak.hogwartartifactsonline.system.Result;
import com.lak.hogwartartifactsonline.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${baseUrl}/users")
public class AuthController {
    private final AuthService authService;
    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication){
        logger.debug("Authenticated User '{}':",authentication.getName());
        return new Result(true, StatusCode.SUCCESS,"User Info and Token",this.authService.createLoginInfo(authentication));
    }
}
