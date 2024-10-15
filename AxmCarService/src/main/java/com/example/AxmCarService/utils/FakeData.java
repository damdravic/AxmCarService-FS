package com.example.AxmCarService.utils;

import com.example.AxmCarService.domain.RepairOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FakeData {

    public static RepairOrder[] repairOrders;

    public static RepairOrder[] getFakeOrders() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        RepairOrder[] orders = new RepairOrder[3];
        orders[0] = new RepairOrder(1L, "Oil change", sdf.parse("2021-01-02"), sdf.parse("2021-01-02"), 100.0, "In progress");
        orders[1] = new RepairOrder(2L, "Brake pads replacement", sdf.parse("2021-01-02"), sdf.parse("2021-01-02"), 200.0, "Completed");
        orders[2] = new RepairOrder(3L, "Engine repair", sdf.parse("2021-01-02"), sdf.parse("2021-01-02"), 500.0, "In progress");
        return orders;
    }

}
