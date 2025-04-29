package com.abimael.deviceresources.controller;

import com.abimael.deviceresources.dto.*;
import com.abimael.deviceresources.util.State;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceControllerTest {

    @LocalServerPort
    private int port;

    /**
     * Set up the test environment by setting the port used by Rest Assured to
     * the value of the {@code @LocalServerPort} annotated field {@code port}.
     */
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    /**
     * Test the POST endpoint for creating a device.
     */
    @Test
    @DisplayName("POST /api/create - Create a new device")
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

    /**
     * Test the POST endpoint for creating a device, but without providing a name.
     */
    @Test
    @DisplayName("POST /api/create - should fail when name is missing")
    void testCreateDeviceWithoutName() {
        DeviceDto device = new DeviceDto();
        device.setBrand("Test Brand");
        device.setState(State.AVAILABLE);

        given()
                .contentType(ContentType.JSON)
                .body(device)
        .when()
                .post("/api/create")
        .then()
                .statusCode(400);
    }

    /**
     * Tests the GET endpoint for fetching all devices.
     */
    @Test
    @DisplayName("GET /api/fetch - Fetch all devices")
    void testFetchAll() {

        // Create DeviceDto instance
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Test Brand");
        deviceDto.setState(State.AVAILABLE);

        given()
                .contentType(ContentType.JSON)
                .body(deviceDto)
                .when()
                .post("/api/create")
                .then()
                .statusCode(201);

        List<DeviceDto> devices =
                given()
                    .contentType(ContentType.JSON)
                .when()
                    .get("/api/fetch")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", DeviceDto.class);
        assertNotNull(devices, "Devices list should not be null");
        assertFalse(devices.isEmpty(), "Devices list should have one or more elements");
        for (DeviceDto device : devices) {
            assertNotNull(device.getName(), "Device name should not be null");
            assertNotNull(device.getBrand(), "Device brand should not be null");
            assertNotNull(device.getState(), "Device state should not be null");
        }
    }

    /**
     * Tests the GET endpoint for fetching all devices filtered by brand.
     */
    @Test
    @DisplayName("GET /api/fetch?brand={brand} - Fetch devices by brand")
    void testFetchBrand() {

        // Create DeviceDto instance
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Apple");
        deviceDto.setState(State.AVAILABLE);

        given()
                .contentType(ContentType.JSON)
                .body(deviceDto)
                .when()
                .post("/api/create")
                .then()
                .statusCode(201);

        List<DeviceDto> devices =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/api/fetch?brand=Apple")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", DeviceDto.class);
        for (DeviceDto device : devices) {
            assertEquals("Apple", device.getBrand(), "Device brand should be Apple");
        }
    }

    /**
     * Tests the GET endpoint for fetching all devices filtered by brand.
     */
    @Test
    @DisplayName("GET /api/fetch?state={state} - Fetch devices by state")
    void testFetchState() {

        // Create DeviceDto instance
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Samsung");
        deviceDto.setState(State.IN_USE);

        given()
                .contentType(ContentType.JSON)
                .body(deviceDto)
                .when()
                .post("/api/create")
                .then()
                .statusCode(201);

        List<DeviceDto> devices =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/api/fetch?state=IN_USE")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .jsonPath()
                        .getList(".", DeviceDto.class);
        for (DeviceDto device : devices) {
            assertEquals(State.IN_USE, device.getState(), "Device state should be IN_USE");
        }
    }

    /**
     * Tests the GET endpoint for fetching a device by its ID.
     */
    @Test
    @DisplayName("GET /api/fetch/{id} - Fetch a single device")
    void testFetchById() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Lenovo");
        deviceDto.setState(State.IN_USE);

        String location =
                given()
                    .contentType(ContentType.JSON)
                    .body(deviceDto)
                .when()
                    .post("/api/create")
                .then()
                    .statusCode(201)
                        .extract()
                        .header("Location");

        String[] parts = location.split("/");
        String id = parts[parts.length - 1];

        DeviceDto device =
                given()
                        .contentType(ContentType.JSON)
                .when()
                        .get("/api/fetch/{id}", id)
                .then()
                        .statusCode(200)
                        .extract()
                        .as(DeviceDto.class);
        assertEquals("Lenovo", device.getBrand(), "Device brand should be Lenovo");
        assertEquals(State.IN_USE, device.getState(), "Device state should be IN_USE");
    }

    /**
     * Test the DELETE endpoint for deleting a device by its ID.
     */
    @Test
    @DisplayName("DELETE /api/delete/{id} - Delete a single device")
    void testDeleteById() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Iphone");
        deviceDto.setState(State.INACTIVE);

        String location =
                given()
                        .contentType(ContentType.JSON)
                        .body(deviceDto)
                .when()
                        .post("/api/create")
                .then()
                        .statusCode(201)
                        .extract()
                        .header("Location");

        String[] parts = location.split("/");
        String id = parts[parts.length - 1];

        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/api/delete/{id}", id)
        .then()
            .statusCode(204);

        given()
            .contentType(ContentType.JSON)
        .when()
             .get("/api/fetch/{id}", id)
        .then()
            .statusCode(404);
    }

    /**
     * Test the DELETE endpoint for deleting a device by its ID, but if the device is in use.
     */
    @Test
    @DisplayName("DELETE /api/delete/{id} - should fail to delete a Devices by id if the state is IN_USE")
    void testDeleteByIdWhenInUse() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Iphone");
        deviceDto.setState(State.IN_USE);

        String location =
                given()
                        .contentType(ContentType.JSON)
                        .body(deviceDto)
                        .when()
                        .post("/api/create")
                        .then()
                        .statusCode(201)
                        .extract()
                        .header("Location");

        String[] parts = location.split("/");
        String id = parts[parts.length - 1];

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/api/delete/{id}", id)
        .then()
                .statusCode(403);
    }

    /**
     * This test creates a new device, updates its state to INACTIVE, and verifies
     * the update operation returns a status code 200 indicating success.
     */
    @Test
    @DisplayName("PUT /api/update - Partially update an existing device")
    void testUpdateDevice() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("Test Device");
        deviceDto.setBrand("Iphone");
        deviceDto.setState(State.AVAILABLE);

        String location =
                given()
                        .contentType(ContentType.JSON)
                        .body(deviceDto)
                .when()
                        .post("/api/create")
                .then()
                        .statusCode(201)
                        .extract()
                        .header("Location");

        String[] parts = location.split("/");
        String id = parts[parts.length - 1];

        UpdateDeviceDto updateDeviceDto = new UpdateDeviceDto();
        updateDeviceDto.setId(Long.parseLong(id));
        updateDeviceDto.setState(State.INACTIVE);

        given()
                .contentType(ContentType.JSON)
                .body(updateDeviceDto)
        .when()
                .put("/api/update")
        .then()
                .statusCode(200);
    }

    /**
     * This test creates a new device, and fully update this device, and verifies
     * the update operation returns a status code 200 indicating success.
     */
    @Test
    @DisplayName("PUT /api/update - Fully update an existing device")
    void testFullyUpdateDevice() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("DEVICE TO BE FULLY UPDATED");
        deviceDto.setBrand("BRAND TO BE FULLY UPDATED");
        deviceDto.setState(State.AVAILABLE);

        String location =
                given()
                        .contentType(ContentType.JSON)
                        .body(deviceDto)
                        .when()
                        .post("/api/create")
                        .then()
                        .statusCode(201)
                        .extract()
                        .header("Location");

        String[] parts = location.split("/");
        String id = parts[parts.length - 1];

        UpdateDeviceDto updateDeviceDto = new UpdateDeviceDto();
        updateDeviceDto.setId(Long.parseLong(id));
        deviceDto.setName("UPDATED Device");
        deviceDto.setBrand("UPDATEDIphone");
        deviceDto.setState(State.IN_USE);

        given()
                .contentType(ContentType.JSON)
                .body(updateDeviceDto)
                .when()
                .put("/api/update")
                .then()
                .statusCode(200);
    }
}
