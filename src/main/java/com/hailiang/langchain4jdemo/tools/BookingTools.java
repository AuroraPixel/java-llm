package com.hailiang.langchain4jdemo.tools;

import com.hailiang.langchain4jdemo.pojo.Booking;
import com.hailiang.langchain4jdemo.service.BookingService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BookingTools {
    @Resource
    private BookingService bookingService;

    /**
     * 获取预定详情
     * @param bookingNumber
     * @param customerName
     * @param customerSurname
     * @return
     */
    @Tool
    public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 获取预定详情 %s for %s %s...%n", bookingNumber, customerName, customerSurname);
        System.out.println("==========================================================================================");

        return bookingService.getBookingDetails(bookingNumber, customerName, customerSurname);
    }


    /**
     * 取消预定
     * @param bookingNumber
     * @param customerName
     * @param customerSurname
     */
    @Tool
    public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: 取消预订 %s for %s %s...%n", bookingNumber, customerName, customerSurname);
        System.out.println("==========================================================================================");

        bookingService.cancelBooking(bookingNumber, customerName, customerSurname);
    }

}
