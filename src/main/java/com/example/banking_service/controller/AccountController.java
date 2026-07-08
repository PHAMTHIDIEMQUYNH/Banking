package com.example.banking_service.controller;

import com.example.banking_service.model.Account;
import com.example.banking_service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Tạo tài khoản mới
     * POST /api/accounts?balance=1000000
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account,
            @RequestParam String balance) {
        Account createdAccount = accountService.createAccount(account, balance);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    /**
     * Lấy tất cả tài khoản
     * GET /api/accounts
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * Lấy tài khoản theo ID
     * GET /api/accounts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    /**
     * Lấy số dư đã giải mã
     * GET /api/accounts/{id}/balance
     */
    @GetMapping("/{id}/balance")
    public ResponseEntity<Map<String, String>> getDecryptedBalance(@PathVariable Long id) {
        String balance = accountService.getDecryptedBalance(id);
        Map<String, String> response = new HashMap<>();
        response.put("accountId", id.toString());
        response.put("balance", balance);
        response.put("currency", "VND");
        return ResponseEntity.ok(response);
    }

    /**
     * Cập nhật số dư
     * PUT /api/accounts/{id}/balance?newBalance=2000000
     */
    @PutMapping("/{id}/balance")
    public ResponseEntity<Account> updateBalance(
            @PathVariable Long id,
            @RequestParam String newBalance) {
        Account updatedAccount = accountService.updateBalance(id, newBalance);
        return ResponseEntity.ok(updatedAccount);
    }

    /**
     * Xóa tài khoản
     * DELETE /api/accounts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Account deleted successfully");
        response.put("deletedId", id.toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Health check
     * GET /api/accounts/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "OK");
        status.put("message", "Banking Service is running!");
        return ResponseEntity.ok(status);
    }
}