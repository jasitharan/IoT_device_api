package org.example.controller;

import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class DeviceControllerTest {


    @Test
    public void testCreateDevice() throws Exception {
        URL url = new URL("http://localhost:8080/devices");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        String json = "{\"id\":\"dev1\",\"name\":\"Sensor\",\"type\":\"Temp\",\"status\":true,\"lastCommunication\":123456}";
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes());
        }

        int code = con.getResponseCode();
        assertEquals(201, code);
    }

}