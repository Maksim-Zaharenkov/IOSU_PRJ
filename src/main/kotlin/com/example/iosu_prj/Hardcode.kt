package com.example.iosu_prj

import com.example.iosu_prj.model.*
import com.example.iosu_prj.parser.asDOCX
import com.example.iosu_prj.parser.asXLSX
import com.example.iosu_prj.parser.rawPatterns
import com.example.iosu_prj.test.*
import javafx.application.Application
import javafx.collections.ObservableList
import org.apache.poi.ss.util.CellRangeAddressList
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper
import org.apache.poi.xwpf.usermodel.*
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign.CENTER
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl
import padeg.lib.Padeg
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

val basePath = File("").absolutePath + "/"
var savePath = ""

var date: String = SimpleDateFormat("dd.MM.yyyy").format(Date())

var showList = ""
val studList = mutableListOf<String>()
val studDayList = mutableListOf<String>()
fun getDay(index: Int) {
    studDayList.clear()
    days[index].second.forEach {
        studDayList.add("${it.fio}\t\t\t${it.group}")
    }
}

var indexDay1 = 0
var indexStudent1 = 0
var indexDay2 = 0
var indexStudent2 = 0


var headOfTheCommission = ""
var secretaryOfTheCommission = ""
var commission = mutableListOf<String>()

//val ks = teachers.filter { it.isInCommission == true }.sortedBy { it.fio }

val ks = mutableListOf<String>()

val es = mutableMapOf<String, String>()

val grPriors = mutableMapOf<String, Int>()

val groupsList = mutableListOf<String>()
val selectedGroups = mutableListOf<String>()
var mainList = listOf<Student>()

var noArchiveList = studentsNotInArchive()
val listIdNotInArchive = mutableListOf<Int>()

//val listAllStudents = mutableListOf<Student>()
//val listAllTeachers = mutableListOf<Teacher>()
//val listAllGroups = mutableListOf<Group>()

var confirmText = ""
var check = 0
var code = 0

fun main() {
    val database = db
    Application.launch(MainWindow::class.java)
//    println("Введите команду")
//    while (true) {
//        val command = readLine()
//        if (command == "exit") return
//        exec(command)
//    }
}

