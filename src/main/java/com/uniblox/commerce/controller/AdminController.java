package com.uniblox.commerce.controller;

import com.uniblox.commerce.contracts.AdminSummary;
import com.uniblox.commerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/summary")
    public AdminSummary getSummary() {
        return adminService.createSummary();
    }
}
