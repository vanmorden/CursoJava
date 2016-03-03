package banco;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BancoReentrantLock extends Banco {

    private Lock cierreBanco = new ReentrantLock(true);
    private Condition saldoSuficiente;

    public BancoReentrantLock() {
        super();
        saldoSuficiente = cierreBanco.newCondition();
    }

    @Override
    public void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException {
        cierreBanco.lock();
        try {
            while (getCuentas()[cuentaOrigen] < cantidad) { //evalúa que el saldo no es inferior a la catida a transferir
                System.out.println(Thread.currentThread() + "===========================> Cuenta: " + String.valueOf(cuentaOrigen)
                        + ", tiene: " + getCuentas()[cuentaOrigen] + ", cantidad: " + cantidad);
                saldoSuficiente.await();
            }
            System.out.println(Thread.currentThread() + "=> Cuenta: " + String.valueOf(cuentaOrigen)
                    + ", tiene: " + getCuentas()[cuentaOrigen]);

            System.out.printf("%s => %10.2f de %d para %d%n", Thread.currentThread(), cantidad, cuentaOrigen, cuentaDestino);
            getCuentas()[cuentaOrigen] -= cantidad; //Restamos la cantidad de la cuenta de origen
            getCuentas()[cuentaDestino] += cantidad; //Sumamos la cantidad a la cuenta de destino

            System.out.println(Thread.currentThread() + "=> Cuenta: " + String.valueOf(cuentaOrigen)
                    + ", tiene: " + getCuentas()[cuentaOrigen]);
            System.out.printf("%s => Saldo Total: %10.2f%n ", Thread.currentThread(), getSaldoTotal());
            saldoSuficiente.signalAll();
        } finally {
            cierreBanco.unlock();
        }
    }

}
