package com.example.iosu_prj.windowsController

import com.example.iosu_prj.*
import com.example.iosu_prj.model.Student
import javafx.animation.FadeTransition
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.stage.DirectoryChooser
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Duration
import padeg.lib.Padeg
import java.io.File
import java.net.URL
import java.util.*


class MainWinController {

    private val fadeTransition = FadeTransition()
    private val directoryChooser = DirectoryChooser()
    private var selectMenuButton = 0
    private var saturday = listOf("Включая субботу", "Исключая субботу")


    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var buttonArchiveMainPanel: Button

    @FXML
    private lateinit var buttonBackToChooseTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonBackToOperationsPaneChooseToDeleteSecond: Button

    @FXML
    private lateinit var buttonBackToOperationsPaneGenerateAllThird: Button

    @FXML
    private lateinit var buttonLoadTimetablePaneTimetableFirst: Button

    @FXML
    private lateinit var buttonConfirmArchivationPaneArchivationStudentSecond: Button

    @FXML
    private lateinit var buttonConfirmChooseTimetablePaneTimetableFirst: Button

    @FXML
    private lateinit var buttonConfirmDeletePaneChooseToDeleteSecond: Button

    @FXML
    private lateinit var buttonDeleteAllPaneTrashCan: Button

    @FXML
    private lateinit var buttonDeleteAllStudentsPaneTrashCan: Button

    @FXML
    private lateinit var buttonDeleteAllTeachersPaneTrashCan: Button


    @FXML
    private lateinit var buttonDeleteGroupPaneTrashCan: Button

    @FXML
    private lateinit var buttonDeleteStudentInArchiveFromTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonDeleteStudentPaneTrashCan: Button

    @FXML
    private lateinit var buttonDeleteTeacherPaneTrashCan: Button

    @FXML
    private lateinit var buttonDeleteTimetablePaneTimetableFirst: Button

    @FXML
    private lateinit var buttonFullPackageMainPanel: Button

    @FXML
    private lateinit var buttonGenerateAllPaneGenerateAllThird: Button

    @FXML
    private lateinit var buttonGetSaveFolderPathPaneAdditionalInfoProtocolsThird: Button

    @FXML
    private lateinit var buttonGetSaveFolderPathPaneForOtherPaneGenerateAllThird: Button

    @FXML
    private lateinit var buttonLoadFileMainPanel: Button

    @FXML
    private lateinit var buttonMoveStudentInTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonNewTimetablePaneTimetableFirst: Button

    @FXML
    private lateinit var buttonOkNewTimetablePaneNewTimetablePaneTimetableFirst: Button

    @FXML
    private lateinit var buttonProtocolsMainPanel: Button

    @FXML
    private lateinit var buttonResultsMainPanel: Button

    @FXML
    private lateinit var buttonShowTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonSkipEditTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonSwapStudentsInTimetablePaneEditTimetableSecond: Button

    @FXML
    private lateinit var buttonTimetableMainPanel: Button

    @FXML
    private lateinit var buttonTrashCanMainPanel: Button

    @FXML
    private lateinit var buttonOkPaneShowTimetable: Button

    @FXML
    private lateinit var choiceBoxSaturdayPaneNewTimetablePaneTimetableFirst: ChoiceBox<String>

    @FXML
    private lateinit var datePickerPaneForOtherPaneGenerateAllThird: DatePicker

    @FXML
    private lateinit var datePickerPaneInfoForProtocolPaneGenerateAllThird: DatePicker

    @FXML
    private lateinit var datePickerPaneNewTimetablePaneTimetableFirst: DatePicker

    @FXML
    private lateinit var listViewChooseGroupPaneNewTimetablePaneTimetableFirst: ListView<Any>

    @FXML
    private lateinit var listViewChooseStudentToArchivationPaneArchivationStudentSecond: ListView<Any>

    @FXML
    private lateinit var listViewChooseTimetablePaneTimetableFirst: ListView<Any>

    @FXML
    private lateinit var listViewChooseToDeleteSecondPaneChooseToDeleteSecond: ListView<Any>

    @FXML
    private lateinit var listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive: ListView<Any>

    @FXML
    private lateinit var listViewDeleteStudentInArchivePaneDeleteStudentInArchive: ListView<Any>

    @FXML
    private lateinit var listViewMoveStudentFromDayPaneMoveStudentFromDay: ListView<Any>

    @FXML
    private lateinit var listViewMoveStudentGetIndexPaneMoveStudentGetIndex: ListView<Any>

    @FXML
    private lateinit var listViewMoveStudentToDayPaneMoveStudentToDay: ListView<Any>

    @FXML
    private lateinit var listViewShowTimetablePaneShowTimetable: ListView<Any>

    @FXML
    private lateinit var listViewSwapStudentsDayOnePaneSwapStudentsDayOne: ListView<Any>

    @FXML
    private lateinit var listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo: ListView<Any>

    @FXML
    private lateinit var listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne: ListView<Any>

    @FXML
    private lateinit var listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo: ListView<Any>

    @FXML
    private lateinit var paneSelectDayToDeleteStudentInArchive: GridPane

    @FXML
    private lateinit var paneArchiveStudentSecond: GridPane

    @FXML
    private lateinit var paneChooseTimetablePaneTimetableFirst: AnchorPane

    @FXML
    private lateinit var paneChooseToDeleteSecond: GridPane

    @FXML
    private lateinit var paneDeleteStudentInArchive: GridPane

    @FXML
    private lateinit var paneEditTimetableSecond: GridPane

    @FXML
    private lateinit var paneGenerateAllThird: GridPane

    @FXML
    private lateinit var paneForOtherPaneGenerateAllThird: VBox

    @FXML
    private lateinit var paneInfoForProtocolPaneGenerateAllThird: VBox

    @FXML
    private lateinit var paneMoveStudentFromDay: GridPane

    @FXML
    private lateinit var paneMoveStudentGetIndex: GridPane

    @FXML
    private lateinit var paneMoveStudentToDay: GridPane

    @FXML
    private lateinit var paneNewTimetablePaneTimetableFirst: AnchorPane

    @FXML
    private lateinit var paneNotInfoPaneTimetableFirst: VBox

    @FXML
    private lateinit var paneSuccessGeneration: StackPane

    @FXML
    private lateinit var paneSwapStudentsDayOne: GridPane

    @FXML
    private lateinit var paneShowTimetable: GridPane

    @FXML
    private lateinit var paneSwapStudentsDayTwo: GridPane

    @FXML
    private lateinit var paneSwapStudentsIndexOne: GridPane

    @FXML
    private lateinit var buttonSortPaneShowTimetable: Button

    @FXML
    private lateinit var paneSwapStudentsIndexTwo: GridPane

