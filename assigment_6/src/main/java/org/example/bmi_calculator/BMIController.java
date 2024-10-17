package org.example.bmi_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BMIController {
    @FXML
    private TextField weightField;
    @FXML
    private TextField heightField;
    @FXML
    private RadioButton kg_button;
    @FXML
    private RadioButton lbs_button;
    @FXML
    private RadioButton cm_button;
    @FXML
    private RadioButton inch_button;
    @FXML
    private Label bmiResultLabel;
    @FXML
    private Label statusResultLabel;

    @FXML
    public void initialize() {
        // Группируем радио-кнопки
        ToggleGroup weightGroup = new ToggleGroup();
        kg_button.setToggleGroup(weightGroup);
        lbs_button.setToggleGroup(weightGroup);

        ToggleGroup heightGroup = new ToggleGroup();
        cm_button.setToggleGroup(heightGroup);
        inch_button.setToggleGroup(heightGroup);

        kg_button.setSelected(true);
        cm_button.setSelected(true);
    }

    @FXML
    protected void onCalculateButtonClick() {
        try {
            if ((kg_button.isSelected() && !cm_button.isSelected()) ||
                    (lbs_button.isSelected() && !inch_button.isSelected())) {
                bmiResultLabel.setText("Ошибка");
                statusResultLabel.setText("Неправильная комбинация единиц");
                return;
            }

            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            boolean isMetric = kg_button.isSelected() && cm_button.isSelected();

            double bmi = calculateBMI(weight, height, isMetric);
            String status = determineBMIStatus(bmi);

            bmiResultLabel.setText(String.format("%.2f", bmi));
            statusResultLabel.setText(status);
        } catch (NumberFormatException e) {
            bmiResultLabel.setText("Ошибка");
            statusResultLabel.setText("Неправильный ввод");
        }
    }

    private double calculateBMI(double weight, double height, boolean isMetric) {
        double bmi;
        if (isMetric) {
            height = height / 100;
            bmi = weight / (height * height);
        } else {
            bmi = (weight / (height * height)) * 703;
        }

        bmiResultLabel.setText(String.valueOf(bmi));

        return bmi;
    }

    private String determineBMIStatus(double bmi) {
        String result;
        if (bmi < 18.5) {
            result = "Underweight";
        } else if (bmi < 24.9) {
            result = "Normal";
        } else if (bmi < 29.9) {
            result = "Overweight";
        } else {
            result = "Obese";
        }
        statusResultLabel.setText(result);
        return result;
    }

    @FXML
    protected void onClearMenuClick() {
        weightField.clear();
        heightField.clear();
        bmiResultLabel.setText("");
        statusResultLabel.setText("");
    }

    @FXML
    protected void onExitMenuClick() {
        System.exit(0);
    }

    @FXML
    protected void onHelpMenuClick() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Help");
        helpAlert.setHeaderText("How to Use BMI Calculator");
        helpAlert.setContentText("This program allows you to calculate your Body Mass Index (BMI).\n\n" +
                "1. Enter your weight and height.\n" +
                "2. Choose the appropriate measurement system (kg/cm or lbs/inch).\n" +
                "3. Click 'Calculate' to see your BMI result and category.\n" +
                "4. Use the 'Clear' option to reset inputs or 'Exit' to close the application.");
        helpAlert.showAndWait();
    }
}
