package com.example.iosu_prj.model

import org.jetbrains.exposed.dao.IntIdTable

object Teachers : IntIdTable("teachers") {
    val surname = text("surname")
    val surnameD = text("surnameD").nullable()
    val surnameR = text("surnameR").nullable()
    val name = text("name").nullable()
    val patronymic = text("patronymic").nullable()
    val phoneNumber = text("phone_number").nullable()
    val data = text("data").nullable()
    val isInCommission = bool("isInCommission").nullable()
    val isLeader = bool("isLeader").nullable()
    val isReviewer = bool("isReviewer").nullable()
    val isConsultant = bool("isConsultant").nullable()
}

object Students : IntIdTable("students") {
    val surname = text("surname")
    val surnameD = text("surnameD").nullable()
    val surnameR = text("surnameR").nullable()
    val name = text("name").nullable()
    val nameR = text("nameR").nullable()
    val patronymic = text("patronymic").nullable()
    val patronymicR = text("patronymicR").nullable()
    val theme = text("theme").nullable()
    val averageScore = decimal("average_score", 2, 2).nullable()
    val paymentPercent = integer("payment_percent").nullable()
    val recordBookId = text("record_book_id").nullable()
    val studentId = text("student_id").nullable()
    val isDiploma = bool("isDiploma").nullable()
    val gekScore = integer("gek_score").nullable()
    val isArchived = integer("is_archived")

    val groupId = reference("group_id", Groups).nullable()
    val leaderId = reference("leader_id", Teachers).nullable()
    val consultantId = reference("consultant_id", Teachers).nullable()
    val reviewerId = reference("reviewer_id", Teachers).nullable()
}

object Groups : IntIdTable("groups") {
    val number = text("number")
    val faculty = text("faculty").nullable()
    val specialization = text("specialization").nullable()
    val economist = text("economist").nullable()
}

object Protocols : IntIdTable("real_numbers") {
    val studentId = reference("student_id", Students)
    val realDate = text("day")
    val number = integer("number")
}