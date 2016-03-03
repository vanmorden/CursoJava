
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vanmorden@gmail.com
 */
public class BancoSinSincronizar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco b = new Banco();

        for (int i = 0; i < 100; i++) {
            EjecucionTransferencias et = new EjecucionTransferencias(b, i, 2000);
            Thread t = new Thread(et);

            t.start();
        }
    }

}

class Banco {

    private final double[] cuentas;

    public Banco() {
        cuentas = new double[100];

        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }

    public void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) {
        if (cuentas[cuentaOrigen] < cantidad) { //evalúa que el saldo no es inferior a la catida a transferir
            return;
        }
        System.out.println(Thread.currentThread() + "=> Cuenta: " + String.valueOf(cuentaOrigen)
                + ", tiene: " + cuentas[cuentaOrigen]);

        System.out.printf("%s => %10.2f de %d para %d ", Thread.currentThread(), cantidad, cuentaOrigen, cuentaDestino);
        cuentas[cuentaOrigen] -= cantidad; //Restamos la cantidad de la cuenta de origen
        cuentas[cuentaDestino] += cantidad; //Sumamos la cantidad a la cuenta de destino

        System.out.printf("%s => Saldo Total: %10.2f%n ", Thread.currentThread(), getSaldoTotal());
    }

    public double getSaldoTotal() {
        double sumaCuentas = 0;
        for (double a : cuentas) {
            sumaCuentas += a;
        }
        return sumaCuentas;
    }
}

class EjecucionTransferencias implements Runnable {

    private Banco banco;
    private int deLaCuenta;
    private double cantidadMax;

    public EjecucionTransferencias(Banco b, int de, double max) {
        banco = b;
        deLaCuenta = de;
        cantidadMax = max;

    }

    @Override
    public void run() {
        try {
            while (true) {
                int paraLaCuenta = (int) (100 * Math.random());
//                while (paraLaCuenta == deLaCuenta) {
//                    paraLaCuenta = (int) (100 * Math.random());
//                }
                double cantidad = cantidadMax * Math.random();
                banco.transferencia(deLaCuenta, paraLaCuenta, cantidad);

                Thread.sleep((int) (10 * Math.random()));
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(EjecucionTransferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
