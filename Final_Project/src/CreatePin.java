import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreatePin {
    private Stage stage;
    private int board_id;
    private String source;
    private Main main;

    CreatePin (Main main) {
        this.main=main;
    }

    public void create() {
        stage = new Stage();
        stage.setTitle("CreatePin");

        final FileChooser fileChooser = new FileChooser();

        final Button addNew = new Button("Upload a Picture");
        final Button useOne = new Button("Use another pin");

        addNew.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            openFile(file);
                        }
                    }
                });

        useOne.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                    }
                });


        final GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(addNew, 0, 0);
        GridPane.setConstraints(useOne, 1, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(addNew, useOne);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    private void openFile(File file) {
        try {
            //source=file.toURI().toString();
            source=file.getAbsolutePath();
            System.out.println(source);
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("createPin.fxml"));
            Parent root = loader.load();

            stage=main.stage;

            createPinController controller = loader.getController();
            controller.initialize(main,this);

            stage.setTitle("Create Pin");
            stage.setScene(new Scene(root, 835.0, 580.0));
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }
}