    @FXML
    private lateinit var paneTimetableFirst: GridPane

    @FXML
    private lateinit var paneTrashCan: GridPane

    @FXML
    private lateinit var paneEmpty: AnchorPane

    @FXML
    private lateinit var textAreaCommissionPaneAdditionalInfoProtocolsThird: TextArea

    @FXML
    private lateinit var textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird: TextField

    @FXML
    private lateinit var textFieldGetSaveFolderPathPaneForOtherPaneGenerateAllThird: TextField

    @FXML
    private lateinit var textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThird: TextField

    @FXML
    private lateinit var textSelectedGroupsHelperPaneNewTimetablePaneTimetableFirst: Text

    @FXML
    private lateinit var textViewCountStudentsInDayPaneNewTimetablePaneTimetableFirst: TextField

    @FXML
    private lateinit var textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez: TextField

    @FXML
    private lateinit var textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez: TextField

    @FXML
    private lateinit var textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez: TextField

    @FXML
    private lateinit var textHeadRez: Text

    @FXML
    private lateinit var textSecretaryFullPackage: Text

    @FXML
    private lateinit var textSecretaryRez: Text

    @FXML
    private lateinit var tvGroups: Text

    // Main buttons in the mainLeftPanel:
    //
    //
    //
    //

    // Button 1: Open window to select file path(you'll can load this file)
    @FXML
    fun clickButtonLoadFileMainPanel(event: ActionEvent) {
        selectMenuButton = 1

        setButtonColorStyle()
        buttonLoadFileMainPanel.style = "-fx-background-color: #303D4C"

        paneEmpty.toFront()

        val stage = Stage()
        val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("LoadWindow.fxml"))
        val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = "Load file"
        stage.scene = scene
        stage.isResizable = false
        stage.showAndWait()

