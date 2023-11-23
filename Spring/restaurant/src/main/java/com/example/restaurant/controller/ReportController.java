package com.example.restaurant.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReport(@RequestParam Timestamp from, @RequestParam Timestamp thru) throws ParseException {
                
        List<ReportRepository.PopularityProduct> products = pp.topProducts(from, thru);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}