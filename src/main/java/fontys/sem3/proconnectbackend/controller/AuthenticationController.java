package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.*;
import fontys.sem3.proconnectbackend.business.exeptions.EmailAlreadyExistsException;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidGoogleAuthException;
import fontys.sem3.proconnectbackend.business.usecases.auth.AuthenticateUserUseCase;
import fontys.sem3.proconnectbackend.business.usecases.auth.AuthenticateWithGoogleUseCase;
import fontys.sem3.proconnectbackend.business.usecases.auth.RefreshTokenUseCase;
import fontys.sem3.proconnectbackend.business.usecases.user.CreateUserUseCase;
import fontys.sem3.proconnectbackend.configuration.OAuth2Service;
import fontys.sem3.proconnectbackend.configuration.security.DataOwnershipValidator;
import fontys.sem3.proconnectbackend.persistence.entity.ServiceEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final OAuth2Service oAuth2Service;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody CreateUserRequest request
    ) {
        try {
            createUserUseCase.createUser(request);
        } catch (EmailAlreadyExistsException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        try {
            LoginResponse authResponse = authenticateUserUseCase.authenticate(request);

            Cookie accessTokenCookie = new Cookie("accessTokenCookie", authResponse.getAccessToken());
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 1 day
            accessTokenCookie.setPath("/");
            accessTokenCookie.setDomain("localhost");
            accessTokenCookie.setHttpOnly(true);  // Make the cookie inaccessible to JavaScript
//            accessTokenCookie.setSecure(true);    // Send the cookie only over HTTPS
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", authResponse.getRefreshToken());
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setDomain("localhost");
            refreshTokenCookie.setHttpOnly(true);  // Make the cookie inaccessible to JavaScript
//            accessTokenCookie.setSecure(true);    // Send the cookie only over HTTPS
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException exception) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationResponse authResponse = refreshTokenUseCase.refreshToken(request);

            Cookie accessTokenCookie = new Cookie("accessTokenCookie", authResponse.getAccessToken());
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 1 day
            accessTokenCookie.setPath("/");
            accessTokenCookie.setDomain("localhost");
            accessTokenCookie.setHttpOnly(true);  // Make the cookie inaccessible to JavaScript
//            accessTokenCookie.setSecure(true);    // Send the cookie only over HTTPS
            response.addCookie(accessTokenCookie);

            return ResponseEntity.ok().body(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleHandler(@RequestBody AuthenticationCodeRequest request, HttpServletResponse response) {
        try {
            LoginResponse authResponse = oAuth2Service.authenticate(request);

            Cookie accessTokenCookie = new Cookie("accessTokenCookie", authResponse.getAccessToken());
            accessTokenCookie.setMaxAge(24 * 60 * 60); // 1 day
            accessTokenCookie.setPath("/");
            accessTokenCookie.setDomain("localhost");
            accessTokenCookie.setHttpOnly(true);  // Make the cookie inaccessible to JavaScript
//            accessTokenCookie.setSecure(true);    // Send the cookie only over HTTPS
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshTokenCookie", authResponse.getRefreshToken());
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setDomain("localhost");
            refreshTokenCookie.setHttpOnly(true);  // Make the cookie inaccessible to JavaScript
//            accessTokenCookie.setSecure(true);    // Send the cookie only over HTTPS
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok().body(authResponse);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidGoogleAuthException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
