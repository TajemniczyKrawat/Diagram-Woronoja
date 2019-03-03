import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

class Polaczenie {
    private Point2D.Double punkt1;
    private Point2D.Double punkt2;

    private double wspA;
    private double wspAProstop;
    private double wspBProstop;
    private Point2D.Double punktSrodkowy;

    double getWspAProstop() {
        return wspAProstop;
    }

    double getWspBProstop() {
        return wspBProstop;
    }

    Polaczenie(Point2D.Double punkt1, Point2D.Double punkt2) {
        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
        wspA = obliczWspA();
        wspAProstop = -1/wspA;
        punktSrodkowy = obliczPunktSrodkowy();
        wspBProstop = obliczWspBProstop();
    }

    private double obliczWspA() {
        return (punkt1.y - punkt2.y) / (punkt1.x - punkt2.x);
    }
    private double obliczWspBProstop() {
        return punktSrodkowy.y - wspAProstop * punktSrodkowy.x;
    }
    private Point2D.Double obliczPunktSrodkowy() {
        return new Point2D.Double((punkt1.x + punkt2.x) / 2.0, (punkt1.y + punkt2.y) / 2.0);
    }

    ArrayList<PunktGraniczny> obliczPunktyPrzeciecia(Polaczenie innePolaczenie, Dimension wymiaryOkna) {
        ArrayList<PunktGraniczny> przeciecia = new ArrayList<>();
        double w, wx, wy;
        w = -wspAProstop - -innePolaczenie.wspAProstop;
        wx = wspBProstop - innePolaczenie.wspBProstop;
        wy = (-wspAProstop * innePolaczenie.wspBProstop)-(-innePolaczenie.wspAProstop * wspBProstop);

        double x = wx/w, y = wy/w;

        if (w != 0) {
            if(y <= wymiaryOkna.height && y >= 0 && x <= wymiaryOkna.width && x >= 0) {
                przeciecia.add(new PunktGraniczny(wx/w, wy/w));
            }
        }

        //gorna
            przeciecia.add(new PunktGraniczny(-wspBProstop/wspAProstop, 0));
        //lewa
            przeciecia.add(new PunktGraniczny(0, wspBProstop));
        //dol
            przeciecia.add(new PunktGraniczny((wymiaryOkna.height-wspBProstop) / wspAProstop, wymiaryOkna.height));
        //prawo
            przeciecia.add(new PunktGraniczny(wymiaryOkna.width, wspAProstop * wymiaryOkna.width + wspBProstop));

        return przeciecia;
    }
}
