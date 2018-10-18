package transactions.controllers;

import transactions.models.accounts.BankAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountsController {

    @GetMapping
    public Set<BankAccount> getBankAccounts() {
        return Collections.emptySet();
    }
}
