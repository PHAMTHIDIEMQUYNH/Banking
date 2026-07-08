package com.example.banking_service.services;

import com.example.banking_service.model.Account;
import com.example.banking_service.repository.AccountRepository;
import com.example.banking_service.util.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Tạo tài khoản mới (mã hóa số dư)
     */
    @Transactional
    public Account createAccount(Account account, String balance) {
        // Kiểm tra số tài khoản đã tồn tại chưa
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new RuntimeException("Account number already exists: " + account.getAccountNumber());
        }

        // Mã hóa số dư trước khi lưu
        String encryptedBalance = EncryptionUtils.encrypt(balance);
        account.setEncryptedBalance(encryptedBalance);

        return accountRepository.save(account);
    }

    /**
     * Lấy tất cả tài khoản
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Lấy tài khoản theo ID
     */
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    /**
     * Lấy tài khoản theo số tài khoản
     */
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with number: " + accountNumber));
    }

    /**
     * Lấy số dư đã giải mã
     */
    public String getDecryptedBalance(Long id) {
        Account account = getAccountById(id);
        return EncryptionUtils.decrypt(account.getEncryptedBalance());
    }

    /**
     * Cập nhật số dư
     */
    @Transactional
    public Account updateBalance(Long id, String newBalance) {
        Account account = getAccountById(id);
        String encryptedBalance = EncryptionUtils.encrypt(newBalance);
        account.setEncryptedBalance(encryptedBalance);
        return accountRepository.save(account);
    }

    /**
     * Xóa tài khoản
     */
    @Transactional
    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}