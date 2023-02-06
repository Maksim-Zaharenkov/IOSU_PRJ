package com.example.iosu_prj.test

import org.apache.poi.xwpf.usermodel.*
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts
import java.io.FileOutputStream
import java.math.BigInteger

fun Double.toInch() = (this*1440).toInt()
fun Double.toBigInch(): BigInteger = toInch().toBigInteger()

inline fun createDoc(block: XWPFDocument.() -> Unit) = XWPFDocument().apply(block)

inline fun wrightDoc(path: String, block: XWPFDocument.() -> Unit) {
    createDoc(block).write(FileOutputStream(path))
}

fun XWPFDocument.defaultFont(name : String) {
    createStyles().setDefaultFonts(CTFonts.Factory.newInstance().apply { ascii = name })
}


inline fun XWPFDocument.paragraph(stdBlock: XWPFParagraph.() -> Unit = {},
                                  block: XWPFParagraph.() -> Unit = {}): XWPFParagraph {
    return createParagraph().apply(stdBlock).apply(block)
}

inline fun XWPFParagraph.block(text:String = "", stdBlock: XWPFRun.() -> Unit = {},
                               block: XWPFRun.() -> Unit = {}): XWPFRun {
    val run = createRun().apply(stdBlock).apply(block)
    run.setText(text)
    return run
}

inline fun XWPFDocument.emptyParagraph(stdBlock: XWPFRun.() -> Unit = {}){ paragraph().block(" ", stdBlock)}

fun createParagraphStyles(block: XWPFParagraph.() -> Unit) = block
fun createBlockStyles(block: XWPFRun.() -> Unit) = block

inline fun XWPFDocument.table(block: XWPFTable.() -> Unit = {}): XWPFTable = createTable().apply { removeRow(0) }.apply(block)

inline fun XWPFTable.row(index: Int = -1, block: XWPFTableRow.() -> Unit = {}) : XWPFTableRow {
    return (if (index > numberOfRows || index < 0) createRow() else getRow(index)).apply(block)
}

inline fun XWPFTableRow.cell(index: Int = -1, clear:Boolean = true, block: XWPFTableCell.() -> Unit = {}) : XWPFTableCell {
    val cell = if (index > ctRow.sizeOfTcArray() - 1 || index < 0) createCell() else getCell(index)
    if (clear) cell.apply { for (i in 0 until paragraphs.size) removeParagraph(0) }
    return cell.apply(block)
}

inline fun XWPFTableCell.paragraph(stdBlock: XWPFParagraph.() -> Unit = {},
                                   block: XWPFParagraph.() -> Unit = {}): XWPFParagraph {
    return addParagraph().apply(stdBlock).apply(block)
}

fun XWPFTable.removeBordersss() {
    ctTbl.tblPr.unsetTblBorders()
}

fun XWPFTableRow.height(inches: Double) {
    height = inches.toInch()
}

fun XWPFTableCell.width(inches : Double) {
    ctTc.addNewTcPr().addNewTcW().w = inches.toBigInch()
}