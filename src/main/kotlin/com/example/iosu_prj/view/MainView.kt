package com.example.iosu_prj.view

//import com.bsuirgek.*
//import com.bsuirgek.model.*
//import com.bsuirgek.parser.*
//import javafx.geometry.*
//import javafx.scene.control.*
//import javafx.scene.layout.*
//import javafx.scene.paint.*
//import javafx.scene.text.*
//import javafx.stage.*
//import javafx.util.converter.*
//import tornadofx.*
//import java.io.*
//import kotlin.system.*
//
//
//class MainView : View() {
//    init {
//        primaryStage.isMaximized = true
//    }
//
//    private val templatesXLSX = listOf("Список ИТиУвТС(xlsx)", "Список ИНО(xlsx)")
//    private val templatesDOCX = listOf("Приказ(docx)", "Рецензенты(docx)")
//    private lateinit var choosedTemp: Pair<String, String>
//    private lateinit var choosedFile: File
//    private var choosedData: Pair<Int, Any> = -1 to Any()
//
//    fun getData(): Pair<Int, Map<String, List<ParseData>>> = when (choosedTemp) {
//        "xlsx" to templatesXLSX[0] -> 0 to TemplateXLSX.ITIU.parse(choosedFile.asXLSX())
//        "xlsx" to templatesXLSX[1] -> 1 to TemplateXLSX.INO.parse(choosedFile.asXLSX())
//        "docx" to templatesDOCX[0] -> 2 to TemplateDOCX.Prikaz.parse(choosedFile.asDOCX())
//        "docx" to templatesDOCX[1] -> 3 to TemplateDOCX.Rec.parse(choosedFile.asDOCX())
//        else -> throw Throwable("NOT_FOUND")
//    }
//
//    private lateinit var tableManager: GridPane
//    private lateinit var placeholder: HBox
//    override val root = drawer(Side.TOP) {
//        item("Считать данные", expanded = true) {
//
//            val onCancel = {
//                tableManager.isVisible = false
//                placeholder.isVisible = true
//            }
//
//            val onSave = {
//                onCancel()
//                save(choosedData)
//            }
//
//            val bar = BottomBar(onSave, onCancel).apply {
//                root.isVisible = false
//            }
//            stackpane {
//                tableManager = gridpane()
//                placeholder = hbox {
//                    alignment = Pos.CENTER
//                    vbox {
//                        alignment = Pos.CENTER
//                        label("Выберите документ и шаблон:") { font = Font.font(25.0) }
//                        hbox {
//                            maxWidth = 700.0
//
//                            vboxConstraints {
//                                marginTop = 50.0
//                                marginBottom = 30.0
//                                alignment = Pos.CENTER
//                            }
//
//                            val filePath = label(File("").absolutePath) {
//                                hgrow = Priority.ALWAYS
//                                maxWidth = Double.MAX_VALUE
//                                minHeight = 30.0
//                                padding = Insets(5.0, 0.0, 5.0, 10.0)
//                                style {
//                                    backgroundColor += Color.WHITE
//                                    backgroundRadius += box(10.px)
//                                }
//                                hboxConstraints {
//                                    marginRight = 20.0
//                                }
//                            }
//
//                            button("Обзор") {
//                                minHeight = 30.0
//                                style {
//                                    backgroundColor += Color.GAINSBORO
//                                    backgroundRadius += box(10.px)
//                                }
//                                action {
//                                    FileChooser().apply {
//                                        extensionFilters += FileChooser.ExtensionFilter(
//                                                "*.docx, *.xlsx",
//                                                "*.docx", "*.xlsx"
//                                        )
//                                        showOpenDialog(FX.primaryStage)?.let {
//                                            filePath.text = it.name
//                                            choosedFile = it
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        label("Шаблоны:")
//                        hbox {
//                            listview(templatesDOCX.observable()) {
//                                padding = Insets(5.0)
//                                fixedCellSize = 30.0
//                                prefHeight = 10.0 + (if (templatesDOCX.size > 5) 5 else templatesDOCX.size) * 30.0
//                                style {
//                                    backgroundRadius += box(10.px)
//                                    backgroundColor += Color.WHITE
//                                }
//                                hboxConstraints {
//                                    marginTop = 10.0
//                                    marginBottom = 20.0
//                                    marginRight = 20.0
//                                }
//
//                                onUserSelect(1) { choosedTemp = "docx" to it }
//                            }
//
//                            listview(templatesXLSX.observable()) {
//                                padding = Insets(5.0)
//                                fixedCellSize = 30.0
//                                prefHeight = 10.0 + (if (templatesXLSX.size > 5) 5 else templatesXLSX.size) * 30.0
//                                style {
//                                    backgroundRadius += box(10.px)
//                                    backgroundColor += Color.WHITE
//                                }
//                                hboxConstraints {
//                                    marginTop = 10.0
//                                    marginBottom = 20.0
//                                }
//
//                                onUserSelect(1) { choosedTemp = "xlsx" to it }
//                            }
//                        }
//                        button("Считать") {
//                            style {
//                                backgroundColor += Color.GAINSBORO
//                                backgroundRadius += box(10.px)
//                            }
//                            action {
//                                tableManager.isVisible = true
//                                placeholder.isVisible = false
//                                val (number, data) = getData()
//                                when (number) {
//                                    0 -> {
//                                        val students = data.flatMap {
//                                            it.value.fold(mutableListOf<Student>()) { acc, obj ->
//                                                if (obj.type == "Student") acc += Student(obj.params)
//                                                        .apply { group = it.key }
//                                                acc
//                                            }
//                                        }
//                                        choosedData = 0 to students
//                                        tableManager.studentTableView(students)
//                                        bar.root.isVisible = true
//                                    }
//                                    1 -> {
//                                        val students = data.flatMap {
//                                            it.value.fold(mutableListOf<Student>()) { acc, obj ->
//                                                if (obj.type == "Student") acc += Student(obj.params)
//                                                        .apply { group = it.key }
//                                                acc
//                                            }
//                                        }
//                                        choosedData = 1 to students
//                                        tableManager.studentINOTableView(students)
//                                        bar.root.isVisible = true
//                                    }
//                                    2 -> {
//                                        val students = data.flatMap {
//                                            it.value.fold(mutableListOf<Pair<Student, Pair<Teacher?, Teacher?>>>()) { acc, obj ->
//                                                if (obj.type == "Student") {
//                                                    val s = Student(obj.params).apply {
//                                                        isDiploma = it.key == "0"
//                                                    }
//                                                    val main =
//                                                            it.value.find { it.id == obj.id.removeSuffix("Student") + "main" }
//                                                    val rev =
//                                                            it.value.find { it.id == obj.id.removeSuffix("Student") + "rev" }
//                                                    acc += s to (main?.params?.let { Teacher(it) } to rev?.params?.let {
//                                                        Teacher(
//                                                            it
//                                                        )
//                                                    })
//                                                }
//                                                acc
//                                            }
//                                        }
//                                        choosedData = 2 to students
//                                        tableManager.studentThemeTableView(students)
//                                        bar.root.isVisible = true
//                                    }
//                                    3 -> {
//                                        val teachers = data.flatMap {
//                                            it.value.fold(mutableListOf<Pair<Teacher, List<Student>>>()) { acc, obj ->
//                                                if (obj.type == "Teacher") {
//                                                    val s = Teacher(obj.params)
//                                                    val i = obj.id.removeSuffix("r") + "List"
//                                                    val students = it.value
//                                                            .filter { it.id.startsWith(i) }
//                                                            .map { Student(it.params) }
//                                                            .filter { it.surname != null }
//                                                    acc += s to students
//                                                }
//                                                acc
//                                            }
//                                        }
//                                        choosedData = 3 to teachers
//                                        tableManager.teachersWithStudentsTableView(teachers)
//                                        bar.root.isVisible = true
//                                    }
//                                    else -> placeholder.isVisible = true
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            add(bar)
//        }
//
//        item("База данных") {
//            val st = students.observable()
//            val gs = groups.observable()
//            val ts = teachers.observable()
//            val ps = protocols.observable()
//            val tsm = ts.mapNotNull { it.fio }.sorted().observable()
//            val gsm = gs.mapNotNull { it.number }.sorted().observable()
//
//            val lts = ts.filter { it.isLeader == true }.mapNotNull { it.fio }.sorted().observable()
//            val rts = ts.filter { it.isReviewer == true }.mapNotNull { it.fio }.sorted().observable()
//            val cts = ts.filter { it.isConsultant == true }.mapNotNull { it.fio }.sorted().observable()
//
//            st.forEach { student ->
//                student.leaderFIO = ts.find { it.id == student.leader?.toIntOrNull() }?.fio
//                student.reviewerFIO = ts.find { it.id == student.reviewer?.toIntOrNull() }?.fio
//                student.consultantFIO = ts.find { it.id == student.consultant?.toIntOrNull() }?.fio
//            }
//
//            hbox {
//                maxHeight = 50.0
//                button("Новый студент") {
//                    action {
//                        st.add(Student(-1, "1НОВЫЙ СТУДЕНТ"))
//                    }
//                }
//                button("Новый преподователь") {
//                    action {
//                        ts.add(Teacher(-1, "1НОВЫЙ ПРЕПОДОВАТЕЛЬ"))
//                    }
//                }
//                button("Новая группа") {
//                    action {
//                        gs.add(Group(-1, "1НОВАЯ ГРУППА"))
//                    }
//                }
//                button("Сохранить всё") {
//                    hboxConstraints { marginLeft = 30.0 }
//                    action {
//                        st.forEach {
//                            println(measureTimeMillis {
//                                updateStudent(
//                                    it.id, it.copy(
//                                        leader = ts.find { t -> t.fio == it.leaderFIO }?.id.toString(),
//                                        reviewer = ts.find { t -> t.fio == it.reviewerFIO }?.id.toString(),
//                                        consultant = ts.find { t -> t.fio == it.consultantFIO }?.id.toString()
//                                    )
//                                )
//                            })
//                        }
//                        ts.forEach { updateTeacher(it.id, it) }
//                        gs.forEach { updateGroup(it.id, it) }
//                    }
//                }
//                button("Сохранить протоколы") {
//                    hboxConstraints { marginLeft = 30.0 }
//                    action { ps.forEach { updateProtocol(it.id, it) } }
//                }
//            }
//
//            tabpane {
//                tab("Студенты", GridPane()) {
//                    table(st) {
//                        column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//                        column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//                        column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//                        column("Фамилия(Р)", Student::surnameR).useTextField().cellDecorator { center(it) }
//                        column("Имя(Р)", Student::nameR).useTextField().cellDecorator { center(it) }
//                        column("Отчество(Р) ", Student::patronymicR).useTextField().cellDecorator { center(it) }
//                        column("Фамилия(Д)", Student::surnameD).useTextField().cellDecorator { center(it) }
//                        column("Тема", Student::theme).useTextField().cellDecorator { center(it) }
//                        column("Группа", Student::group).useComboBox(gsm).cellDecorator { center(it) }
//                        column("Средний балл", Student::average).useTextField(DoubleStringConverter())
//                                .cellDecorator { center(it) }
//                        column("Оплата", Student::paymentPercent).useTextField(IntegerStringConverter())
//                                .cellDecorator { center(it) }
//                        column("Отметка", Student::gekScore).useTextField(IntegerStringConverter()).cellDecorator { center(it) }
//                        column("Номер", Student::stId).useTextField().cellDecorator { center(it) }
//                        val lc = column("Руководитель", Student::leaderFIO).useComboBox(lts).apply { cellDecorator { center(it) } }
//                        val rc = column("Рецензент", Student::reviewerFIO).useComboBox(rts).apply { cellDecorator { center(it) } }
//                        val cc = column("Консультант", Student::consultantFIO).useComboBox(cts).apply { cellDecorator { center(it) } }
//
//                        column("Сохранить", Student::save).cellFormat {
//                            graphic = button("Сохранить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.ROYALBLUE }
//                                action {
//                                    updateStudent(
//                                        rowItem.id,
//                                        rowItem.copy(
//                                            leader = ts.find { it.fio == lc.getValue(rowItem).toString() }?.id.toString(),
//                                            reviewer = ts.find { it.fio == rc.getValue(rowItem).toString() }?.id.toString(),
//                                            consultant = ts.find { it.fio == cc.getValue(rowItem).toString() }?.id.toString()
//                                        )
//                                    )
//                                }
//                            }
//                        }
//
//                        column("Удалить", Student::save).cellFormat {
//                            graphic = button("Удалить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.INDIANRED }
//                                action {
//                                    removeStudent(rowItem.id)
//                                    st.remove(rowItem)
//                                }
//                            }
//                        }
//                    }
//                }
//                tab("Преподаватель", GridPane()) {
//                    table(ts) {
//                        column("Фамилия", Teacher::surname).useTextField().cellDecorator { center(it) }
//                        column("Имя", Teacher::name).useTextField().cellDecorator { center(it) }
//                        column("Отчество", Teacher::patronymic).useTextField().cellDecorator { center(it) }
//                        column("Фамилия(Р)", Teacher::surnameR).useTextField().cellDecorator { center(it) }
//                        column("Данные", Teacher::data).useTextField().cellDecorator { center(it) }
//                        column("Руководитель", Teacher::isLeader).useCheckbox()
//                        column("Рецензент", Teacher::isReviewer).useCheckbox()
//                        column("Консультант", Teacher::isConsultant).useCheckbox()
//                        column("Комиссия", Teacher::isInCommission).useCheckbox()
//
//                        column("Сохранить", Teacher::save).cellFormat {
//                            graphic = button("Сохранить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.ROYALBLUE }
//                                action {
//                                    updateTeacher(rowItem.id, rowItem)
//                                    if (rowItem.id == -1)
//                                        tsm.add(rowItem.fio)
//                                }
//                            }
//                        }
//
//                        column("Удалить", Teacher::save).cellFormat {
//                            graphic = button("Удалить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.INDIANRED }
//                                action {
//                                    removeTeacher(rowItem.id)
//                                    ts.remove(rowItem)
//                                }
//                            }
//                        }
//                    }
//                }
//                tab("Группы", GridPane()) {
//                    table(gs) {
//                        column("Номер", Group::number).useTextField().cellDecorator { center(it) }
//
//                        column("Сохранить", Group::save).cellFormat {
//                            graphic = button("Сохранить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.ROYALBLUE }
//                                action {
//                                    updateGroup(rowItem.id, rowItem)
//                                    if (rowItem.id == -1)
//                                        gsm.add(rowItem.number)
//                                }
//                            }
//                        }
//
//                        column("Удалить", Group::save).cellFormat {
//                            graphic = button("Удалить") {
//                                this@cellFormat.alignment = Pos.CENTER
//                                textFill = Color.WHITE
//                                style { backgroundColor += Color.INDIANRED }
//                                action {
//                                    removeGroup(rowItem.id)
//                                    gs.remove(rowItem)
//                                }
//                            }
//                        }
//                    }
//                }
//                tab("Протоколы", GridPane()) {
//                    table(ps) {
//                        column("Номер", Protocol::number).useTextField(IntegerStringConverter()).cellDecorator { center(it) }
//                        column("День", Protocol::date).useTextField().cellDecorator { center(it) }
//                        column("Студент", Protocol::student).useComboBox(st).cellDecorator { center(it) }
//                    }
//                }
//            }
//            hbox {
//                maxHeight = 50.0
//                textfield("Студенты: ${st.size}")
//                textfield("Преподаватели: ${ts.size}").hboxConstraints { marginLeft = 30.0 }
//                textfield("Группы: ${gs.size}").hboxConstraints { marginLeft = 30.0 }
//            }
//        }
//        item("Режим хакера") {
//            vbox {
//                var textArea: TextArea? = null
//                hbox {
//                    val textField = textfield {
//                        minWidth = 300.0
//                    }
//
//                    button("HACK") {
//                        action {
//                            textArea?.text = textArea?.text + "\n" + execute(textField.text)
//                            textField.text = ""
//                        }
//                    }
//                }
//                scrollpane {
//                    minHeight = 700.0
//                    textArea = textarea {
//                        minHeight = Double.MAX_VALUE
//                    }
//                }
//            }
//        }
//    }
//}