package ru.learn.learnSpring.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ExperimentalController {

    @GetMapping("ex/req")
    public String paramController(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        return offset + " " + limit;
    }

    @GetMapping("ex/list")
    public ListResponse listController() {
       List<Car> carList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            carList.add(new Car());
        }

        ListResponse listResponse = new ListResponse();
        listResponse.setCars(carList);

        return listResponse;
    }

    @Data
    private class ListResponse {
        private List<Car> cars;
    }

    private class Car {
        private final String name = "car" + new Random().nextInt(100);

        public String getName() {
            return name;
        }
    }
}
