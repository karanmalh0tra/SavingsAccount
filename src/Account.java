import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    Lock lock= new ReentrantLock();

    /*Condition to inform on a deposit*/
    Condition informDeposit = lock.newCondition();

    /*Condition for having preferredWithdrawals*/
    Condition preferredWithdrawals = lock.newCondition();

    /*Balance present in the account */
    int balance;

    /* Initializing currently present preferredWithdrawals*/
    int currentQueueOfWithdraws=0;

    public Account(){
        /* Initialize Balance to 0 if constructor passed without arguments */
        this.balance=0;
    }

    public Account(int initialBalance){
        /* setup the account with some initial balance */
        this.balance=initialBalance;
    }

    /* Implementation of the Transfer Function */
    /* Pass amount and another account. Mentioned in the Main function */
    void transfer(int k, Account reserve) {

        lock.lock();
        try {
            reserve.withdraw(k,false);
            deposit(k);
        } finally {
            lock.unlock();
        }

        System.out.println("Transaction Successful");
    }

    /* preferred withdraw function */
    public void preferredWithdraw(int withdrawAmount){
        currentQueueOfWithdraws++;
        /* Acquire the lock */
        lock.lock();

        while (balance < withdrawAmount){
            try{
                informDeposit.await();
            }
            catch (InterruptedException e){
                System.out.println("Interrupted");
            }
        }

        balance = balance - withdrawAmount;
        System.out.println("Withdraw Successful, New Balance = "+balance);
        currentQueueOfWithdraws--;
        preferredWithdrawals.signalAll();

        /* Unlock since withdraw is now successful */
        lock.unlock();
    }

    //withdrawal method to withdraw based on preferred or ordinary transaction
    public void withdraw(int withdrawAmount,boolean isPreferred)
    {
        if(isPreferred){
            /* Add to the queue if the boolean for isPreferred is true */
            preferredWithdraw(withdrawAmount);
        } else {
            /* Acquire the lock */
            lock.lock();

            while(balance < withdrawAmount || currentQueueOfWithdraws > 0){
                while (balance < withdrawAmount) {
                    try {
                        informDeposit.await();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted");
                    }
                }

                while (currentQueueOfWithdraws > 0) {
                    try {
                        preferredWithdrawals.await();
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted");
                    }
                }
            }

            balance = balance - withdrawAmount;
            System.out.println("Withdraw Successful, New Balance = " + balance);

            /* Unlock since withdraw is now successful */
            lock.unlock();
        }
    }

    //Deposit and signal when deposit is done
    public void deposit(int depositAmount)
    {
        /* Acquire the lock */
        lock.lock();
        /* Add the amount deposited to the balance */
        balance = balance + depositAmount;

        System.out.println("Deposit Successful, New Balance for the account is = "+balance);

        /* Call the condition */
        informDeposit.signal();

        /* Unlock since deposit is now successful */
        lock.unlock();
    }

}