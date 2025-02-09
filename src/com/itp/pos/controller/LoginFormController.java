package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import com.itp.pos.util.PasswordEncoder;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void createAnAccountOnAction(ActionEvent actionEvent) {
       try {
           setUi("RegisterForm");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void setUi(String location) throws IOException {
        Database.log("User Access "+location +" page.");
        Stage stage = (Stage)
                context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/itp/pos/view/"+location+".fxml"));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {

            stage.setScene(new Scene(root));


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });


        fadeOut.play();

    }

    public void signInOnAction(ActionEvent actionEvent) {
        Database.log("User attempt to log in");
        String email = txtEmail.getText();
        /*Optional<Boolean> first = Database.userTable.stream()
                .map(e -> e.getEmail()
                        .equals(email)).findFirst();*/
        User selectedUser = findUser(email);
        if(selectedUser == null){
            Database.log("User Logged in attempt failed.");
            new Alert(Alert.AlertType.WARNING,
                    "user email not found..",
                    ButtonType.CLOSE).show();
            return;
        }
        if(PasswordEncoder.check(
                txtPassword.getText(),
                selectedUser.getPassword()
        )){

            // load user interface
            Database.user=selectedUser;
            Database.log("User Logged in");
            try {
                setUi("DashboardForm");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else{
            Database.log("User Logged in failed");
            new Alert(Alert.AlertType.WARNING,
                    "Wrong password..",
                    ButtonType.CLOSE).show();
        }
    }

    private User findUser(String email) {
        for(User u : Database.userTable){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }
}