fun exec(command: String?) {
    val commands = command?.split(' ')
    if (commands.isNullOrEmpty()) {
        println("Нет команды")
        return
    }
    try {
        when (commands[0]) {
//            "loadFile"      -> {
//                loadFile()
//            }
            "full"          -> {
//                val folderName = commands[1]

                File("$savePath/proto").mkdirs()
                File("$savePath/protoNOT").mkdirs()
                listOf(
//                    "date 20.11.2022",
//                    "load",
//                    "show",
                    "write $savePath/rasp.docx",
                    "writeOff $savePath/raspOff.docx",
                    "writeGec proto_rasp_form/gec.docx $savePath/gec.docx",
                    "writeREZU proto_rasp_form/rezu.docx $savePath/rez-ucheb-12.docx",
                    "writeREZD proto_rasp_form/rezd.docx $savePath/rez-dekanat-14.docx",
                    "proto proto_rasp_form/p.xlsx $savePath/proto",
                    "proto2 proto_rasp_form/p.xlsx $savePath/protoNOT"
                ).forEach(::exec)
            }
            "date"          -> {
                val d = commands[1]
                date = inputDateFormat.format(inputDateFormat.parse(d))
                println("Дата установлена: $date")
            }
            "generate"      -> {
                val count = commands[1].toInt()
                val saturday = commands[2].toBoolean()
                println("Генерация расписания по $count человек")
                days = rasp(count, saturday)
                println("Генерация окончена")
            }
            "load"          -> {
                println("Загрузка из БД")
                days = transaction {
                    Protocols.selectAll()
                        .map {
                            Protocols.run {
                                Protocol(
                                    students.find { st -> st.id == it[studentId].value }!!,
                                    it[realDate],
                                    it[number]
                                )
                            }
                        }
                        .sortedBy { it.number }
                        .groupBy { it.date }
                        .mapValues { it.value.map { it.student }.filterNotNull().toMutableList() }
                        .mapKeys { input2DateFormat.parse(it.key) }
                        .toList()
                        .sortedBy { it.first }
                        .toMutableList()
                }
                println("Загрузка из БД окончена")
            }
            "save"          -> {
                println("Сохранение в БД")
                var n = 1
                val realNumbers = days.foldIndexed(mutableListOf<Protocol>()) { i, list, value ->
                    list += value.second.map { Protocol(it, value.first.pair().second, n++) }
                    list
                }
                transaction {
                    realNumbers.forEach { obj ->
                        val c = Protocols.select { Protocols.studentId eq obj.student?.id }.count()
                        if (c == 0) {
                            Protocols.insert {
                                it[studentId] =
                                    Students.select { Students.id eq obj.student?.id }.map { it[Students.id] }
                                        .first()
                                it[number] = obj.number!!
                                it[realDate] = obj.date!!
                            }
                            println("${obj.student?.fio} сохранён")
                        } else {
                            Protocols.update({ Protocols.studentId eq obj.student?.id }) {
                                it[number] = obj.number!!
                                it[realDate] = obj.date!!
                            }
                            println("${obj.student?.fio} обновлён")
                        }
                    }
                }
                println("Сохранение в БД окончено")
            }
            "write"         -> {
                println("Генерация расписания")
                write(commands[1], days)
                println("Генерация окончена")
            }
            "writeOff"      -> {
                println("Генерация расписания")
                write(commands[1], offDays())
                println("Генерация окончена")
            }
            "writeGec"      -> {
                writeGec(commands[1], commands[2], days)
            }
            "writeREZD"     -> {
                writeREZD(commands[1], commands[2], realProtos())
            }
            "sort"          -> {
                val n = commands[1].toInt()
                days[n].second.sortWith(Comparator { s1, s2 ->
                    val i = grPriors[s1.group]?.compareTo(grPriors[s2.group] ?: 8) ?: 0
                    if (i != 0) return@Comparator i
                    val i1 = s2.average?.compareTo(s1.average ?: 0.0) ?: 0
                    if (i1 != 0) return@Comparator i1
                    s1.fio.compareTo(s2.fio)
                })
                println(days[n].second)
                println("День $n отсортирован")
            }
            "writeREZD1"    -> {
                val i = commands[3].toInt() - 1
                writeREZD1(commands[1], commands[2], realProtos().sortedBy { it.first }[i])
            }
            "writeGec1"     -> {
                val i = commands[3].toInt() - 1
                writeGec1(commands[1], commands[2], days[i])
            }
            "writeREZU1"    -> {
                val i = commands[3].toInt() - 1
                writeREZU1(commands[1], commands[2], offProtos()[i])
            }
            "writeREZU"     -> {
                writeREZU(commands[1], commands[2], offProtos())
            }
            "proto"         -> {
                println("Генерация протоколов")
                writeProtocols(commands[1], commands[2], offDays())
                println("Генерация окончена")
            }
            "proto2"        -> {
                println("Генерация протоколов")
                writeProtocols(commands[1], commands[2], days)
                println("Генерация окончена")
            }
            "proto1"        -> {
                val i = commands[3].toInt()
                println("Генерация протокола $i")
                val f = offDays()[i - 1]
                proto(commands[1], commands[2], f.first.pair().second, f.second, i - 1)
                println("Генерация протокола $i окончена")
            }
            "move"          -> {
                check = 1
                val dayFrom = commands[1].toInt()
                val stIndex = commands[2].toInt()
                val dayTo = commands[3].toInt()
                println(
                    "Студент: ${days[dayFrom].second[stIndex].fio} из ${days[dayFrom].first.pair().second} перемещён в ${
                        days[dayTo].first
                        .pair().second}"
                )
                val st = days[dayFrom].second.removeAt(stIndex)
                days[dayTo].second.add(st)

                shiftStudents(dayFrom)
            }
            "deleteFromTimetable"          -> {
                check = 1
                val dayFrom = commands[1].toInt()
                val stIndex = commands[2].toInt()

                println("test")

                days[dayFrom].second.removeAt(stIndex)

                shiftStudents(dayFrom)
            }

            "returnToTimetable"          -> {
                check = 1
                val student = commands[1].toInt()

                days[days.size - 1].second.add(mainList[student])
            }
            "swap"          -> {
                check = 1
                val dayFrom = commands[1].toInt()
                val stIndex = commands[2].toInt()
                val dayTo = commands[3].toInt()
                val stIndex2 = commands[4].toInt()
                println(
                    "Студент: ${days[dayFrom].second[stIndex].fio} из ${
                        days[dayFrom].first.pair()
                        .second} заменён студентом ${days[dayTo].second[stIndex2].fio} из ${days[dayTo].first.pair().second}"
                )
                val st = days[dayFrom].second.removeAt(stIndex)
                val st2 = days[dayTo].second.removeAt(stIndex2)
                days[dayTo].second.add(st)
                days[dayFrom].second.add(st2)
            }
            "show"          -> {
                studList.clear()
                days.forEachIndexed { index, it ->
                    studList.add("${index + 1} ДЕНЬ: ${it.first.pair().second}\n${it.second.mapIndexed { i, s -> (i + 1) to s }.joinToString(
                            "\n",
                            "",
                            "\n"
                        ) { "${it.first}. ${it.second.fio}\t\t\t${it.second.group}" }}")
                }
            }
            "showStudents"  -> {
                students.forEachIndexed { index, it ->
                    println("${index + 1}) ${it.fio} ${it.group}")
                }
            }
            "deleteStudent" -> {
                val number = commands[1].toInt()
                removeStudent(students[number].id)
            }
            "read"          -> {
                val tempPath = commands[1]
                val filePath = commands[2]
                read(tempPath, filePath)
            }
            else            -> println("Нет такой команды")
        }
    } catch (e: Exception) {
        println("Произошла ошибка при обработке команды: $e")
        e.printStackTrace()
    }
}

