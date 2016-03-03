package banco;

public abstract class Banco {

    private final double[] cuentas;

    public Banco() {
        cuentas = new double[100];

        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }

    public double[] getCuentas() {
        return cuentas;
    }

    public abstract void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException;

    public double getSaldoTotal() {
        double sumaCuentas = 0;
        for (double a : cuentas) {
            sumaCuentas += a;
        }
        return sumaCuentas;
    }
}
