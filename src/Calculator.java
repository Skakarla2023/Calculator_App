// java.awt for components such as Frame,button,Label etc
import java.awt.*;

// java.awt.event is for packages and classes for handling events like mouseListener,keylistener,Action Listener etc
import java.awt.event.*;

import java.util.Arrays;

// used for creating components such as a JFrame,JButton,JLabel
import javax.swing.*;

// lineborder is used to create a simple colored border with a defined thickness
import javax.swing.border.LineBorder;


public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customBlack = new Color(28,28,28);
    Color CustomDarkGrey = new Color(80,80,80);
    Color customOrange = new Color(255,149,0);


    // now we create three arrays, one for numbers,one for operator symbols(right symbols),one for topsymbols(AC,%,+/-)
    String[] buttonValues = {
        "AC", "+/-", "%", "+",
        "7" , "8" ,"9" ,"×",
        "4" , "5" ,"6" ,"-",
        "1" , "2" ,"3" ,"+",
        "0" , "." ,"√" ,"="
    };

    String[] rightSymbols = {"+","×","-","÷","="};
    String[] topSymbols = {"AC","+/-","%"};


    JFrame frame = new JFrame("Calculator");
    
    // we place the text inside the labe, aand label is placed inside the panel and the panel is placed inside the window.
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();

    // we created the first panel to display the result, adn this is to place all the buttons and calculator part
    JPanel buttonsPanel = new JPanel();

    // variables to keep tract of the 2 numbers and the operators like A+b,A-B,A%B etc
    String A = "0";
    String operator = null;
    String B = null;

    Calculator(){
        // we set it to true to make the frame visible
        //frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);

        // this is defined to align the window to the center of the screen.
        frame.setLocationRelativeTo(null);
        // frame.setresizable is set to false so that user or the player cannot edit the window size.
        frame.setResizable(false);
        // this is esetting the frame to close on clicking the X button on the top of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this is used to arrange the components in north or south or any direction we want
        frame.setLayout(new BorderLayout());


        displayLabel.setBackground(customBlack);
        
        // used to set the text color
        displayLabel.setForeground(Color.white);

        displayLabel.setFont(new Font("Arial",Font.PLAIN,80));
        
        // to display text to the right in calculator
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);

        // to display 0 as default value of the calculator
        displayLabel.setText("0");

        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        
        // we defined the properties for displaylabel and added that to the JPanel,
        // as we dont want only this 0 to occupy the entire space we place it to the north
        displayPanel.add(displayLabel);
        frame.add(displayPanel,BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // creating buttons for each value in the array.
        for(int i=0;i<buttonValues.length;i++){
            // create each button for each value
            JButton button = new JButton();
            // assign the value to the button with the value of array elements 
            String buttonValue = buttonValues[i];
            // setting the font of the button
            button.setFont(new Font("Arial",Font.PLAIN,30));
            // setting the text to be displayes on the button as the button value
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            if(Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(CustomDarkGrey);
                button.setForeground(Color.white);
            }            

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    // event is e here, and souce of that click is a JButton here
                    JButton button= (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if(buttonValue=="="){
                            if(A!=null){
                                B = displayLabel.getText();
                                double numA=Double.parseDouble(A);
                                double numB=Double.parseDouble(B);

                                if(operator=="+"){
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                else if(operator=="-"){
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                }
                                else if(operator=="×"){
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                else if(operator=="÷"){
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();
                            }
                        }
                        else if("+-÷×".contains(buttonValue)){
                            if(operator==null){
                                A=displayLabel.getText();
                                displayLabel.setText("0");
                                B="0";
                            }
                            operator=buttonValue;
                        }                     
                        

                    }
                    else if(Arrays.asList(topSymbols).contains(buttonValue)) {
                        if(buttonValue=="AC"){
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if(buttonValue== "+/-"){
                            double numDisplay =  Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if(buttonValue=="%"){
                            double numDisplay =  Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else { // digits or decimals
                        if(buttonValue == "."){
                            if(!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText()+buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)){
                            if(displayLabel.getText()=="0"){
                                displayLabel.setText(buttonValue);// instead of doing 05 we just get 5
                            }
                            else{
                                displayLabel.setText(displayLabel.getText()+buttonValue);
                            }
                        }
                        else if(buttonValue == "√"){
                         try {
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                if (numDisplay < 0) {
                                    displayLabel.setText("Invalid");
                                } else {
                                    double sqrt = Math.sqrt(numDisplay);
                                    displayLabel.setText(removeZeroDecimal(sqrt));
                                }
                            } catch (NumberFormatException ex) {
                                displayLabel.setText("Error");
                            }
                        }

                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearAll()
    {
        A="0";
        B=null;
        operator=null;
    }

    String removeZeroDecimal(double numDisplay){
        if(numDisplay%1==0){
            return Integer.toString((int)numDisplay);
        }
        else{
            return Double.toString(numDisplay);
        }
    }
}
