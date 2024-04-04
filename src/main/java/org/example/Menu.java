package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<User> users = new ArrayList<>();

    private List<Transaction> transactions = new ArrayList<>();

    public void accountDataBase() {
        User user1 = new User("Зита", "8888", 100.0);
        User user2 = new User("Ваня", "7777", 100.0);
        User user3 = new User("Дима", "6666", 100.0);
        User user4 = new User("Ришат", "5544", 100.0);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    public void startMenu() {

        accountDataBase();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите число соотвествующему пункту:\n" +
                    "1-Зарегистрировать нового пользователя\n" +
                    "2-Войти");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        registerAnAccount();
                        break;
                    case 2:
                        loginToAccount();
                        break;
                    case 3:
                        printAllAccounts();
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Неверный выбор! попробуйте снова");


                }
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Введите из предложенных вариантов");
                scanner.next();//очистка  от не верных данных
            }
        }
    }

    public void registerAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----Регистрация нового пользователя----");
        System.out.println("Введите имя пользователя :");
        String name = scanner.nextLine();
        System.out.println("Ведите номер телефона пользователя : ");
        String numberFon = scanner.nextLine();
        User newUser = new User(name, numberFon, 100.0);
        if (users.contains(newUser)) {
            System.out.println("номер :" + newUser.getNumberFone() + " уже используется");
        } else {
            users.add(newUser);
            System.out.println("пользователь " + newUser.getName() + " Зарегистрирован");
        }
    }

    public void printAllAccounts() {
        int num = 1;
        for (User user : users) {
            System.out.println(num + " имя : " + user.getName() + ", Номер телефона : "
                    + user.getNumberFone() + ", Баланс : " + user.getBalance());
            num++;
        }
    }

    public void loginToAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---Вход в аккаунт---");
        System.out.println("Введите имя : ");
        String name = scanner.nextLine();
        System.out.println("Введите номер телефона : ");
        String numberFon = scanner.nextLine();
        User loginUser = findUserByNameAndNumber(name, numberFon);
        if (loginUser != null) {
            System.out.println(getGreeting() + " " + loginUser.getName() + " !");
            accountUser(loginUser);
        } else {
            System.out.println("Пользователь не найден");
        }
    }


    // Метод для поиска по имени и номеру телефона
    private User findUserByNameAndNumber(String name, String numberFon) {
        for (User user : users) {
            if (user.getName().equals(name) && user.getNumberFone().equals(numberFon)) {
                return user;
            }
        }
        return null; // Если пользователь не найден
    }

    public void accountUser(User user) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("Введите число соотвествующему пункту:\n" +
                    "1-Посмотреть баланс\n" +
                    "2-Перевод\n" +
                    "3-История операций\n" +
                    "0-Выйти в главное меню");
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        showBalance(user);
                        break;
                    case 2:
                        depositBalance(user);
                        break;
                    case 3:
                        printHistory(user);
                        break;
                    case 4:
                        printAllAccounts();
                        break;
                    case 0:
                        System.out.println("Выход в главное меню");
                        flag = false;
                        break;
                    default:
                        System.out.println("Неверный выбор! попробуйте снова");


                }
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Введите из предложенных вариантов");
                scanner.next();//очистка  от не верных данных
            }
        }
    }

    private void showBalance(User user) {
        System.out.println("Баланс пользователя " + user.getName() + " = " + user.getBalance());
    }

    public void depositBalance(User sender) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите номер телефона чей баланс хотите пополнить : ");
        String numberRecipient = scanner.nextLine();
        User checkedNumber = findUser(numberRecipient);//поиск получателя
        if (checkedNumber != null) {
            double manySend;
            do {
                System.out.println("Введите сумму перевода : ");
                while (!scanner.hasNextDouble()){
                    System.out.println("Введите корректную сумму !!!");
                    scanner.next();//сброс данных
                }
                manySend = scanner.nextDouble();
                if (manySend <= 0) {
                    System.out.println("Введенна не корректная сумма, повторите снова!");
                }
            }
            while (manySend <= 0);

            if (sender.getBalance() >= manySend) {//проверка что введен положительная сумма еще
                checkedNumber.deposit(manySend);//пополнение баланса
                sender.deposit(-manySend);//списание баланса
                LocalDateTime transactionTime = LocalDateTime.now();
                Transaction transaction = new Transaction(sender, checkedNumber, manySend, transactionTime);
                transactions.add(transaction);
                System.out.println("перевод выполнен! остаток на вашем балансе : " + sender.getBalance());
            } else {
                System.out.println("Недостаточно средств на счете");
            }
        } else {
            System.out.println("Получатель не найден");
        }
    }

    //Метод для проверки наличия пользователя по номеру телефона
    public User findUser(String query) {
        for (User user : users) {
            if (user.getNumberFone().equals(query)) {
                return user;
            }
        }
        return null;
    }

    private String getGreeting() {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(5, 0)) && currentTime.isBefore(LocalTime.of(12, 0))) {
            return "Доброе утро,";
        } else if (currentTime.isAfter(LocalTime.of(12, 0)) && currentTime.isBefore(LocalTime.of(17, 0))) {
            return "Добрый день,";
        } else if (currentTime.isAfter(LocalTime.of(17, 0)) && currentTime.isBefore(LocalTime.of(22, 0))) {
            return "Добрый вечер,";
        } else {
            return "Доброй ночи,";
        }
    }

    //    public void printHistory(User sender){
//        System.out.println("История : ");
//        for (Transaction transaction : transactions) {
//            System.out.println("Кому : "+transaction.getRecipient().getName()+", Сумма перевода : "+transaction.getAmount());
//        }
//
//    }
    public void printHistory(User user) {
        System.out.println("История транзакций для пользователя " + user.getName() + ":");
        for (Transaction transaction : transactions) {
            // Если текущий пользователь является отправителем в транзакции, печатаем информацию об исходящем переводе
            if (transaction.getSender().equals(user)) {
                System.out.println("Исходящий перевод пользователю " + transaction.getRecipient().getName() + ", Сумма перевода: " + transaction.getAmount());
            }
            // Если текущий пользователь является получателем денег, печатаем информацию о входящем переводе
            if (transaction.getRecipient().equals(user)) {
                System.out.println("Входящий перевод от " + transaction.getSender().getName() + ", Сумма перевода: " + transaction.getAmount() + " Время транзакции : " + transaction.getTransactionTime());
            }
        }
    }

}

