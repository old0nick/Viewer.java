import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.*;

public class Viewer {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // slider to control horizontal rotation
        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        // slider to control vertical rotation
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -180, 180, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        // panel to display render results
        final double[] distance = {500.0};
        final double[] cd = {0.0};

        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                List<Square> cube = new ArrayList<>();
                cube.add(new Square(new Vertex(100, 100, 100), new Vertex(-100, 100, 100), new Vertex(-100, -100, 100), new Vertex(100, -100, 100)));
                cube.add(new Square(new Vertex(100, 100, 100), new Vertex(-100, 100, 100), new Vertex(-100, 100, -100), new Vertex(100, 100, -100)));
                cube.add(new Square(new Vertex(100, 100, -100), new Vertex(-100, 100, -100), new Vertex(-100, -100, -100), new Vertex(100, -100, -100)));
                cube.add(new Square(new Vertex(100, -100, -100), new Vertex(-100, -100, -100), new Vertex(-100, -100, 100), new Vertex(100, -100, 100)));
                cube.add(new Square(new Vertex(100, 100, 100), new Vertex(100, 100, -100), new Vertex(100, -100, -100), new Vertex(100, -100, 100)));
                cube.add(new Square(new Vertex(-100, 100, 100), new Vertex(-100, 100, -100), new Vertex(-100, -100, -100), new Vertex(-100, -100, 100)));

                Vertex camera = new Vertex(0, 500 , 0);

                double heading = -Math.toRadians(headingSlider.getValue());
                double pitch = -Math.toRadians(pitchSlider.getValue());
                Matrix3 headingTransform = new Matrix3(new double[] {
                    Math.cos(heading), -Math.sin(heading), 0,
                    0, 0, 1,
                    Math.sin(heading), Math.cos(heading), 0
                });
                Matrix3 pitchTransform = new Matrix3(new double[] {
                    1, 0, 0,
                    0, Math.cos(pitch), Math.sin(pitch),
                    0, Math.sin(pitch), -Math.cos(pitch)
                });

                g2.translate(getWidth()/2, getHeight()/2);
                g2.setColor(Color.WHITE);

                Matrix3 transform = headingTransform.multiply(pitchTransform);

                for (Square s : cube) {
                    Vertex v1 = transform.transform(s.v1);
                    Vertex v2 = transform.transform(s.v2);
                    Vertex v3 = transform.transform(s.v3);
                    Vertex v4 = transform.transform(s.v4);

                    v1 = v1.viewCoordinateTrans(camera, cd[0]);
                    v2 = v2.viewCoordinateTrans(camera, cd[0]);
                    v3 = v3.viewCoordinateTrans(camera, cd[0]);
                    v4 = v4.viewCoordinateTrans(camera, cd[0]);

                    Point2D vs1 = v1.screenTransition(distance[0]);
                    Point2D vs2 = v2.screenTransition(distance[0]);
                    Point2D vs3 = v3.screenTransition(distance[0]);
                    Point2D vs4 = v4.screenTransition(distance[0]);

                    Path2D path = new Path2D.Double();
                    path.moveTo(vs1.getX(), vs1.getY());
                    path.lineTo(vs2.getX(), vs2.getY());
                    path.lineTo(vs3.getX(), vs3.getY());
                    path.lineTo(vs4.getX(), vs4.getY());
                    path.closePath();

                    g2.draw(path);
                }
            }
        };

        renderPanel.setFocusable(true);
        renderPanel.addMouseWheelListener((MouseWheelEvent e) -> {
            int z = e.getWheelRotation();
            System.out.println(z);
            if (z < 0) {
                distance[0] -= 10.0;
                cd[0] -= 10.0;
            } else if (z > 0) {
                distance[0] += 10.0;
                cd[0] += 10.0;
            }
            renderPanel.repaint();
        });

        pane.add(renderPanel, BorderLayout.CENTER);

        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(1200, 1200);
        frame.setVisible(true);
    }
}
