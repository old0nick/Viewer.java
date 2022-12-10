import java.awt.*;
import java.awt.geom.Point2D;


public class Vertex {

    double x, y, z;

    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Point2D.Double screenTransition(Vertex camera) {

        double distance = 200;

        return new Point2D.Double(distance * this.x / this.z, distance * this.y / this.z);
    }

    Vertex viewCoordinateTrans() {

        double r = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        double zeta = Math.acos(z/r);
        double phi = Math.acos(this.x/(r * Math.sin(zeta)));

        double xE = -this.x * Math.sin(phi) + this.y * Math.cos(phi);
        double yE = -this.x * Math.cos(zeta) * Math.cos(phi) - this.y * Math.cos(zeta) * Math.sin(phi) + this.z * Math.sin(zeta);
        double zE = -this.x * Math.sin(zeta) * Math.cos(phi) - this.y * Math.sin(zeta) * Math.sin(phi) - this.z * Math.cos(zeta) + r;

        return new Vertex(xE, yE, zE);
    }

}
