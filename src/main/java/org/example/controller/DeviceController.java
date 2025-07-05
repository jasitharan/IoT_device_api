package org.example.controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.Device;
import org.example.repository.DeviceRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.stream.Collectors;

public class DeviceController implements HttpHandler {

    private final DeviceRepository deviceRepository = new DeviceRepository();
    private static final Gson gson = new Gson();


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] parts = exchange.getRequestURI().getPath().split("/");

        String response = "";
        int code = 200;

        System.out.println("Handling request: " + exchange.getRequestMethod() + " " + exchange.getRequestURI());


        try {
            switch (method) {
                case "GET":
                    if (parts.length == 2) {
                        response = gson.toJson(deviceRepository.findAll());
                    } else if (parts.length == 3) {
                        Device device = deviceRepository.findById(parts[2]);
                        if (device != null) {
                            response = gson.toJson(device);
                        } else {
                            code = 404;
                            response = "Device not found";
                        }
                    }
                    break;

                case "POST":
                    String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                            .lines().collect(Collectors.joining("\n"));
                    Device newDevice = gson.fromJson(body, Device.class);
                    deviceRepository.save(newDevice);
                    response = "Device created";
                    code = 201;
                    break;

                case "PUT":
                    if (parts.length == 3) {
                        String putBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                                .lines().collect(Collectors.joining("\n"));
                        Device updatedDevice = gson.fromJson(putBody, Device.class);
                        boolean updated = deviceRepository.update(parts[2], updatedDevice);
                        if (updated) {
                            response = "Device updated";
                        } else {
                            code = 404;
                            response = "Device not found";
                        }
                    }
                    break;

                case "DELETE":
                    if (parts.length == 3) {
                        boolean deleted = deviceRepository.delete(parts[2]);
                        if (deleted) {
                            response = "Device deleted";
                        } else {
                            code = 404;
                            response = "Device not found";
                        }
                    }
                    break;

                default:
                    code = 405;
                    response = "Method not allowed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 500;
            response = "Internal Server Error: " + e.getMessage();
        }



        try {
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(code, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace(); // catch any final response errors
        }

    }
}
