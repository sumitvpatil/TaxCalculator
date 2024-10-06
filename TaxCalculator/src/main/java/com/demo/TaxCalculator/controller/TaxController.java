package com.demo.TaxCalculator.controller;

import com.demo.TaxCalculator.model.TaxResponse;
import com.demo.TaxCalculator.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api")
public class TaxController {

    @Autowired
    private TaxService taxService;

    @GetMapping("/test")
    public String testGet(){
        return "Get Success";
    }

    @PostMapping("/test/upload/csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process the CSV line here
                System.out.println(line);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file");
        }

        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/calculateTax")
    public ResponseEntity<TaxResponse> calculateTax(@RequestParam("taxReport") MultipartFile taxReport) throws IOException {
        if (taxReport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TaxResponse());
        }
        return ResponseEntity.ok(taxService.generateTaxReport(taxReport));
    }
}