fun shiftStudents(dayFrom: Int) {
    var i = dayFrom
    while (i < days.size - 1) {
        val st = days[i + 1].second.removeAt(0)
        days[i].second.add(st)
        i++
    }
}

val marks = listOf("один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять", "десять")

val wordMarks = listOf("отличную", "хорошую", "удовлетворительную", "неудовлетворительную")

var days: MutableList<Pair<Date, MutableList<Student>>> = mutableListOf()

fun read(tempPath: String, filePath: String) {
    val temp = (basePath + tempPath).asXLSX()
    val file = (basePath + filePath).asXLSX()

    val namePosition = temp.getSheetAt(0).rawPatterns.find { it.second == "fioFULLR" } ?: return
    val markPosition = temp.getSheetAt(1).rawPatterns.find { it.second == "dropNumbers" } ?: return

    val names = file.groupBy { it.sheetName.replace("_", "").toInt() }

    names.forEach {
        val (index, sheets) = it
        val mark: String
        val fio: String
        if (sheets[0].sheetName.contains("_")) {
            mark = sheets[0].getRow(markPosition.first.first).getCell(markPosition.first.second).stringCellValue
            fio = sheets[1].getRow(namePosition.first.first).getCell(namePosition.first.second).stringCellValue
        } else {
            mark = sheets[1].getRow(markPosition.first.first).getCell(markPosition.first.second).stringCellValue
            fio = sheets[0].getRow(namePosition.first.first).getCell(namePosition.first.second).stringCellValue
        }
        val array = fio.split(' ')
        val intMark = marks.indexOf(mark.toLowerCase())

        updateMark(
            Student(
                id = -1,
                surnameR = array[0],
                nameR = array[1],
                patronymicR = array[2],
                gekScore = if (intMark == -1) null else intMark
            )
        )
    }

}

