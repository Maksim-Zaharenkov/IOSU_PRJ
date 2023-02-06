package com.example.iosu_prj

import com.example.iosu_prj.model.*
import com.example.iosu_prj.model.Groups
import com.example.iosu_prj.model.Protocols
import com.example.iosu_prj.model.Students
import com.example.iosu_prj.model.Teachers
import com.zaxxer.hikari.*
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.transactions.*
import padeg.lib.Padeg
import java.io.*
import java.sql.*

val dataSource = HikariDataSource(HikariConfig().apply {
    jdbcUrl = "jdbc:sqlite:${File("").absolutePath}/bsuirgek.db"
    driverClassName = "org.sqlite.JDBC"
})

val db = Database.connect(dataSource) {
    ThreadLocalTransactionManager(it, Connection.TRANSACTION_READ_UNCOMMITTED, -1)
}

val t = transaction {
    createMissingTablesAndColumns(
        Students,
        Teachers,
        Groups,
        Protocols
    )
}

fun save(value: Pair<Int, Any>) {
    when (value.first) {
        0 -> {
            val students = value.second as List<Student>

            transaction {
                students.filter { it.save == true }.forEach { st ->
                    if (
                        Students.select {
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }.count() != 0
                    ) {
                        Students.update({
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }) {
                            it[averageScore] = st.average!!.toBigDecimal()
                            val gr = Groups.select { Groups.number eq st.group!! }
                            if (gr.count() == 0) Groups.insert {
                                it[number] = st.group!!
                                it[faculty] = st.faculty
                                it[specialization] = st.specialization
                                it[economist] = st.economist
                            }
                            it[groupId] = gr.map { it[Groups.id] }.firstOrNull()
                        }
                    } else
                        Students.insert {
                            val fioR = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")
                            val firstNameR = fioR.substringAfter("$lastNameR ").substringBefore(" ")
                            val middleNameR = fioR.substringAfter("$firstNameR ")

                            val fioD = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = st.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = st.name
                            it[nameR] = firstNameR
                            it[patronymic] = st.patronymic
                            it[patronymicR] = middleNameR
                            it[averageScore] = st.average!!.toBigDecimal()
                            val gr = Groups.select { Groups.number eq st.group!! }
                            if (gr.count() == 0) Groups.insert {
                                it[number] = st.group!!
                                it[faculty] = st.faculty
                                it[specialization] = st.specialization
                                it[economist] = st.economist
                            }
                            it[groupId] = gr.map { it[Groups.id] }.firstOrNull()
                        }
                }
            }
        }
        1 -> {
            val students = value.second as List<Student>

            transaction {
                students.filter { it.save == true }.forEach { st ->
                    if (
                        Students.select {
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }.count() != 0
                    ) {
                        Students.update({
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }) {
                            it[studentId] = st.stId
                            val gr = Groups.select { Groups.number eq st.group!! }
                            if (gr.count() == 0) Groups.insert {
                                it[number] = st.group!!
                                it[faculty] = st.faculty
                                it[specialization] = st.specialization
                                it[economist] = st.economist
                            }
                            it[groupId] = gr.map { it[Groups.id] }.firstOrNull()
                        }
                    } else
                        Students.insert {
                            val fioR = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")
                            val firstNameR = fioR.substringAfter("$lastNameR ").substringBefore(" ")
                            val middleNameR = fioR.substringAfter("$firstNameR ")

                            val fioD = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = st.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = st.name
                            it[nameR] = firstNameR
                            it[patronymic] = st.patronymic
                            it[patronymicR] = middleNameR
                            it[studentId] = st.stId
                            val gr = Groups.select { Groups.number eq st.group!! }
                            if (gr.count() == 0) Groups.insert {
                                it[number] = st.group!!
                                it[faculty] = st.faculty
                                it[specialization] = st.specialization
                                it[economist] = st.economist
                            }
                            it[groupId] = gr.map { it[Groups.id] }.firstOrNull()
                        }
                }
            }
        }
        2 -> {
            val all = value.second as List<Pair<Student, Pair<Teacher?, Teacher?>>>
            val teachers = all.flatMap { listOf(it.second.first, it.second.second) }.filterNotNull()
            val students = all.map { it.first }
            transaction {

                teachers.filter { it.save == true }.forEach { t ->
                    if (
                        Teachers.select {
                            (Teachers.surname eq t.surname!!)
                                .and(Teachers.name eq t.name?.get(0).toString())
                                .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                        }.count() != 0
                    ) {
                        Teachers.update({
                            (Teachers.surname eq t.surname!!)
                                .and(Teachers.name eq t.name?.get(0).toString())
                                .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                        }) {
                            it[data] = t.data

                            it[isLeader] = all.any { it.second.first?.fio == t.fio }
                            it[isConsultant] = all.any { it.second.second?.fio == t.fio }
                        }
                    } else {
                        Teachers.insert {
                            val fioR = Padeg.getFIOPadegAS(t.surname!!, t.name, t.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")

                            val fioD = Padeg.getFIOPadegAS(t.surname!!, t.name, t.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = t.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = t.name?.get(0).toString()
                            it[patronymic] = t.patronymic?.get(0).toString()
                            it[data] = t.data

                            it[isLeader] = all.any { it.second.first?.fio == t.fio }
                            it[isConsultant] = all.any { it.second.second?.fio == t.fio }
                        }
                    }
                }

                students.filter { it.save == true }.forEach { st ->
                    if (
                        Students.select {
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }.count() != 0
                    ) {
                        Students.update({
                            (Students.surname eq st.surname!!)
                                .and(Students.name eq st.name!!)
                                .and(Students.patronymic eq st.patronymic!!)
                        }) {
                            it[theme] = st.theme
                            it[isDiploma] = st.isDiploma

                            val t = all.find { it.first == st }?.second

                            val main = t?.first
                            if (main != null) {
                                it[leaderId] = Teachers.select {
                                    (Teachers.surname eq main.surname!!)
                                        .and(Teachers.name eq main.name?.get(0).toString())
                                        .and(Teachers.patronymic eq main.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            }

                            val rev = t?.second
                            if (rev != null) {
                                it[consultantId] = Teachers.select {
                                    (Teachers.surname eq rev.surname!!)
                                        .and(Teachers.name eq rev.name?.get(0).toString())
                                        .and(Teachers.patronymic eq rev.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            } else if (main != null) {
                                it[consultantId] = Teachers.select {
                                    (Teachers.surname eq main.surname!!)
                                        .and(Teachers.name eq main.name?.get(0).toString())
                                        .and(Teachers.patronymic eq main.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            }
                        }
                    } else {
                        Students.insert {
                            val fioR = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")
                            val firstNameR = fioR.substringAfter("$lastNameR ").substringBefore(" ")
                            val middleNameR = fioR.substringAfter("$firstNameR ")

                            val fioD = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = st.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = st.name
                            it[nameR] = firstNameR
                            it[patronymic] = st.patronymic
                            it[patronymicR] = middleNameR
                            it[theme] = st.theme
                            it[isDiploma] = st.isDiploma
                            val t = all.find { it.first == st }?.second

                            val main = t?.first
                            if (main != null) {
                                it[leaderId] = Teachers.select {
                                    (Teachers.surname eq main.surname!!)
                                        .and(Teachers.name eq main.name!!)
                                        .and(Teachers.patronymic eq main.patronymic!!)
                                }.map { it[Teachers.id] }.firstOrNull()
                            }

                            val rev = t?.second
                            if (rev != null) {
                                it[consultantId] = Teachers.select {
                                    (Teachers.surname eq rev.surname!!)
                                        .and(Teachers.name eq rev.name?.get(0).toString())
                                        .and(Teachers.patronymic eq rev.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            } else if (main != null) {
                                it[consultantId] = Teachers.select {
                                    (Teachers.surname eq main.surname!!)
                                        .and(Teachers.name eq main.name?.get(0).toString())
                                        .and(Teachers.patronymic eq main.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            }
                        }
                    }
                }
            }
        }
        3 -> {
            val all = value.second as List<Pair<Teacher, List<Student>>>
            val teachers = all.mapNotNull { it.first }
            val students = all.flatMap { it.second }
            transaction {
                teachers.filter { it.save == true }.forEach { t ->
                    if (
                        Teachers.select {
                            (Teachers.surname eq t.surname!!)
                                .and(Teachers.name eq t.name?.get(0).toString())
                                .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                        }.count() != 0
                    ) {
                        Teachers.update({
                            (Teachers.surname eq t.surname!!)
                                .and(Teachers.name eq t.name?.get(0).toString())
                                .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                        }) {
                            it[data] = t.data
                            it[isReviewer] = true
                        }
                    } else {
                        Teachers.insert {
                            val fioR = Padeg.getFIOPadegAS(t.surname!!, t.name, t.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")

                            val fioD = Padeg.getFIOPadegAS(t.surname!!, t.name, t.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = t.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = t.name?.get(0).toString()
                            it[patronymic] = t.patronymic?.get(0).toString()
                            it[data] = t.data
                            it[isReviewer] = true
                        }
                    }
                }

                students.filter { it.save == true }.forEach { st ->
                    if (
                        Students.select {
                            (Students.surname eq st.surname!!)
                                .and(Students.name like (st.name!!.first() + "%"))
                                .and(Students.patronymic like (st.patronymic!!.first() + "%"))
                        }.count() != 0
                    ) {
                        Students.update({
                            (Students.surname eq st.surname!!)
                                .and(Students.name like (st.name!!.first() + "%"))
                                .and(Students.patronymic like (st.patronymic!!.first() + "%"))
                        }) {
                            it[reviewerId] = all.find { it.second.contains(st) }?.let {
                                val t = it.first
                                Teachers.select {
                                    (Teachers.surname eq t.surname!!)
                                        .and(Teachers.name eq t.name?.get(0).toString())
                                        .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            }
                        }
                    } else {
                        Students.insert {
                            val fioR = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 2)
                            val lastNameR = fioR.substringBefore(" ")
                            val firstNameR = fioR.substringAfter("$lastNameR ").substringBefore(" ")
                            val middleNameR = fioR.substringAfter("$firstNameR ")

                            val fioD = Padeg.getFIOPadegAS(st.surname!!, st.name, st.patronymic, 3)
                            val lastNameD = fioD.substringBefore(" ")

                            it[surname] = st.surname!!
                            it[surnameR] = lastNameR
                            it[surnameD] = lastNameD
                            it[name] = st.name
                            it[nameR] = firstNameR
                            it[patronymic] = st.patronymic
                            it[patronymicR] = middleNameR
                            it[reviewerId] = all.find { it.second.contains(st) }?.let {
                                val t = it.first
                                Teachers.select {
                                    (Teachers.surname eq t.surname!!)
                                        .and(Teachers.name eq t.name?.get(0).toString())
                                        .and(Teachers.patronymic eq t.patronymic?.get(0).toString())
                                }.map { it[Teachers.id] }.firstOrNull()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun deleteTimetable() = transaction {
    Protocols.deleteAll()
}

fun deleteAll() = transaction {
    Protocols.deleteAll()
    Groups.deleteAll()
    Teachers.deleteAll()
    Students.deleteAll()
}

fun deleteAllTeachers() = transaction {
    Students.update {
        it[leaderId] = null
        it[reviewerId] = null
        it[consultantId] = null
    }

    Teachers.deleteAll()
}

fun deleteAllStudents() = transaction {
    Protocols.deleteAll()
    Groups.deleteAll()
    Students.deleteAll()
}

fun updateMark(student: Student) = transaction {
    if (student.gekScore != null) Students.update({
        (Students.surnameR eq student.surnameR!!)
            .and(Students.nameR eq student.nameR!!)
            .and(Students.patronymicR eq student.patronymicR!!)
    }) {
        it[gekScore] = student.gekScore
    }
}

fun updateStudent(stId: Int, newStudent: Student): Unit = transaction {
    if (stId == -1) Students.insert {
        it[surname] = newStudent.surname!!
        it[surnameR] = newStudent.surnameR!!
        it[surnameD] = newStudent.surnameD!!
        it[name] = newStudent.name
        it[nameR] = newStudent.nameR
        it[patronymic] = newStudent.patronymic
        it[patronymicR] = newStudent.patronymicR

        it[theme] = newStudent.theme
        it[isDiploma] = newStudent.isDiploma
        it[gekScore] = newStudent.gekScore

        it[groupId] = if (newStudent.group != null) Groups.select {
            Groups.number eq newStudent.group!!
        }.map { it[Groups.id] }.firstOrNull()
        else null
        it[averageScore] = newStudent.average?.toBigDecimal()
        it[paymentPercent] = newStudent.paymentPercent
        it[studentId] = newStudent.stId
        it[recordBookId] = newStudent.recordBookId
        it[leaderId] = Teachers.select {
            Teachers.id eq newStudent.leader?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
        it[consultantId] = Teachers.select {
            Teachers.id eq newStudent.consultant?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
        it[reviewerId] = Teachers.select {
            Teachers.id eq newStudent.reviewer?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
    } else Students.update({ Students.id eq stId }) {
        it[surname] = newStudent.surname!!
        it[surnameR] = newStudent.surnameR!!
        it[surnameD] = newStudent.surnameD!!
        it[name] = newStudent.name
        it[nameR] = newStudent.nameR
        it[patronymic] = newStudent.patronymic
        it[patronymicR] = newStudent.patronymicR
        it[theme] = newStudent.theme
        it[isDiploma] = newStudent.isDiploma
        it[gekScore] = newStudent.gekScore

        it[groupId] = if (newStudent.group != null) Groups.select {
            Groups.number eq newStudent.group!!
        }.map { it[Groups.id] }.firstOrNull()
        else null
        it[averageScore] = newStudent.average?.toBigDecimal()
        it[paymentPercent] = newStudent.paymentPercent
        it[studentId] = newStudent.stId
        it[recordBookId] = newStudent.recordBookId
        it[leaderId] = Teachers.select {
            Teachers.id eq newStudent.leader?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
        it[consultantId] = Teachers.select {
            Teachers.id eq newStudent.consultant?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
        it[reviewerId] = Teachers.select {
            Teachers.id eq newStudent.reviewer?.toIntOrNull()
        }.map { it[Teachers.id] }.firstOrNull()
    }
}

val students =
    transaction {
        Students.selectAll().map {
            Students.run {
                Student(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[nameR],
                    it[patronymic],
                    it[patronymicR],
                    it[theme],
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.number] }.firstOrNull(),
                    it[averageScore]?.toDouble(),
                    it[paymentPercent],
                    it[studentId],
                    null,
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.faculty] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.specialization] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.economist] }.firstOrNull(),
                    it[leaderId]?.value.toString(),
                    it[consultantId]?.value.toString(),
                    it[reviewerId]?.value.toString(),
                    it[isDiploma],
                    it[gekScore]
                )
            }
        }
    }

fun removeStudent(id: Int) = transaction {
    Protocols.deleteWhere { Protocols.studentId eq id }
    Students.deleteWhere { Students.id eq id }
}

fun studentsForRasp(groupList: MutableList<String>) =
    transaction {
        (Groups innerJoin Students).select { Groups.number inList groupList }.map {
            Students.run {
                Student(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[nameR],
                    it[patronymic],
                    it[patronymicR],
                    it[theme],
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.number] }.firstOrNull(),
                    it[averageScore]?.toDouble(),
                    it[paymentPercent],
                    it[studentId],
                    null,
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.faculty] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.specialization] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.economist] }.firstOrNull(),
                    it[leaderId]?.value.toString(),
                    it[consultantId]?.value.toString(),
                    it[reviewerId]?.value.toString(),
                    it[isDiploma],
                    it[gekScore]
                )
            }
        }
    }

fun studentsNotInArchive() =
    transaction {
        (Students leftJoin Protocols).select { Students.id eq Protocols.studentId }.map {
            Students.run {
                Student(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[nameR],
                    it[patronymic],
                    it[patronymicR],
                    it[theme],
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.number] }.firstOrNull(),
                    it[averageScore]?.toDouble(),
                    it[paymentPercent],
                    it[studentId],
                    null,
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.faculty] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.specialization] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.economist] }.firstOrNull(),
                    it[leaderId]?.value.toString(),
                    it[consultantId]?.value.toString(),
                    it[reviewerId]?.value.toString(),
                    it[isDiploma],
                    it[gekScore]
                )
            }
        }
    }

fun studentsInArchive(list: MutableList<Int>) =
    transaction {
        Students.select { Students.id notInList list }.map {
            Students.run {
                Student(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[nameR],
                    it[patronymic],
                    it[patronymicR],
                    it[theme],
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.number] }.firstOrNull(),
                    it[averageScore]?.toDouble(),
                    it[paymentPercent],
                    it[studentId],
                    null,
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.faculty] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.specialization] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.economist] }.firstOrNull(),
                    it[leaderId]?.value.toString(),
                    it[consultantId]?.value.toString(),
                    it[reviewerId]?.value.toString(),
                    it[isDiploma],
                    it[gekScore]
                )
            }
        }
    }

val teachers =
    transaction {
        Teachers.selectAll().map {
            Teachers.run {
                Teacher(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[patronymic],
                    it[phoneNumber],
                    it[data],
                    it[isInCommission] ?: false,
                    it[isLeader] ?: false,
                    it[isReviewer] ?: false,
                    it[isConsultant] ?: false
                )
            }
        }
    }

fun updateTeacher(stId: Int, newTeacher: Teacher): Unit = transaction {
    if (stId == -1) Teachers.insert {
        it[surname] = newTeacher.surname!!
        it[surnameR] = newTeacher.surnameR!!
        it[surnameD] = newTeacher.surnameD!!
        it[name] = newTeacher.name
        it[patronymic] = newTeacher.patronymic
        it[phoneNumber] = newTeacher.phone
        it[data] = newTeacher.data
        it[isInCommission] = newTeacher.isInCommission
        it[isLeader] = newTeacher.isLeader
        it[isReviewer] = newTeacher.isReviewer
        it[isConsultant] = newTeacher.isConsultant
    } else
        Teachers.update({ Teachers.id eq stId }) {
            it[surname] = newTeacher.surname!!
            it[surnameR] = newTeacher.surnameR!!
            it[surnameD] = newTeacher.surnameD!!
            it[name] = newTeacher.name
            it[patronymic] = newTeacher.patronymic
            it[phoneNumber] = newTeacher.phone
            it[data] = newTeacher.data
            it[isInCommission] = newTeacher.isInCommission
            it[isLeader] = newTeacher.isLeader
            it[isReviewer] = newTeacher.isReviewer
            it[isConsultant] = newTeacher.isConsultant
        }
}

val groups = transaction {
    Groups.selectAll().map {
        Groups.run {
            Group(
                it[id].value,
                it[number],
                it[faculty],
                it[specialization],
                it[economist]
            )
        }
    }
}

fun removeTeacher(id: Int) = transaction {
    val studentLeadersList = mutableListOf<Int>()
    val studentConsultantsList = mutableListOf<Int>()
    val studentReviewersList = mutableListOf<Int>()

    showStudent().forEach { if (it.leader == id.toString()) { studentLeadersList.add(it.id) }
        if (it.consultant == id.toString()) { studentConsultantsList.add(it.id) }
        if (it.reviewer == id.toString()) { studentReviewersList.add(it.id) }
    }

    Students.update({ Students.id inList studentLeadersList }) {
        it[leaderId] = null
    }

    Students.update({ Students.id inList studentConsultantsList }) {
        it[consultantId] = null
    }

    Students.update({ Students.id inList studentReviewersList }) {
        it[reviewerId] = null
    }

    Teachers.deleteWhere { Teachers.id eq id }
}

fun removeGroup(id: Int, group: String) = transaction {
    val studentInGroupList = mutableListOf<Int>()

    showStudent().forEach { if (it.group == group) { studentInGroupList.add(it.id) }}

    Protocols.deleteWhere { Protocols.studentId inList studentInGroupList }
    Students.deleteWhere { Students.id inList studentInGroupList }
    Groups.deleteWhere { Groups.id eq id }
}

fun showStudent() =
    transaction {
        Students.selectAll().map {
            Students.run {
                Student(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[nameR],
                    it[patronymic],
                    it[patronymicR],
                    it[theme],
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.number] }.firstOrNull(),
                    it[averageScore]?.toDouble(),
                    it[paymentPercent],
                    it[studentId],
                    null,
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.faculty] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.specialization] }.firstOrNull(),
                    Groups.select {
                        Groups.id eq it[groupId]?.value
                    }.map { it[Groups.economist] }.firstOrNull(),
                    it[leaderId]?.value.toString(),
                    it[consultantId]?.value.toString(),
                    it[reviewerId]?.value.toString(),
                    it[isDiploma],
                    it[gekScore]
                )
            }
        }
    }
