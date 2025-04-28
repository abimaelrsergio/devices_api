package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.dto.*;
import com.abimael.deviceresources.util.*;
import io.restassured.*;
import io.restassured.http.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.server.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("POST /api/create - should create device and return valid ResponseDto")
    void testCreateDevice() {
        // Create DeviceDto instance
        DeviceDto device = new DeviceDto();
        device.setName("Test Device");
        device.setBrand("Test Brand");
        device.setState(State.AVAILABLE);

        given()
                .contentType(ContentType.JSON)
                .body(device)
                .when()
                .post("/api/create")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("code", notNullValue())
                .body("message", notNullValue());
    }
}
