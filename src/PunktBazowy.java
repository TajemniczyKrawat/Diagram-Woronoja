import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class PunktBazowy extends Point2D.Double {
    private ArrayList<Polaczenie> polaczenia = new ArrayList<>();
    private Set<PunktGraniczny> punktyPrzeciecia = new TreeSet<>(new Atan2Comparator(this));

    PunktBazowy(double x, double y) {
        super(x, y);
    }

    void stworzPolaczenia(ArrayList<PunktBazowy> punkty) {
        for (PunktBazowy punktBazowy : punkty) {
            if (punktBazowy == this) continue;
            polaczenia.add(new Polaczenie(this, punktBazowy));
        }
    }

   private TreeSet<PunktGraniczny> obliczPrzeciecia(Dimension wymiaryOkna) {
        TreeSet<PunktGraniczny> punkty = new TreeSet<>(new Atan2Comparator(this));
        punkty.add(new PunktGraniczny(0,0));
        punkty.add(new PunktGraniczny(wymiaryOkna.width,0));
        punkty.add(new PunktGraniczny(0,wymiaryOkna.height));
        punkty.add(new PunktGraniczny(wymiaryOkna.width,wymiaryOkna.height));

        for(int i = 0; i < polaczenia.size(); i++) {
            for(int j = i; j < polaczenia.size(); j++) {
                punkty.addAll(polaczenia.get(i).obliczPunktyPrzeciecia(polaczenia.get(j), wymiaryOkna));
            }
        }
        TreeSet<PunktGraniczny> wynik = odrzucPunkty(wymiaryOkna, punkty);
        return wynik;
    }

    Polygon wyliczPoligon(Dimension wymiaryOkna) {
        TreeSet<PunktGraniczny> punktyLista = obliczPrzeciecia(wymiaryOkna);
        PunktGraniczny[] punkty = new PunktGraniczny[punktyLista.size()];
        punkty = punktyLista.toArray(punkty);

        int[] x = new int[punkty.length];
        int[] y = new int[punkty.length];
        for(int i = 0; i < punkty.length; i++) {
            x[i] = (int)punkty[i].x;
            y[i] = (int)punkty[i].y;
        }

        System.out.println(punkty.length + "punk");
        return new Polygon(x, y, punkty.length);
    }

    private TreeSet<PunktGraniczny> odrzucPunkty(Dimension wymiaryOkna, Set<PunktGraniczny> punktyPrzeciecia) {
        ArrayList<PunktGraniczny> doUsuniecia = new ArrayList<>();

        System.out.println(punktyPrzeciecia.toString());

        System.out.println(punktyPrzeciecia.size());

        //odrzuca punkty poza granicami
        for (PunktGraniczny punkt : punktyPrzeciecia) {
            if (!jestWGranicach(wymiaryOkna, punkt)) doUsuniecia.add(punkt);
        }

        //odrzuca NaN
        for (PunktGraniczny punkt : punktyPrzeciecia) {
            if (punkt.x != punkt.x) {
                doUsuniecia.add(punkt);
            }
        }

        //odrzuca punkty lezace po roznej stronie prostych
        for(PunktGraniczny punkt : punktyPrzeciecia) {
            for(Polaczenie pol : polaczenia) {
                if ((pol.getWspAProstop() * x - y + pol.getWspBProstop()) * (pol.getWspAProstop() * punkt.x - punkt.y + pol.getWspBProstop()) < -0.001) {
                    doUsuniecia.add(punkt);
                }
            }
        }

        System.out.println(doUsuniecia.size());


        punktyPrzeciecia.removeAll(doUsuniecia);

        System.out.println(punktyPrzeciecia.size());

        return (TreeSet<PunktGraniczny>) punktyPrzeciecia;
    }

    private boolean jestWGranicach(Dimension wymiaryOkna, PunktGraniczny punkt) {
        return punkt.y <= wymiaryOkna.height && punkt.y >= 0 && punkt.x <= wymiaryOkna.width && punkt.x >= 0;
    }

    private class Atan2Comparator implements Comparator<PunktGraniczny> {
        private PunktBazowy punktOdniesienia;

        Atan2Comparator(PunktBazowy punktOdniesienia) {
            this.punktOdniesienia = punktOdniesienia;
        }

        @Override
        public int compare(PunktGraniczny o1, PunktGraniczny o2) {
            if(o1.getRadiany(punktOdniesienia) > o2.getRadiany(punktOdniesienia)) return 1;
            else return -1;
        }
    }

    public static void main(String[] args) {

    }
}
