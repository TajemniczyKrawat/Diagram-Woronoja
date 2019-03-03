import javax.swing.*;
import java.awt.*;
import java.util.*;

public class VoronoiDiagram extends JPanel {
    private int iloscPunktow;
    private Dimension wymiaryOkna;
    private ArrayList<PunktBazowy> punktyBazowe;

    private VoronoiDiagram(Dimension wymiaryOkna, int iloscPunktow) {
        this.wymiaryOkna = wymiaryOkna;
        this.iloscPunktow = iloscPunktow;
        punktyBazowe = new ArrayList<>(Arrays.asList(generujPunktyBazowe()));
        polaczPunkty();
        ustawOpcjeOkna();
    }
    //podstawowe ustawienia tworzenia i wyświetlania okna
    private void ustawOpcjeOkna() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Diagram Voronoi");
        jFrame.setSize(new Dimension(wymiaryOkna.width+15, wymiaryOkna.height+38));

        jFrame.add(this);

        jFrame.setVisible(true);
    }
    //wypełnia tablice punktyBazowe obiektami klasy PunktBazowy z losowymi wartościami pól 'x' i 'y' w granicach panelu
    private PunktBazowy[] generujPunktyBazowe() {
        PunktBazowy[] wygenerowanePunkty = new PunktBazowy[iloscPunktow];
        for(int i = 0; i < iloscPunktow; i++) {
            wygenerowanePunkty[i] = new PunktBazowy(Math.random() * wymiaryOkna.width, Math.random() * wymiaryOkna.height);
        }
        return wygenerowanePunkty;
    }
    //każdy obiekt w liście punktyBazowe w swojej liście połączeń będzie miał obiekty połączeń ze wszystkimi punktami
    private void polaczPunkty() {
        for(PunktBazowy punkt : punktyBazowe) {
            punkt.stworzPolaczenia(punktyBazowe);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D)g;

        for(PunktBazowy punktBazowy : punktyBazowe) {
            g2d.setColor(new Color((int)(Math.random() * 0x1000000)));
            g2d.fillPolygon(punktBazowy.wyliczPoligon(wymiaryOkna));
        }

        /*
        g2d.setStroke(new BasicStroke(14));
        for(PunktBazowy punktBazowy : punktyBazowe) {
            g2d.setColor(new Color((int)(Math.random() * 0x1000000)));
            g2d.drawPolygon(punktBazowy.wyliczPoligon(wymiaryOkna));
        }
        */

        g2d.setColor(Color.BLACK);
        for(PunktBazowy punkt : punktyBazowe) {
            g2d.fillOval((int)punkt.x, (int)punkt.y, 5,5);
        }
    }

    public static void main(String[] args) {
        VoronoiDiagram v = new VoronoiDiagram(new Dimension(500,500), 44);
    }
}



