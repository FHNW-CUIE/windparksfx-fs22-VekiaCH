package cuie.VekiaCH.turbinecontrol;

import java.util.Objects;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The Turbine Control is a dashboard that substitutes the header of the OOP2 Form Windparks. It calculates the average
 * wind speed over the past 4 years given the 4 yearly MWhs. It also displays a Turbine turning with the average RPM
 * at the given wind speed. Clicking on the Turbine stops the animation and clicking again resumes it. Clicking the name
 * of the city allows it to be edited (max 20 characters). Pressing escape undoes the changes and pressing enter saves
 * them. The same for the canton. The number of Turbines can also be set, (max 99 turbines). The rest of the values are
 * not editable as they are all dependent on the 2015-2018 MWh and are not found on the dashboard. Changing these would
 * cause the data to no longer be linked/correct.
 *
 * @author
 * Kyle Egli
 */

public class TurbineControl extends Region {

    private static final double ARTBOARD_WIDTH  = 250;
    private static final double ARTBOARD_HEIGHT = 240;

    // Turbine image and animation
    private ImageView turbine = new ImageView();
    private ImageView turbinePost = new ImageView();
    private RotateTransition rotateTurbine = new RotateTransition();
    private BooleanProperty transitionOn = new SimpleBooleanProperty(true);

    //Turbine values
    private final DoubleProperty powerMWh2015 = new SimpleDoubleProperty();
    private final DoubleProperty powerMWh2016 = new SimpleDoubleProperty();
    private final DoubleProperty powerMWh2017 = new SimpleDoubleProperty();
    private final DoubleProperty powerMWh2018 = new SimpleDoubleProperty();
    private final DoubleProperty powerMWhTotal = new SimpleDoubleProperty();
    private final DoubleProperty powerkW = new SimpleDoubleProperty();
    private final DoubleProperty windSpeed = new SimpleDoubleProperty();
    private final DoubleProperty rpm = new SimpleDoubleProperty();
    private final DoubleProperty airDensity = new SimpleDoubleProperty(1.225);
    private final DoubleProperty bladeLength = new SimpleDoubleProperty(45);
    private final DoubleProperty efficiency = new SimpleDoubleProperty(0.3);
    private final DoubleProperty tipSpeedRatio = new SimpleDoubleProperty(6);
    private final IntegerProperty numberOfTurbines = new SimpleIntegerProperty();
    private final DoubleProperty turbinePower = new SimpleDoubleProperty();

    //text
    TextField cityTextField = new TextField();
    Text cityText = new Text();
    TextField cantonTextField = new TextField();
    Text cantonText = new Text();
    Text windSpeedText = new Text();
    Text rpmText = new Text();
    Text powerMHhTotalText = new Text();
    TextField numberOfTurbinesField = new TextField();
    Text numberOfTurbinesText = new Text();
    Text numberOfTurbinesTextLabel = new Text(" Turbine(s)");
    Text powerText = new Text();

    private String cityPersistent = "";
    private String cantonPersistent = "";
    private int numberOfTurbinesPersistent = 1;

    //turbine Pane
    private Pane drawingPane;

    public TurbineControl() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBindings();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Lato/Lato-Lig.ttf", "/fonts/Lato/Lato-Reg.ttf");
        addStylesheetFiles("style.css");

