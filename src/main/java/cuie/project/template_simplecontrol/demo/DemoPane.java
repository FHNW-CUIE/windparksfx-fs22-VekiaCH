package cuie.project.template_simplecontrol.demo;

import cuie.project.template_simplecontrol.TurbineControl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DemoPane extends BorderPane {

    private final PresentationModel pm;

    // declare the custom control
    private TurbineControl cc;

    // all controls
    private Slider powerMWh2015Slider;
    private Label powerMWh2015Label;
    private Slider powerMWh2016Slider;
    private Label powerMWh2016Label;
    private Slider powerMWh2017Slider;
    private Label powerMWh2017Label;
    private Slider powerMWh2018Slider;
    private Label powerMWh2018Label;
    private Label powerMWhTotalLabel;
    private Label powerMWhTotal;
    private Label numberOfTurbinesLabel;
    private Slider numberOfTurbinesSlider;
    private Label turbinePowerLabel;
    private Label turbinePower;

    public DemoPane(PresentationModel pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupBindings();
    }

    private void initializeControls() {
        setPadding(new Insets(10));

        cc = new TurbineControl();

        powerMWh2015Slider = new Slider(0, 1000000,0);
        powerMWh2015Label = new Label();
        powerMWh2016Slider = new Slider(0, 1000000,0);
        powerMWh2016Label = new Label();
        powerMWh2017Slider = new Slider(0, 1000000,0);
        powerMWh2017Label = new Label();
        powerMWh2018Slider = new Slider(0, 1000000,0);
        powerMWh2018Label = new Label();

        powerMWhTotalLabel = new Label("Total power in MWh (2015-2018): ");
        powerMWhTotal = new Label();

        numberOfTurbinesLabel = new Label();
        numberOfTurbinesSlider = new Slider(0, 15, 0);

        turbinePowerLabel = new Label();
        turbinePower = new Label();
    }

    private void layoutControls() {
        VBox controlPane = new VBox(new HBox(new Label("Power in MWh for 2015: "), powerMWh2015Label), powerMWh2015Slider,
                                    new HBox(new Label("Power in MWh for 2016: "), powerMWh2016Label), powerMWh2016Slider,
                                    new HBox(new Label("Power in MWh for 2017: "), powerMWh2017Label), powerMWh2017Slider,
                                    new HBox(new Label("Power in MWh for 2018: "), powerMWh2018Label), powerMWh2018Slider,
                                    new HBox(powerMWhTotalLabel, powerMWhTotal),
                                    new HBox(new Label("Number of Turbines: "), numberOfTurbinesLabel), numberOfTurbinesSlider,
                                    new HBox(new Label("Turbine power in kW: "), turbinePowerLabel, turbinePower));
        controlPane.setPadding(new Insets(0, 50, 0, 50));
        controlPane.setSpacing(10);
        controlPane.setMinWidth(340);

        setCenter(cc);
        setRight(controlPane);
    }

    private void setupBindings() {
        powerMWh2015Slider.valueProperty().bindBidirectional(pm.powerValue2015Property());
        powerMWh2015Label.textProperty().bind(pm.powerValue2015Property().asString("%.1f"));
        powerMWh2016Slider.valueProperty().bindBidirectional(pm.powerValue2016Property());
        powerMWh2016Label.textProperty().bind(pm.powerValue2016Property().asString("%.1f"));
        powerMWh2017Slider.valueProperty().bindBidirectional(pm.powerValue2017Property());
        powerMWh2017Label.textProperty().bind(pm.powerValue2017Property().asString("%.1f"));
        powerMWh2018Slider.valueProperty().bindBidirectional(pm.powerValue2018Property());
        powerMWh2018Label.textProperty().bind(pm.powerValue2018Property().asString("%.1f"));
        powerMWhTotal.textProperty().bind(pm.powerValueTotalProperty().asString("%.1f"));
        numberOfTurbinesLabel.textProperty().bind(pm.numberOfTurbinesProperty().asString());
        numberOfTurbinesSlider.valueProperty().bindBidirectional(pm.numberOfTurbinesProperty());
        turbinePower.textProperty().bind(pm.turbinePowerProperty().asString("%.1f"));

        cc.powerMWh2015Property().bindBidirectional(pm.powerValue2015Property());
        cc.powerMWh2016Property().bindBidirectional(pm.powerValue2016Property());
        cc.powerMWh2017Property().bindBidirectional(pm.powerValue2017Property());
        cc.powerMWh2018Property().bindBidirectional(pm.powerValue2018Property());
        cc.powerMWhTotalProperty().bindBidirectional(pm.powerValueTotalProperty());
        cc.cityTextProperty().bindBidirectional(pm.cityProperty());
        cc.cantonTextProperty().bindBidirectional(pm.cantonProperty());
        cc.numberOfTurbinesProperty().bindBidirectional(pm.numberOfTurbinesProperty());
        cc.turbinePowerProperty().bindBidirectional(pm.turbinePowerProperty());
    }

}
