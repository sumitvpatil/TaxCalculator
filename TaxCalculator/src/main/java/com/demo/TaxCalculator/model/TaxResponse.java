package com.demo.TaxCalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxResponse {
    private String name;
    private String panNumber;
    private String financialYear;
    private Double totalIncome;
    private Double totalDeductions;
    private Double calculatedTax;

}