fun showTeacher() =
    transaction {
        Teachers.selectAll().map {
            Teachers.run {
                Teacher(
                    it[id].value,
                    it[surname],
                    it[surnameD],
                    it[surnameR],
                    it[name],
                    it[patronymic],
                    it[phoneNumber],
                    it[data],
                    it[isInCommission] ?: false,
                    it[isLeader] ?: false,
                    it[isReviewer] ?: false,
                    it[isConsultant] ?: false
                )
            }
        }
    }
fun showGroup() = transaction {
    Groups.selectAll().map {
        Groups.run {
            Group(
                it[id].value,
                it[number],
                it[faculty],
                it[specialization],
                it[economist]
            )
        }
    }
}

fun showProtocols() =
    transaction {
        Protocols.selectAll().map { row ->
            Protocol(
                students.find { it.id == row[Protocols.studentId].value }!!,
                row[Protocols.realDate],
                row[Protocols.number],
                row[Protocols.id].value
            )
        }
    }

fun getGroupsWithoutRasp() = transaction {
    (Groups fullJoin Students leftJoin Protocols ).selectAll().map {
        Groups.run {
            Group(
                it[id].value,
                it[number],
                it[faculty],
                it[specialization],
                it[economist]
            )
        }
    }
}

fun updateGroup(stId: Int, newGroup: Group): Unit = transaction {
    if (stId == -1) Groups.insert {
        it[number] = newGroup.number!!
    } else
        Groups.update({ Groups.id eq stId }) {
            it[number] = newGroup.number!!
        }
}

val protocols =
    transaction {
        Protocols.selectAll().map { row ->
            Protocol(
                students.find { it.id == row[Protocols.studentId].value }!!,
                row[Protocols.realDate],
                row[Protocols.number],
                row[Protocols.id].value
            )
        }
    }

fun updateProtocol(stId: Int, protocol: Protocol): Unit = transaction {
    if (stId == -1) Protocols.insert {
        it[realDate] = protocol.date!!
        it[number] = protocol.number!!
        it[studentId] = Students.select { Students.id eq protocol.student?.id }.map { it[Students.id] }.first()
    }
    else Protocols.update({ Protocols.id eq stId }) {
        it[realDate] = protocol.date!!
        it[number] = protocol.number!!
        it[studentId] = Students.select { Students.id eq protocol.student?.id }.map { it[Students.id] }.first()
    }
}