package fontys.sem3.proconnectbackend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import fontys.sem3.proconnectbackend.persistence.UserRepository;
import fontys.sem3.proconnectbackend.persistence.entity.ClientEntity;
import fontys.sem3.proconnectbackend.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class StripeController {
    private final UserRepository userRepository;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession() throws StripeException {
        Stripe.apiKey = "sk_test_51OX23MDTKpEYN8MYMFuWee9a09FB7N6iCogwvqbQyVQnOrBt7oyMSEjRpCs9tdL7V92YFLhjPEu6QgI9i41q9rFL003cZbWVSP";

        String YOUR_DOMAIN = "http://localhost:5173/payment";
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setReturnUrl(YOUR_DOMAIN + "/return?session_id={CHECKOUT_SESSION_ID}")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                        .setPrice("price_1OXfMyDTKpEYN8MYTilr1bbc")
                                        .build())
                        .build();

        Session session = Session.create(params);

        Map<String, String> map = new HashMap();
        map.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/session-status")
    public ResponseEntity<?> getSessionStatus(@RequestParam(name = "session_id") String session_id) throws StripeException {
        Session session = Session.retrieve(session_id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(authentication.getName()).get();
        Map<String, String> map = new HashMap();
        map.put("status", session.getRawJsonObject().getAsJsonPrimitive("status").getAsString());
        map.put("customer_email", session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email").getAsString());
        map.put("user_id", userEntity.getId().toString());
        if (userEntity instanceof ClientEntity) {
            map.put("user_role", "ROLE_Client");
        } else {
            map.put("user_role", "ROLE_Expert");
        }
        map.put("first_name", userEntity.getFirstName());
        map.put("last_name", userEntity.getLastName());
        map.put("profile_pic_url", userEntity.getProfileImageUrl());


        return ResponseEntity.ok().body(map);
    }
}
