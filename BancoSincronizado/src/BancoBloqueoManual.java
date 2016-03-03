
import banco.*;


/**
 *
 * @author t138974 - Fernando Sancerni <fernando.sancernifatas@telefonica.com>
 */
public class BancoBloqueoManual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco b = new BancoReentrantLock();

        for (int i = 0; i < 100; i++) {
            EjecucionTransferencias et = new EjecucionTransferencias(b, i, 2000);
            Thread t = new Thread(et);

            t.start();
        }
    }

}
