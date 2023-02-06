//package com.agt.bsuirgek.test
//
//import javafx.event.EventTarget
//import javafx.geometry.Insets
//import javafx.scene.layout.VBox
//import javafx.scene.paint.Color
//import javafx.scene.text.Font
//import javafx.scene.text.TextAlignment
//import tornadofx.*
//import java.io.FileInputStream
//
//8.27 x 11.69
//class DocumentView : View() {
//
//    val document by lazy { XWPFDocument(FileInputStream("/home/kotone/Documents/doc0.docx")) }
//    val section: CTSectPr by lazy { document.document.body.sectPr }
//
//    init {
//        primaryStage.isMaximized = true
//    }
//
//    fun EventTarget.page(op: VBox.() -> Unit = {}) = vbox {
//        section.pgSz.apply {
//            setMaxSize(w.pix, h.pix)
//            setMinSize(w.pix, h.pix)
//        }
//        section.pgMar.apply {
//            padding = Insets(top.pix, right.pix, bottom.pix, left.pix)
//        }
//        style {
//            backgroundColor += Color.WHITE
//        }
//        apply(op)
//    }
//
//    override val root = scrollpane(fitToWidth = true) {
//        vbox {
//            page {
//                vboxConstraints {
//                    margin = Insets(100.0)
//                    marginLeft = 400.0
//                }
//                var listStart = 0
//                document.bodyElements.forEach {
//                    when (it) {
//                        is XWPFParagraph -> {
//                            textflow {
//                                style {
//                                    borderColor += box(Color.RED)
//                                    borderWidth += box(2.px)
//                                    borderRadius += box(5.px)
//                                }
//                                if (it.runs.isEmpty()) text()
//                                else {
//                                    textAlignment = when (it.alignment) {
//                                        ParagraphAlignment.CENTER -> TextAlignment.CENTER
//                                        ParagraphAlignment.RIGHT -> TextAlignment.RIGHT
//                                        ParagraphAlignment.BOTH -> TextAlignment.JUSTIFY
//                                        else -> TextAlignment.LEFT
//                                    }
//                                    if (it.firstLineIndent != -1) {
//                                        label {
//                                            minWidth = it.firstLineIndent.pix
//                                        }
//                                    }
//                                    if (it.numID == null) listStart = 0
//                                    else {
//                                        listStart++
//                                        label("$listStart. ") {
//                                            font = Font("Times New Roman", 14.0)
//                                        }
//                                    }
//
//                                    it.runs.forEach {
//                                        val text = if(it.isCapitalized) it.text().toUpperCase() else it.text()
//                                        text(text) {
//                                            println("${it.fontName}  ${it.fontSize}  ${it.isCapitalized}")
//                                            font = Font("Times New Roman", 14.0)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        is XWPFTable -> {
//                            gridpane {
//                                it.rows.forEachIndexed { i, row ->
//                                    row.tableCells.forEachIndexed { j, cell ->
//                                        val t = textfield(cell.text) {
//
//                                        }
//                                                .gridpaneConstraints {
//                                                    columnRowIndex(j, i)
//                                                }
//                                        t.hide()
//                                        val l = label(cell.text) {
//                                            //                                            style {
////                                                borderColor += box(Color.BLACK)
////                                                borderWidth += box(2.px)
////                                            }
//                                            gridpaneConstraints {
//                                                columnRowIndex(j, i)
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}





