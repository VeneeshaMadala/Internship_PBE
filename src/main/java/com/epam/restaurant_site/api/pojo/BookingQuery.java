package com.epam.restaurant_site.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingQuery {
    private String locationId;
    private String date;
    private String time;
    private String guests;

    // Getters
    public Map<String, String> toQueryMap() {
        Map<String, String> map = new HashMap<>();
        map.put("locationId", locationId);
        map.put("date", date);
        map.put("time", time);
        map.put("guests", guests);
        return map;
    }
}
