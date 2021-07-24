package mygeocal;

/**
 *
 * @author Tanvir Tareq
 */
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
/**
 *
 * @MAGIC_KIRI
 */
public class Grid {

    public static double positionOfXAxis, tmpX;
    public static double positionOfYAxis, tmpY;
    public static double unitOfScale;
    public static double increment;
    public static boolean bug1 = false;
    public static double mousePressedX, dX;
    public static double mousePressedY, dY;
    static AnchorPane pane;
    static boolean willGraphPaperExist, newNodeBool;

    public static Line xAxis, yAxis;

    static GraphicsContext gc;

//    static NodeBound canvasBound;
    public static void main(String[] args) {

    }

    Grid(GraphicsContext gc, AnchorPane pane, double dX, double dY) {

        this.gc = gc;
        this.pane = pane;
        this.dX = dX;
        this.dY = dY;
        willGraphPaperExist = true;
        newNodeBool = false;
        gc.setLineWidth(0.1);
        positionOfXAxis = (gc.getCanvas().getBoundsInLocal().getMinY() + gc.getCanvas().getBoundsInLocal().getMaxY()) / 2.0;
        positionOfYAxis = (gc.getCanvas().getBoundsInLocal().getMinX() + gc.getCanvas().getBoundsInLocal().getMaxX()) / 2.0;

        xAxis = new Line();
        xAxis.setStrokeWidth(2.);
        xAxis.setStroke(Color.GREEN);
        yAxis = new Line();
        yAxis.setStrokeWidth(2.3);
        yAxis.setStroke(Color.GREEN);

        xAxis.setStartX(gc.getCanvas().getBoundsInLocal().getMinX() + dX);
        xAxis.setStartY(dY + positionOfXAxis);
        xAxis.setEndX(gc.getCanvas().getBoundsInLocal().getMaxX() + dX);
        xAxis.setEndY(dY + positionOfXAxis);
        unitOfScale = 1;
        increment = 70;
        yAxis.setStartX(dX + positionOfYAxis);
        yAxis.setStartY(gc.getCanvas().getBoundsInLocal().getMinY() + dY);
        yAxis.setEndX(dX + positionOfYAxis);
        yAxis.setEndY(gc.getCanvas().getBoundsInLocal().getMaxY() + dY);
        xAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (FXMLDocumentController.key == 0) {
                    xAxis.setCursor(Cursor.CROSSHAIR);
                } else {
                    xAxis.setCursor(Cursor.CLOSED_HAND);
                }
            }
        });
        yAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (FXMLDocumentController.key == 0) {
                    yAxis.setCursor(Cursor.CROSSHAIR);
                } else {
                    yAxis.setCursor(Cursor.CLOSED_HAND);
                }
            }
        });
        pane.getChildren().addAll(xAxis, yAxis);

        drawHorizontalLines(gc, increment);
        drawVerticalLines(gc, increment);
        redraw(gc);
    }

    public void graphPaperButtonPressed(ActionEvent event) {
        if (willGraphPaperExist == true) {
            willGraphPaperExist = false;
            xAxis.setVisible(false);
            yAxis.setVisible(false);
            clearCanvas(gc);

        } else {
            xAxis.setVisible(true);
            yAxis.setVisible(true);
            willGraphPaperExist = true;
            clearCanvas(gc);
            redraw(gc);
        }
    }

    public void zoomInButtonPressed(ActionEvent event) {
        if (unitOfScale > 0.00001) {
            increment += 10;

            if (increment > 100) {
                increment = 50;
                unitOfScale = unitOfScale / 2;
            }
        }
        redraw(gc);
    }

    public void zoomOutButtonPressed(ActionEvent event) {
        if (unitOfScale < 100000000) {
            increment -= 10;
            if (increment < 50) {
                increment = 100;
                unitOfScale = unitOfScale * 2;

            }
        }
        redraw(gc);
    }

    public void setPositionOfXAxis(double positionOfXAxis) {
        this.positionOfXAxis = positionOfXAxis;
    }

    public void setPositionOfYAxis(double positionOfYAxis) {
        this.positionOfYAxis = positionOfYAxis;
    }

    private void drawHorizontalLines(GraphicsContext gc, double increment) {
        xAxis.setStartX(dX);
//        System.out.println( gc.getCanvas().get) + "  " + gc.getCanvas().getBoundsInLocal().getMinX() );
        xAxis.setStartY(dY + positionOfXAxis);
        xAxis.setEndX(gc.getCanvas().getWidth() + dX);
        xAxis.setEndY(dY + positionOfXAxis);
        int p, k;
        for (double i = increment + ((positionOfXAxis - gc.getCanvas().getHeight()) % increment) + gc.getCanvas().getHeight(); i > 0 ;) {
            for (int h = 0; h < 4; h++) {
                i -= (increment / 5);
                gc.strokeLine(dX, i, gc.getCanvas().getWidth(), i);
            }
            i -= (increment / 5);
            Double d = ((positionOfXAxis - i) / increment) * unitOfScale;
            if (Math.abs(Math.round(d) - d) < 0.000000001) {
                d = (double) Math.round(d);
            }
            d = Double.parseDouble(String.format("%.5f", d));
            Integer inti = d.intValue();
            if (0 != (d - inti)) {
                writeForHorizontal(gc, d.toString(), i);
            } else {
                writeForHorizontal(gc, inti.toString(), i);
            }
            for (int j = 0; j < 8; j++) {
                gc.strokeLine(dX, i, gc.getCanvas().getWidth(), i);
            }

        }

    }

    private void drawVerticalLines(GraphicsContext gc, double increment) {
        yAxis.setStartX(dX + positionOfYAxis);
        yAxis.setStartY(gc.getCanvas().getBoundsInLocal().getMinY() + dY);
        yAxis.setEndX(dX + positionOfYAxis);
        yAxis.setEndY(gc.getCanvas().getBoundsInLocal().getMaxY() + dY);
        gc.fillText("0", positionOfYAxis + 3, positionOfXAxis - 3);
        for (double i = increment + ((positionOfYAxis - gc.getCanvas().getBoundsInLocal().getMaxX()) % increment) + gc.getCanvas().getBoundsInLocal().getMaxX(); i > gc.getCanvas().getBoundsInLocal().getMinX();) {
            for (int h = 0; h < 4; h++) {
                i -= (increment / 5);
                gc.strokeLine(i, gc.getCanvas().getBoundsInLocal().getMinY(), i, gc.getCanvas().getBoundsInLocal().getMaxY());
            }
            i -= (increment / 5);
            Double d = ((i - positionOfYAxis) / increment) * unitOfScale;
            if (Math.abs(Math.round(d) - d) < 0.000000001) {
                d = (double) Math.round(d);
            }
            d = Double.parseDouble(String.format("%.5f", d));
            Integer inti = d.intValue();
            if (0 != (d - inti)) {
                writeForVertical(gc, d.toString(), i);
            } else {
                writeForVertical(gc, inti.toString(), i);
            }
            for (int j = 0; j < 8; j++) {
                gc.strokeLine(i, gc.getCanvas().getBoundsInLocal().getMinY(), i, gc.getCanvas().getBoundsInLocal().getMaxY());
            }
        }
    }

    public void clearCanvas(GraphicsContext gc) {
        gc.clearRect(gc.getCanvas().getBoundsInLocal().getMinX(), gc.getCanvas().getBoundsInLocal().getMinY(), gc.getCanvas().getBoundsInLocal().getMaxX(), gc.getCanvas().getBoundsInLocal().getMaxY());
    }

    public void redraw(GraphicsContext gc) {
        clearCanvas(gc);
        if (willGraphPaperExist) {
            drawHorizontalLines(gc, increment);
            drawVerticalLines(gc, increment);
        }
    }

    public void changeAxes(double x, double y) {
        double changeOfX = (y - mousePressedY);
        double changeOfY = (x - mousePressedX);
        positionOfXAxis = tmpX + changeOfX;
        positionOfYAxis = tmpY + changeOfY;

    }

    public void writeForHorizontal(GraphicsContext gc, String text, double i) {
        if (gc.getCanvas().getBoundsInLocal().getMinX() <= positionOfYAxis && positionOfYAxis <= gc.getCanvas().getBoundsInLocal().getMaxX()) {
            gc.fillText(text, 3 + positionOfYAxis, i - 3);
            yAxis.setVisible(true);
        } else if (positionOfYAxis < gc.getCanvas().getBoundsInLocal().getMinX()) {
            gc.fillText(text, gc.getCanvas().getBoundsInLocal().getMinX() + 3, i - 3);
            yAxis.setVisible(false);
        } else if (gc.getCanvas().getBoundsInLocal().getMaxX() < positionOfYAxis) {
            gc.fillText(text, gc.getCanvas().getBoundsInLocal().getMaxX() - 20 - 30 + 3, i - 3);
            yAxis.setVisible(false);
        }
    }

    public void writeForVertical(GraphicsContext gc, String text, double i) {

        if (gc.getCanvas().getBoundsInLocal().getMinY() <= positionOfXAxis && gc.getCanvas().getBoundsInLocal().getMaxY() - 100 >= positionOfXAxis) {
            // hudai 100 minus disi
            xAxis.setVisible(true);
            gc.fillText(text, 3 + i, positionOfXAxis - 3);
        } else if (gc.getCanvas().getBoundsInLocal().getMinY() > positionOfXAxis) {
            gc.fillText(text, i + 3, gc.getCanvas().getBoundsInLocal().getMinY() + 30 - 3);
            xAxis.setVisible(false);

        } else if (gc.getCanvas().getBoundsInLocal().getMaxY() - 100 < positionOfXAxis) {
            xAxis.setVisible(false);
            gc.fillText(text, i + 3, gc.getCanvas().getBoundsInLocal().getMaxY() - 80 - 30 - 3);
        }
    }

    void drawFunctions() {

    }

    public void scrollZoom(ScrollEvent event) {
        double X = (event.getX() - dX - positionOfYAxis) * unitOfScale / increment;
        double Y = (positionOfXAxis - (event.getY() - dY)) * unitOfScale / increment;

        if (event.getDeltaY() > 0) {
            //ZOOM IN
            if (unitOfScale > 0.00001) {
                increment += 10;

                if (increment > 100) {
                    increment = 50;
                    unitOfScale = unitOfScale / 2;
                }
            }

        } else {
            //ZOOM OUT
            if (unitOfScale < 100000000) {
                increment -= 10;
                if (increment < 50) {
                    increment = 100;
                    unitOfScale = unitOfScale * 2;

                }
            }
        }
        positionOfYAxis = (event.getX() - dX) - ((increment / unitOfScale) * X);
        positionOfXAxis = (event.getY() - dY) + ((increment / unitOfScale) * Y);
        if (willGraphPaperExist) {
            redraw(gc);
        }
    }
}
