package corejava.v1ch14.synch;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Программа, имитирующая банк со счетами и использующая блокировки
 * для организации последовательного доступа к остаткам на счетах
 * @version 1.30 2004-08-01
 * @author Cay Horstmann
 */
public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    /**
     * Конструирует объект банка
     * @param n Количество счетов
     * @param initialBalance Первоначальный остаток на каждом счете
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    /**
     * Переводит деньги с одного счета на другой
     * @param from Счет, с которого переводятся деньги
     * @param to Счет, на который переводятся деньги
     * @param amount Сумма перевода
     */
    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount)
                sufficientFunds.await();
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        } finally {
            bankLock.unlock();
        }
    }

    /**
     * Получает сумму остатков на всех счетах
     * @return Возвращает общий баланс
     */
    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : accounts)
                sum += a;
            return sum;
        } finally {
            bankLock.unlock();
        }
    }

    /**
     * Получает количество счетов в банке
     * @return Возвращает количество счетов
     */
    public int size() {
        return accounts.length;
    }
}