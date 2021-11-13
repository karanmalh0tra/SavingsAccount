public class Main {
    public static void main(String[] args){

        Account account = new Account();
        Account anotherAccount = new Account(100);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw(100,true);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(200);
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(100);
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw(200,false);
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(200);
            }
        });
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw(100,false);
            }
        });
        Thread t7 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(300);
            }
        });
        Thread t8 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw(100,true);
            }
        });
        Thread t9 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(100);
            }
        });
        Thread t10 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.withdraw(400,true);
            }
        });
        Thread t13 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.transfer(200,anotherAccount);
            }
        });
        Thread t12 = new Thread(new Runnable() {
            @Override
            public void run() {
                anotherAccount.transfer(300,account);
            }
        });
        Thread t11 = new Thread(new Runnable() {
            @Override
            public void run() {
                account.deposit(400);
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
    }
}
