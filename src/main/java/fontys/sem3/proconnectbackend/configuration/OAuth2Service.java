package fontys.sem3.proconnectbackend.configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import fontys.sem3.proconnectbackend.business.converters.ClientConverter;
import fontys.sem3.proconnectbackend.business.converters.ExpertConverter;
import fontys.sem3.proconnectbackend.business.dtos.AuthenticationCodeRequest;
import fontys.sem3.proconnectbackend.business.dtos.LoginResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidGoogleAuthException;
import fontys.sem3.proconnectbackend.configuration.security.JwtService;
import fontys.sem3.proconnectbackend.domain.User;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.ExpertEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    public LoginResponse authenticate(AuthenticationCodeRequest request) throws IOException, GeneralSecurityException, InvalidCredentialsException, InvalidGoogleAuthException {
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new GsonFactory(),
                "https://oauth2.googleapis.com/token",
                GOOGLE_CLIENT_ID,
                GOOGLE_CLIENT_SECRET,
                request.getAuthCode(),
                "postmessage")
                .execute();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(tokenResponse.getIdToken());
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(payload.getEmail());
            if (userEntityOptional.isEmpty()) {
                throw new InvalidCredentialsException();
            }
            User user = userEntityOptional.map(userEntity -> {
                if (userEntity instanceof ClientEntity clientEntity) {
                    return ClientConverter.convert(clientEntity);
                } else if (userEntity instanceof ExpertEntity expertEntity) {
                    return ExpertConverter.convert(expertEntity);
                }

                return null;
            }).orElseThrow();
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.getRole());
            String accessToken = jwtService.generateToken(extraClaims, user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();
        } else {
            throw new InvalidGoogleAuthException();
        }
    }
}
