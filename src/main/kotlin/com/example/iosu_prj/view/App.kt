package com.example.iosu_prj.view

import com.example.iosu_prj.model.Student
import com.example.iosu_prj.model.Teacher
import com.example.iosu_prj.model.TemplateDOCX
import com.example.iosu_prj.model.TemplateXLSX
import com.example.iosu_prj.parser.ParseData
import com.example.iosu_prj.parser.asDOCX
import com.example.iosu_prj.parser.asXLSX
import com.example.iosu_prj.save
import java.io.File

val templatesDOCX = listOf("Приказ(docx)", "Рецензенты(docx)")
val templatesXLSX = listOf("Список ИТиУвТС(xlsx)", "Список ИНО(xlsx)")

fun loadFile(choosedTemp: String, choosedFile: File) {

//    println("Выбрать тип документа (написать цифру):")
//    println("1. xlsx ($templatesXLSX)")
//    println("2. docx ($templatesDOCX)")
//    val choosed = if (readLine()!!.toInt() == 1) {
//        println("Выбрать шаблон (написать цифру):")
//        println(templatesXLSX.withIndex().joinToString { "${it.index}. ${it.value}" })
//        "xlsx"
//    } else {
//        println("Выбрать шаблон (написать цифру):")
//        println(templatesDOCX.withIndex().joinToString { "${it.index}. ${it.value}" })
//        "docx"
//    }
//
//    val choosedTemp: Pair<String, Int> = choosed to readLine()!!.toInt()
//    println("Выбрать файл (полный путь к файлу от папки в которой лежит приложение, к примеру ГЭК/file.docx):")
//
//    val choosedFile = File(readLine()!!)
//
//    fun getData(): Pair<Int, Map<String, List<ParseData>>> = when (choosedTemp) {
//        "xlsx" to 0 -> 0 to TemplateXLSX.ITIU.parse(choosedFile.asXLSX())
//        "xlsx" to 1 -> 1 to TemplateXLSX.INO.parse(choosedFile.asXLSX())
//        "docx" to 0 -> 2 to TemplateDOCX.Prikaz.parse(choosedFile.asDOCX())
//        "docx" to 1 -> 3 to TemplateDOCX.Rec.parse(choosedFile.asDOCX())
//        else        -> error("NOT_FOUND: $choosedTemp")
//    }

    var choosed: Pair<String, Int>? = null
    when (choosedTemp) {
        "Список ИТиУвТС(xlsx)" -> choosed = "Список ИТиУвТС(xlsx)" to 0
        "Список ИНО(xlsx)" -> choosed = "Список ИНО(xlsx)" to 1
        "Приказ(docx)" -> choosed = "Приказ(docx)" to 0
        "Рецензенты(docx)" -> choosed = "Рецензенты(docx)" to 1
    }

    fun getData(): Pair<Int, Map<String, List<ParseData>>> = when (choosed) {
        "Список ИТиУвТС(xlsx)" to 0 -> 0 to TemplateXLSX.ITIU.parse(choosedFile.asXLSX())
        "Список ИНО(xlsx)" to 1 -> 1 to TemplateXLSX.INO.parse(choosedFile.asXLSX())
        "Приказ(docx)" to 0 -> 2 to TemplateDOCX.Prikaz.parse(choosedFile.asDOCX())
        "Рецензенты(docx)" to 1 -> 3 to TemplateDOCX.Rec.parse(choosedFile.asDOCX())
        else        -> error("NOT_FOUND: $choosedTemp")
    }

    val (number, data) = getData()
    val choosedData: Pair<Int, Any> = when (number) {
        0    -> {
            val students = data.flatMap {
                it.value.fold(mutableListOf<Student>()) { acc, obj ->
                    if (obj.type == "Student") acc += Student(obj.params)
                        .apply { group = it.key
                            faculty = obj.faculty
                            specialization = obj.specialization
                            economist = obj.economist
                        }
                    acc
                }
            }
            0 to students
        }
        1    -> {
            val students = data.flatMap {
                it.value.fold(mutableListOf<Student>()) { acc, obj ->
                    if (obj.type == "Student") acc += Student(obj.params)
                        .apply { group = it.key
                            faculty = obj.faculty
                            specialization = obj.specialization
                            economist = obj.economist
                        }
                    acc
                }
            }
            1 to students
        }
        2    -> {
            val students = data.flatMap {
                it.value.fold(mutableListOf<Pair<Student, Pair<Teacher?, Teacher?>>>()) { acc, obj ->
                    if (obj.type == "Student") {
                        val s = Student(obj.params).apply {
                            isDiploma = it.key == "1"
                        }
                        val main =
                            it.value.find { it.id == obj.id.removeSuffix("Student") + "main" }
                        val rev =
                            it.value.find { it.id == obj.id.removeSuffix("Student") + "rev" }
                        acc += s to (main?.params?.let { Teacher(it) } to rev?.params?.let {
                            Teacher(
                                it
                            )
                        })
                    }
                    acc
                }
            }
            2 to students
        }
        3    -> {
            val teachers = data.flatMap {
                it.value.fold(mutableListOf<Pair<Teacher, List<Student>>>()) { acc, obj ->
                    if (obj.type == "Teacher") {
                        val s = Teacher(obj.params)
                        val i = obj.id.removeSuffix("r") + "List"
                        val students = it.value
                            .filter { it.id.startsWith(i) }
                            .map { Student(it.params) }
                            .filter { it.surname != null }
                        acc += s to students
                    }
                    acc
                }
            }
            3 to teachers
        }
        else -> error("NNNNNNNNNNNNNN")
    }

    save(choosedData)
}

