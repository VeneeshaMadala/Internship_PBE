package com.epam.restaurant_site.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String locationId;
    private String tableNumber;
    private String date;
    private String guestsNumber;
    private String timeFrom;
    private String timeTo;

    // Optional fields (usually in response)
    private String id;
    private String status;
    private String locationAddress;
    private String timeSlot;
    private String preOrder;
    private String feedbackId;
}
