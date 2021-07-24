/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Tanvir Tareq
 */
public class Equation extends Application {

    AnchorPane pane;

    Line prev = null, prevOfPrev = null;

    static ArrayList<Equation> equations = new ArrayList<Equation>();

    ArrayList<Line> lines = new ArrayList<Line>();
    ArrayList<Number> numbers = new ArrayList<Number>();

    String infixEquation = "", postfixEquation = "";

    void Equation1(String infixEquation) {

        setInfix(infixEquation);
        infixToPostfixConverter(this.infixEquation);
        draw();

    }

    Equation(String infixEquation) {

        equations.add(this);
        setInfix(infixEquation);
        infixToPostfixConverter(this.infixEquation);
        System.out.println(this.infixEquation);
        System.out.println(this.postfixEquation);
        draw();

    }

    private void infixToPostfixConverter(String infixEquation) {
        /**
         *
         *
         * 22+33 n22n+n33n
         *
         */

        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < infixEquation.length(); ++i) {

            char c = infixEquation.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfixEquation += stack.pop();
                }

                stack.pop();

                if (!stack.isEmpty() && stack.peek() == 'f') {
                    postfixEquation += stack.pop();
                    while (!stack.isEmpty() && stack.peek() != 'f') {
                        postfixEquation += stack.pop();
                    }
                    if (!stack.isEmpty()) {
                        postfixEquation += stack.pop();
                    }
                }

            } else if (c == 'x') {
                this.postfixEquation += c;
            } else if (c == '-' && infixEquation.charAt(i - 1) == 'n') {
                this.postfixEquation += c;
            } else if (c == '+'
                    || c == '-'
                    || c == '*'
                    || c == '/'
                    || c == '^') {
                while (!stack.isEmpty() && prec(c) <= prec(stack.peek())) {
                    this.postfixEquation += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            } else if (c == 'f') {
                stack.push(c);

                i++;
                c = infixEquation.charAt(i);

                while (c != 'f') {
                    stack.push(c);

                    i++;
                    c = infixEquation.charAt(i);
                }

                stack.push(c);

            } else {
                this.postfixEquation += c;

            }

        }

        while (!stack.isEmpty()) {
            this.postfixEquation += stack.pop();
        }

    }

    private void setInfix(String infixEquation) {
        int isstart = 0;

        int isFstart = 0;

        int log = 0;

        for (int i = 0; i < infixEquation.length(); i++) {

            char c = infixEquation.charAt(i);

            if (infixEquation.charAt(i) >= '0' && infixEquation.charAt(i) <= '9' || infixEquation.charAt(i) == '.') {

                if (isstart == 0) {

                    this.infixEquation += 'n';
                    this.infixEquation += infixEquation.charAt(i);
                    isstart = 1;
                } else {
                    this.infixEquation += infixEquation.charAt(i);
                }

            }
            else if (c == '-' && 
                        (i == 0 || 
                            (!Character.isDigit(infixEquation.charAt(i - 1)) && infixEquation.charAt(i - 1) != '.' && infixEquation.charAt(i-1)!='x') ) ) {
                if (isstart == 0) {

                    this.infixEquation += 'n';
                    this.infixEquation += infixEquation.charAt(i);
                    isstart = 1;
                } else {
                    this.infixEquation += infixEquation.charAt(i);
                }
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%' || c == '(' || c == ')' || c == 'x') {

                if (isstart == 1) {
                    isstart = 0;
                    this.infixEquation += 'n';

                }
                if (isFstart == 1) {
                    isFstart = 0;
                    this.infixEquation += 'f';

                }

                this.infixEquation += infixEquation.charAt(i);
            } else if (c == ' ') {
                continue;
            } else if (c == ',') {
                if (isstart == 1) {
                    isstart = 0;
                    this.infixEquation += 'n';
                }
            } else {
                if (isstart == 1) {
                    isstart = 0;
                    this.infixEquation += 'n';
                }

                if (isFstart == 0) {

                    this.infixEquation += 'f';
                    this.infixEquation += infixEquation.charAt(i);
                    isFstart = 1;
                } else {
                    this.infixEquation += infixEquation.charAt(i);
                }
            }

        }

        if (isstart == 1) {
            this.infixEquation += 'n';
        }

    }

    private int prec(char c) {
        if (c == '+' || c == '-') {
            return 1;
        }
        if (c == '*' || c == '/') {
            return 2;
        }
        if (c == '^' || c == '%') {
            return 3;
        }

        return -1;

    }

    private void draw() {

        double px = 0;
        double x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);

        double y = 0, py = 0, prevx = 0, prevy = 0;
        try {
            y = evaluate(x);
            py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment + Grid.dY;

            prevx = px;
            prevy = py;

        } catch (Exception ex) {
//            System.out.println("yes");
//            System.out.println(ex.getClass().getName());

            py = -10;
            prevx = px;
            prevy = py;

        }

        for (px = 1; px <= Toolkit.getDefaultToolkit().getScreenSize().getWidth(); px += 2) {
            Line line = null;

            x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);

            try {
                y = evaluate(x);
//                System.out.println(x+" "+y);
                        

                py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment + Grid.dY;
                line = new Line(prevx, prevy, px, py);

            } catch (Exception ex) {
                py = -10;
                line = new Line(prevx, prevy, px, py);
//                System.out.println("py= "+py);
            }

            prevx = px;
            prevy = py;
            line.setStrokeWidth(2);
            line.setStroke(Color.DARKBLUE);

            if (line.getStartY() == -10 || line.getEndY() == -10) {
                line.setVisible(false);

            } else {
                line.setVisible(true);
            }

            if (line.isVisible() && prevOfPrev != null) {

                try {

//                    System.out.println((line.getEndX() - line.getStartX()));
                    double tangentOfLine = -(line.getEndY() - line.getStartY()) / ((line.getEndX() - line.getStartX()));
                    double tangentOfPrevLine = -(prev.getEndY() - prev.getStartY()) / ((prev.getEndX() - prev.getStartX()));
                    double tangentOfPrevOfPrevLine = -(prevOfPrev.getEndY() - prevOfPrev.getStartY()) / ((prevOfPrev.getEndX() - prevOfPrev.getStartX()));
                    if (tangentOfLine < 0) {
//                        System.out.println(x);
                        if (tangentOfPrevLine >= 0 && tangentOfPrevOfPrevLine >= 0 && tangentOfPrevLine > tangentOfPrevOfPrevLine) {
                            prev.setEndY(0);
                            line.setStartY(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                            line.setVisible(false);
                        }

                    } else if (tangentOfLine > 0) {
                        if (tangentOfPrevLine < 0 && tangentOfPrevOfPrevLine < 0 && tangentOfPrevLine < tangentOfPrevOfPrevLine) {
                            prev.setEndY(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                            line.setStartY(0);
                            line.setVisible(false);
                        }

                    }

                } catch (Exception e) {
//                    System.out.println(e.getClass().getName());
//                    System.out.println(x);
                }

            }

            if (line.isVisible()) {
                if (prev == null) {
                    prev = line;
                } else if (prevOfPrev == null) {
                    prevOfPrev = prev;
                    prev = line;
                } else {
                    prevOfPrev = prev;
                    prev = line;
                }
            }

            lines.add(line);

            FXMLDocumentController.pane.getChildren().add(line);

//            pane.getChildren().add(line);
        }

    }

    private double evaluate(double x) throws Exception {

        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < postfixEquation.length(); ++i) {
            char c = postfixEquation.charAt(i);

            if (Character.isDigit(c) || c == 'n' || c == '.') {
//                System.out.println("yes"+i);
                stack.push(c);
            }
//            else if (c == '-' && postfixEquation.charAt(i - 1) == 'n') {
//                stack.push(c);
//            } 
            else if (c == 'x') {
                Double d = x;
                String str = Double.toString(d);
//                System.out.println(str);

                stack.push('n');
//                System.out.println("x");

                for (int j = 0; j < str.length(); j++) {
                    stack.push(str.charAt(j));

                }
                stack.push('n');
                
//                System.out.println(str+" "+i);

            } else if (c == 'f') {
                i++;
                c = postfixEquation.charAt(i);
                String temp = "";
                while (c != 'f') {
                    temp += c;
                    i++;
                    c = postfixEquation.charAt(i);
                }

//                i++;
                stack.pop();
                String str = "";
                while (stack.peek() != 'n') {
                    str += stack.pop();
                }
                StringBuffer buffer = new StringBuffer(str);
                String rev = buffer.reverse().toString();

                stack.pop();
                Double val1 = Double.parseDouble(rev);

                StringBuffer buffer1 = new StringBuffer(temp);
                String rev1 = buffer1.reverse().toString();

                if (rev1.compareTo("Sine") == 0 || rev1.compareTo("Sin") == 0 || rev1.compareTo("sin") == 0) {
                    Double val3 = Math.sin(val1);

                    String temp1 = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp1.length(); j++) {
                        stack.push(temp1.charAt(j));
                    }
                    stack.push('n');

                }

                if (rev1.compareTo("Cos") == 0 || rev1.compareTo("cos") == 0) {
                    Double val3 = Math.cos(val1);

                    String temp1 = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp1.length(); j++) {
                        stack.push(temp1.charAt(j));
                    }
                    stack.push('n');

                }

                if (rev1.compareTo("Tan") == 0 || rev1.compareTo("tan") == 0) {

                    Double d = Math.tan(val1);

                    Double xtmp = val1 * 2 / Math.PI;

//                    long xint = Math.round(xtmp);

//                    System.out.println(xtmp-xint);
//                    if(xtmp-xint<=-0.001)
//                    {
//                        d=0.0;
//                    }
//                    else if(xtmp-xint>=0.001)
//                    {
//                        d=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//                    }
//                    if (Math.abs(xtmp - xint) <= 0.01) {
//
//                        throw new Exception();
//                    }

                    Double val3 = d;

                    String temp1 = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp1.length(); j++) {
                        stack.push(temp1.charAt(j));
                    }
                    stack.push('n');

                }

                if (rev1.compareTo("Log") == 0 || rev1.compareTo("log") == 0) {

                    stack.pop();
                    String str2 = "";
                    while (stack.peek() != 'n') {
                        str2 += stack.pop();
                    }
                    StringBuffer buffer2 = new StringBuffer(str2);
                    String rev2 = buffer2.reverse().toString();

                    stack.pop();
                    Double val2 = Double.parseDouble(rev2);

                    Double val3 = Math.log10(val1) / Math.log10(val2);

                    String temp1 = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp1.length(); j++) {
                        stack.push(temp1.charAt(j));
                    }
                    stack.push('n');

                }

            } else {

                stack.pop();
                String str = "";
                while (stack.peek() != 'n') {
                    str += stack.pop();
                }
                StringBuffer buffer = new StringBuffer(str);
                String rev = buffer.reverse().toString();

                stack.pop();
                Double val1 = Double.parseDouble(rev);

                str = "";

                stack.pop();

                while (stack.peek() != 'n') {
                    str += stack.pop();
                }
                stack.pop();
                StringBuffer buffer2 = new StringBuffer(str);
                String rev2 = buffer2.reverse().toString();

                Double val2 = Double.parseDouble(rev2);

                if (c == '+') {
                    Double val3 = val2 + val1;
                     

                    String temp = val3.toString();
                    

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');
//                    System.out.println(temp);

                } else if (c == '-') {
//                    System.out.println("yes");
                    Double val3 = val2 - val1;
                   

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                } else if (c == '/') {
                    

                    Double val3 = val2 / val1;
//                  System.out.println(val3);

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                } else if (c == '*') {
                    Double val3 = val2 * val1;

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                } else if (c == '^') {

                    Double val3 = Math.pow(val2, val1);

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                } else if (c == '%') {
                    Double val3 = val2 % val1;

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                }

            }

        }

        stack.pop();
        String str = "";
        while (stack.peek() != 'n') {
            str += stack.pop();
        }
        StringBuffer buffer = new StringBuffer(str);
        String rev = buffer.reverse().toString();

        stack.pop();
        Double val1 = Double.parseDouble(rev);

        return val1;

    }

    @Override
    public void start(Stage stage) throws Exception {

        pane = new AnchorPane();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        setInfix("x+21*x+x*x");
        infixToPostfixConverter(this.infixEquation);

        draw();

    }

    public static void main(String[] args) {

        launch(args);

    }

    static void redraw() {

        for (int i = 0; i < equations.size(); ++i) {
            Equation eq = equations.get(i);
            eq.redrawEquation();
        }

    }

    private void redrawEquation() {

        prev = prevOfPrev = null;

        double px = 0;
        double x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);

        double y = 0, py = 0, prevx = 0, prevy = 0;
        try {
            y = evaluate(x);
            py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment + Grid.dY;

            prevx = px;
            prevy = py;

        } catch (Exception ex) {

            py = -10;
            prevx = px;
            prevy = py;

        }

        int i = 0;
        lines.get(i).setStartX(prevx);
        lines.get(i).setStartY(prevy);

        for (px = 1; px <= Toolkit.getDefaultToolkit().getScreenSize().getWidth(); px += 2) {
            x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);
            
