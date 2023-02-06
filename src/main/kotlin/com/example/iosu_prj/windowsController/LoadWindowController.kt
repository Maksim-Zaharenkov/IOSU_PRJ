package com.example.iosu_prj.windowsController

import com.example.iosu_prj.view.loadFile
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.File
import java.net.URL
import java.util.*
import javafx.scene.control.Label


class LoadWindowController() {

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    private var controllerM: MainWindowController? = null

    @FXML
    private lateinit var choiceDoc: ChoiceBox<String>
    private var documents = listOf("Приказ(docx)", "Рецензенты(docx)", "Список ИТиУвТС(xlsx)", "Список ИНО(xlsx)")

    @FXML
    private lateinit var chooseBtn: Button
    private var fileChooser = FileChooser()

    @FXML
    private lateinit var txtCh: TextField

    @FXML
    private lateinit var exceptionText: Label

    @FXML
    private lateinit var cancelBtn: Button

    @FXML
    private lateinit var okBtn: Button

    @FXML
    fun clickCh(event: ActionEvent) {
        val file = fileChooser.showOpenDialog(null)
        if (file != null) {
            txtCh.text = ""
            txtCh.appendText(file.absolutePath)
        }
    }

    @FXML
    fun cancel(event: ActionEvent) {
        val source: Node = event.getSource() as Node
        val stage: Stage = source.getScene().getWindow() as Stage
        stage.close()
    }

    @FXML
    fun ok(event: ActionEvent) {
        val source: Node = event.getSource() as Node
        val stage: Stage = source.getScene().getWindow() as Stage
        var exception = false

        try {
            loadFile(choiceDoc.value.toString(), File(txtCh.text))
        }
        catch (_: Exception) {
            exception = true
            exceptionText.isVisible = true
        }

        if (!exception) {
            stage.close()
        }
    }

    @FXML
    fun initialize() {
        choiceDoc.items.addAll(documents)
        assert(cancelBtn != null) {"fx:id=\"cancelBtn\" was not injected: check your FXML file 'LoadWindow.fxml'." }
        assert(choiceDoc != null) {"fx:id=\"choiceDoc\" was not injected: check your FXML file 'LoadWindow.fxml'." }
        assert(chooseBtn != null) {"fx:id=\"chooseBtn\" was not injected: check your FXML file 'LoadWindow.fxml'." }
        assert(okBtn != null) {"fx:id=\"okBtn\" was not injected: check your FXML file 'LoadWindow.fxml'." }
        assert(txtCh != null) {"fx:id=\"txtCh\" was not injected: check your FXML file 'LoadWindow.fxml'." }
        assert(exceptionText != null) {"fx:id=\"exceptionText\" was not injected: check your FXML file 'LoadWindow.fxml'." }

    }

}