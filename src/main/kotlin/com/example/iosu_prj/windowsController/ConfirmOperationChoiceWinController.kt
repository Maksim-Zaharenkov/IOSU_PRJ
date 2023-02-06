package com.example.iosu_prj.windowsController

import com.example.iosu_prj.code
import com.example.iosu_prj.confirmText
import java.net.URL
import java.util.ResourceBundle
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.text.Text
import javafx.stage.Stage

class ConfirmOperationChoiceWinController {
    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var cancelBtn: Button

    @FXML
    private lateinit var okBtn: Button

    @FXML
    private lateinit var textAttention: Text

    @FXML
    fun cancel(event: ActionEvent) {
        val source: Node = event.getSource() as Node
        val stage: Stage = source.getScene().getWindow() as Stage

        code = 0

        stage.close()
    }

    @FXML
    fun ok(event: ActionEvent) {
        val source: Node = event.getSource() as Node
        val stage: Stage = source.getScene().getWindow() as Stage

        code = 1

        stage.close()
    }

    @FXML
    fun initialize() {
        assert(cancelBtn != null) {"fx:id=\"cancelBtn\" was not injected: check your FXML file 'confirm_operation_choice_window.fxml'." }
        assert(okBtn != null) {"fx:id=\"okBtn\" was not injected: check your FXML file 'confirm_operation_choice_window.fxml'." }
        assert(textAttention != null) {"fx:id=\"textAttention\" was not injected: check your FXML file 'confirm_operation_choice_window.fxml'." }
        textAttention.text = confirmText
    }
}