//            if(prev!=null && !prev.isVisible())
//            {
//                System.out.println("there is the problem");
//            }
//            
//            if(prev!=null && prevOfPrev!=null){
//            System.out.println("linex " + lines.get(i).getStartX());
//            System.out.println("prex " + prev.getStartX());
//            System.out.println("pre of pre x " + prevOfPrev.getStartX());
//            }
            try {
                y = evaluate(x);
                py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment + Grid.dY;

                lines.get(i).setEndX(px);
                lines.get(i).setEndY(py);

            }
            catch(RuntimeException ex){
//                System.out.println(ex.getClass().getName()+"as");
                
            }
            catch (Exception ex) {
//                System.out.println(ex.getClass().getName());
                py = -10;
                lines.get(i).setEndX(px);
                lines.get(i).setEndY(py);
            }
            
            prevx = px;
            prevy = py;

            if (lines.get(i).getStartY() == -10 || lines.get(i).getEndY() == -10) {
                lines.get(i).setVisible(false);
            } else {
                lines.get(i).setVisible(true);
            }

            if (
                    lines.get(i).isVisible() &&
                    prevOfPrev != null) {

                try {

                    double tangentOfLine = -(lines.get(i).getEndY() - lines.get(i).getStartY()) / ((lines.get(i).getEndX() - lines.get(i).getStartX()));
                    double tangentOfPrevLine = -(prev.getEndY() - prev.getStartY()) / ((prev.getEndX() - prev.getStartX()));
                    double tangentOfPrevOfPrevLine = -(prevOfPrev.getEndY() - prevOfPrev.getStartY()) / ((prevOfPrev.getEndX() - prevOfPrev.getStartX()));
//                    System.out.println(tangentOfLine);
                    if (tangentOfLine < 0) {
//                        System.out.println("line " + tangentOfLine);
//                        System.out.println("pre " + tangentOfPrevLine);
//                        System.out.println("pre of pre  " + tangentOfPrevLine);

                        if (tangentOfPrevLine > 0 && tangentOfPrevOfPrevLine > 0 && tangentOfPrevLine/tangentOfPrevOfPrevLine > 1) {
//                            System.out.println(tangentOfPrevLine);
//                            System.out.println("x= " + x);
                            prev.setEndY(0);
//                            lines.get(i).setEndY(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                            lines.get(i).setStartY(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
                            lines.get(i).setVisible(false);
//                            py = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//                            prevx = px;
//                            prevy = py;
                        }

                    } else if (tangentOfLine > 0) {
//                        System.out.println("line " + tangentOfLine);
//                        System.out.println("pre " + tangentOfPrevLine);
//                        System.out.println("pre of pre  " + tangentOfPrevOfPrevLine);

                        if (tangentOfPrevLine < 0 && tangentOfPrevOfPrevLine < 0 && tangentOfPrevLine/tangentOfPrevOfPrevLine >1) {
                            prev.setEndY(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
//                            System.out.println("yy");
//                            lines.get(i).setEndY(0);
                            lines.get(i).setStartY(0);
                            lines.get(i).setVisible(false);
//                             py = 0;
//                            prevx = px;
//                            prevy = py;
                        }

                    }

                } catch (Exception e) {
//                    System.out.println(e.getClass().getName());
//                    System.out.println(x);
                }

            }

            if (lines.get(i).isVisible()) {
                if (prev == null) {
                    prev = lines.get(i);
                } else {
                    prevOfPrev = prev;
                    prev = lines.get(i);
                }
            }

//            if(Math.abs(lines.get(i).getEndY()-lines.get(i).getStartY())>=100)
//            {
//                lines.get(i).setVisible(false);
//            }
            i++;
            

            if (i < lines.size()) {
                lines.get(i).setStartX(prevx);
                lines.get(i).setStartY(prevy);
            }

        }
    }

}
