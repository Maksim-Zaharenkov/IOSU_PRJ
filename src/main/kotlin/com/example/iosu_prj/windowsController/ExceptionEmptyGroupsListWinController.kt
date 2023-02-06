package com.example.iosu_prj.windowsController

import java.net.URL
import java.util.ResourceBundle
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.stage.Stage

class ExceptionEmptyGroupsListWinController {
    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var okBtn: Button

    @FXML
    fun ok(event: ActionEvent) {
        val source: Node = event.getSource() as Node
        val stage: Stage = source.getScene().getWindow() as Stage

        stage.close()
    }

    @FXML
    fun initialize() {
        assert(okBtn != null) {"fx:id=\"okBtn\" was not injected: check your FXML file 'exception_empty_timetable_window.fxml'." }

    }
}