fun proto(filePath: String, folderPath: String, date: String, students: MutableList<Student>, dayNumber: Int) {

    val protocol = (basePath + filePath).asXLSX()
    val output = "$folderPath/ПРОТОКОЛ-$date.xlsx"

    students.forEachIndexed { n, student ->
        val number = dayNumber * 12 + n
        val sheet = protocol.cloneSheet(0, (number + 1).toString())
        val sheet2 = protocol.cloneSheet(1, "_${number + 1}")
        val teachers = sheet.rawPatterns.filter { it.second == "dropDown" }
        val numbers = sheet.rawPatterns.filter { it.second == "dropNumbers" }
        val numbers2 = sheet2.rawPatterns.filter { it.second == "dropNumbers" }
        val wordMarks2 = sheet2.rawPatterns.filter { it.second == "dropWordMarks" }
        sheet.rawPatterns.forEach {
            sheet.getRow(it.first.first).getCell(it.first.second)
                .setCellValue(it.second.replacePatternWith(number, date, student))
        }

        sheet2.rawPatterns.forEach {
            sheet2.getRow(it.first.first).getCell(it.first.second)
                .setCellValue(it.second.replacePatternWith(number, date, student))
        }
        val vHelper = XSSFDataValidationHelper(sheet)
        val vHelper2 = XSSFDataValidationHelper(sheet2)
        val teachersConstraint = vHelper.createExplicitListConstraint(ks.map { it }.toTypedArray())
        val numbersConstraint =
            vHelper.createExplicitListConstraint(marks.map { it.toUpperCase() }.reversed().toTypedArray())
        val numbersConstraint2 =
            vHelper2.createExplicitListConstraint(marks.map { it.toUpperCase() }.reversed().toTypedArray())
        val wordMarksConstraint2 =
            vHelper2.createExplicitListConstraint(wordMarks.map { it }.reversed().toTypedArray())

        teachers.forEach {
            sheet.addValidationData(
                vHelper.createValidation(
                    teachersConstraint,
                    CellRangeAddressList(it.first.first, it.first.first, it.first.second, it.first.second)
                ).apply { suppressDropDownArrow = true }
            )
        }
        numbers.forEach {
            sheet.addValidationData(
                vHelper.createValidation(
                    numbersConstraint,
                    CellRangeAddressList(it.first.first, it.first.first, it.first.second, it.first.second)
                ).apply { suppressDropDownArrow = true }
            )
        }
        numbers2.forEach {
            sheet2.addValidationData(
                vHelper2.createValidation(
                    numbersConstraint2,
                    CellRangeAddressList(it.first.first, it.first.first, it.first.second, it.first.second)
                ).apply { suppressDropDownArrow = true }
            )
        }
        wordMarks2.forEach {
            sheet2.addValidationData(
                vHelper2.createValidation(
                    wordMarksConstraint2,
                    CellRangeAddressList(it.first.first, it.first.first, it.first.second, it.first.second)
                ).apply { suppressDropDownArrow = true }
            )
        }
    }
    protocol.removeSheetAt(0)
    protocol.removeSheetAt(0)
    val names = protocol.map { it.sheetName }.sortedBy {
        it.replace("_", "").toInt() + if (it.contains('_')) 100 else 0
    }.withIndex()

    names.forEach {
        protocol.setSheetOrder(it.value, it.index)
    }

    protocol.write(FileOutputStream(output))

}

fun offDays(): MutableList<Pair<Date, MutableList<Student>>> {

    val startDate = getInstance().apply {
        time = inputDateFormat.parse(date)
        firstDayOfWeek = MONDAY
    }

    return protocols
        .sortedBy { it.number }
        .chunked(12)
        .map { it.mapNotNull { it.student } }
        .map {
            if (startDate[DAY_OF_WEEK] == SUNDAY) startDate.add(DAY_OF_MONTH, 1)
            val time = startDate.time
            startDate.add(DAY_OF_MONTH, 1)
            time to it.toMutableList()
        }.toMutableList()
}

fun offProtos(): MutableList<Pair<Date, MutableList<Protocol>>> {

    val startDate = getInstance().apply {
        time = inputDateFormat.parse(date)
        firstDayOfWeek = MONDAY
    }

    return protocols
        .sortedBy { it.number }
        .chunked(12)
        .map {
            if (startDate[DAY_OF_WEEK] == SUNDAY) startDate.add(DAY_OF_MONTH, 1)
            val time = startDate.time
            startDate.add(DAY_OF_MONTH, 1)
            time to it.toMutableList()
        }.toMutableList()
}

fun realProtos(): MutableList<Pair<Date, MutableList<Protocol>>> {
    return protocols
        .groupBy { it.date }
        .mapKeys { input2DateFormat.parse(it.key) }
        .mapValues { it.value.toMutableList() }
        .toList().toMutableList()
}

