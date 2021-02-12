package com.aerialdev.kafka.spring4kafkademo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colour {
    private String colour;
    private Long price;
}
