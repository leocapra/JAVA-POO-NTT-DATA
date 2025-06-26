package br.com.dio.repository;

import java.util.List;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.model.AccountWallet;

import lombok.Getter;

@Getter
public final class AccountRepository {

    private List<AccountWallet> accounts;

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "deposito");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        CommonsRepository.checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void tranferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        CommonsRepository.checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "Pix enviado de " + sourcePix + " para " + targetPix + " com sucesso";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);

    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("A conta com a chave pix " + pix + " não existe"));
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }
}
