package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;

public class DashboardFormController {
    public Label lblUser;
    public Label lblTime;

    public void initialize() {
        loadUser();
        loadDateAndTime();
    }

    private void loadDateAndTime() {
        // time
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,e->{
                    LocalTime localTime = LocalTime.now();
                    lblTime.setText(
                    localTime.getHour()+":"+
                    localTime.getMinute()+":"+
                    localTime.getSecond()
                    );
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadUser() {
        User user = Database.user;
        lblUser.setText(
                user.getFullName()
        );
    }
}