val outputDateFormat = SimpleDateFormat("EEEE dd MMMM yyyy", Locale("ru"))
val inputDateFormat = SimpleDateFormat("dd.MM.yyyy")
val input2DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
val input3DateFormat = SimpleDateFormat("dd.MM")

fun rasp(
    chunkCount: Int,
    skipSaturday: Boolean
): MutableList<Pair<Date, MutableList<Student>>> {

    val startDate = getInstance().apply {
        time = inputDateFormat.parse(date)
        firstDayOfWeek = MONDAY
    }
    return studentsForRasp(selectedGroups)
        .sortedWith(Comparator { s1, s2 ->
            val i = grPriors[s1.group]?.compareTo(grPriors[s2.group] ?: 8) ?: 0
            if (i != 0) return@Comparator i
            val i1 = s2.average?.compareTo(s1.average ?: 0.0) ?: 0
            if (i1 != 0) return@Comparator i1
            s1.fio.compareTo(s2.fio)
        })
        .chunked(chunkCount)
        .map {
            if (skipSaturday && startDate[DAY_OF_WEEK] == SATURDAY) startDate.add(DAY_OF_MONTH, 2)
            if (startDate[DAY_OF_WEEK] == SUNDAY) startDate.add(DAY_OF_MONTH, 1)
            val time = startDate.time
            startDate.add(DAY_OF_MONTH, 1)
            time to it.sortedByDescending { it.average }.toMutableList()
        }.toMutableList()
}

fun Date.pair() = outputDateFormat.format(this).run {
    substringBefore(' ').toUpperCase() to substringAfter(' ')
}

fun XWPFTableRow.copy(table: XWPFTable) = XWPFTableRow(CTRow.Factory.parse(ctRow.newInputStream()), table)
fun XWPFTable.copy(doc: XWPFDocument) = XWPFTable(CTTbl.Factory.parse(ctTbl.newInputStream()), doc)

fun XWPFDocument.cloneParagraph(source: XWPFParagraph): XWPFParagraph {
    val clone = createParagraph()
    val pPr = if (clone.ctp.isSetPPr) clone.ctp.pPr else clone.ctp.addNewPPr()
    pPr.set(source.ctp.pPr)
    source.runs.forEach {
        val cln = clone.createRun()
        val rPr = if (cln.ctr.isSetRPr) cln.ctr.rPr else cln.ctr.addNewRPr()
        rPr.set(it.ctr.rPr)
        cln.setText(it.getText(0))
    }
    return clone
}

fun writeGec1(file: String, filePath: String, day: Pair<Date, MutableList<Student>>) {
    val doc = (basePath + file).asDOCX()
    doc.cloneParagraph(doc.paragraphs[0])
    doc.cloneParagraph(doc.paragraphs[1]).runs[0].setText(input2DateFormat.format(day.first))
    val t = doc.tables[0].copy(doc)
    day.second.forEachIndexed { stIndex, student ->
        t.addRow(t.rows[2].copy(t).apply {
            tableCells.forEachIndexed { index, cell ->
                cell.text = when (index) {
                    0    -> (stIndex + 1).toString()
                    1    -> student.fioFull
                    2    -> student.average?.toString() ?: ""
                    3    -> student.group ?: ""
                    else -> ""
                }
            }
        })
    }
    t.removeRow(2)
    doc.createTable()
    doc.setTable(1, t)
    doc.createParagraph()
    doc.createParagraph()
    doc.removeBodyElement(0)
    doc.removeBodyElement(0)
    doc.removeBodyElement(0)
    doc.write(FileOutputStream(filePath))
}

