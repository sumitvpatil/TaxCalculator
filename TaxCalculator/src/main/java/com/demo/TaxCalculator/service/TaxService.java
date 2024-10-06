package com.demo.TaxCalculator.service;

import com.demo.TaxCalculator.model.TaxResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class TaxService {
    public TaxResponse generateTaxReport(MultipartFile taxReport) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(taxReport.getInputStream(), StandardCharsets.UTF_8))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            TaxResponse taxResponse = new TaxResponse();
            for (CSVRecord record : records) {
                // Assuming the CSV has columns: Name, Age, Location
                String section = record.get("Section");
                String desc = record.get("Description");
                String value = record.get("Value/Amount(INR)");
                // Process each cell as needed
                switch (section) {
                    case "Personal Information" -> {
                        switch (desc) {
                            case "Name" -> taxResponse.setName(value);
                            case "PAN" -> taxResponse.setPanNumber(value);
                            case "Financial Year" -> taxResponse.setFinancialYear(value);
                        }
                    }
                    case "Income" -> {
                        if (desc.equals("Total Income")) {
                            taxResponse.setTotalIncome(Double.valueOf(value));
                        }
                    }
                    case "Deductions" -> {
                        if (desc.equals("Total Deductions")) {
                            taxResponse.setTotalDeductions(Double.valueOf(value));
                        }
                    }
                }
            }
            taxResponse.setCalculatedTax(calculateTax(taxResponse));
            return taxResponse;
        } catch (IOException e) {
            throw e;
        }
    }

    public Double calculateTax(TaxResponse taxResponse){
        double taxableIncome = taxResponse.getTotalIncome() - taxResponse.getTotalDeductions();
        // Tax calculation logic


        return taxableIncome * 0.1;
    }
}
