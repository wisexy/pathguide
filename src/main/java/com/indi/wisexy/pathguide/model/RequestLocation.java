package com.indi.wisexy.pathguide.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.stream.Collectors;

@ToString
@AllArgsConstructor
public class RequestLocation {

    private String key;

    private String origin;

    private String destination;

    public String getRequestParam() {
        Class<?> clazz = RequestLocation.class;

        return Arrays.stream(clazz.getDeclaredFields()).map(field -> {
            field.setAccessible(true);
            try {
                return field.getName() + "=" + field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining("&"));
    }

}