        getStyleClass().add("turbine-control");
    }

    private void initializeParts() {
        Image turbineImage = loadImage("/windmill_250x250.png");
        turbine.setImage(turbineImage);
        turbine.setCursor(Cursor.HAND);

        Image turbinePostImage = loadImage("/windmill_post_250x250.png");
        turbinePost.setImage(turbinePostImage);

        cityText.getStyleClass().add("display-large-editable");
        cantonText.getStyleClass().add("display-editable");
        windSpeedText.getStyleClass().add("display");
        rpmText.getStyleClass().add("display");
        powerMHhTotalText.getStyleClass().add("display");
        numberOfTurbinesText.getStyleClass().add("display-editable");
        numberOfTurbinesTextLabel.getStyleClass().add("display");
        powerText.getStyleClass().add("display");

        cityTextField.getStyleClass().add("display-large-field");
        cityTextField.setAlignment(Pos.CENTER_RIGHT);
        cityTextField.setVisible(false);
        cityTextField.setMaxWidth(320);

        cantonTextField.getStyleClass().add("display-field");
        cantonTextField.setAlignment(Pos.CENTER_RIGHT);
        cantonTextField.setVisible(false);
        cantonTextField.setMaxWidth(50);

        numberOfTurbinesField.getStyleClass().add("display-field");
        numberOfTurbinesField.setAlignment(Pos.CENTER_RIGHT);
        numberOfTurbinesField.setVisible(false);
        numberOfTurbinesField.setMaxWidth(50);
    }



    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH,  ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void initializeAnimations(){
        rotateTurbine.setNode(turbine);
        rotateTurbine.setDuration(Duration.seconds(6));
        rotateTurbine.setByAngle(360);
        rotateTurbine.setCycleCount(Transition.INDEFINITE);
        rotateTurbine.setInterpolator(Interpolator.LINEAR);
        rotateTurbine.play();
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(turbinePost,turbine);
        StackPane stackPane1 = new StackPane(cityText, cityTextField);
        stackPane1.setAlignment(Pos.TOP_RIGHT);

        StackPane stackPane2 = new StackPane(cantonText, cantonTextField);
        stackPane2.setAlignment(Pos.TOP_RIGHT);

        StackPane stackPane3 = new StackPane(numberOfTurbinesText, numberOfTurbinesField);
        stackPane3.setAlignment(Pos.TOP_RIGHT);
        HBox hBox1 = new HBox(stackPane3, numberOfTurbinesTextLabel);
        hBox1.setAlignment(Pos.TOP_RIGHT);

        Text spacer = new Text(" ");
        spacer.getStyleClass().add("display");

        VBox vBox1 = new VBox(stackPane1, stackPane2, spacer, rpmText,
                hBox1, powerText, powerMHhTotalText, windSpeedText);
        vBox1.setAlignment(Pos.CENTER_RIGHT);
        vBox1.setMinWidth(265);
        HBox hBox2 = new HBox(drawingPane, vBox1);
        setMinWidth(450);
        setMaxHeight(250);
        getChildren().addAll(hBox2);
    }

    private void setupEventHandlers() {
        turbine.setOnMouseClicked(e -> {
            if(transitionOn.getValue()) {
                rotateTurbine.stop();
                transitionOn.set(false);
                powerMWhTotal.set(0.0);
            } else {
                rotateTurbine.play();
                transitionOn.set(true);
                powerMWhTotal.set(getPowerMWh2015() + getPowerMWh2016() + getPowerMWh2017() + getPowerMWh2018());
            }
        });

        cityText.setOnMouseClicked(e -> {
            cityText.setVisible(!cityText.isVisible());
            cityTextField.setVisible(!cityTextField.isVisible());
            cityPersistent = cityText.getText();
        });

        cityTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ESCAPE)){
                cityTextField.textProperty().set(cityPersistent);
                cityText.setVisible(!cityText.isVisible());
                cityTextField.setVisible(!cityTextField.isVisible());
            }
            if(event.getCode().equals(KeyCode.ENTER)){
                cityText.setVisible(!cityText.isVisible());
                cityTextField.setVisible(!cityTextField.isVisible());
            }
        });

        cantonText.setOnMouseClicked(e -> {
            cantonText.setVisible(!cantonText.isVisible());
            cantonTextField.setVisible(!cantonTextField.isVisible());
            cantonPersistent = cantonText.getText();
        });

        cantonTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ESCAPE)){
                cantonTextField.textProperty().set(cantonPersistent);
                cantonText.setVisible(!cantonText.isVisible());
                cantonTextField.setVisible(!cantonTextField.isVisible());
            }
            if (event.getCode().equals(KeyCode.ENTER)){
                cantonText.setVisible(!cantonText.isVisible());
                cantonTextField.setVisible(!cantonTextField.isVisible());
            }
        });

        numberOfTurbinesText.setOnMouseClicked(e -> {
            numberOfTurbinesText.setVisible(!numberOfTurbinesText.isVisible());
            numberOfTurbinesField.setVisible(!numberOfTurbinesField.isVisible());
            numberOfTurbinesPersistent = numberOfTurbines.get();
        });

        numberOfTurbinesField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode().equals(KeyCode.ESCAPE)){
                numberOfTurbinesField.textProperty().set(String.valueOf(numberOfTurbinesPersistent));
                numberOfTurbinesText.setVisible(!numberOfTurbinesText.isVisible());
                numberOfTurbinesField.setVisible(!numberOfTurbinesField.isVisible());
            }
            if(event.getCode().equals(KeyCode.ENTER)){
                numberOfTurbinesText.setVisible(!numberOfTurbinesText.isVisible());
                numberOfTurbinesField.setVisible(!numberOfTurbinesField.isVisible());
                numberOfTurbines.set(Integer.parseInt(numberOfTurbinesField.getText()));
            }
            if(event.getCode().equals(KeyCode.UP)){
                numberOfTurbinesField.setText(String.valueOf(Integer.parseInt(numberOfTurbinesField.getText())+1));
            }
            if(event.getCode().equals(KeyCode.DOWN)){
                numberOfTurbinesField.setText(String.valueOf(Integer.parseInt(numberOfTurbinesField.getText())-1));
            }
        });
    }

    private void setupValueChangeListeners() {
        powerkW.addListener((observable, oldValue, newValue) -> {
            updateValues();
            updateUI();
        });

        powerMWh2015.addListener((observable, oldValue, newValue) ->
                powerMWhTotal.set(getPowerMWh2015() + getPowerMWh2016() + getPowerMWh2017() + getPowerMWh2018()));

        powerMWh2016.addListener((observable, oldValue, newValue) ->
                powerMWhTotal.set(getPowerMWh2015() + getPowerMWh2016() + getPowerMWh2017() + getPowerMWh2018()));

        powerMWh2017.addListener((observable, oldValue, newValue) ->
                powerMWhTotal.set(getPowerMWh2015() + getPowerMWh2016() + getPowerMWh2017() + getPowerMWh2018()));

        powerMWh2018.addListener((observable, oldValue, newValue) ->
                powerMWhTotal.set(getPowerMWh2015() + getPowerMWh2016() + getPowerMWh2017() + getPowerMWh2018()));

        numberOfTurbines.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() == 0){
                numberOfTurbines.set(oldValue.intValue());
            }
            powerkW.setValue(getPowerMWhTotal()*1000/(35040 * getNumberOfTurbines()));
        });

        powerMWhTotal.addListener((observable, oldValue, newValue) ->
            powerkW.setValue(getPowerMWhTotal()*1000/(35040 * getNumberOfTurbines())));

        cityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 20){
                cityTextField.setText(oldValue);
            }
        });

        cantonTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() <= 2){
                if (!newValue.matches("\\sa-zA-Z*")) {
                    cantonTextField.setText(newValue.replaceAll("[^\\sa-zA-Z]", "").toUpperCase());
                }
            }

            else {
                cantonTextField.setText(oldValue);
            }
        });

        numberOfTurbinesField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() <= 2){
                if(newValue.equals("0")){
                    numberOfTurbinesField.setText(oldValue);
                }
                else if(!newValue.matches("\\s0-9")) {
                    numberOfTurbinesField.setText(newValue.replaceAll("[^\\s0-9]", ""));
                }
            }

            else {
                numberOfTurbinesField.setText(oldValue);
            }
        });

        numberOfTurbinesText.textProperty().addListener((observable, oldValue, newValue) ->
                numberOfTurbinesField.textProperty().set(newValue));
    }

    private void setupBindings() {
        windSpeedText.textProperty().bind(new SimpleStringProperty("Wind Speed:  ")
                .concat(windSpeed.asString("%.1f").concat(" m/s")));
        rpmText.textProperty().bind(rpm.asString("%.1f").concat(" RPM"));
        powerMHhTotalText.textProperty().bind(powerMWhTotal.asString("%.1f").concat(" MWh"));
        numberOfTurbinesText.textProperty().bind(numberOfTurbinesProperty().asString());
        powerText.textProperty().bind(turbinePower.asString("%.1f").concat(" kW"));
        cityTextField.textProperty().bindBidirectional(cityTextProperty());
        cantonTextField.textProperty().bindBidirectional(cantonTextProperty());
    }

    private void updateValues() {
        windSpeed.set(calculateWindSpeed());
        rpm.set(calculateRPM());
    }

    private double calculateWindSpeed() {
        double windSpeedCubed = powerkW.getValue() * 1000.0 /
                (0.5 * efficiency.getValue() * airDensity.getValue() * Math.PI * Math.pow(bladeLength.getValue(), 2));
        return Math.cbrt(windSpeedCubed);
    }

    private double calculateRPM() {
        return ((60.0 * windSpeed.getValue() * tipSpeedRatio.getValue()) / (Math.PI * bladeLength.getValue() * 2.0));
    }

    private void updateUI(){
        rotateTurbine.stop();
        if(powerMWhTotal.getValue().equals(0.0)){
            rotateTurbine.stop();
            transitionOn.set(false);
        } else {
            rotateTurbine.setDuration(Duration.seconds(1.0/(rpm.getValue() / 60.0)));
            rotateTurbine.play();
            transitionOn.set(true);
        }
    }

    private Image loadImage(String s) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(s)));
    }

    // Sammlung nuetzlicher Funktionen

    private void loadFonts(String... font){
        for(String f : font){
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    private void addStylesheetFiles(String... stylesheetFile){
        for(String file : stylesheetFile){
            String stylesheet = Objects.requireNonNull(getClass().getResource(file)).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    public DoubleProperty powerMWh2015Property() {
        return powerMWh2015;
    }

    public double getPowerMWh2015() {
        return powerMWh2015.get();
    }

    public DoubleProperty powerMWh2016Property() {
        return powerMWh2016;
    }

    public double getPowerMWh2016() {
        return powerMWh2016.get();
    }

    public DoubleProperty powerMWh2017Property() {
        return powerMWh2017;
    }

    public double getPowerMWh2017() {
        return powerMWh2017.get();
    }

    public DoubleProperty powerMWh2018Property() {
        return powerMWh2018;
    }

    public double getPowerMWh2018() {
        return powerMWh2018.get();
    }

    public DoubleProperty powerMWhTotalProperty() {
        return powerMWhTotal;
    }

    public double getPowerMWhTotal() {
        return powerMWhTotal.get();
    }

    public int getNumberOfTurbines() {
        return numberOfTurbines.get();
    }

    public IntegerProperty numberOfTurbinesProperty() {
        return numberOfTurbines;
    }

    public String getCityText() {
        return cityText.getText();
    }

    public StringProperty cityTextProperty(){
        return cityText.textProperty();
    }

    public Text getCantonText() {
        return cantonText;
    }

    public StringProperty cantonTextProperty(){
        return cantonText.textProperty();
    }

    public double getPowerkW() {
        return powerkW.get();
    }

    public DoubleProperty powerkWProperty() {
        return powerkW;
    }

    public double getTurbinePower() {
        return turbinePower.get();
    }

    public DoubleProperty turbinePowerProperty() {
        return turbinePower;
    }
}