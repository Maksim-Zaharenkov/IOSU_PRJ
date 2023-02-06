package com.example.iosu_prj.view

//import javafx.geometry.Insets
//import javafx.geometry.Pos
//import javafx.scene.control.Button
//import javafx.scene.control.Label
//import javafx.scene.paint.Color
//import javafx.scene.text.Font
//import tornadofx.*
//
//class BottomBar(onSave: () -> Unit, onCancel: () -> Unit) : View() {
//
//    lateinit var label: Label
//    lateinit var okButton: Button
//    lateinit var cancelButton: Button
//
//    override val root = hbox {
//        maxHeight = 45.0
//        padding = Insets(10.0)
//        alignment = Pos.BASELINE_CENTER
//        stackpaneConstraints { alignment = Pos.BOTTOM_CENTER }
//        style { backgroundColor += Color.DARKSLATEGRAY }
//        label = label("Что сделать с данными?") {
//            font = Font(20.0)
//            textFill = Color.WHITE
//            hboxConstraints {
//                marginRight = 30.0
//            }
//        }
//        cancelButton = button("Отмена") {
//            style { backgroundColor += Color.LIGHTSKYBLUE }
//            hboxConstraints {
//                marginRight = 30.0
//            }
//            action {
//                onCancel()
//                this@hbox.isVisible = false
//            }
//        }
//        okButton = button("Сохранить") {
//            style { backgroundColor += Color.LIGHTSKYBLUE }
//            action {
//                onSave()
//                this@hbox.isVisible = false
//            }
//        }
//    }
//}