fun writeGec(file: String, filePath: String, days: MutableList<Pair<Date, MutableList<Student>>>) {
    val doc = (basePath + file).asDOCX()
    days.forEachIndexed { index, it ->
        doc.cloneParagraph(doc.paragraphs[0]).isPageBreak = true
        doc.cloneParagraph(doc.paragraphs[1]).runs[0].setText(input2DateFormat.format(it.first))
        val t = doc.tables[0].copy(doc)
        it.second.forEachIndexed { stIndex, student ->
            t.addRow(t.rows[2].copy(t).apply {
                tableCells.forEachIndexed { index, cell ->
                    cell.text = when (index) {
                        0    -> (stIndex + 1).toString()
                        1    -> student.fioFull
//                        2    -> student.average?.toString() ?: ""
                        2    -> student.group ?: ""
                        else -> ""
                    }
                }
            })
        }
        t.removeRow(2)
        doc.createTable()
        doc.setTable(index + 1, t)
        doc.createParagraph()
        doc.createParagraph()

    }
    doc.removeBodyElement(0)
    doc.removeBodyElement(0)
    doc.removeBodyElement(0)
    doc.write(FileOutputStream(filePath))
}

fun writeREZD1(file: String, filePath: String, day: Pair<Date, MutableList<Protocol>>) {
    val doc = (basePath + file).asDOCX()

    val startDate = getInstance().apply {
        time = inputDateFormat.parse(date)
        firstDayOfWeek = MONDAY
    }

    val dates = students.chunked(12).map {
        if (startDate[DAY_OF_WEEK] == SUNDAY) startDate.add(DAY_OF_MONTH, 1)
        val time = startDate.time
        startDate.add(DAY_OF_MONTH, 1)
        input3DateFormat.format(time)
    }

    (0..8).forEach { i ->
        val p = doc.cloneParagraph(doc.paragraphs[i])
        if (i == 7) p.runs.last().setText(input2DateFormat.format(day.first))
    }
    val t = doc.tables[0].copy(doc)
    day.second.sortedBy { it.number }.forEachIndexed { stIndex, st ->
        val student = st.student ?: return@forEachIndexed
        t.addRow(t.rows[1].copy(t).apply {
            tableCells.forEachIndexed { index, cell ->
                cell.text = when (index) {
                    0    -> (stIndex + 1).toString() + "."
                    1    -> student.fioFull
                    2    -> student.group ?: ""
                    3    -> "${st.number} от ${dates[((st.number ?: 1) - 1) / 12]}"
                    4    -> (if (student.gekScore == null) "" else marks[student.gekScore ?: 0]).toUpperCase()
                    else -> ""
                }
            }
        })
    }
    t.removeRow(1)
    doc.createTable()
    doc.setTable(1, t)
    (9..13).forEach { i ->
        val p = doc.cloneParagraph(doc.paragraphs[i])
        if (i == 13) p.runs.last().setText(input2DateFormat.format(day.first))
    }
    (0..13).forEach { doc.removeBodyElement(0) }
    doc.write(FileOutputStream(filePath))
}

fun writeREZD(file: String, filePath: String, days: MutableList<Pair<Date, MutableList<Protocol>>>) {
    val doc = (basePath + file).asDOCX()

    val startDate = getInstance().apply {
        time = inputDateFormat.parse(date)
        firstDayOfWeek = MONDAY
    }

    val dates = students.chunked(12).map {
        if (startDate[DAY_OF_WEEK] == SUNDAY) startDate.add(DAY_OF_MONTH, 1)
        val time = startDate.time
        startDate.add(DAY_OF_MONTH, 1)
        input3DateFormat.format(time)
    }

    var page = 0
    days.sortedBy { it.first }.forEachIndexed { index, date ->
        (0..8).forEach { i ->
            val p = doc.cloneParagraph(doc.paragraphs[i])
            if (i == 0 && page != 0) p.isPageBreak = true
            if (i == 7) p.runs.last().setText(input2DateFormat.format(date.first))
        }
        val t = doc.tables[0].copy(doc)
        date.second.sortedBy { it.number }.forEachIndexed { stIndex, st ->
            val student = st.student ?: return@forEachIndexed
            t.addRow(t.rows[1].copy(t).apply {
                tableCells.forEachIndexed { index, cell ->
                    cell.text = when (index) {
                        0    -> (stIndex + 1).toString() + "."
                        1    -> student.fioFull
                        2    -> student.group ?: ""
                        3    -> "${st.number} от ${dates[((st.number ?: 1) - 1) / 12]}"
                        4    -> (if (student.gekScore == null) "" else marks[student.gekScore ?: 0]).toUpperCase()
                        else -> ""
                    }
                }
            })
        }
        t.removeRow(1)
        doc.createTable()
        doc.setTable(index + 1, t)
        (9..13).forEach { i ->
            val p = doc.cloneParagraph(doc.paragraphs[i])
            if (i == 11) p.runs.last().setText("($secretaryOfTheCommission)")
            if (i == 13) p.runs.last().setText(input2DateFormat.format(date.first))
        }
        page++
    }
    (0..14).forEach { doc.removeBodyElement(0) }
    doc.write(FileOutputStream(filePath))
}

