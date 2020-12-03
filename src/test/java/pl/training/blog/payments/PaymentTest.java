package pl.training.blog.payments;

import pl.training.blog.commons.jpa.EntityTest;

public class PaymentTest extends EntityTest<Payment> {

    @Override
    protected void initializeEntity() {
        entity = Payment.builder()
                .value(1_000L)
                .build();
    }

}