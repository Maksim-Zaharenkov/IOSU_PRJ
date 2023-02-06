package com.example.iosu_prj.windowsController

import com.example.iosu_prj.*
import javafx.animation.FadeTransition
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.Pane
import javafx.scene.text.Text
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Duration
import java.net.URL
import java.util.*


class MainWindowController() {

//    fun actionPerformed(e: ActionEvent) {
//        val src = e.source
//        val jfc = JFileChooser()
//        // показывать только директории
//        jfc.setFileFilter(object : FileFilter() {
//            fun accept(f: File): Boolean {
//                return f.isDirectory()
//            }
//
//            val description: String?
//                get() = ""
//        })
//        // выбирать только директории
//        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
//        if (src === parent.jb1) {
//            a = jfc.showDialog(null, "Open directory")
//            if (a === JFileChooser.APPROVE_OPTION) {
//                val file: File = jfc.getCurrentDirectory()
//                str = file.getAbsolutePath()
//            }
//        }
//    }

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL


    @FXML
    private lateinit var loadFileBtn: Button

    @FXML
    fun clickLoadFile(event: ActionEvent) {
        val stage = Stage()
        val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("LoadWindow.fxml"))
        val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = "Load file"
        stage.scene = scene
        stage.isResizable = false
        stage.showAndWait()
    }

    @FXML
    private lateinit var loadFromDBBtn: Button

    @FXML
    fun clickLoadFromDBBtn(event: ActionEvent) {
        loadFromDBBtn.setOnAction { event -> exec("load") }
    }

    @FXML
    private lateinit var changeStudentBtn: Button

    @FXML
    private lateinit var deleteStudentBtn: Button

    @FXML
    private lateinit var genProtoBtn: Button

    @FXML
    private lateinit var genTimetableBtn: Button

    @FXML
    private lateinit var saveBtn: Button

    @FXML
    private lateinit var saveInDBBtn: Button

    @FXML
    private lateinit var showDayBtn: Button

    @FXML
    private lateinit var sortBtn: Button

    @FXML
    private lateinit var readBtn: Button

    @FXML
    fun clickRead(event: ActionEvent) {
        readBtn.setOnAction { event -> exec("read") }
    }
    private val fadeTransition = FadeTransition()

    @FXML
    fun click(event: ActionEvent) {
        saveInDBBtn.setOnAction { event -> exec("save") }
        genTimetableBtn.setOnAction { event -> exec("full TestIOSU" ) }
        sortBtn.setOnAction { event -> exec("sort") }
        genProtoBtn.setOnAction { event -> exec("proto") }
        changeStudentBtn.setOnAction { event -> exec("swap") }
        showDayBtn.setOnAction { event ->

            exec("load")
            exec("show")

            listView.items.clear()
            for (i in 0 until studList.size) {
                listView.items.add(studList[i])
            }
            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = listView

            fadeTransition.fromValue = 0.0
            fadeTransition.toValue = 10.0

            //Setting the cycle count for the transition
            fadeTransition.cycleCount = 1

            //Setting auto reverse value to false
            fadeTransition.isAutoReverse = true
            fadeTransition.play()
            listView.toFront()

            listView.selectionModel.selectedItemProperty().addListener { changed, oldValue, newValue ->
                indexDay1 = listView.selectionModel.selectedIndex
                getDay(listView.selectionModel.selectedIndex)

                listView1.items.clear()
                for (i in 0 until studDayList.size) {
                    listView1.items.add(studDayList[i])
                }
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = listView1

                fadeTransition.fromValue = 0.0
                fadeTransition.toValue = 10.0

                //Setting the cycle count for the transition
                fadeTransition.cycleCount = 1

                //Setting auto reverse value to false
                fadeTransition.isAutoReverse = true
                fadeTransition.play()
                listView1.toFront()

                listView1.selectionModel.selectedItemProperty().addListener { changed1, oldValue1, newValue1 ->
                    indexStudent1 = listView1.selectionModel.selectedIndex

                    listView2.items.clear()
                    for (i in 0 until studList.size) {
                        listView2.items.add(studList[i])
                    }
                    fadeTransition.duration = Duration.millis(500.0)
                    fadeTransition.node = listView2

                    fadeTransition.fromValue = 0.0
                    fadeTransition.toValue = 10.0

                    //Setting the cycle count for the transition
                    fadeTransition.cycleCount = 1

                    //Setting auto reverse value to false
                    fadeTransition.isAutoReverse = true
                    fadeTransition.play()
                    listView2.toFront()

                    listView2.selectionModel.selectedItemProperty().addListener { changed, oldValue, newValue ->
                        indexDay2 = listView2.selectionModel.selectedIndex
                        getDay(listView2.selectionModel.selectedIndex)

                        listView3.items.clear()
                        for (i in 0 until studDayList.size) {
                            listView3.items.add(studDayList[i])
                        }
                        fadeTransition.duration = Duration.millis(500.0)
                        fadeTransition.node = listView3

                        fadeTransition.fromValue = 0.0
                        fadeTransition.toValue = 10.0

                        //Setting the cycle count for the transition
                        fadeTransition.cycleCount = 1

                        //Setting auto reverse value to false
                        fadeTransition.isAutoReverse = true
                        fadeTransition.play()
                        listView3.toFront()

                        listView3.selectionModel.selectedItemProperty().addListener { changed1, oldValue1, newValue1 ->
                            indexStudent2 = listView3.selectionModel.selectedIndex

                            fadeTransition.duration = Duration.millis(500.0)
                            fadeTransition.node = pane1

                            fadeTransition.fromValue = 0.0
                            fadeTransition.toValue = 10.0

                            //Setting the cycle count for the transition
                            fadeTransition.cycleCount = 1

                            //Setting auto reverse value to false
                            fadeTransition.isAutoReverse = true
                            fadeTransition.play()
                            pane1.toFront()

                            exec("swap $indexDay1 $indexStudent1 $indexDay2 $indexStudent2")
                            exec("sort $indexDay1")
                            exec("sort $indexDay2")

//                            exec("show")
//
//                            listView.items.clear()
//                            for (i in 0 until studList.size) {
//                                listView.items.add(studList[i])
//                            }
//                            fadeTransition.duration = Duration.millis(500.0)
//                            fadeTransition.node = listView
//
//                            fadeTransition.fromValue = 0.0
//                            fadeTransition.toValue = 10.0
//
//                            //Setting the cycle count for the transition
//                            fadeTransition.cycleCount = 1
//
//                            //Setting auto reverse value to false
//                            fadeTransition.isAutoReverse = true
//                            fadeTransition.play()
//                            listView.toFront()
                        }
                    }
                }
            }


//           println(listView.selectionModel.selectedItems)

//            textShow.text = showList
//            fadeTransition.duration = Duration.millis(500.0)
//            fadeTransition.node = scrPane
//
//            fadeTransition.fromValue = 0.0
//            fadeTransition.toValue = 10.0
//
//            //Setting the cycle count for the transition
//            fadeTransition.cycleCount = 1
//
//            //Setting auto reverse value to false
//            fadeTransition.isAutoReverse = true
//            fadeTransition.play()
//            scrPane.toFront()
            }
        deleteStudentBtn.setOnAction { event ->
            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = pane1

            fadeTransition.fromValue = 0.0
            fadeTransition.toValue = 10.0

            //Setting the cycle count for the transition
            fadeTransition.cycleCount = 1

            //Setting auto reverse value to false
            fadeTransition.isAutoReverse = true
            fadeTransition.play()
            pane1.toFront() }
//        saveBtn.setOnAction { event -> exec("full") }

        changeStud.setOnAction { event ->

//            for (i in 0 until studList.size) {
//                listView.items.add(studList[i])
//            }
//            fadeTransition.duration = Duration.millis(500.0)
//            fadeTransition.node = pane1
//
//            fadeTransition.fromValue = 0.0
//            fadeTransition.toValue = 10.0
//
//            //Setting the cycle count for the transition
//            fadeTransition.cycleCount = 1
//
//            //Setting auto reverse value to false
//            fadeTransition.isAutoReverse = true
//            fadeTransition.play()
//            pane1.toFront()
        }
    }

    @FXML
    private lateinit var pane1: Pane

    @FXML
    private lateinit var pane2: Pane

    @FXML
    private lateinit var textShow: Text

    @FXML
    private lateinit var scrPane: ScrollPane

    @FXML
    private lateinit var changeStud: Button

    @FXML
    private lateinit var listView: ListView<Any>

    @FXML
    private lateinit var listView1: ListView<Any>

    @FXML
    private lateinit var listView2: ListView<Any>

    @FXML
    private lateinit var listView3: ListView<Any>



    @FXML
    private lateinit var calendar: DatePicker

    @FXML
    private lateinit var day_count: TextField

    @FXML
    private lateinit var grops: TextField

    @FXML
    private lateinit var btn_gen_rasp: Button

    @FXML
    fun onClickGenRasp(event: ActionEvent) {
//        exec("date ${calendar.value}")

        exec("date 12.12.2022")

        exec("generate ${day_count.text}")

        exec("save")
    }


    @FXML
    fun initialize() {
        assert(changeStudentBtn != null) {"fx:id=\"changeStudentBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(deleteStudentBtn != null) {"fx:id=\"deleteStudentBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(genProtoBtn != null) {"fx:id=\"genProtoBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(genTimetableBtn != null) {"fx:id=\"genTimetableBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(loadFileBtn != null) {"fx:id=\"loadFileBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(loadFromDBBtn != null) {"fx:id=\"loadFromDBBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(saveBtn != null) {"fx:id=\"saveBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(saveInDBBtn != null) {"fx:id=\"saveInDBBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(showDayBtn != null) {"fx:id=\"showDayBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(sortBtn != null) {"fx:id=\"sortBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(readBtn != null) {"fx:id=\"writeBtn\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(pane1 != null) {"fx:id=\"pane1\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(pane2 != null) {"fx:id=\"pane2\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(textShow != null) {"fx:id=\"textShow\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(scrPane != null) {"fx:id=\"scrPane\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(changeStud != null) {"fx:id=\"changeStud\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(listView != null) {"fx:id=\"listView\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(listView1 != null) {"fx:id=\"listView1\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(listView2 != null) {"fx:id=\"listView2\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(listView3 != null) {"fx:id=\"listView3\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(btn_gen_rasp != null) {"fx:id=\"btn_gen_rasp\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(calendar != null) {"fx:id=\"calendar\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(grops != null) {"fx:id=\"grops\" was not injected: check your FXML file 'MainWindow.fxml'." }
        assert(day_count != null) {"fx:id=\"day_count\" was not injected: check your FXML file 'MainWindow.fxml'." }
    }

}