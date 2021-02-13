package com.aerialdev.kafka.spring4kafkademo.kafka;

import com.aerialdev.kafka.spring4kafkademo.domain.Car;
import com.aerialdev.kafka.spring4kafkademo.domain.Colour;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import static com.aerialdev.kafka.spring4kafkademo.kafka.config.KafkaTopics.*;

@Service
public class ApplicationStreams {

    @Bean
    public KStream<?, ?> kStream(StreamsBuilder kStreamBuilder) {
        Consumed<String, Colour> consumed = Consumed.with(Serdes.String(), new JsonSerde<>(Colour.class));
        Consumed<String, Car> consumedStream = Consumed.with(Serdes.String(), new JsonSerde<>(Car.class));
        Produced<String, Long> produced = Produced.with(Serdes.String(), Serdes.Long());

        KTable<String, Colour> colourTable = kStreamBuilder.table(COLOR_TOPIC, consumed);
        KStream<String, Car> stream = kStreamBuilder.stream(CAR_TOPIC, consumedStream);

        ValueJoiner<Car, Colour, Car> joiner = (car, colour) -> {
            car.setPrice(colour.getPrice());
            return car;
        };
//
        stream.selectKey((k,v)->v.getColour())
        .join(colourTable, joiner, Joined.with(Serdes.String(), new JsonSerde<>(Car.class), new JsonSerde<>(Colour.class)))
                .selectKey((k, v) -> v.getMake())
                .mapValues(Car::getPrice)
        .to(PRICE_TOPIC, produced);
        // Fluent KStream API
        return stream;
    }

//    @Bean
    public KTable<?, ?> kStream2(StreamsBuilder kStreamBuilder) {
        Consumed<String, Colour> consumed = Consumed.with(Serdes.String(), new JsonSerde<>(Colour.class));
        KTable<String, Colour> colourTable = kStreamBuilder.table(COLOR_TOPIC, consumed);
        Produced<String, Long> produced = Produced.with(Serdes.String(), Serdes.Long());
        colourTable.mapValues(v->{
            return v.getPrice();
        })
                .toStream()
                .to(PRICE_TOPIC, produced);
        return colourTable;
    }
}
