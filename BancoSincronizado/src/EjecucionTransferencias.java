
import banco.Banco;
import java.util.logging.Level;
import java.util.logging.Logger;


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