fun writeREZU1(file: String, filePath: String, day: Pair<Date, MutableList<Protocol>>) {
    val doc = (basePath + file).asDOCX()
    (0..8).forEach { i ->
        val p = doc.cloneParagraph(doc.paragraphs[i])
        if (i == 7) p.runs.last().setText(input2DateFormat.format(day.first))
    }
    val t = doc.tables[0].copy(doc)
    day.second.sortedBy { it.student?.surname }.forEachIndexed { stIndex, st ->
        val student = st.student ?: return@forEachIndexed
        t.addRow(t.rows[1].copy(t).apply {
            tableCells.forEachIndexed { index, cell ->
                cell.text = when (index) {
                    0    -> (stIndex + 1).toString() + "."
                    1    -> student.fioFull
                    2    -> student.group ?: ""
                    3    -> "${st.number}"
                    4    -> (if (student.gekScore == null) "" else marks[student.gekScore ?: 0]).toUpperCase()
                    else -> ""
                }
            }
        })
    }
    t.removeRow(1)
    doc.createTable()
    doc.setTable(1, t)
    (9..13).forEach { i ->
        val p = doc.cloneParagraph(doc.paragraphs[i])
        if (i == 13) p.runs.last().setText(input2DateFormat.format(day.first))
    }
    (0..13).forEach { doc.removeBodyElement(0) }
    doc.write(FileOutputStream(filePath))
}

fun writeREZU(file: String, filePath: String, days: MutableList<Pair<Date, MutableList<Protocol>>>) {
    val doc = (basePath + file).asDOCX()

    var page = 0
    days.forEachIndexed { index, date ->
        (0..8).forEach { i ->
            val p = doc.cloneParagraph(doc.paragraphs[i])
            if (i == 0 && page != 0) p.isPageBreak = true
            if (i == 6) p.runs.last().setText(headOfTheCommission)
            if (i == 7) p.runs.last().setText(input2DateFormat.format(date.first))
        }
        val t = doc.tables[0].copy(doc)
        date.second.sortedBy { it.student?.surname }.forEachIndexed { stIndex, st ->
            val student = st.student ?: return@forEachIndexed
            t.addRow(t.rows[1].copy(t).apply {
                tableCells.forEachIndexed { index, cell ->
                    cell.text = when (index) {
                        0    -> (stIndex + 1).toString() + "."
                        1    -> student.fioFull
                        2    -> student.group ?: ""
                        3    -> "${st.number}"
                        4    -> (if (student.gekScore == null) "" else marks[student.gekScore ?: 0]).toUpperCase()
                        else -> ""
                    }
                }
            })
        }
        t.removeRow(1)
        doc.createTable()
        doc.setTable(index + 1, t)
        (9..13).forEach { i ->
            val p = doc.cloneParagraph(doc.paragraphs[i])
            if (i == 11) p.runs.last().setText("($secretaryOfTheCommission)")
            if (i == 13) p.runs.last().setText(input2DateFormat.format(date.first))
        }
        page++
    }
    (0..14).forEach { doc.removeBodyElement(0) }
    doc.write(FileOutputStream(filePath))
}

