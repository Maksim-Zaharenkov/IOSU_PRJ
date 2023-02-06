package com.example.iosu_prj.view

//import com.bsuirgek.model.*
//import javafx.event.*
//import javafx.geometry.*
//import javafx.scene.control.*
//import javafx.scene.layout.*
//import javafx.scene.paint.*
//import javafx.util.converter.*
//import tornadofx.*
//
//fun <S, T> TableCell<S, T?>.center(element: T?) {
//    text = element.toString()
//    alignment = Pos.CENTER_LEFT
//    isEditable = true
//}
//
//fun <T> EventTarget.table(items: List<T>, op: TableView<T>.() -> Unit = {}) = tableview(items.observable()) {
//    gridpaneConstraints {
//        hGrow = Priority.ALWAYS
//        vGrow = Priority.ALWAYS
//    }
//    fixedCellSize = 40.0
//    columnResizePolicy = SmartResize.POLICY
//    isEditable = true
//    apply(op)
//}
//
//fun EventTarget.studentTableView(items: List<Student>, op: TableView<Student>.() -> Unit = {}) = table(items) {
//
//    column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//    column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//    column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//    column("Группа", Student::group).useTextField().cellDecorator { center(it) }
//    column("Средний балл", Student::average).useTextField(DoubleStringConverter()).cellDecorator { center(it) }
//    column("Оплата", Student::paymentPercent).useTextField(IntegerStringConverter()).cellDecorator { center(it) }
//    column("Сохранить", Student::save).useCheckbox()
//
//    apply(op)
//}
//
//fun EventTarget.studentINOTableView(items: List<Student>, op: TableView<Student>.() -> Unit = {}) = table(items) {
//
//    column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//    column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//    column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//    column("Оплата", Student::paymentPercent).useTextField(IntegerStringConverter()).cellDecorator { center(it) }
//    column("Номер", Student::stId).useTextField().cellDecorator { center(it) }
//    column("Сохранить", Student::save).useCheckbox()
//
//    apply(op)
//}
//
//fun EventTarget.studentThemeTableView(
//    items: List<Pair<Student, Pair<Teacher?, Teacher?>>>,
//    op: TableView<Student>.() -> Unit = {}
//) {
//
//    val t = items.flatMap {
//        listOf(it.second.first, it.second.second)
//    }.filterNotNull()
//    val s = t
//            .map { (it.surname ?: "") + (it.name ?: "") + (it.patronymic ?: "") }
//            .toSet()
//            .mapNotNull { its ->
//                t.find { (it.surname ?: "") + (it.name ?: "") + (it.patronymic ?: "") == its }
//            }
//
//    val someT = table(s) {
//        multiSelect(true)
//        gridpaneConstraints {
//            columnRowIndex(1, 0)
//            columnSpan = 1
//        }
//        column("Фамилия", Teacher::surname).useTextField().cellDecorator { center(it) }
//        column("Имя", Teacher::name).useTextField().cellDecorator { center(it) }
//        column("Отчество", Teacher::patronymic).useTextField().cellDecorator { center(it) }
//        column("Данные", Teacher::data).useTextField().cellDecorator { center(it) }
//        column("Сохранить", Teacher::save).useCheckbox()
//    }
//
//    table(items.map { it.first }) {
//        gridpaneConstraints {
//            columnRowIndex(0, 0)
//            columnSpan = 1
//        }
//        column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//        column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//        column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//        column("Тема", Student::theme).useTextField().cellDecorator { center(it) }
//        column("Сохранить", Student::save).useCheckbox()
//        column("Руководители", Student::save).cellFormat {
//            graphic = button("Показать") {
//                this@cellFormat.alignment = Pos.CENTER
//                textFill = Color.WHITE
//                style { backgroundColor += Color.ROYALBLUE }
//                action {
//                    val pair = items.find { it.first == rowItem }?.second ?: return@action
//                    someT.selectionModel.apply {
//                        clearSelection()
//                        select(pair.first)
//                        select(pair.second)
//                    }
//                }
//            }
//        }
//        apply(op)
//    }
//
//
//}
//
//fun EventTarget.teachersWithStudentsTableView(
//        items: List<Pair<Teacher, List<Student>>>
//) {
//
//    val t = items.map { it.first }
//    val s = items.flatMap { it.second }
//
//    val someT = table(s) {
//        multiSelect(true)
//        gridpaneConstraints {
//            columnRowIndex(1, 0)
//            columnSpan = 1
//        }
//        column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//        column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//        column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//        column("Сохранить", Student::save).useCheckbox()
//    }
//
//    table(t) {
//        gridpaneConstraints {
//            columnRowIndex(0, 0)
//            columnSpan = 1
//        }
//        column("Фамилия", Teacher::surname).useTextField().cellDecorator { center(it) }
//        column("Имя", Teacher::name).useTextField().cellDecorator { center(it) }
//        column("Отчество", Teacher::patronymic).useTextField().cellDecorator { center(it) }
//        column("Данные", Teacher::data).useTextField().cellDecorator { center(it) }
//        column("Сохранить", Teacher::save).useCheckbox()
//        column("Студенты", Teacher::save).cellFormat {
//            graphic = button("Показать") {
//                this@cellFormat.alignment = Pos.CENTER
//                textFill = Color.WHITE
//                style { backgroundColor += Color.ROYALBLUE }
//                action {
//                    val list = items.find { it.first == rowItem }?.second ?: return@action
//                    someT.selectionModel.apply {
//                        clearSelection()
//                        list.forEach { select(it) }
//                    }
//                }
//            }
//        }
//    }
//}
////
////fun EventTarget.miniStudentTableView(items: Map<Teacher, List<Student>>, op: TableView<Student>.() -> Unit = {}) =
////        table(emptyList<Student>()) {
////
////            gridpaneConstraints {
////                columnRowIndex(1, 0)
////                columnSpan = 2
////            }
////
////            column("Фамилия", Student::surname).remainingWidth().useTextField().cellDecorator { center(it) }
////            column("Имя", Student::name).useTextField().cellDecorator { center(it) }
////            column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
////            column("Сохранить", Student::save).useCheckbox()
////
////            apply(op)
////        }
////
////fun EventTarget.teacherTableView(items: List<Teacher>, op: TableView<Teacher>.() -> Unit = {}) = table(items) {
////
////    column("Фамилия", Teacher::surname).useTextField().cellDecorator { center(it) }
////    column("Имя", Teacher::name).useTextField().cellDecorator { center(it) }
////    column("Отчество", Teacher::patronymic).useTextField().cellDecorator { center(it) }
////    column("Данные", Teacher::data).remainingWidth().useTextField().cellDecorator { center(it) }
////    column("Сохранить", Teacher::save).useCheckbox()
////
////    apply(op)
////}
////
////fun EventTarget.tables(items: Map<Teacher, List<Student>>) {
////
////    val stdTable = table(emptyList<Student>()) {
////
////        gridpaneConstraints {
////            columnRowIndex(1, 0)
////            columnSpan = 2
////        }
////
////        column("Фамилия", Student::surname).remainingWidth().useTextField().cellDecorator { center(it) }
////        column("Имя", Student::name).useTextField().cellDecorator { center(it) }
////        column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
////        column("Сохранить", Student::save).useCheckbox().cellDecorator { alignment = Pos.CENTER }
////
////    }
////
////    table(items.keys.toList()) {
////        var preCell: Button? = null
////        column("Фамилия", Teacher::surname).useTextField().cellDecorator { center(it) }
////        column("Имя", Teacher::name).useTextField().cellDecorator { center(it) }
////        column("Отчество", Teacher::patronymic).useTextField().cellDecorator { center(it) }
////        column("Телефон", Teacher::phone).useTextField().cellDecorator { center(it) }
////        column("Данные", Teacher::data).remainingWidth().useTextField().cellDecorator { center(it) }
////        column("Сохранить", Teacher::save).useCheckbox()
////        column("Студенты", Teacher::save).cellFormat {
////            graphic = button("Показать") {
////                this@cellFormat.alignment = Pos.CENTER
////                textFill = Color.WHITE
////                style { backgroundColor += Color.ROYALBLUE }
////                action {
////                    preCell?.style { backgroundColor += Color.ROYALBLUE }
////                    preCell = this
////                    style { backgroundColor += Color.ROSYBROWN }
////                    stdTable.items = items[rowItem]?.observable()
////                }
////            }
////        }
////    }
////
////}