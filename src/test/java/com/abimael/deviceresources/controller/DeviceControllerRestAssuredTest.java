package com.abimael.deviceresources.controller;

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
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", notNullValue())
                .body("message", notNullValue());
    }
}
