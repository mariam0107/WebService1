package com.mariam2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLDocument;

@WebService(serviceName = "ScreenShotSimple")
public class ScreenShotSimple {

    private BufferedImage image = null;

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "takeScreenShot")
    public void takeScreenShot(String webpageurl) {

        try {
            URL url = new URL(webpageurl);
            final JEditorPane jep = new JEditorPane();
            jep.setContentType("text/html");
            ((HTMLDocument) jep.getDocument()).setBase(url);
            jep.setEditable(false);
            jep.setBounds(0, 0, 1024, 768);
            jep.addPropertyChangeListener("page", new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    try {
                        image = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
                        Graphics g = image.getGraphics();
                        Graphics2D graphics = (Graphics2D) g;
                        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        jep.paint(graphics);
                        ImageIO.write(image, "png", new File("D:/simpleone.png"));
                    } catch (Exception re) {
                        re.printStackTrace();
                    }
                }
            });
            jep.setPage(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
