package br.com.dio;

import java.util.Arrays;
import java.util.Scanner;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmenteRepository;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AccountRepository accountRepository = new AccountRepository();
    private static final InvestmenteRepository investmenteRepository = new InvestmenteRepository();

    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao DIO Bank");
        while (true) {
            System.out.println("\nSelecione a operação desejada:");
            System.out.println(" 1 - Criar uma conta");
            System.out.println(" 2 - Criar um investimento");
            System.out.println(" 3 - Fazer um investimento");
            System.out.println(" 4 - Depositar na conta");
            System.out.println(" 5 - Sacar da conta");
            System.out.println(" 6 - Transferência entre contas");
            System.out.println(" 7 - Investir");
            System.out.println(" 8 - Sacar investimento");
            System.out.println(" 9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Histórico de conta");
            System.out.println("14 - Sair");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    createInvestment();
                    break;
                case 3:
                    createWalletInvestment();
                    break;
                case 4:
                    deposit();
                    break;
                case 5:
                    withdraw();
                    break;
                case 6:
                    transferToAccount();
                    break;
                case 7:
                    incInvestment();
                    break;
                case 8:
                    rescueInvestment();
                    break;
                case 9:
                    accountRepository.list().forEach(System.out::println);
                    break;
                case 10:
                    investmenteRepository.list().forEach(System.out::println);
                    break;
                case 11:
                    investmenteRepository.listWallets().forEach(System.out::println);
                    break;
                case 12:
                    investmenteRepository.updateAmount(0);
                    System.out.println("Investimentos reajustados");
                    break;
                case 13:
                    chaeckHistory();
                    break;
                case 14:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private static void createAccount() {
        System.out.println("Informe as chaves pix (separadas por ';'):");
        var pix = Arrays.stream(scanner.nextLine().split(";")).toList();
        System.out.println("Informe o valor inicial de depósito:");
        long amount = scanner.nextLong();
        scanner.nextLine();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento");
        var tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de depósito:");
        long initialFunds = scanner.nextLong();
        scanner.nextLine();
        var investment = investmenteRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }

    private static void withdraw() {
        System.out.println("Informe a chave pix da conta para saque:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para deposito:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);

        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());

        }
    }

    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem:");
        var source = scanner.next();
        System.out.println("Informe a chave pix da conta de destino:");
        var target = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.tranferMoney(source, target, amount);

        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());

        }
    }

    private static void createWalletInvestment() {
        System.out.println("Informe a chave pix da conta:");
        var pix = scanner.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento: ");
        var investimentId = scanner.nextInt();
        var investmentWallet = investmenteRepository.initInvestment(account, investimentId);
        System.out.println("Conta de investimento criada: " + investmentWallet);
    }

    private static void incInvestment() {
        System.out.println("Informe a chave pix da conta para investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será investido: ");
        var amount = scanner.nextLong();
        try {
            investmenteRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment() {
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        var amount = scanner.nextLong();
        try {
            investmenteRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void chaeckHistory() {
        System.out.println("Desculpe, o professor pecou aqui!");
        // var pix = scanner.next();
        // AccountWallet wallet;
        // try {
        // var sortedHistory = accountRepository.getHistory(pix);
        // sortedHistory.forEach((k, v) -> {
        // System.out.println(k.format(ISO_DATE_TIME));
        // System.out.println(v.getFirst().transactionId());
        // System.out.println(v.getFirst().description());
        // System.out.println(v.size());
        // })
        // } catch (AccountNotFoundException ex) {
        // System.out.println(ex.getMessage());
        // }
    }

}
