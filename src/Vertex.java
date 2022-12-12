import java.awt.geom.Point2D;


public class Vertex {

    double x, y, z;

    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Point2D.Double screenTransition(double distance) {
        return new Point2D.Double(distance * this.x / this.z, distance * this.y / this.z);
    }

    Vertex viewCoordinateTrans(Vertex camera, double cd) {

        double r = Math.sqrt(Math.pow(camera.x, 2) + Math.pow(camera.y, 2) + Math.pow(camera.z, 2)) + cd;
        double zeta = Math.acos(camera.z/r);
        double rxy = Math.sqrt(Math.pow(camera.x, 2) + Math.pow(camera.y, 2)) + cd;
        double phi = 0;
        if (rxy != 0) {
            phi = Math.acos(camera.x/rxy);
        }

        double xE = -this.x * Math.sin(phi) + this.y * Math.cos(phi);
        double yE = -this.x * Math.cos(zeta) * Math.cos(phi) - this.y * Math.cos(zeta) * Math.sin(phi) + this.z * Math.sin(zeta);
        double zE = -this.x * Math.sin(zeta) * Math.cos(phi) - this.y * Math.sin(zeta) * Math.sin(phi) - this.z * Math.cos(zeta) + r;

        return new Vertex(xE, yE, zE);
    }

}
