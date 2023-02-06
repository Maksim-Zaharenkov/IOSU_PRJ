package com.example.iosu_prj.model

data class Student(
    val id: Int,
    var surname: String? = null,
    var surnameD: String? = null,
    var surnameR: String? = null,
    var name: String? = null,
    var nameR: String? = null,
    var patronymic: String? = null,
    var patronymicR: String? = null,
    var theme: String? = null,
    var group: String? = null,
    var average: Double? = null,
    var paymentPercent: Int? = null,
    var stId: String? = null,
    var recordBookId: String? = null,
    var faculty: String? = null,
    var specialization: String? = null,
    var economist: String? = null,
    var leader: String? = null,
    var consultant: String? = null,
    var reviewer: String? = null,
    var isDiploma: Boolean? = null,
    var gekScore: Int? = null,
    var leaderFIO: String? = null,
    var consultantFIO: String? = null,
    var reviewerFIO: String? = null,
    var save: Boolean? = true
) {
    val fio = "$surname ${name?.firstOrNull() ?: ""}. ${patronymic?.firstOrNull() ?: ""}."
    val fioD = "$surnameD ${name?.firstOrNull() ?: ""}. ${patronymic?.firstOrNull() ?: ""}."
    val fioFull = "$surname ${name ?: ""} ${patronymic ?: ""}"
    val fioFullR = "$surnameR ${nameR ?: ""} ${patronymicR ?: ""}"

    override fun toString() = fio

    constructor(map: Map<String, String>) : this(
        -1,
        map["surname"]?.filter { it != ' ' },
        map["surname"]?.filter { it != ' ' },
        map["surname"]?.filter { it != ' ' },
        map["name"]?.filter { it != ' ' },
        map["name"]?.filter { it != ' ' },
        map["patronymic"]?.filter { it != ' ' },
        map["patronymic"]?.filter { it != ' ' },
        map["theme"],
        map["group"]?.filter { it != ' ' },
        map["average"]?.toDouble(),
        map["paymentPercent"]?.removePrefix(" ")?.removeSuffix(" ")?.removeSuffix("%")?.toIntOrNull() ?: 0,
        map["studentId"]?.filter { it != ' ' }
    )
}

data class Teacher(
    val id: Int,
    var surname: String? = null,
    var surnameD: String? = null,
    var surnameR: String? = null,
    var name: String? = null,
    var patronymic: String? = null,
    var phone: String? = null,
    var data: String? = null,
    var isInCommission: Boolean? = false,
    var isLeader: Boolean? = null,
    var isReviewer: Boolean? = null,
    var isConsultant: Boolean? = null,
    var save: Boolean? = true
) {
    val fio = "${surname!!} ${name ?: ""}. ${patronymic ?: ""}."
    val fioR = "${surnameR!!} ${name ?: ""}. ${patronymic ?: ""}."

    constructor(map: Map<String, String>) : this(
        -1,
        map["surname"]?.filter { it != ' ' },
        map["surname"]?.filter { it != ' ' },
        map["surname"]?.filter { it != ' ' },
        map["name"]?.filter { it != ' ' },
        map["patronymic"]?.filter { it != ' ' },
        map["phone"],
        map["data"]
    )
}

data class Group(
    val id: Int,
    var number: String?,
    var faculty: String?,
    var specialization: String?,
    var economist: String?,
    var save: Boolean? = true
)

data class Protocol(
    var student: Student?,
    var date: String?,
    var number: Int?,
    val id: Int = -1
)