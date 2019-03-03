import java.awt.geom.Point2D;

class PunktGraniczny extends Point2D.Double{
    private java.lang.Double kat = null;

    double getRadiany(Point2D.Double punktOdniesienia) {
        if(kat == null) {
            kat = Math.atan2(punktOdniesienia.y - y, punktOdniesienia.x - x);
        }
        return kat;
    }

    PunktGraniczny(int x, int y) {
        super.x = x;
        super.y = y;
    }

    PunktGraniczny(double x, int y) {
        super.x = x;
        super.y = y;
    }

    PunktGraniczny(int x, double y) {
        super.x = x;
        super.y = y;
    }

    PunktGraniczny(double x, double y) {
        super.x = x;
        super.y = y;
    }
}
