package com.example.epamfinalproject.Database.Shaper;

import com.example.epamfinalproject.Database.FieldKey;
import com.example.epamfinalproject.Entities.*;
import com.example.epamfinalproject.Entities.Enums.Status;
import com.example.epamfinalproject.Entities.Enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderShaper implements DataShaper<Order> {
    DataShaper<Cruise> cruiseShaper = new CruiseShaper();

    @Override
    public Order shapeData(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        Cruise cruise = cruiseShaper.shapeData(resultSet);
        cruise.setId(resultSet.getLong(FieldKey.ORDER_CRUISE_ID));

        order.setId(resultSet.getLong(FieldKey.ENTITY_ID));
        order.setCruise(cruise);
        order.setUser(userShaper(resultSet));
        order.setNumberOfSeats(resultSet.getInt(FieldKey.ORDER_SEATS));
        order.setPrice(resultSet.getInt(FieldKey.ORDER_TOTAL_PRICE));
        order.setStatus(Status.fromString(resultSet.getString(FieldKey.ORDER_STATUS)));
        return order;
    }

    @Override
    public List<Order> shapeDataToList(ResultSet resultSet) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        if(resultSet.next()) {
            order.setId(resultSet.getLong(FieldKey.ENTITY_ID));
            Cruise cruise = cruiseShaper.shapeData(resultSet);
            cruise.setId(resultSet.getLong(FieldKey.ORDER_CRUISE_ID));
            order.setCruise(cruise);
            order.setUser(userShaper(resultSet));
            order.setNumberOfSeats(resultSet.getInt(FieldKey.ORDER_SEATS));
            order.setPrice(resultSet.getInt(FieldKey.ORDER_TOTAL_PRICE));
            order.setStatus(Status.fromString(resultSet.getString(FieldKey.ORDER_STATUS)));
            orderList.add(order);
        }
        return orderList;
    }
    public User userShaper(ResultSet resultSet) throws SQLException {
        return new User.UserBuilder()
                .id(resultSet.getLong(FieldKey.ORDER_USER_ID))
                .firstName(resultSet.getString(FieldKey.FIRST_NAME))
                .lastName(resultSet.getString(FieldKey.LAST_NAME))
                .login(resultSet.getString(FieldKey.LOGIN))
                .password(resultSet.getString(FieldKey.PASSWORD))
                .role(UserRole.fromString(resultSet.getString(FieldKey.ROLE)))
                .build();
    }
}