package com.example.iosu_prj

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class MainWindow : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("window_main.fxml"))
        val scene = Scene(fxmlLoader.load(), 800.0, 500.0)
        stage.title = "JavaFX Application"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}

//fun main() {
//    Application.launch(MainWindow::class.java)
//}