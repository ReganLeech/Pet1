package com.codedotorg;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PetSelectionScene extends PetApp {

    /** The name of the pet provided by the user */
    private String petName;

    /** The type of pet chosen by the user */
    private String petType;

    /**
     * This class represents a scene for selecting a pet. It extends the Scene class and
     * provides a constructor for initializing the pet name and type.
     */
    public PetSelectionScene(Stage window, int width, int height) {
        super(window, width, height);

        petName = "";
        petType = "";
    }

    /**
     * This method starts the application by creating a VBox layout for pet selection
     * and setting it as the scene to be displayed.
     */
    public void startApp() {
        VBox petSelectionLayout = createPetSelectionLayout();
        setAndShowScene(petSelectionLayout);
    }

    /**
     * Sets petName to the name entered by the user.
     *
     * @param name the name entered by the user
     */
    public void setPetName(String name) {
        petName = name != null ? name.trim() : "";
    }

    /**
     * Sets petType to the type of pet chosen by the user.
     *
     * @param type the selected pet type
     */
    public void setPetType(String type) {
        petType = type != null ? type : "";
    }

    /**
     * Creates the main layout for the PetSelection scene
     * 
     * @return the VBox layout for the PetSelection scene
     */
    public VBox createPetSelectionLayout() {
        VBox tempLayout = new VBox(15);
        tempLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Choose your pet and name it");
        titleLabel.setId("titleLabel");

        Label nameLabel = new Label("Pet Name:");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your pet's name");
        HBox nameLayout = new HBox(10, nameLabel, nameInput);
        nameLayout.setAlignment(Pos.CENTER);

        Label typeLabel = new Label("Pet Type:");
        ToggleGroup typeGroup = new ToggleGroup();

        RadioButton dogOption = new RadioButton("Dog");
        dogOption.setToggleGroup(typeGroup);
        dogOption.setUserData("Dog");

        RadioButton catOption = new RadioButton("Cat");
        catOption.setToggleGroup(typeGroup);
        catOption.setUserData("Cat");

        HBox typeLayout = new HBox(10, dogOption, catOption);
        typeLayout.setAlignment(Pos.CENTER);

        Button submitButton = createSubmitButton();
        submitButton.setDisable(true);

        nameInput.textProperty().addListener((observable, oldValue, newValue) -> {
            setPetName(newValue);
            submitButton.setDisable(!isFormComplete());
        });

        typeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setPetType(newValue.getUserData().toString());
            } else {
                setPetType("");
            }
            submitButton.setDisable(!isFormComplete());
        });

        tempLayout.getChildren().addAll(titleLabel, nameLayout, typeLabel, typeLayout, submitButton);
        return tempLayout;
    }

    /**
     * Returns true when both the pet name and type have been selected.
     *
     * @return true if the form is complete, false otherwise
     */
    private boolean isFormComplete() {
        return !petName.isBlank() && !petType.isBlank();
    }

    /**
     * Creates a submit button that, when clicked, creates a new MainScene object with
     * the given pet name and type, and displays it.
     *
     * @return the submit button
     */
    public Button createSubmitButton() {
        Button tempButton = new Button("Submit");

        tempButton.setOnAction(event -> {
            MainScene mainScene = new MainScene(getWindow(), getWidth(), getHeight(), petName, petType);
            mainScene.showMainScene();
        });

        return tempButton;
    }

}