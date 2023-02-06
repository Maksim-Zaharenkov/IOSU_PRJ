//package com.agt.bsuirgek.test
//
//import javafx.event.EventTarget
//import javafx.geometry.Insets
//import javafx.geometry.Pos
//import javafx.scene.layout.HBox
//import javafx.scene.paint.Color
//import javafx.scene.text.Font
//import tornadofx.*
//
//class TemplateCardView : View() {
//
//    lateinit var parameters: HBox
//
//    override val root = vbox {
//        padding = Insets(50.0)
//        prefWidth = 800.0
//        prefHeight = 500.0
//        style { backgroundColor += Color.WHITE }
//
//        val card = vbox {
//            style {
//                backgroundColor += Color.valueOf("#eeeeee")
//                backgroundRadius += box(20.px)
//            }
//
//            borderpane {
//                style {
//                    backgroundColor += Color.valueOf("#11477B")
//                    backgroundRadius += box(15.px, 15.px, 0.px, 0.px)
//                }
//                center = label("List") {
//                    padding = Insets(5.0)
//                    font = Font(30.0)
//                    textFill = Color.WHITE
//                }
//            }
//
//            val elements = hbox {
//                padding = Insets(20.0)
//                hbox {
//                    style {
//                        backgroundColor += Color.valueOf("#3C72A6")
//                        backgroundRadius += box(10.px)
//                    }
//                    minHeight = 70.0
//                    maxHeight = 70.0
//                    minWidth = 100.0
//                    padding = Insets(10.0)
//                    alignment = Pos.CENTER_LEFT
//
//                    hboxConstraints {
//                        margin = Insets(.0, 20.0, .0, .0)
//                    }
//
//                    label("Teacher") {
//                        font = Font(20.0)
//                        textFill = Color.WHITE
//                        paddingRight = 10.0
//                    }
//
//                    parameters = hbox {
//                        param("Name", isStart = true)
//                        param(" ", true)
//                        param("Surname")
//                        param(" ", true)
//                    }
//
//                    borderpane {
//                        top = label("+") {
//                            style {
//                                backgroundColor += Color.valueOf("#BE430E")
//                                backgroundRadius += box(0.px, 5.px, 0.px, 0.px)
//                            }
//                            maxHeight = 23.0
//                            minHeight = 23.0
//                            minWidth = 30.0
//                            alignment = Pos.CENTER
//                            font = Font(15.0)
//                            textFill = Color.WHITE
//                            setOnMouseClicked {
//                                parameters.param("Andrey")
//                            }
//                        }
//                        center = label {
//                            maxHeight = 4.0
//                            minHeight = 4.0
//                            minWidth = 30.0
//                            style {
//                                backgroundColor += Color.valueOf("#ED6126")
//                            }
//                        }
//                        bottom = label("-") {
//                            style {
//                                backgroundColor += Color.valueOf("#BE430E")
//                                backgroundRadius += box(0.px, 0.px, 5.px, 0.px)
//                            }
//                            maxHeight = 23.0
//                            minHeight = 23.0
//                            minWidth = 30.0
//                            alignment = Pos.CENTER
//                            font = Font(15.0)
//                            textFill = Color.WHITE
//                            setOnMouseClicked {
//                                parameters.children.remove(parameters.children.last())
//                            }
//                        }
//                    }
//                }
//
//                borderpane {
//                    style {
//                        backgroundColor += Color.valueOf("#8CB4DA")
//                        backgroundRadius += box(10.px)
//                    }
//                    minHeight = 70.0
//                    minWidth = 70.0
//                    center = label("+") {
//                        padding = Insets(-4.0, .0, .0, .0)
//                        font = Font(35.0)
//                        textFill = Color.WHITE
//                    }
//                }
//            }
//        }
//    }
//}
//
////fun EventTarget.
//
//fun EventTarget.param(name: String, isSpace: Boolean = false, isStart: Boolean = false, isEnd: Boolean = false) =
//    borderpane {
//        style {
//            backgroundColor += Color.valueOf(if (isSpace) "#FF9C72" else "#FF844F")
//            if (isStart)
//                backgroundRadius += box(5.px, 0.px, 0.px, 5.px)
//            else if (isEnd)
//                backgroundRadius += box(0.px, 5.px, 5.px, 0.px)
//        }
//        val name = label(name) {
//            padding = Insets(5.0)
//            font = Font(20.0)
//            textFill = Color.WHITE
//        }
//        val text = textfield {
//            alignment = Pos.CENTER_LEFT
//        }
//        center = name
//        left = text
//        text.hide()
//        setOnMouseClicked {
//            if (isSpace) {
//                if (!left.isVisible) {
//                    text.text = name.text
//                    text.show()
//                } else {
//                    text.hide()
//                    name.text = text.text
//                }
//            }
//        }
//    }
