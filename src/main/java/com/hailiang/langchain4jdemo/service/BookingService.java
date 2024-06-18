package com.hailiang.langchain4jdemo.service;

import com.hailiang.langchain4jdemo.pojo.Booking;
import com.hailiang.langchain4jdemo.pojo.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    /**
     * 获取预定详情
     * @param bookingNumber
     * @param customerName
     * @param customerSurname
     * @return
     */
    public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);
        LocalDate bookingFrom = LocalDate.now().plusDays(1);
        LocalDate bookingTo = LocalDate.now().plusDays(3);
        Customer customer = new Customer(customerName, customerSurname);
        return new Booking(bookingNumber, bookingFrom, bookingTo, customer);
    }

    /**
     * 取消预定
     * @param bookingNumber
     * @param customerName
     * @param customerSurname
     */
    public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);
        throw new RuntimeException("预定" + bookingNumber + " 无法取消!");
    }

    /**
     * 确保预定存在
     * @param bookingNumber
     * @param customerName
     * @param customerSurname
     */

    private void ensureExists(String bookingNumber, String customerName, String customerSurname) {
        // Imitating check
        if (!(bookingNumber.equals("123-456")
                && customerName.equals("Klaus")
                && customerSurname.equals("Heisler"))) {
            throw new RuntimeException("预定" + bookingNumber + " 不存在!");
        }
    }
}
