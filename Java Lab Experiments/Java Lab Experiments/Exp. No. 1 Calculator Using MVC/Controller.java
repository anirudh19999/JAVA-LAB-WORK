import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private Model model;
    private View view;

    private double firstNumber;
    private String operator;
    private boolean newNumber;

    public Controller(Model model, View view) {

        this.model = model;
        this.view = view;

        firstNumber = 0;
        operator = "";
        newNumber = true;

        view.addButtonListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {

            if (newNumber || view.getDisplay().equals("0")) {
                view.setDisplay(command);
                newNumber = false;
            } else {
                view.setDisplay(view.getDisplay() + command);
            }

        } else if (command.equals(".")) {

            if (newNumber) {
                view.setDisplay("0.");
                newNumber = false;
            } else if (!view.getDisplay().contains(".")) {
                view.setDisplay(view.getDisplay() + ".");
            }

        } else if (command.equals("C")) {

            firstNumber = 0;
            operator = "";
            newNumber = true;
            view.setDisplay("0");

        } else if (command.equals("+") ||
                   command.equals("-") ||
                   command.equals("*") ||
                   command.equals("/")) {

            firstNumber = Double.parseDouble(view.getDisplay());
            operator = command;
            newNumber = true;

        } else if (command.equals("=")) {

            if (operator.equals("")) {
                return;
            }

            try {

                double secondNumber =
                    Double.parseDouble(view.getDisplay());

                double result =
                    model.calculate(firstNumber, secondNumber, operator);

                view.setDisplay(formatResult(result));

                firstNumber = result;
                operator = "";
                newNumber = true;

            } catch (ArithmeticException ex) {

                view.setDisplay("Cannot divide by 0");
                newNumber = true;
            }

        } else if (command.equals("EXIT")) {

            System.exit(0);
        }
    }

    private String formatResult(double result) {

        if (result == (long) result) {
            return String.valueOf((long) result);
        }

        return String.valueOf(result);
    }
}