        buttonLoadFileMainPanel.style = null
    }

    // Button 2: Result - timetable(after some steps)
    @FXML
    fun clickButtonTimetableMainPanel(event: ActionEvent) {
        selectMenuButton = 2
        checkProtocols()

        setButtonColorStyle()
        buttonTimetableMainPanel.style = "-fx-background-color: #303D4C"

        paneNotInfoPaneTimetableFirst.toFront()
        paneEmpty.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTimetableFirst

        fadeTransitionHelper()

        paneTimetableFirst.toFront()
    }

    // Button 3: Result - results(after some steps)
    @FXML
    fun clickButtonResultsMainPanel(event: ActionEvent) {
        selectMenuButton = 3
        checkProtocols()

        setButtonColorStyle()
        buttonResultsMainPanel.style = "-fx-background-color: #303D4C"

        paneNotInfoPaneTimetableFirst.toFront()
        paneEmpty.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTimetableFirst

        fadeTransitionHelper()

        paneTimetableFirst.toFront()
    }

    // Button 4: Result - protocols(after some steps)
    @FXML
    fun clickButtonProtocolsMainPanel(event: ActionEvent) {
        selectMenuButton = 4
        checkProtocols()

        setButtonColorStyle()
        buttonProtocolsMainPanel.style = "-fx-background-color: #303D4C"

        paneNotInfoPaneTimetableFirst.toFront()
        paneEmpty.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTimetableFirst

        fadeTransitionHelper()

        paneTimetableFirst.toFront()
    }

    // Button 5: Result - timetable, results, protocols(after some steps)
    @FXML
    fun clickButtonFullPackageMainPanel(event: ActionEvent) {
        selectMenuButton = 5
        checkProtocols()

        setButtonColorStyle()
        buttonFullPackageMainPanel.style = "-fx-background-color: #303D4C"

        paneNotInfoPaneTimetableFirst.toFront()
        paneEmpty.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTimetableFirst

        fadeTransitionHelper()

        paneTimetableFirst.toFront()
    }

    // Button 6: Open pane archive
    @FXML
    fun clickButtonArchiveMainPanel(event: ActionEvent) {
        selectMenuButton = 6

        setButtonColorStyle()
        buttonArchiveMainPanel.style = "-fx-background-color: #303D4C"

        exec("load")

        paneEmpty.toFront()

        //

        check = 0

        listIdNotInArchive.clear()
        noArchiveList = studentsNotInArchive()
        noArchiveList.forEach { listIdNotInArchive.add(it.id) }
        mainList = studentsInArchive(listIdNotInArchive)

            listViewChooseStudentToArchivationPaneArchivationStudentSecond.items.clear()
        for (i in mainList.indices) {
            listViewChooseStudentToArchivationPaneArchivationStudentSecond.items.add("${mainList[i].fio}   гр. ${mainList[i].group}")
        }

        listViewChooseStudentToArchivationPaneArchivationStudentSecond.selectionModel.selectedItemProperty()
            .addListener { changed1, oldValue1, newValue1 ->
                check = 0
            }

        if (mainList.isEmpty()) {
            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneEmpty

            fadeTransitionHelper()

            paneEmpty.toFront()

            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_archive_empty_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()

            buttonArchiveMainPanel.style = null
        }
        else {
            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneArchiveStudentSecond

            fadeTransitionHelper()

            paneArchiveStudentSecond.toFront()
        }
    }

    // Button 7: Open paneTrashCan
    @FXML
    fun clickButtonTrashCanMainPanel(event: ActionEvent) {
        selectMenuButton = 7

        setButtonColorStyle()
        buttonTrashCanMainPanel.style = "-fx-background-color: #303D4C"

        paneEmpty.toFront()
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTrashCan

        fadeTransitionHelper()

        paneTrashCan.toFront()
    }

    fun checkProtocols() {
        buttonLoadTimetablePaneTimetableFirst.isDisable = protocols.isEmpty()
    }




    //Buttons in steps in Results, Protocols, Timetables, Full package:
    //
    //
    //
    //
    //

    //First step: paneChooseTimetable
    //
    //
    //Buttons:

    //1)

    //load timetable
    @FXML
    fun clickButtonLoadTimetablePaneTimetableFirst(event: ActionEvent) {
        exec("load")

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneEditTimetableSecond

        fadeTransitionHelper()

        paneEditTimetableSecond.toFront()
    }

    //WINDOW1:
    //confirm your choice in timetable list
    @FXML
    fun clickButtonConfirmChooseTimetablePaneTimetableFirst(event: ActionEvent) {
//
    }

    //delete timetable, which you choose
    @FXML
    fun clickButtonDeleteTimetablePaneTimetableFirst(event: ActionEvent) {
//
    }

    //2)

    //run WINDOW2, which is show all params to create new timetable
    @FXML
    fun clickButtonNewTimetablePaneTimetableFirst(event: ActionEvent) {
        if (showGroup().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_groups_list_empty_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }

        else {
            code = 0

            if (showProtocols().isNotEmpty()) {
                val stage = Stage()
                val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_timetable_window.fxml"))
                val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
                stage.initModality(Modality.APPLICATION_MODAL)
                stage.title = "Attention!"
                stage.scene = scene
                stage.isResizable = false
                stage.showAndWait()

                if (code == 1) {
                    doNewTimetable()
                }
            } else {
                doNewTimetable()
            }
        }
    }

    private fun doNewTimetable() {
        //create listView
        tvGroups.text = "Выберите необходимые группы в порядке их приоритета:"
        textSelectedGroupsHelperPaneNewTimetablePaneTimetableFirst.text = "Выбранные группы:"
        groupsList.clear()
        selectedGroups.clear()
        es.clear()
        grPriors.clear()
        var numberPrior = 0
        showGroup().forEachIndexed { index, it ->
            groupsList.add(it.number.toString())
        }

        listViewChooseGroupPaneNewTimetablePaneTimetableFirst.items.clear()
        for (i in 0 until groupsList.size) {
            listViewChooseGroupPaneNewTimetablePaneTimetableFirst.items.add(groupsList[i])
        }

        //update listView before click
        listViewChooseGroupPaneNewTimetablePaneTimetableFirst.selectionModel
            .selectedItemProperty().addListener { changed, oldValue, newValue ->

                if (newValue != null) {
                    textSelectedGroupsHelperPaneNewTimetablePaneTimetableFirst.text += " $newValue,"
                    selectedGroups.add(newValue.toString())
                    grPriors[newValue.toString()] = numberPrior
                    numberPrior++
                }

                groupsList.remove(newValue)

                if (groupsList.isEmpty()) {
                    tvGroups.text = "Список групп пуст!"
                }

                Platform.runLater(Runnable {
                    listViewChooseGroupPaneNewTimetablePaneTimetableFirst.items.clear()
                    for (i in 0 until groupsList.size) {
                        listViewChooseGroupPaneNewTimetablePaneTimetableFirst.items.add(groupsList[i])
                    }
                })
            }

        //show WINDOW2
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneNewTimetablePaneTimetableFirst

        fadeTransitionHelper()

        paneNewTimetablePaneTimetableFirst.toFront()
    }

    //WINDOW2:
    //create new timetable
    @FXML
    fun clickButtonOkNewTimetablePaneNewTimetablePaneTimetableFirst(event: ActionEvent) {
        var right = true
        try {
            val date = "${datePickerPaneNewTimetablePaneTimetableFirst.value.dayOfMonth}." +
                    "${datePickerPaneNewTimetablePaneTimetableFirst.value.monthValue}." +
                    "${datePickerPaneNewTimetablePaneTimetableFirst.value.year}"

            val countOfStudentsInDay = textViewCountStudentsInDayPaneNewTimetablePaneTimetableFirst.text

            val stateSaturday =
                choiceBoxSaturdayPaneNewTimetablePaneTimetableFirst.value.toString() != "Включая субботу"

            exec("date $date")

            exec("generate $countOfStudentsInDay $stateSaturday")

            exec("save")
        }
        catch (_: Exception) {
            right = false

            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_check_data_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }

        if (right) {
            if (showProtocols().isEmpty()) {
                val stage = Stage()
                val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_timetable_window.fxml"))
                val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
                stage.initModality(Modality.APPLICATION_MODAL)
                stage.title = "Attention!"
                stage.scene = scene
                stage.isResizable = false
                stage.showAndWait()

                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneEmpty

                fadeTransitionHelper()

                paneEmpty.toFront()

                setButtonColorStyle()
            } else {
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneEditTimetableSecond

                fadeTransitionHelper()

                paneEditTimetableSecond.toFront()
            }
        }
    }



    //Second step: paneEditTimetable
    //
    //
    //
    //Buttons:

    //back to chooseTimetable
    @FXML
    fun clickButtonBackToChooseTimetablePaneEditTimetableSecond(event: ActionEvent) {
        checkProtocols()
        paneNotInfoPaneTimetableFirst.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTimetableFirst

        fadeTransitionHelper()

        paneTimetableFirst.toFront()
    }

    //the next step after editing timetable
    @FXML
    fun clickButtonSkipEditTimetablePaneEditTimetableSecond(event: ActionEvent) {
        deleteTimetable()
        exec("save")

        showGroup().forEachIndexed { index, it ->
            val economistR = Padeg.getFIOPadegFSAS(it.economist.toString(), 2)
            val lastNameR = economistR.substringBefore(" ")
            val firstNameR = economistR.substringAfter("$lastNameR ").substringBefore(" ")
            val middleNameR = economistR.substringAfter("$firstNameR ")
            val economist = "$lastNameR ${firstNameR[0]}. ${middleNameR[0]}."
            es[it.number.toString()] = economist
        }

        if (selectMenuButton == 3) {
            textHeadRez.isVisible = true
            textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.isVisible = true
            textSecretaryRez.isVisible = true
            textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.isVisible = true
        }
        else {
            textHeadRez.isVisible = false
            textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.isVisible = false
            textSecretaryRez.isVisible = false
            textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.isVisible = false
        }

        if (selectMenuButton == 5) {
            textSecretaryFullPackage.isVisible = true
            textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez.isVisible = true
        }
        else {
            textSecretaryFullPackage.isVisible = false
            textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez.isVisible = false
        }

        if (selectMenuButton == 4 || selectMenuButton == 5) paneInfoForProtocolPaneGenerateAllThird.toFront()
        else paneForOtherPaneGenerateAllThird.toFront()

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneGenerateAllThird

        fadeTransitionHelper()

        paneGenerateAllThird.toFront()
    }

    // Operations Buttons:

    //show timetable
    @FXML
    fun clickButtonShowTimetablePaneEditTimetableSecond(event: ActionEvent) {
        exec("show")

        listViewShowTimetablePaneShowTimetable.items.clear()
        for (i in 0 until studList.size) {
            listViewShowTimetablePaneShowTimetable.items.add(studList[i])
        }

        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneShowTimetable

        fadeTransitionHelper()

        paneShowTimetable.toFront()
    }

    @FXML
    fun clickButtonSortShowTimetable(event: ActionEvent) {
        exec("sort ${listViewShowTimetablePaneShowTimetable.selectionModel.selectedIndex}")

        //update list
        exec("show")

        Platform.runLater(Runnable {
            listViewShowTimetablePaneShowTimetable.items.clear()
            for (i in 0 until studList.size) {
                listViewShowTimetablePaneShowTimetable.items.add(studList[i])
            }
        })
    }

    //back to operations before showTimetable
    @FXML
    fun clickButtonOkShowTimetable(event: ActionEvent) {
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneEditTimetableSecond

        fadeTransitionHelper()

        paneEditTimetableSecond.toFront()
    }

    //swap students
    @FXML
    fun clickButtonSwapStudentsInTimetablePaneEditTimetableSecond(event: ActionEvent) {
        exec("show")
        check = 0
        var st1 = ""
        var day1 = ""
        var st2 = ""
        var day2 = ""

        //create listViewFirstDay
        listViewSwapStudentsDayOnePaneSwapStudentsDayOne.items.clear()
        for (i in 0 until studList.size) {
            listViewSwapStudentsDayOnePaneSwapStudentsDayOne.items.add(studList[i])
        }

        //go to listViewFirstDay
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneSwapStudentsDayOne

        fadeTransitionHelper()

        paneSwapStudentsDayOne.toFront()

        listViewSwapStudentsDayOnePaneSwapStudentsDayOne.selectionModel.selectedItemProperty().addListener { changed, oldValue, newValue ->
            if (newValue != null && oldValue == null) {
                indexDay1 = listViewSwapStudentsDayOnePaneSwapStudentsDayOne.selectionModel.selectedIndex
                getDay(listViewSwapStudentsDayOnePaneSwapStudentsDayOne.selectionModel.selectedIndex)
                day1 = newValue.toString()

                //create listViewStudentsInFirstDay
                listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne.items.clear()
                for (i in 0 until studDayList.size) {
                    listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne.items.add(studDayList[i])
                }

                //go to listViewStudentIndexOne
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneSwapStudentsIndexOne

                fadeTransitionHelper()

                paneSwapStudentsIndexOne.toFront()

                listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne.selectionModel.selectedItemProperty()
                    .addListener { changed1, oldValue1, newValue1 ->
                        if (newValue1 != null && oldValue1 == null) {
                            indexStudent1 =
                                listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne.selectionModel.selectedIndex
                            st1 = newValue1.toString()

                            //create listViewSecondDay
                            listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo.items.clear()
                            for (i in 0 until studList.size) {
                                listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo.items.add(studList[i])
                            }

                            //go to listViewSecondDay
                            fadeTransition.duration = Duration.millis(500.0)
                            fadeTransition.node = paneSwapStudentsDayTwo

                            fadeTransitionHelper()

                            paneSwapStudentsDayTwo.toFront()

                            listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo.selectionModel.selectedItemProperty()
                                .addListener { changed2, oldValue2, newValue2 ->
                                    if (newValue2 != null && oldValue2 == null) {
                                        indexDay2 =
                                            listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo.selectionModel.selectedIndex
                                        getDay(listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo.selectionModel.selectedIndex)
                                        day2 = newValue2.toString()

                                        //create listViewStudentsInSecondDay
                                        listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo.items.clear()
                                        for (i in 0 until studDayList.size) {
                                            listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo.items.add(studDayList[i])
                                        }

                                        //go to listViewStudentIndexTwo
                                        fadeTransition.duration = Duration.millis(500.0)
                                        fadeTransition.node = paneSwapStudentsIndexTwo

                                        fadeTransitionHelper()

                                        paneSwapStudentsIndexTwo.toFront()

                                        listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo.selectionModel.selectedItemProperty()
                                            .addListener { changed3, oldValue3, newValue3 ->
                                                if (newValue3 != null && oldValue3 == null && check < 1) {
                                                    indexStudent2 =
                                                    listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo.selectionModel.selectedIndex
                                                    st2 = newValue3.toString()

                                                    code = 0
                                                    confirmText = "Вы уверены, что хотите поменять местами студента" +
                                                            " ${st1.substringBeforeLast(".")}. из дня" +
                                                            " '${day1.substringAfter(": ").substringBefore("\n")}' " +
                                                            "со студентом ${st2.substringBeforeLast(".")}. из дня" +
                                                            " '${day2.substringAfter(": ").substringBefore("\n")}'?"
                                                    openWinConfirm()

                                                    if (code == 1) {
                                                        exec("swap $indexDay1 $indexStudent1 $indexDay2 $indexStudent2")
                                                        openWinOperationDone()
                                                    }
                                                    else check++

                                                    fadeTransition.duration = Duration.millis(500.0)
                                                    fadeTransition.node = paneEditTimetableSecond

                                                    fadeTransitionHelper()

                                                    paneEditTimetableSecond.toFront()
                                                }
                                            }
                                    }
                                }

                        }
                    }
            }
        }
    }

    //move student
    @FXML
    fun clickButtonMoveStudentInTimetablePaneEditTimetableSecond(event: ActionEvent) {
        exec("show")
        check = 0
        var day1 = ""
        var st1 = ""
        var day2 = ""

        //create listViewFirstDay
        listViewMoveStudentFromDayPaneMoveStudentFromDay.items.clear()
        for (i in 0 until studList.size) {
            listViewMoveStudentFromDayPaneMoveStudentFromDay.items.add(studList[i])
        }

        //go to listViewFirstDay
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneMoveStudentFromDay

        fadeTransitionHelper()

        paneMoveStudentFromDay.toFront()

        listViewMoveStudentFromDayPaneMoveStudentFromDay.selectionModel.selectedItemProperty().addListener { changed, oldValue, newValue ->
            if (newValue != null && oldValue == null) {
                indexDay1 = listViewMoveStudentFromDayPaneMoveStudentFromDay.selectionModel.selectedIndex
                getDay(listViewMoveStudentFromDayPaneMoveStudentFromDay.selectionModel.selectedIndex)
                day1 = newValue.toString()

                //create listViewStudentsInFirstDay
                listViewMoveStudentGetIndexPaneMoveStudentGetIndex.items.clear()
                for (i in 0 until studDayList.size) {
                    listViewMoveStudentGetIndexPaneMoveStudentGetIndex.items.add(studDayList[i])
                }

                //go to listViewStudentIndexOne
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneMoveStudentGetIndex

                fadeTransitionHelper()

                paneMoveStudentGetIndex.toFront()

                listViewMoveStudentGetIndexPaneMoveStudentGetIndex.selectionModel.selectedItemProperty()
                    .addListener { changed1, oldValue1, newValue1 ->
                        if (newValue1 != null && oldValue1 == null) {
                            indexStudent1 =
                                listViewMoveStudentGetIndexPaneMoveStudentGetIndex.selectionModel.selectedIndex
                            st1 = newValue1.toString()

                            //create listViewSecondDay
                            listViewMoveStudentToDayPaneMoveStudentToDay.items.clear()
                            for (i in 0 until studList.size) {
                                listViewMoveStudentToDayPaneMoveStudentToDay.items.add(studList[i])
                            }

                            //go to listViewSecondDay
                            fadeTransition.duration = Duration.millis(500.0)
                            fadeTransition.node = paneMoveStudentToDay

                            fadeTransitionHelper()

                            paneMoveStudentToDay.toFront()

                            listViewMoveStudentToDayPaneMoveStudentToDay.selectionModel.selectedItemProperty()
                                .addListener { changed2, oldValue2, newValue2 ->
                                    if (newValue2 != null && oldValue2 == null && check < 1) {
                                        indexDay2 =
                                            listViewMoveStudentToDayPaneMoveStudentToDay.selectionModel.selectedIndex
                                        day2 = newValue2.toString()
                                        code = 0
                                        confirmText = "Вы уверены, что хотите переместить студента" +
                                                " ${st1.substringBeforeLast(".")}. из дня" +
                                                " '${day1.substringAfter(": ").substringBefore("\n")}' " +
                                                "в день '${day2.substringAfter(": ").substringBefore("\n")}'?"
                                        openWinConfirm()

                                        if (code == 1) {
                                            exec("move $indexDay1 $indexStudent1 $indexDay2")
                                            openWinOperationDone()
                                        }
                                        else check++

                                        fadeTransition.duration = Duration.millis(500.0)
                                        fadeTransition.node = paneEditTimetableSecond

                                        fadeTransitionHelper()

                                        paneEditTimetableSecond.toFront()
                                    }
                                }
                        }
                    }
            }
        }
    }

    //delete student
    @FXML
    fun clickButtonDeleteStudentInArchiveFromTimetablePaneEditTimetableSecond(event: ActionEvent) {
        exec("show")
        check = 0

        //create listViewDayToDelete
        listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive.items.clear()
        for (i in 0 until studList.size) {
            listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive.items.add(studList[i])
        }

        //go to listViewDayToDelete
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneSelectDayToDeleteStudentInArchive

        fadeTransitionHelper()

        paneSelectDayToDeleteStudentInArchive.toFront()

        listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive.selectionModel.selectedItemProperty().addListener { changed, oldValue, newValue ->
            if (newValue != null && oldValue == null) {
                indexDay1 =
                    listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive.selectionModel.selectedIndex
                getDay(listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive.selectionModel.selectedIndex)

                //create listViewSelectStudentToDelete
                listViewDeleteStudentInArchivePaneDeleteStudentInArchive.items.clear()
                for (i in 0 until studDayList.size) {
                    listViewDeleteStudentInArchivePaneDeleteStudentInArchive.items.add(studDayList[i])
                }

                //go to listViewStudentIndexOne
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneDeleteStudentInArchive

                fadeTransitionHelper()

                paneDeleteStudentInArchive.toFront()

                listViewDeleteStudentInArchivePaneDeleteStudentInArchive.selectionModel.selectedItemProperty()
                    .addListener { changed1, oldValue1, newValue1 ->
                        if (newValue1 != null && oldValue1 == null && check < 1) {

                            indexStudent1 =
                                listViewDeleteStudentInArchivePaneDeleteStudentInArchive.selectionModel.selectedIndex

                            code = 0
                            confirmText = "Вы уверены, что хотите удалить в архив студента" +
                                    " ${newValue1.toString().substringBeforeLast(".")}.?"
                            openWinConfirm()

                            if (code == 1) {
                                exec("deleteFromTimetable $indexDay1 $indexStudent1")
                                openWinOperationDone()
                            }
                            else check++

                            fadeTransition.duration = Duration.millis(500.0)
                            fadeTransition.node = paneEditTimetableSecond

                            fadeTransitionHelper()

                            paneEditTimetableSecond.toFront()
                        }
                    }
            }
        }
        if(showProtocols().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_timetable_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()

            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneEmpty

            fadeTransitionHelper()

            paneEmpty.toFront()

            setButtonColorStyle()
        }
    }

    //Third step: paneGenerate
    //
    //
    //
    //
    //Buttons:

    //back to operations
    @FXML
    fun clickButtonBackToOperationsPaneGenerateAllThird(event: ActionEvent) {
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneEditTimetableSecond

        fadeTransitionHelper()

        paneEditTimetableSecond.toFront()
    }

    //generate protocol, results, timetable, full package
    @FXML
    fun clickButtonGenerateAllPaneGenerateAllThird(event: ActionEvent) {
        var right = true
        try {
            var dateProto = ""
            var dateNotProto = ""
            if (selectMenuButton == 4 || selectMenuButton == 5) {
                headOfTheCommission = textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThird.text
                val textCommission = textAreaCommissionPaneAdditionalInfoProtocolsThird.text

                var surnameHeadOfTheCommission = headOfTheCommission.substringBefore(" ")
                var nameHeadOfTheCommission = headOfTheCommission.substringAfter(" ")[0]
                var patronymicHeadOfTheCommission = headOfTheCommission.substringAfterLast(" ")[0]
                var fioHeadOfTheCommission =
                    "$surnameHeadOfTheCommission $nameHeadOfTheCommission. $patronymicHeadOfTheCommission."

                ks.add(fioHeadOfTheCommission)
                var commissionForKs = textCommission.substringBefore(",")
                for (i in 0..7) {
                    ks.add(commissionForKs)
                    commissionForKs = textCommission.substringAfter("$commissionForKs, ").substringBefore(",")
                }
                println(ks)

                var last = textCommission.substringBefore(",")
                for (item in 0..3) {
                    commission.add("$last,")
                    last = textCommission.substringAfter("$last,").substringBefore(",")
                    commission[item] += last
                    last = textCommission.substringAfter("$last, ").substringBefore(",")
                }

                dateProto = "${datePickerPaneInfoForProtocolPaneGenerateAllThird.value.dayOfMonth}." +
                        "${datePickerPaneInfoForProtocolPaneGenerateAllThird.value.monthValue}." +
                        "${datePickerPaneInfoForProtocolPaneGenerateAllThird.value.year}"

                savePath = textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird.text
            } else {
                dateNotProto = "${datePickerPaneForOtherPaneGenerateAllThird.value.dayOfMonth}." +
                        "${datePickerPaneForOtherPaneGenerateAllThird.value.monthValue}." +
                        "${datePickerPaneForOtherPaneGenerateAllThird.value.year}"

                savePath = textFieldGetSaveFolderPathPaneForOtherPaneGenerateAllThird.text
            }

            if (selectMenuButton == 5) {
                secretaryOfTheCommission = textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez.text
            }

            if (selectMenuButton == 3) {
                secretaryOfTheCommission = textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.text
                headOfTheCommission = textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez.text
            }

            when (selectMenuButton) {
                2 -> {
                    exec("date $dateNotProto")
                    exec("write $savePath/rasp.docx")
                    exec("writeOff $savePath/raspOff.docx")
                }

                3 -> {
                    exec("date $dateNotProto")
                    exec("writeGec proto_rasp_form/gec.docx $savePath/gec.docx")
                    exec("writeREZU proto_rasp_form/rezu.docx $savePath/rez-ucheb-12.docx")
                    exec("writeREZD proto_rasp_form/rezd.docx $savePath/rez-dekanat-14.docx")
                }

                4 -> {
                    File("$savePath/proto").mkdirs()
                    File("$savePath/protoNOT").mkdirs()

                    exec("date $dateProto")
                    exec("proto proto_rasp_form/p.xlsx $savePath/proto")
                    exec("proto2 proto_rasp_form/p.xlsx $savePath/protoNOT")
                }

                5 -> {
                    exec("date $dateProto")
                    exec("full")
                }
            }
        }
        catch (_: Exception) {
            right = false

            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_check_data_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }

        if (right) {
            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneSuccessGeneration

            fadeTransitionHelper()

            paneSuccessGeneration.toFront()
        }
    }

    //
    //WINDOW1(Protocol):

    //get save path
    @FXML
    fun clickButtonGetSaveFolderPathPaneAdditionalInfoProtocolsThird(event: ActionEvent) {
        directoryChooser.title = "Выберите папку для сохранения ваших файлов"

        val folder = directoryChooser.showDialog(null)

        if (folder != null) {
            textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird.text = ""
            textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird.appendText(folder.absolutePath)
        }
    }

    //
    //WINDOW2(not Protocol):

    //get save path
    @FXML
    fun clickButtonGetSaveFolderPathPaneForOtherPaneGenerateAllThird(event: ActionEvent) {
        directoryChooser.title = "Выберите папку для сохранения ваших файлов"

        val folder = directoryChooser.showDialog(null)

        if (folder != null) {
            textFieldGetSaveFolderPathPaneForOtherPaneGenerateAllThird.text = ""
            textFieldGetSaveFolderPathPaneForOtherPaneGenerateAllThird.appendText(folder.absolutePath)
        }
    }





    //Buttons in archive:
    //
    //
    //
    //
    //

    //First step: paneArchive
    //
    //
    //Operations buttons:

    //delete timetable
    @FXML
    fun clickButtonDeleteTimetablePaneArchivation(event: ActionEvent) {
//
    }

    // delete student
    @FXML
    fun clickButtonReturnStudentFromArchivePaneArchivation(event: ActionEvent) {
//
    }

    //Second step: paneChooseStudentToDeleteInArchive
    //
    //
    //Buttons:

    //back to operations
    @FXML
    fun clickButtonBackToOperationsPaneArchivation(event: ActionEvent) {
//
    }

    //confirm archivation
    @FXML
    fun clickButtonConfirmArchivationPaneArchivation(event: ActionEvent) {
        code = 0

        if (check == 0) {
            confirmText = "Вы уверены, что хотите разархивировать" +
                    " ${listViewChooseStudentToArchivationPaneArchivationStudentSecond.selectionModel.selectedItem}?"
            openWinConfirm()

            if (code == 1) {
                check++
                exec("returnToTimetable ${listViewChooseStudentToArchivationPaneArchivationStudentSecond.selectionModel.selectedIndex}")

                exec("save")
                fadeTransition.duration = Duration.millis(500.0)

                //update list

                noArchiveList = studentsNotInArchive()
                noArchiveList.forEach { listIdNotInArchive.add(it.id) }
                mainList = studentsInArchive(listIdNotInArchive)

                Platform.runLater(Runnable {
                    listViewChooseStudentToArchivationPaneArchivationStudentSecond.items.clear()
                    for (i in mainList.indices) {
                        listViewChooseStudentToArchivationPaneArchivationStudentSecond.items.add(mainList[i])
                    }
                })
                openWinOperationDone()
            }

            if (mainList.isEmpty()) {
                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneEmpty

                fadeTransitionHelper()

                paneEmpty.toFront()

                val stage = Stage()
                val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_archive_empty_window.fxml"))
                val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
                stage.initModality(Modality.APPLICATION_MODAL)
                stage.title = "Attention!"
                stage.scene = scene
                stage.isResizable = false
                stage.showAndWait()

                buttonArchiveMainPanel.style = null
            }
        }
    }




    //Buttons in trash can:
    //
    //
    //
    //
    //

    //First step: paneOperations
    //
    //
    //Operations buttons:

    private var checkDelete = ""

    @FXML
    fun clickButtonDeleteStudentPaneTrashCan(event: ActionEvent) {
        if (showStudent().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            checkDelete = "Student"
            createListViewTrashCan()

            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneChooseToDeleteSecond

            fadeTransitionHelper()

            paneChooseToDeleteSecond.toFront()
        }
    }

    @FXML
    fun clickButtonDeleteTeacherPaneTrashCan(event: ActionEvent) {
        if (showTeacher().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            checkDelete = "Teacher"
            createListViewTrashCan()

            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneChooseToDeleteSecond

            fadeTransitionHelper()

            paneChooseToDeleteSecond.toFront()
        }
    }

    @FXML
    fun clickButtonDeleteGroupPaneTrashCan(event: ActionEvent) {
        if (showGroup().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            checkDelete = "Group"
            createListViewTrashCan()

            fadeTransition.duration = Duration.millis(500.0)
            fadeTransition.node = paneChooseToDeleteSecond

            fadeTransitionHelper()

            paneChooseToDeleteSecond.toFront()
        }
    }

    @FXML
    fun clickButtonDeleteAllStudentsPaneTrashCan(event: ActionEvent) {
        if (showStudent().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            code = 0
            confirmText = "Вы уверены, что хотите удалить всех студентов из БД?"
            openWinConfirm()
            if (code == 1) {
        deleteAllStudents()
                openWinOperationDone()
            }
        }
    }

    @FXML
    fun clickButtonDeleteAllTeachersPaneTrashCan(event: ActionEvent) {
        if (showTeacher().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            code = 0
            confirmText = "Вы уверены, что хотите удалить всех преподавателей из БД?"
            openWinConfirm()
            if (code == 1) {
        deleteAllTeachers()
                openWinOperationDone()
            }
        }
    }

    @FXML
    fun clickButtonDeleteAllPaneTrashCan(event: ActionEvent) {
        if (showStudent().isEmpty() && showTeacher().isEmpty() && showGroup().isEmpty()) {
            val stage = Stage()
            val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("exception_empty_list_window.fxml"))
            val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
            stage.initModality(Modality.APPLICATION_MODAL)
            stage.title = "Attention!"
            stage.scene = scene
            stage.isResizable = false
            stage.showAndWait()
        }
        else {
            code = 0
            confirmText = "Вы уверены, что хотите полностью очистить БД?"
            openWinConfirm()
            if (code == 1) {
        deleteAll()
                openWinOperationDone()
            }
        }
    }

    private fun createListViewTrashCan() {
        check = 0

        when(checkDelete) {
            "Student" -> {
                val list = showStudent()
                listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.clear()
                for (i in list.indices) {
                    listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.add("${list[i].fio}   гр. ${list[i].group}")
                }
            }

            "Teacher" -> {
                val list = showTeacher()
                listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.clear()
                for (i in list.indices) {
                    listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.add(list[i].fio)
                }
            }

            "Group" -> {
                val list = showGroup()
                listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.clear()
                for (i in list.indices) {
                    listViewChooseToDeleteSecondPaneChooseToDeleteSecond.items.add(list[i].number)
                }
            }
        }

        listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedItemProperty()
            .addListener { changed1, oldValue1, newValue1 ->
                check = 0
            }
    }

    //Second step: paneChooseToDelete
    //
    //
    //Buttons:

    //back to operations
    @FXML
    fun clickButtonBackToOperationsPaneChooseToDeleteSecond(event: ActionEvent) {
        paneEmpty.toFront()
        fadeTransition.duration = Duration.millis(500.0)
        fadeTransition.node = paneTrashCan

        fadeTransitionHelper()

        paneTrashCan.toFront()
    }

    //confirm delete
    @FXML
    fun clickButtonConfirmDeletePaneChooseToDeleteSecond(event: ActionEvent) {
        code = 0

        if (check == 0) {
            confirmText = "Вы уверены, что хотите удалить" +
                    " ${listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedItem}?"
            openWinConfirm()

            if (code == 1) {
                check++

                when(checkDelete) {
                    "Student" -> {
                        removeStudent(showStudent()[listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedIndex].id)
                    }

                    "Teacher" -> {
                        removeTeacher(showTeacher()[listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedIndex].id)
                    }

                    "Group" -> {
                        removeGroup(showGroup()[listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedIndex].id,
                            showGroup()[listViewChooseToDeleteSecondPaneChooseToDeleteSecond.selectionModel.selectedIndex].number.toString()
                        )
                    }
                }
                openWinOperationDone()

                fadeTransition.duration = Duration.millis(500.0)
                fadeTransition.node = paneTrashCan

                fadeTransitionHelper()

                paneTrashCan.toFront()
            }
        }
    }



    private fun openWinConfirm() {
        val stage = Stage()
        val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("confirm_operation_choice_window.fxml"))
        val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = "Attention!"
        stage.scene = scene
        stage.isResizable = false
        stage.showAndWait()
    }

    private fun openWinOperationDone() {
        val stage = Stage()
        val fxmlLoader = FXMLLoader(MainWindow::class.java.getResource("operation_done_window.fxml"))
        val scene = Scene(fxmlLoader.load(), 450.0, 250.0)
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = ""
        stage.scene = scene
        stage.isResizable = false
        stage.showAndWait()
    }

    private fun fadeTransitionHelper() {
        fadeTransition.fromValue = 0.0
        fadeTransition.toValue = 10.0

        //Setting the cycle count for the transition
        fadeTransition.cycleCount = 1

        //Setting auto reverse value to true
        fadeTransition.isAutoReverse = true
        fadeTransition.play()
    }

    private fun setButtonColorStyle() {
        buttonLoadFileMainPanel.style = null
        buttonTimetableMainPanel.style = null
        buttonResultsMainPanel.style = null
        buttonProtocolsMainPanel.style = null
        buttonFullPackageMainPanel.style = null
        buttonArchiveMainPanel.style = null
        buttonTrashCanMainPanel.style = null
    }

    @FXML
    fun initialize() {
        choiceBoxSaturdayPaneNewTimetablePaneTimetableFirst.items.addAll(saturday)
        assert(buttonArchiveMainPanel != null) {"fx:id=\"buttonArchivationMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonBackToChooseTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonBackToChooseTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonBackToOperationsPaneChooseToDeleteSecond != null) {"fx:id=\"buttonBackToOperationsPaneChooseToDeleteSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonBackToOperationsPaneGenerateAllThird != null) {"fx:id=\"buttonBackToOperationsPaneGenerateAllThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonLoadTimetablePaneTimetableFirst != null) {"fx:id=\"buttonChooseTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonConfirmArchivationPaneArchivationStudentSecond != null) {"fx:id=\"buttonConfirmArchivationPaneArchivationStudentSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonConfirmChooseTimetablePaneTimetableFirst != null) {"fx:id=\"buttonConfirmChooseTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonConfirmDeletePaneChooseToDeleteSecond != null) {"fx:id=\"buttonConfirmDeletePaneChooseToDeleteSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonDeleteGroupPaneTrashCan != null) {"fx:id=\"buttonDeleteGroupPaneTrashCan\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonDeleteStudentInArchiveFromTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonDeleteStudentInArchiveFromTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonDeleteStudentPaneTrashCan != null) {"fx:id=\"buttonDeleteStudentPaneTrashCan\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonDeleteTeacherPaneTrashCan != null) {"fx:id=\"buttonDeleteTeacherPaneTrashCan\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonDeleteTimetablePaneTimetableFirst != null) {"fx:id=\"buttonDeleteTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonFullPackageMainPanel != null) {"fx:id=\"buttonFullPackageMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonGenerateAllPaneGenerateAllThird != null) {"fx:id=\"buttonGenerateAllPaneGenerateAllThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonGetSaveFolderPathPaneAdditionalInfoProtocolsThird != null) {"fx:id=\"buttonGetSaveFolderPathPaneAdditionalInfoProtocolsThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonGetSaveFolderPathPaneForOtherPaneGenerateAllThird != null) {"fx:id=\"buttonGetSaveFolderPathPaneGenerateAllThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonLoadFileMainPanel != null) {"fx:id=\"buttonLoadFileMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonMoveStudentInTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonMoveStudentInTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonNewTimetablePaneTimetableFirst != null) {"fx:id=\"buttonNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonOkNewTimetablePaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"buttonOkNewTimetablePaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonOkPaneShowTimetable != null) {"fx:id=\"buttonOkPaneShowTimetable\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(buttonProtocolsMainPanel != null) {"fx:id=\"buttonProtocolsMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonResultsMainPanel != null) {"fx:id=\"buttonResultsMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonShowTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonShowTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonSkipEditTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonSkipEditTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonSwapStudentsInTimetablePaneEditTimetableSecond != null) {"fx:id=\"buttonSwapStudentsInTimetablePaneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonTimetableMainPanel != null) {"fx:id=\"buttonTimetableMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(buttonTrashCanMainPanel != null) {"fx:id=\"buttonTrashCanMainPanel\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(choiceBoxSaturdayPaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"choiceBoxSaturdayPaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(datePickerPaneForOtherPaneGenerateAllThird != null) {"fx:id=\"datePickerPaneForOtherPaneGenerateAllThird\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(datePickerPaneInfoForProtocolPaneGenerateAllThird != null) {"fx:id=\"datePickerPaneInfoForProtocolPaneGenerateAllThird\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(datePickerPaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"datePickerPaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewChooseGroupPaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"listViewChooseGroupPaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewChooseStudentToArchivationPaneArchivationStudentSecond != null) {"fx:id=\"listViewChooseStudentToArchivationPaneArchivationStudentSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive != null) {"fx:id=\"listViewSelectDayToDeleteStudentInArchivePaneSelectDayToDeleteStudentInArchive\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(listViewChooseTimetablePaneTimetableFirst != null) {"fx:id=\"listViewChooseTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewChooseToDeleteSecondPaneChooseToDeleteSecond != null) {"fx:id=\"listViewChooseToDeleteSecondPaneChooseToDeleteSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewDeleteStudentInArchivePaneDeleteStudentInArchive != null) {"fx:id=\"listViewDeleteStudentInArchivePaneDeleteStudentInArchive\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewMoveStudentFromDayPaneMoveStudentFromDay != null) {"fx:id=\"listViewMoveStudentFromDayPaneMoveStudentFromDay\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewMoveStudentGetIndexPaneMoveStudentGetIndex != null) {"fx:id=\"listViewMoveStudentGetIndexPaneMoveStudentGetIndex\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewMoveStudentToDayPaneMoveStudentToDay != null) {"fx:id=\"listViewMoveStudentToDayPaneMoveStudentToDay\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewShowTimetablePaneShowTimetable != null) {"fx:id=\"listViewShowTimetablePaneShowTimetable\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(listViewSwapStudentsDayOnePaneSwapStudentsDayOne != null) {"fx:id=\"listViewSwapStudentsDayOnePaneSwapStudentsDayOne\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo != null) {"fx:id=\"listViewSwapStudentsDayTwoPaneSwapStudentsDayTwo\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne != null) {"fx:id=\"listViewSwapStudentsIndexOnePaneSwapStudentsIndexOne\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo != null) {"fx:id=\"listViewSwapStudentsIndexTwoPaneSwapStudentsIndexTwo\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneArchiveStudentSecond != null) {"fx:id=\"paneArchivationStudentSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneChooseTimetablePaneTimetableFirst != null) {"fx:id=\"paneChooseTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSelectDayToDeleteStudentInArchive != null) {"fx:id=\"paneSelectDayToDeleteStudentInArchive\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(paneChooseToDeleteSecond != null) {"fx:id=\"paneChooseToDeleteSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneDeleteStudentInArchive != null) {"fx:id=\"paneDeleteStudentInArchive\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneEditTimetableSecond != null) {"fx:id=\"paneEditTimetableSecond\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneGenerateAllThird != null) {"fx:id=\"paneGenerateAllThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneInfoForProtocolPaneGenerateAllThird != null) {"fx:id=\"paneInfoForProtocolPaneGenerateAllThird\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(paneForOtherPaneGenerateAllThird != null) {"fx:id=\"paneForOtherPaneGenerateAllThird\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(paneMoveStudentFromDay != null) {"fx:id=\"paneMoveStudentFromDay\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneMoveStudentGetIndex != null) {"fx:id=\"paneMoveStudentGetIndex\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneMoveStudentToDay != null) {"fx:id=\"paneMoveStudentToDay\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneNewTimetablePaneTimetableFirst != null) {"fx:id=\"paneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneNotInfoPaneTimetableFirst != null) {"fx:id=\"paneNotInfoPaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSuccessGeneration != null) {"fx:id=\"paneSuccessGeneration\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSwapStudentsDayOne != null) {"fx:id=\"paneSwapStudentsDayOne\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSwapStudentsDayTwo != null) {"fx:id=\"paneSwapStudentsDayTwo\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSwapStudentsIndexOne != null) {"fx:id=\"paneSwapStudentsIndexOne\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneSwapStudentsIndexTwo != null) {"fx:id=\"paneSwapStudentsIndexTwo\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneShowTimetable != null) {"fx:id=\"paneShowTimetable\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(paneTimetableFirst != null) {"fx:id=\"paneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneTrashCan != null) {"fx:id=\"paneTrashCan\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(paneEmpty != null) {"fx:id=\"paneEmpty\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textAreaCommissionPaneAdditionalInfoProtocolsThird != null) {"fx:id=\"textAreaCommissionPaneAdditionalInfoProtocolsThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird != null) {"fx:id=\"textFieldGetSaveFolderPathPaneAdditionalInfoProtocolsThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textFieldGetSaveFolderPathPaneForOtherPaneGenerateAllThird != null) {"fx:id=\"textFieldGetSaveFolderPathPaneGenerateAllThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThird != null) {"fx:id=\"textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThird\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textSelectedGroupsHelperPaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"textSelectedGroupsHelperPaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textViewCountStudentsInDayPaneNewTimetablePaneTimetableFirst != null) {"fx:id=\"textViewCountStudentsInDayPaneNewTimetablePaneTimetableFirst\" was not injected: check your FXML file 'Main_W.fxml'." }
        assert(textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez != null) {"fx:id=\"textFieldHeadOfTheCommissionPaneAdditionalInfoProtocolsThirdRez\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez != null) {"fx:id=\"textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdFullRez\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez != null) {"fx:id=\"textFieldSecretaryOfTheCommissionPaneAdditionalInfoProtocolsThirdRez\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textHeadRez != null) {"fx:id=\"textHeadRez\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textSecretaryFullPackage != null) {"fx:id=\"textSecretaryFullPackage\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(textSecretaryRez != null) {"fx:id=\"textSecretaryRez\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(tvGroups != null) {"fx:id=\"tvGroups\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(buttonDeleteAllPaneTrashCan != null) {"fx:id=\"buttonDeleteAllPaneTrashCan\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(buttonDeleteAllStudentsPaneTrashCan != null) {"fx:id=\"buttonDeleteAllStudentsPaneTrashCan\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(buttonDeleteAllTeachersPaneTrashCan != null) {"fx:id=\"buttonDeleteAllTeachersPaneTrashCan\" was not injected: check your FXML file 'window_main.fxml'." }
        assert(buttonSortPaneShowTimetable != null) {"fx:id=\"buttonSortPaneShowTimetable\" was not injected: check your FXML file 'window_main.fxml'." }

    }
}