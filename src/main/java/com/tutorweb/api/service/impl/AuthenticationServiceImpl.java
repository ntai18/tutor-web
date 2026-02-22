package com.tutorweb.api.service.impl;
import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.model.dto.response.TokenResponse;
import com.tutorweb.api.model.dto.request.LoginRequest;
import com.tutorweb.api.model.dto.request.SignUpRequest;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.security.CustomUserDetail;
import com.tutorweb.api.service.AuthenticationService;
import com.tutorweb.api.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import static com.tutorweb.api.type.RoleType.USER;
import static com.tutorweb.api.type.TokenType.REFRESH_TOKEN;
import static com.tutorweb.api.type.UserStatusType.ACTIVE;
import static com.tutorweb.api.type.UserStatusType.BANNED;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if(user.getStatus() == BANNED)
            throw new AppException(ErrorCode.USR_011);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        CustomUserDetail userDetails = new CustomUserDetail(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

//    private boolean authenticate(LoginRequest loginRequest) {
//        var user =  userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USR_010));
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return  passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
//    }

    @Override
    public UserResponse signup(SignUpRequest signUpRequest) {
        User user = new User();
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent())
            throw new AppException(ErrorCode.USR_007);
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent())
            throw new AppException(ErrorCode.USR_009);
        user.setEmail(signUpRequest.getEmail());
        if (userRepository.findByPhone(signUpRequest.getPhone()).isPresent())
            throw new AppException(ErrorCode.USR_008);
        user.setPhone(signUpRequest.getPhone());
        user.setRole(USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(ACTIVE);
        userRepository.save(user);
        return UserResponse.builder()
                             .email(user.getEmail())
                             .phone(user.getPhone())
                             .roleType(user.getRole())
                             .username(user.getUsername())
                             .userStatusType(user.getStatus())
                             .build();
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        final String refreshToken = token.substring(7);
        final String username = jwtService.extractUsername(refreshToken , REFRESH_TOKEN);
        Optional<User> user = userRepository.findByUsername(username);
        CustomUserDetail customUserDetails = new CustomUserDetail(user.get());
        if(!jwtService.isValid(refreshToken , REFRESH_TOKEN ,customUserDetails))
            throw new AppException(ErrorCode.AUTH_006);
        String accessToken = jwtService.generateAccessToken(customUserDetails);
        return TokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
    }
}
