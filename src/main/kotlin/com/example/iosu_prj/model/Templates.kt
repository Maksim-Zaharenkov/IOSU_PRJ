package com.example.iosu_prj.model

import com.example.iosu_prj.parser.ParseData
import com.example.iosu_prj.parser.toPattern
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.*
import org.apache.poi.xwpf.usermodel.*

enum class TemplateXLSX(val row: Int, val cell: Int, s: String) {
    ITIU(
        5,
        1,
        """<Table skip="0"><Cell></Cell><Cell><Student><surname/><s t=" "/><name/><s t=" "/><patronymic/><s t=" "/></Student></Cell><Cell><Student><average/></Student></Cell><Cell><Student><paymentPercent/></Student></Cell></Table>"""
    ),
    INO(
        6,
        1,
        """<Table skip="0"><Cell></Cell><Cell><Student><studentId/></Student></Cell><Cell><Student><surname/><s t=" "/><name/><s t=" "/><patronymic/><s t=" "/></Student></Cell><Cell><Student><paymentPercent/></Student></Cell></Table>"""
    );

    val pattern = s.toPattern()

    fun parse(document: XSSFWorkbook): Map<String, List<ParseData>> =
        document.fold(mutableMapOf()) { acc, sheet: Sheet ->
            acc[sheet.sheetName] = pattern.parseSheet(sheet as XSSFSheet, row to cell);acc
        }
}

enum class TemplateDOCX(val isParagraph: Boolean, val number: Int, val s: String) {
    Prikaz(
        false,
        0,
        """
            |<Table skip="0">
                |<Cell></Cell>
                |<Cell><Paragraph>
                    |<Student><surname/><s t=" "/><name/><s t=" "/><patronymic/></Student>
                |</Paragraph></Cell>
                |<Cell><Paragraph>
                    |<Student><theme/></Student>
                |</Paragraph></Cell>
                |<Cell><Paragraph>
                    |<Teacher id="main">
                        |<surname/><s t=" "/>
                        |<name/><s t=" "/>
                        |<patronymic/><s t=","/>
                        |<data/>
                    |</Teacher>
                |</Paragraph></Cell>
                |<Cell><Paragraph>(
                    |<Teacher id="rev">
                        |<surname/><s t=" "/>
                        |<name/><s t=" "/>
                        |<patronymic/><s t=","/>
                        |<data/>
                    |</Teacher>
                |</Paragraph></Cell>
            |</Table>
            |""".trimMargin()
    ),
    Rec(
            false,
            1,
            """
                |<Table skip="0">
                |<Cell><Paragraph>
                    |<Teacher id="r">
                        |<surname/><s t=" "/>
                        |<name/><s t=" "/>
                        |<patronymic/>
                    |</Teacher>
                |</Paragraph></Cell>
                |<Cell><Paragraph>
                    |<Teacher id="r"><data/></Teacher>
                |</Paragraph></Cell>
                |<Cell><List>
                    |<Student><surname/><s t=" "/><name/><s t=" "/><patronymic/></Student>
                |</List></Cell>
            |</Table>
            |""".trimMargin()
    );

    val pattern = s.toPattern()

    fun parse(document: XWPFDocument): Map<String, List<ParseData>> =
        if (isParagraph)
            document.paragraphs.foldIndexed(mutableMapOf()) { index, acc, paragraph ->
                acc[index.toString()] = pattern.parseParagraph(paragraph)
                acc
            }
        else
            document.tables.foldIndexed(mutableMapOf()) { index, acc, table ->
                acc[index.toString()] = try {
                    pattern.parseTable(table)
                } catch (e: Exception) {
                    println(e)
                    emptyList<ParseData>()
                }
                acc
            }
}