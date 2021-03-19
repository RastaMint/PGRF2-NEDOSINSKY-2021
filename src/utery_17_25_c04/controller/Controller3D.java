package utery_17_25_c04.controller;

import utery_17_25_c04.transforms.*;
import utery_17_25_c04.model.Element;
import utery_17_25_c04.model.TopologyType;
import utery_17_25_c04.model.Vertex;
import utery_17_25_c04.rasterize.Raster;
import utery_17_25_c04.renderer.GPURenderer;
import utery_17_25_c04.renderer.RendererZBuffer;
import utery_17_25_c04.view.Panel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private final GPURenderer renderer;
    private final Panel panel;
    private final Raster<Integer> raster;

    private final List<Element> elements;
    private final List<Vertex> vb; // vertex buffer
    private final List<Integer> ib; // index buffer

    private Mat4 model, projection;
    private Camera camera;
    private int xpressed;
    private int ypressed;
    private boolean perspektiva;
    public boolean dratovyModel;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        this.renderer = new RendererZBuffer(raster);

        elements = new ArrayList<>();
        vb = new ArrayList<>();
        ib = new ArrayList<>();

        initMatrices();
        initListeners(panel);
        initObjects();
        display();
    }

    private void initObjects() {

        //TROJÚHELNÍKY
        vb.add(new Vertex(new Point3D(1.5, .0, .9), new Col(255, 0, 0))); // 0 // nejvíce vlevo
        vb.add(new Vertex(new Point3D(1.7, .7, .9), new Col(255, 120, 0))); // 1 // nejvíce dole
        vb.add(new Vertex(new Point3D(1.0, .5, .3), new Col(255, 255, 0))); // 2 // společný
        vb.add(new Vertex(new Point3D(1.3, .8, .5), new Col(0, 255, 0))); // 3 // nejvíce vpravo
        vb.add(new Vertex(new Point3D(1.1, .2, 1), new Col(0, 255, 120))); // 4 // nejvíce nahoře
        vb.add(new Vertex(new Point3D(1.7, .3, .2), new Col(0, 255, 255))); // 4 // nejvíce nahoře

        //OSY
        vb.add(new Vertex(new Point3D(0, 0, 0), new Col(255, 255, 255))); //Střed obrazovky?
        vb.add(new Vertex(new Point3D(2, 0, 0), new Col(255, 0, 0))); //osa x
        vb.add(new Vertex(new Point3D(0, 2, 0), new Col(0, 255, 0))); //osa y
        vb.add(new Vertex(new Point3D(0, 0, 2), new Col(0, 0, 255))); //osa z

        //KVÁDR
        vb.add(new Vertex(new Point3D(-2, 2, 1), new Col(255, 255, 255))); // podstava kvádru 1
        vb.add(new Vertex(new Point3D(-4, 2, 1), new Col(255, 50, 55))); // podstava kvádru 2
        vb.add(new Vertex(new Point3D(-4, 4, 1), new Col(35, 255, 155))); // podstava kvádru 3
        vb.add(new Vertex(new Point3D(-2, 4, 1), new Col(255, 128, 255))); // podstava kvádru 4

        vb.add(new Vertex(new Point3D(-2, 2, 2), new Col(128, 0, 155))); // strop kvádru 1
        vb.add(new Vertex(new Point3D(-4, 2, 2), new Col(128, 255, 35))); // strop kvádru 2
        vb.add(new Vertex(new Point3D(-4, 4, 2), new Col(12, 55, 170))); // strop kvádru 3
        vb.add(new Vertex(new Point3D(-2, 4, 2), new Col(255, 0, 255))); // strop kvádru 4

        //JEHLAN
        vb.add(new Vertex(new Point3D(2, 2, 1), new Col(25, 155, 75))); // podstava jehlanu 1
        vb.add(new Vertex(new Point3D(4, 2, 1), new Col(35, 70, 85))); // podstava jehlanu 2
        vb.add(new Vertex(new Point3D(4, 4, 1), new Col(85, 55, 255))); // podstava jehlanu 3
        vb.add(new Vertex(new Point3D(2, 4, 1), new Col(13, 128, 255))); // podstava jehlanu 4
        vb.add(new Vertex(new Point3D(3, 3, 3.5), new Col(255, 40, 75))); // horní vrchol jehlanu

        //ŠIPKY PRO OSY
        vb.add(new Vertex(new Point3D(1.8, 0.2, 0), new Col(255, 0, 0))); // šipka 1 X
        vb.add(new Vertex(new Point3D(1.8, -0.2, 0), new Col(255, 0, 0, 0))); // šipka 1 X

        vb.add(new Vertex(new Point3D(1.8, 0, 0.2), new Col(255, 0, 0))); // šipka 2 X
        vb.add(new Vertex(new Point3D(1.8, 0, -0.2), new Col(255, 0, 0, 0))); // šipka 2 X

        vb.add(new Vertex(new Point3D(0.2, 1.8, 0), new Col(0, 255, 0))); // šipka 1 Y
        vb.add(new Vertex(new Point3D(-0.2, 1.8, 0), new Col(0, 255, 0))); // šipka 1 Y

        vb.add(new Vertex(new Point3D(0, 1.8, 0.2), new Col(0, 255, 0))); // šipka 2 Y
        vb.add(new Vertex(new Point3D(0, 1.8, -0.2), new Col(0, 255, 0))); // šipka 2 Y


        vb.add(new Vertex(new Point3D(0.2, 0, 1.8), new Col(0, 0, 255))); // šipka 1 Z
        vb.add(new Vertex(new Point3D(-0.2, 0, 1.8), new Col(0, 0, 255))); //šipka 1 Z

        vb.add(new Vertex(new Point3D(0, 0.2, 1.8), new Col(0, 0, 255))); // šipka 2 Z
        vb.add(new Vertex(new Point3D(0, -0.2, 1.8), new Col(0, 0, 255))); //šipka 2 Z


        //TROJÚHELNÍK 1
        ib.add(0);
        ib.add(1);
        ib.add(2);

        //TROJÚHELNÍK 2
        ib.add(3);
        ib.add(4);
        ib.add(5);

        //OSA X
        ib.add(6);
        ib.add(7);

        //OSA Y
        ib.add(6);
        ib.add(8);

        //OSA Z
        ib.add(6);
        ib.add(9);

        //KVÁDR
        // podstava 1
        ib.add(10);
        ib.add(11);
        ib.add(12);
        // podstava 2
        ib.add(10);
        ib.add(12);
        ib.add(13);
        // strop 1
        ib.add(14);
        ib.add(15);
        ib.add(16);
        //strop 2
        ib.add(17);
        ib.add(14);
        ib.add(16);
        //STĚNY
        //zadní stěna
        ib.add(13);
        ib.add(16);
        ib.add(17);
        ib.add(12);
        ib.add(16);
        ib.add(13);
        // pravá stěna
        ib.add(17);
        ib.add(13);
        ib.add(10);
        ib.add(17);
        ib.add(10);
        ib.add(14);
        // levá stěna
        ib.add(11);
        ib.add(12);
        ib.add(16);
        ib.add(15);
        ib.add(16);
        ib.add(11);
        // přední stěna
        ib.add(11);
        ib.add(10);
        ib.add(15);
        ib.add(14);
        ib.add(15);
        ib.add(10);

        //JEHLAN
        //podstava
        ib.add(18);
        ib.add(19);
        ib.add(20);
        ib.add(18);
        ib.add(20);
        ib.add(21);
        //stěny
        //zadní stěna
        ib.add(21);
        ib.add(20);
        ib.add(22);
        //přední stěna
        ib.add(19);
        ib.add(18);
        ib.add(22);
        //levá stěna
        ib.add(19);
        ib.add(20);
        ib.add(22);
        //pravá stěna
        ib.add(21);
        ib.add(18);
        ib.add(22);

        //ŠIPKY
        //šipka 1 osy X
        ib.add(7);
        ib.add(23);
        ib.add(24);
        //šipka 2 osy X
        ib.add(7);
        ib.add(25);
        ib.add(26);
        //šipka 1 osy Y
        ib.add(8);
        ib.add(27);
        ib.add(28);
        //šipka 2 osy Y
        ib.add(8);
        ib.add(29);
        ib.add(30);
        //šipka 1 osy Z
        ib.add(9);
        ib.add(31);
        ib.add(32);
        //šipka 2 osy Z
        ib.add(9);
        ib.add(33);
        ib.add(34);

        elements.add(new Element(TopologyType.TRIANGLE, 0, 6)); //trojúhelníky ze cvičení
        elements.add(new Element(TopologyType.LINE, 6, 6)); //osy
        elements.add(new Element(TopologyType.TRIANGLE, 12, 36)); //kvádr
        elements.add(new Element(TopologyType.TRIANGLE, 48, 18)); //jehlan
        elements.add(new Element(TopologyType.TRIANGLE, 66, 18)); // šipky os

    }

    private void initMatrices() {
        model = new Mat4Identity();

        var e = new Vec3D(0, -5, 2);
        camera = new Camera()
                .withPosition(e)
                .withAzimuth(Math.toRadians(90))
                .withZenith(Math.toRadians(-20));

        perspektiva = true;

        projection = new Mat4PerspRH(
                Math.PI / 3,
                raster.getHeight() / (float) raster.getWidth(),
                0.5,
                50
        );
    }

    public boolean isDratovyModel() {
        return dratovyModel;
    }

    private void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                xpressed = e.getX();
                ypressed = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (xpressed < e.getX()) {
                    camera = camera.addAzimuth(-(e.getX() - xpressed) / 1000.0);
                    xpressed = e.getX();
                } else if (e.getX() < xpressed) {
                    camera = camera.addAzimuth((xpressed - e.getX()) / 1000.0);
                    xpressed = e.getX();
                }
                if (ypressed < e.getY()) {
                    camera = camera.addZenith(-(e.getY() - ypressed) / 1000.0);
                    ypressed = e.getY();
                } else if (e.getY() < ypressed) {
                    camera = camera.addZenith((ypressed - e.getY()) / 1000.0);
                    ypressed = e.getY();
                }
                display();


            }
        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() == -1) {
                    Mat4 mat = new Mat4Scale(1.1, 1.1, 1.1);
                    model = model.mul(mat);

                } else {
                    Mat4 mat = new Mat4Scale(0.9, 0.9, 0.9);
                    model = model.mul(mat);

                }
                display();


            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.forward(0.5);

                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(0.5);

                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(0.5);

                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.backward(0.5);

                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    if (perspektiva) {
                        perspektiva = false;
                        projection = new Mat4OrthoRH(
                                10.0,
                                10.0 * (raster.getHeight() / (float) raster.getWidth()),
                                0.5,
                                50
                        );
                    } else {
                        perspektiva = true;
                        projection = new Mat4PerspRH(
                                Math.PI / 3,
                                raster.getHeight() / (float) raster.getWidth(),
                                0.5,
                                50
                        );
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (dratovyModel) {
                        display();
                        dratovyModel = false;
                    } else {
                        display();
                        dratovyModel = true;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    camera = camera.up(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    camera = camera.down(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    Mat4 matrotate = new Mat4RotX(0.1);
                    model = model.mul(matrotate);
                } else if (e.getKeyCode() == KeyEvent.VK_T) {
                    Mat4Transl mattransl = new Mat4Transl(1, 0, 0);
                    model = model.mul(mattransl);
                }
                display();
            }


        });
    }

    private synchronized void display() {

        renderer.clear();


        renderer.setModel(model);
        renderer.setView(camera.getViewMatrix());
        renderer.setProjection(projection);
        renderer.draw(elements, ib, vb);
        panel.repaint();
        // musíme nakonec říci, že panel má nový obsah zobrazit

    }

}
