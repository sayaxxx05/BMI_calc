module org.example.bmi_calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bmi_calculator to javafx.fxml;
    exports org.example.bmi_calculator;
}