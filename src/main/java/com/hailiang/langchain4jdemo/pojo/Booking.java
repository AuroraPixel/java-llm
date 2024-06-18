package com.hailiang.langchain4jdemo.pojo;


import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Description("预订号")
    private String bookingNumber;
    @Description("预订开始日期")
    private LocalDate bookingFrom;
    @Description("预订结束日期")
    private LocalDate bookingTo;
    @Description("客户信息")
    private Customer customer;
}