//item("Отчёты") {
//    val chooser = vbox()
//
//    val ss = students().observable()
//    val ts = teachers().observable()
//
//    ss.forEach { st ->
//        st.leader = ts.find { it.id == st.leader?.toIntOrNull() }?.fio
//        st.reviewer = ts.find { it.id == st.reviewer?.toIntOrNull() }?.fio
//        st.consultant = ts.find { it.id == st.consultant?.toIntOrNull() }?.fio
//    }
//
//    val ssCopy = ss.toList()
//
//    chooser.apply {
//        alignment = Pos.TOP_CENTER
//        label("Выберите документ:") { font = Font.font(20.0) }
//        hbox {
//            maxWidth = 500.0
//            maxHeight = 100.0
//
//            vboxConstraints {
//                marginTop = 10.0
//                alignment = Pos.CENTER
//            }
//
//            val filePath = label("Документ") {
//                hgrow = Priority.ALWAYS
//                maxWidth = Double.MAX_VALUE
//                minHeight = 30.0
//                padding = Insets(5.0, 0.0, 5.0, 10.0)
//                style {
//                    backgroundColor += Color.WHITE
//                    backgroundRadius += box(10.px)
//                }
//                hboxConstraints {
//                    marginRight = 20.0
//                }
//            }
//
//            button("Обзор") {
//                minHeight = 30.0
//                style {
//                    backgroundColor += Color.GAINSBORO
//                    backgroundRadius += box(10.px)
//                }
//                hboxConstraints {
//                    marginRight = 20.0
//                }
//                action {
//                    FileChooser().apply {
//                        extensionFilters += FileChooser.ExtensionFilter(
//                                "*.docx, *.xlsx",
//                                "*.docx", "*.xlsx"
//                        )
//                        showOpenDialog(FX.primaryStage)?.let {
//                            filePath.text = it.name
//                            writeController.choosedFile = it
//                        }
//                    }
//                }
//            }
//
//            button("Сгенерировать") {
//                minHeight = 30.0
//                style {
//                    backgroundColor += Color.GAINSBORO
//                    backgroundRadius += box(10.px)
//                }
//                action {
//                    val pattern = writeController.openFile()
//                    val p = pattern.second
//                }
//            }
//        }
//        val request: MutableList<List<Student>.() -> List<Student>> = mutableListOf()
//        hbox {
//            var filterText: TextField? = null
//            val fButton = button("+Фильтр") {
//                hboxConstraints { margin = Insets(5.0) }
//            }
//            val filters = combobox<String> {
//                hboxConstraints { margin = Insets(5.0) }
//                isVisible = false
//                items = listOf(
//                        "ОТМЕНА",
//                        "Фамилия",
//                        "Имя",
//                        "Отчество",
//                        "Группа",
//                        "Средний балл",
//                        "Оплата",
//                        "Номер",
//                        "Руководитель",
//                        "Рецензент",
//                        "Консультант"
//                ).observable()
//                valueProperty().addListener { o, old, new ->
//                    isVisible = false
//                    filterText?.isVisible = false
//                    fButton.isVisible = true
//
//                    when (new) {
//                        "Фамилия" -> request.add {
//                            filter {
//                                it.surname?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Имя" -> request.add { filter { it.name?.contains(filterText?.text ?: "") == true } }
//                        "Отчество" -> request.add {
//                            filter {
//                                it.patronymic?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Группа" -> request.add {
//                            filter {
//                                it.group?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Средний балл" -> request.add {
//                            filter {
//                                it.average?.toString()?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Оплата" -> request.add {
//                            filter {
//                                it.paymentPercent?.toString()?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Номер" -> request.add {
//                            filter {
//                                it.stId?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Руководитель" -> request.add {
//                            filter {
//                                it.leader?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Рецензент" -> request.add {
//                            filter {
//                                it.reviewer?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                        "Консультант" -> request.add {
//                            filter {
//                                it.consultant?.contains(
//                                        filterText?.text ?: ""
//                                ) == true
//                            }
//                        }
//                    }
//                }
//            }
//            filterText = textfield {
//                hboxConstraints { margin = Insets(5.0) }
//            }
//            fButton.action {
//                fButton.isVisible = false
//                filters.isVisible = true
//                filterText.isVisible = true
//            }
//
//            val sButton = button("+Сортировка") {
//                hboxConstraints { margin = Insets(5.0) }
//            }
//            val sorting = combobox<String> {
//                hboxConstraints { margin = Insets(5.0) }
//                isVisible = false
//                items = listOf(
//                        "ОТМЕНА",
//                        "Фамилия",
//                        "Имя",
//                        "Отчество",
//                        "Группа",
//                        "Средний балл",
//                        "Оплата",
//                        "Номер",
//                        "Руководитель",
//                        "Рецензент",
//                        "Консультант"
//                ).observable()
//                valueProperty().addListener { o, old, new ->
//                    isVisible = false
//                    sButton.isVisible = true
//                    when (new) {
//                        "Фамилия" -> request.add { sortedBy { it.surname } }
//                        "Имя" -> request.add { sortedBy { it.name } }
//                        "Отчество" -> request.add { sortedBy { it.patronymic } }
//                        "Группа" -> request.add { sortedBy { it.group } }
//                        "Средний балл" -> request.add { sortedBy { it.average } }
//                        "Оплата" -> request.add { sortedBy { it.paymentPercent } }
//                        "Номер" -> request.add { sortedBy { it.stId } }
//                        "Руководитель" -> request.add { sortedBy { it.leader } }
//                        "Рецензент" -> request.add { sortedBy { it.reviewer } }
//                        "Консультант" -> request.add { sortedBy { it.consultant } }
//                    }
//                }
//            }
//            sButton.action {
//                sButton.isVisible = false
//                sorting.isVisible = true
//            }
//            text("Разделить по") {
//                hboxConstraints { margin = Insets(5.0) }
//            }
//            textfield { hboxConstraints { margin = Insets(5.0) } }
//            button("Сброс") {
//                hboxConstraints { margin = Insets(5.0) }
//                action {
//                    ss.clear()
//                    ss.addAll(ssCopy)
//                }
//            }
//            button("Применить") {
//                hboxConstraints { margin = Insets(5.0) }
//                action {
//                    val result = request.fold(ss.toList()) { acc, f -> acc.f() }
//                    ss.removeAll { !result.contains(it) }
//                }
//            }
//        }
//        gridpane {
//            table(ss) {
//
//                column("Фамилия", Student::surname).useTextField().cellDecorator { center(it) }
//                column("Имя", Student::name).useTextField().cellDecorator { center(it) }
//                column("Отчество", Student::patronymic).useTextField().cellDecorator { center(it) }
//                column("Группа", Student::group).useTextField().cellDecorator { center(it) }
//                column("Средний балл", Student::average).useTextField(DoubleStringConverter())
//                        .cellDecorator { center(it) }
//                column("Оплата", Student::paymentPercent).useTextField(IntegerStringConverter())
//                        .cellDecorator { center(it) }
//                column("Номер", Student::stId).useTextField().cellDecorator { center(it) }
//                column("Руководитель", Student::leader).useTextField().cellDecorator { center(it) }
//                column("Рецензент", Student::reviewer).useTextField().cellDecorator { center(it) }
//                column("Консультант", Student::consultant).useTextField().cellDecorator { center(it) }
//            }
//        }
//    }
//}