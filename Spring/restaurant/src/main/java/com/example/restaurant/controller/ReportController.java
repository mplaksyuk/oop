package com.example.restaurant.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.example.restaurant.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ReportController {

    @Autowired
    private final ReportRepository pp;

    @GetMapping("/product-popularity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPopularityProduct(@RequestParam Timestamp from, @RequestParam Timestamp thru) {

        List<ReportRepository.PopularityProduct> products = pp.topProducts(from, thru);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @GetMapping("/ingredient-spend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSpendIngredient(@RequestParam Timestamp from, @RequestParam Timestamp thru) {

        List<ReportRepository.SpendIngredient> ingredients = pp.topSpendIngredients(from, thru);

        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
    
    @GetMapping("/user-top")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTopUser(@RequestParam Timestamp from, @RequestParam Timestamp thru) {

        List<ReportRepository.TopUser> users = pp.topUser(from, thru);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/product-percentage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductPercentage(@RequestParam Timestamp from, @RequestParam Timestamp thru) {

        List<ReportRepository.ProductPercentage> products = pp.productPercentage(from, thru);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product-revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductRevenue(@RequestParam Timestamp from, @RequestParam Timestamp thru) {

        List<ReportRepository.ProductRevenue> products = pp.productRevenues(from, thru);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/report-excel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getExcelReport(@RequestParam Timestamp from, @RequestParam Timestamp thru) throws Exception {

        ByteArrayOutputStream report = generateExcel(from, thru);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "report.xlsx");

        return new ResponseEntity<>(report.toByteArray(), headers, HttpStatus.OK);
    }
    

    private ByteArrayOutputStream generateExcel(Timestamp from, Timestamp thru) throws Exception {
        try (
                XSSFWorkbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            List<ReportRepository.PopularityProduct> reportData1 = pp.topProducts(from, thru);
            List<ReportRepository.SpendIngredient> reportData2 = pp.topSpendIngredients(from, thru);
            List<ReportRepository.TopUser> reportData3 = pp.topUser(from, thru);
            List<ReportRepository.ProductPercentage> reportData4 = pp.productPercentage(from, thru);
            List<ReportRepository.ProductRevenue> reportData5 = pp.productRevenues(from, thru);

            XSSFSheet sheet1 = workbook.createSheet("Popularity Product Report");
            XSSFSheet sheet2 = workbook.createSheet("Ingredient Spend Report");
            XSSFSheet sheet3 = workbook.createSheet("Top Customers Report");
            XSSFSheet sheet4 = workbook.createSheet("Product Percentage Report");
            XSSFSheet sheet5 = workbook.createSheet("Product Revenue");

            XSSFRow headerRow1 = sheet1.createRow(0);
            headerRow1.createCell(0).setCellValue("Product Name");
            headerRow1.createCell(1).setCellValue("Quantity");
            headerRow1.createCell(2).setCellValue("Total Price");

            XSSFRow headerRow2 = sheet2.createRow(0);
            headerRow2.createCell(0).setCellValue("Ingredient Name");
            headerRow2.createCell(1).setCellValue("Usage Quantity");
            headerRow2.createCell(2).setCellValue("Volume");

            XSSFRow headerRow3 = sheet3.createRow(0);
            headerRow3.createCell(0).setCellValue("User Name");
            headerRow3.createCell(1).setCellValue("Order Quantity");
            headerRow3.createCell(2).setCellValue("Average Amount of Order");

            XSSFRow headerRow4 = sheet4.createRow(0);
            headerRow4.createCell(0).setCellValue("Product Name");
            headerRow4.createCell(1).setCellValue("Percentage Income");
            headerRow4.createCell(2).setCellValue("Total Order Amount");

            XSSFRow headerRow5 = sheet5.createRow(0);
            headerRow5.createCell(0).setCellValue("Product Name");
            headerRow5.createCell(1).setCellValue("Total Count");
            headerRow5.createCell(2).setCellValue("Total Product Amount");
            headerRow5.createCell(3).setCellValue("Total Prime Cost");
            headerRow5.createCell(4).setCellValue("Total Profit");

            int rowNum = 1;
            for (ReportRepository.PopularityProduct repData : reportData1) {
                XSSFRow row = sheet1.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(repData.getName()));
                row.createCell(1).setCellValue(String.valueOf(repData.getQuantity()));
                row.createCell(2).setCellValue(String.valueOf(repData.getTotalPrice()));
            }

            rowNum = 1;
            for (ReportRepository.SpendIngredient repData : reportData2) {
                XSSFRow row = sheet2.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(repData.getName()));
                row.createCell(1).setCellValue(String.valueOf(repData.getQuantity()));
                row.createCell(2).setCellValue(String.valueOf(repData.getVolume()));
            }

            rowNum = 1;
            for (ReportRepository.TopUser repData : reportData3) {
                XSSFRow row = sheet3.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(repData.getName()));
                row.createCell(1).setCellValue(String.valueOf(repData.getQuantity()));
                row.createCell(2).setCellValue(String.valueOf(repData.getAverageAmount()));
            }

            rowNum = 1;
            for (ReportRepository.ProductPercentage repData : reportData4) {
                XSSFRow row = sheet4.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(repData.getName()));
                row.createCell(1).setCellValue(String.valueOf(repData.getPercent()) + "%");
                row.createCell(2).setCellValue(String.valueOf(repData.getSumTotal()));
            }

            rowNum = 1;
            for (ReportRepository.ProductRevenue repData : reportData5) {
                XSSFRow row = sheet5.createRow(rowNum++);
                row.createCell(0).setCellValue(String.valueOf(repData.getName()));
                row.createCell(1).setCellValue(String.valueOf(repData.getCountOfProducts()));
                row.createCell(2).setCellValue(String.valueOf(repData.getProductsRevenue()));
                row.createCell(3).setCellValue(String.valueOf(repData.getPrimeCost()));
                row.createCell(4).setCellValue(String.valueOf(repData.getFinalProfit()));
            }
        
            workbook.write(outputStream);
            return outputStream;

        } catch (Exception e){
            throw e;
        }
    }
}