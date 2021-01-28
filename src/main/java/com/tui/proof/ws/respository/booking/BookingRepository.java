package com.tui.proof.ws.respository.booking;

import com.tui.proof.ws.model.booking.BookingModel;
import com.tui.proof.ws.respository.InMemoryRepositoryImpl;
import org.springframework.stereotype.Component;

@Component
public class BookingRepository extends InMemoryRepositoryImpl<BookingModel> {

    @Override
    protected String getObjectName() {
        return "booking";
    }
}
