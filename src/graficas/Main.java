package graficas;

import javax.swing.JPanel;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author david
 */
public class Main extends ApplicationFrame{
    public Main(String titulo){
        super(titulo);
        setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
        double Q = 800D;
        String tituloProblema = "Comedor Ingeniería";
        String unidades = "Lb. de carne";
        
        JPanel jpanel = (new PanelGrafica(Q, -400D, tituloProblema, unidades)).getPanel();
        setContentPane(jpanel);
    }
    public static void main(String args[]) {
        Main m = new Main("Prueba grafica");
        m.pack();
        RefineryUtilities.centerFrameOnScreen(m);
        m.setVisible(true);
    }
}
