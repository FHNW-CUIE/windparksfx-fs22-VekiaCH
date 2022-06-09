package cuie.project.template_simplecontrol.demo;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class PresentationModel {
    private final DoubleProperty powerValue2015 = new SimpleDoubleProperty(4278);
    private final DoubleProperty powerValue2016 = new SimpleDoubleProperty(4372);
    private final DoubleProperty powerValue2017 = new SimpleDoubleProperty(4137);
    private final DoubleProperty powerValue2018 = new SimpleDoubleProperty(4920);
    private final DoubleProperty powerValueTotal = new SimpleDoubleProperty(17707);
    private final StringProperty city = new SimpleStringProperty("Taggenberg 1 und 2");
    private final StringProperty canton = new SimpleStringProperty("ZH");
    private final IntegerProperty numberOfTurbines = new SimpleIntegerProperty(1);
    private final DoubleProperty turbinePower = new SimpleDoubleProperty(3000);

    public double getPowerValue2015() {
        return powerValue2015.get();
    }

    public DoubleProperty powerValue2015Property() {
        return powerValue2015;
    }

    public void setPowerValue2015(double powerValue2015) {
        this.powerValue2015.set(powerValue2015);
    }

    public double getPowerValue2016() {
        return powerValue2016.get();
    }

    public DoubleProperty powerValue2016Property() {
        return powerValue2016;
    }

    public void setPowerValue2016(double powerValue2016) {
        this.powerValue2016.set(powerValue2016);
    }

    public double getPowerValue2017() {
        return powerValue2017.get();
    }

    public DoubleProperty powerValue2017Property() {
        return powerValue2017;
    }

    public void setPowerValue2017(double powerValue2017) {
        this.powerValue2017.set(powerValue2017);
    }

    public double getPowerValue2018() {
        return powerValue2018.get();
    }

    public DoubleProperty powerValue2018Property() {
        return powerValue2018;
    }

    public void setPowerValue2018(double powerValue2018) {
        this.powerValue2018.set(powerValue2018);
    }

    public double getPowerValueTotal() {
        return powerValueTotal.get();
    }

    public DoubleProperty powerValueTotalProperty() {
        return powerValueTotal;
    }

    public void setPowerValueTotal(double powerValueTotal) {
        this.powerValueTotal.set(powerValueTotal);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() { return city;
    }

    public String getCanton() {
        return canton.get();
    }

    public StringProperty cantonProperty() { return canton;
    }

    public int getNumberOfTurbines() {
        return numberOfTurbines.get();
    }

    public IntegerProperty numberOfTurbinesProperty() {
        return numberOfTurbines;
    }

    public double getTurbinePower() {
        return turbinePower.get();
    }

    public DoubleProperty turbinePowerProperty() {
        return turbinePower;
    }
}