fun write(filePath: String, days: MutableList<Pair<Date, MutableList<Student>>>) {
    wrightDoc(filePath) {

        val center = createParagraphStyles {
            alignment = ParagraphAlignment.CENTER
            spacingAfter = 0
        }
        val font16 = createBlockStyles {
            fontSize = 16
        }

        defaultFont("Times New Roman")

        paragraph(center).block("РАСПИСАНИЕ", font16) { isBold = true }

        paragraph(center).block("работы Государственной Экзаменационной Комиссии", font16)
        paragraph(center).block("по специальности 1-53 01 07", font16)
        paragraph(center).block("\"Информационные технологии и управление в технических системах\"", font16)

        for ((date, students) in days) {
            val (f, s) = outputDateFormat.format(date).run {
                substringBefore(' ').toUpperCase() to substringAfter(' ')
            }

            emptyParagraph(font16)
            emptyParagraph(font16)

            paragraph {
                block("$f, ", font16) { isBold = true; isItalic = true }
                block(s, font16)
            }

            emptyParagraph(font16)
            emptyParagraph(font16)

            table {
                removeBordersss()
                students.forEachIndexed { index, student ->
                    row {
                        height(0.24)
                        cell(0) {
                            width(0.37)
                            paragraph().block((index + 1).toString()) { fontSize = 14 }
                        }
                        cell(1) {
                            verticalAlignment = CENTER
                            width(4.17)
                            paragraph().block(student.fioFull) { fontSize = 11 }
                        }
                        cell(2) {
                            verticalAlignment = CENTER
                            width(1.62)
                            paragraph().block(student.group ?: "") { fontSize = 11 }
                        }
                    }
                }
            }
        }
    }
}

fun writeProtocols(
    filePath: String,
    folderPath: String,
    days: MutableList<Pair<Date, MutableList<Student>>>
) {
    for ((index, dateWithStudents) in days.withIndex()) proto(
        filePath,
        folderPath,
        dateWithStudents.first.pair().second,
        dateWithStudents.second,
        index
    )
}

fun String.replacePatternWith(number: Int, date: String, student: Student) = when (this) {
    "N"               -> (number + 1).toString()
    "day"             -> date
    "hourStart"       -> (9 + number % 12 / 2).toString()
    "minStart"        -> ((number % 12 % 2) * 30).toString()
    "hourEnd"         -> (9 + (number % 12 + 1) / 2).toString()
    "minEnd"          -> (((number % 12 + 1) % 2) * 30).toString()
    "fioD"            -> student.fioD
    "fioFULLR"        -> student.fioFullR
    "theme"           -> student.theme
    "diplom"          -> "по рассмотрению ${if (student.isDiploma == true) "дипломного проекта" else "дипломной работы"} студента"
    "diplom0"         -> "${if (student.isDiploma == true) "Проект выполнен" else "Работа выполнена"} под руководством тов."
    "diplom1"         -> if (student.isDiploma == true) "1. Расчетно-пояснительная записка по дипломному проекту" else "1. Текст дипломной работы"
    "diplom2"         -> if (student.isDiploma == true) "2. Чертежи (таблицы) к проекту на" else "3. Чертежи (таблицы) к работе на"
    "diplom3"         -> if (student.isDiploma == true) "3. Отзыв руководителя дипломного проекта" else "3. Отзыв руководителя дипломной работы"
    "diplom4"         -> if (student.isDiploma == true) "4. Рецензия по проекту" else "4. Рецензия по работе"
    "diplom5"         -> if (student.isDiploma == true) "проект оценен на" else "работа оценена на"
    "faculty"         -> "факультета " + student.faculty
    "specialization"  -> "специальности " + student.specialization
    "leaderFIOR"      -> teachers.find { it.id.toString() == student.leader }?.fioR ?: ""
    "leaderDATA"      -> Padeg.getAppointmentPadeg(teachers.find { it.id.toString() == student.leader }?.data ?: "", 2) ?: ""
    "reviewerR"       -> (teachers.find { it.id.toString() == student.reviewer }?.fioR ?: "") + ", " +
            (Padeg.getAppointmentPadeg(teachers.find { it.id.toString() == student.reviewer }?.data?.decapitalize() ?: "", 2) ?: "")
    "consultantR+eco" -> (teachers.find { it.id.toString() == student.consultant }?.fioR
        ?: "") + ", " + (es[student.group] ?: "")
    "dropDown"        -> ""
    "dropNumbers"     -> ""
    "dropWordMarks"   -> "хорошую"
    "headOfTheCommission"     -> headOfTheCommission
    "commission1"     -> commission[0] + ","
    "commission2"     -> commission[1] + ","
    "commission3"     -> commission[2] + ","
    "commission4"     -> commission[3]
    else              -> throw Throwable("no temp: ${this}")
}
