package mari709.com.calculadora.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mari709.com.calculadora.operation.Operation;
import mari709.com.calculadora.R;

public class Calculator extends AppCompatActivity {

    private TextView tv_display, tv_display_operation;

    public static final String ACCUMULATOR  = "accumulator";
    public static final String DISPLAY      = "display";
    public static final String DISPLAY2     = "display2";
    public static final String OPERATION    = "operation";

    private double accumulator = 0.0;
    private Operation currentOperation = Operation.NONE;
    private String display = "0.0";
    private String display2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        tv_display              = (TextView) findViewById(R.id.tv_display);
        tv_display_operation    = (TextView) findViewById(R.id.tv_display_operation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(ACCUMULATOR, accumulator);
        outState.putString(DISPLAY, display);
        outState.putString(DISPLAY2, display2);
        outState.putString(OPERATION, currentOperation.name());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        accumulator = savedInstanceState.getDouble(ACCUMULATOR);
        currentOperation = Operation.valueOf(savedInstanceState.getString(OPERATION));
        display = savedInstanceState.getString(DISPLAY, "0.0");
        display2 = savedInstanceState.getString(DISPLAY2, "");
        updateDisplay();
    }

    private void updateDisplay() {
        tv_display.setText(display);
        tv_display_operation.setText(display2);
    }

    public void btnClicked(View v) {

        Button button = (Button) v;
        String value = button.getText().toString();

        switch (value) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (display.equals("0.0")) {
                    display = "";
                    display2 = "";
                }//END IF "NUMBERS"
                display += value;
                display2 += value;
                break;
            case ".":
                if (!display.contains(".")) {
                    display += value;
                    display2 += value;
                }//END IF "DOT"
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (!display.isEmpty()) {
                    display2 = display + value;
                    calculateBasicOperation(value);
                }//END IF "BASIC OPERATION"
                break;
            case "SQRT":
                if (!display.isEmpty()) {
                    display2 = "SQRT(" + display + ")";
                    calculateRoot();
                }//END IF "SQRT"
                break;
            case "=":
                if (!display.isEmpty()) {
                    calculateResult();
                }//EN IF "EQUALS"
                break;
            case "C":
                clearDisplay();
                break;
            case "%":
                display2 = display + value + " of " + accumulator;
                //display2 = accumulator + "+"+display + value; //ADD
                calculatePercent();
                break;
        }
        updateDisplay();
    }

    private void calculateRoot() {
        double displayValue = Double.parseDouble(display);
        display = String.valueOf(Math.sqrt(displayValue));
    }

    private void calculatePercent(){
        //display = String.valueOf(accumulator + accumulator*Double.parseDouble(display)/100);  //ADD
        display = String.valueOf(accumulator*Double.parseDouble(display)/100);
    }

    private void clearDisplay() {
        accumulator = 0.0;
        currentOperation = Operation.NONE;
        display = "0.0";
        display2 = "";
    }

    private void calculateResult() {
        double displayValue = Double.parseDouble(display);

        switch (currentOperation) {
            case ADD:
                display = String.valueOf(accumulator + displayValue);
                break;
            case SUBTRACT:
                display = String.valueOf(accumulator - displayValue);
                break;
            case MULTIPLY:
                display = String.valueOf(accumulator * displayValue);
                break;
            case DIVIDE:
                display = String.valueOf(accumulator / displayValue);
                break;
        }
        currentOperation = Operation.NONE;
        accumulator = Double.parseDouble("0.0");
    }
    private void calculateBasicOperation(String value) {
        accumulator = Double.parseDouble(display);
        currentOperation = Operation.operationKey(value);
        display = "";
    }
}