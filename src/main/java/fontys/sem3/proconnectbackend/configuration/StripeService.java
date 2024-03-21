package fontys.sem3.proconnectbackend.configuration;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {
    String stripeAPIKey = "sk_test_51OX23MDTKpEYN8MYMFuWee9a09FB7N6iCogwvqbQyVQnOrBt7oyMSEjRpCs9tdL7V92YFLhjPEu6QgI9i41q9rFL003cZbWVSP";

    public Account createAccount() throws StripeException {
        var params =AccountCreateParams.builder().setType(AccountCreateParams.Type.EXPRESS).build();

        return Account.create(params);
    }

    public PaymentIntent createPaymentIntent() throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(500L)
                        .setCurrency("gbp")
                        .setPaymentMethod("pm_card_visa")
                        .build();

        return PaymentIntent.create(params);
    }

    public Session createSession() throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice("{{PRICE_ID}}")
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .setApplicationFeeAmount(123L)
                                        .setTransferData(
                                                SessionCreateParams.PaymentIntentData.TransferData.builder()
                                                        .setDestination("{{CONNECTED_ACCOUNT_ID}}")
                                                        .build()
                                        )
                                        .build()
                        )
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setReturnUrl("https://example.com/checkout/return?session_id={CHECKOUT_SESSION_ID}")
                        .build();

        return Session.create(params);
    }
}
