package com.learning.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message<T> {

    private T data;
    private Date time;
    private UUID identifier;

}
