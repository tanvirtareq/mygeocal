/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygeocal;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Tanvir Tareq
 */
public class Equation extends Application {

    AnchorPane pane;

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
        draw();

    }

    private void infixToPostfixConverter(String infixEquation) {
        /**
         * sin(x+2*4)+cos(x) is saved as Sx+2*4)+Cx)
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

            } else if (c == 'x') {
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

        for (int i = 0; i < infixEquation.length(); i++) {

            if (infixEquation.charAt(i) >= '0' && infixEquation.charAt(i) <= '9' || infixEquation.charAt(i) == '.') {

                if (isstart == 0) {

                    this.infixEquation += 'n';
                    this.infixEquation += infixEquation.charAt(i);
                    isstart = 1;
                } else {
                    this.infixEquation += infixEquation.charAt(i);
                }

            } else {
                if (isstart == 1) {
                    isstart = 0;
                    this.infixEquation += 'n';
                    this.infixEquation += infixEquation.charAt(i);

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
        if (c == '^') {
            return 3;
        }

        return -1;

    }

    private void draw() {

        double px = 0;
        double x = (((px-Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);

        double y = evaluate(x);

        double py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment+Grid.dY;

        double prevx = px;
        double prevy = py;

        for (px = 1; px <= Toolkit.getDefaultToolkit().getScreenSize().getWidth(); px++) {
            x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);
            y = evaluate(x);
            py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment+Grid.dY;
            Line line = new Line(prevx, prevy, px, py);
            prevx = px;
            prevy = py;
            lines.add(line);

            FXMLDocumentController.pane.getChildren().add(line);

//            pane.getChildren().add(line);
        }

    }

    private double evaluate(double x) {

        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < postfixEquation.length(); ++i) {
            char c = postfixEquation.charAt(i);

            if (Character.isDigit(c) || c == 'n') {
                stack.push(c);
            } else if (c == 'x') {
                Double d = x;
                String str = Double.toString(d);

                stack.push('n');
                for (int j = 0; j < str.length(); j++) {
                    stack.push(str.charAt(j));
                }
                stack.push('n');
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

                } else if (c == '-') {
                    Double val3 = val2 - val1;

                    String temp = val3.toString();

                    stack.push('n');
                    for (int j = 0; j < temp.length(); j++) {
                        stack.push(temp.charAt(j));
                    }
                    stack.push('n');

                } else if (c == '/') {
                    Double val3 = val2 / val1;

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

//        Equation eq = new Equation("2*x");
//        System.out.println(eq.infixEquation);
//        System.out.println(eq.postfixEquation);
    }

    static void redraw() {
        
        
        for(int i=0;i<equations.size();++i)
        {
            Equation eq=equations.get(i);
            eq.redrawEquation();
        }
       
    }

    private void redrawEquation() {
        
        double px = 0+Grid.dX;
        double x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);

        double y = evaluate(x);

        double py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment+Grid.dY;

        double prevx = px;
        double prevy = py;
        
        int i=0;
        lines.get(i).setStartX(prevx);
        lines.get(i).setStartY(prevy);

        for (px = 1; px <= Toolkit.getDefaultToolkit().getScreenSize().getWidth(); px+=1) {
            x = (((px - Grid.positionOfYAxis) / Grid.increment) * Grid.unitOfScale);
            y = evaluate(x);
            py = Grid.positionOfXAxis - y / Grid.unitOfScale * Grid.increment+Grid.dY;
            
            lines.get(i).setEndX(px);
            lines.get(i).setEndY(py);
            i++;
            prevx = px;
            prevy = py;
            
            if(i<lines.size()){
            lines.get(i).setStartX(prevx);
        lines.get(i).setStartY(prevy);
            }
        }
    }

}
