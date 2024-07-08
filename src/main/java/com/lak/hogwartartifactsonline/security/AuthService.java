package com.lak.hogwartartifactsonline.security;

import com.lak.hogwartartifactsonline.hogwartsuser.HogwartsUser;
import com.lak.hogwartartifactsonline.hogwartsuser.MyPrincipal;
import com.lak.hogwartartifactsonline.hogwartsuser.converter.UserToUserDtoConverter;
import com.lak.hogwartartifactsonline.hogwartsuser.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserToUserDtoConverter userToUserDtoConverter;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    public Map<String,Object> createLoginInfo(Authentication authentication) {
        //create user info
        MyPrincipal principal= (MyPrincipal)authentication.getPrincipal();
        HogwartsUser user= principal.getHogwartsUser();
        UserDto userDto= this.userToUserDtoConverter.convert(user);

        //create access token
        String token= this.jwtProvider.createToken(authentication);

        Map<String,Object>  loginResultMap= new HashMap<>();
        loginResultMap.put("userInfo",userDto);
        loginResultMap.put("accessToken",token);
        return loginResultMap;
    }
}
