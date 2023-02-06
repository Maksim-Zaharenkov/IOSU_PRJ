package com.example.iosu_prj.parser

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.w3c.dom.Node
import org.w3c.dom.Node.ELEMENT_NODE
import java.util.*

class Pattern(val type: String) {

    val tags by lazy { LinkedHashMap<String, String>() }
    private val isSpace by lazy { type == "s" }
    private val text by lazy { tags["t"] ?: "" }
    private val skips by lazy { tags["skip"]?.split(",").orEmpty().map { it.toInt() } }
    val patterns by lazy { mutableListOf<Pattern>() }
    private val faculty by lazy { "getFaculty" }
    private val specialization by lazy { "getSpecialization" }
    private val economist by lazy { "getEconomist" }

    val data by lazy {
        ParseData(
            tags["id"] ?: type,
            type,
            faculty,
            specialization,
            economist,
            mutableMapOf(),
            tags["links"]?.split(',').orEmpty().fold(mutableMapOf()) { map, el ->
                val (id, field) = el.split('=')
                map[id] = field
                map
            },
            tags
        )
    }

    inline operator fun String.invoke(vararg tags: Pair<String, String>, init: Pattern.() -> Unit = {}) =
            Pattern(this).also { pattern ->
                patterns.add(pattern)
                tags.forEach { pattern.tags[it.first] = it.second }
                pattern.init()
            }

    override fun toString() = format(true)

    fun toInlineString() = format(false)

    private fun format(prettyFormat: Boolean) = StringBuilder().apply { render(this, prettyFormat) }.toString().trim()

    private fun render(builder: StringBuilder, prettyFormat: Boolean, tab: String = "") {
        val lineEnd = if (prettyFormat) "\n" else ""
        val tags = if (tags.isEmpty()) "" else " " + tags.map { "${it.key}=\"${it.value}\"" }.joinToString(" ")

        builder.apply {
            append("$tab<$type$tags")
            if (patterns.isEmpty()) append("/>$lineEnd")
            else {
                append(">$lineEnd")
                patterns.forEach { it.render(this, prettyFormat, if (prettyFormat) "$tab\t" else "") }
                append("$tab</$type>$lineEnd")
            }
        }
    }

    fun copy(source: Node) {
        source.attributes?.apply {
            (0 until length).forEach {
                item(it).run { tags[nodeName] = nodeValue }
            }
        }
        source.childNodes?.apply {
            (0 until length).forEach {
                item(it).takeIf { it.nodeType == ELEMENT_NODE }?.run { nodeName().copy(this) }
            }
        }
    }

    private fun String.correct(): String {
        var startSpace = true
        val text = StringBuilder().also { s ->
            forEachIndexed { index, c ->
                if (startSpace && c != ' ') startSpace = false
                if (!startSpace) s.append(c)
                if (
                        c == '.' &&
                        index + 1 < length &&
                        !get(index + 1).isWhitespace() &&
                        !get(index + 1).isDigit()
                ) s.append(" ")
            }
        }.toString()

        return text
    }


    private fun parseText(text: String): ParseData {
        var temp = text.correct()
        val params = patterns.foldIndexed(mutableMapOf<String, String>()) { index, params, pattern ->
            val (txt, tmp) = nextBlock(pattern, temp, index)
            if (txt != null) params[pattern.type] = txt
            temp = tmp
            params
        }
        return data.copyWithParams(params)
    }

    fun parseParagraph(paragraph: XWPFParagraph) = parseParagraph(paragraph.text)

    fun parseParagraph(paragraph: String): List<ParseData> {
        var temp = paragraph
        return patterns.foldIndexed(mutableListOf<ParseData>()) { index, list, pattern ->
            val (txt, tmp) = nextBlock(pattern, temp, index)
            if (txt != null) list += pattern.parseText(txt)
            temp = tmp
            list
        }.collapse()
    }

    fun parseList(paragraphs: List<XWPFParagraph>): List<ParseData> {
        val list = paragraphs.mapIndexed { index, it ->
            parseParagraph(it).linkList(index)
        }.flatMap { it }

        return list.collapse().link()
    }

    fun parseMulti(paragraphs: List<XWPFParagraph>): List<ParseData> {
        val elements = paragraphs.toMutableList()
        return patterns.map {
            when (it.type) {
                "Paragraph" -> it.parseParagraph(elements[0]).apply { elements.removeAt(0) }
                "List" -> it.parseList(elements).apply { (0 until size - 1).forEach { elements.removeAt(0) } }
                else -> throw Throwable("Multi Parse Error")
            }
        }.flatMap { it }.collapse()
    }

    fun parseTable(table: XWPFTable): List<ParseData> {
        var ii = 0
        return table.rows.foldIndexed(mutableListOf<ParseData>()) { i, list, row ->
            if (skips.contains(i)) return@foldIndexed list
            list += row.tableCells.foldIndexed(mutableListOf<ParseData>()) { index, cells, cell ->
                cells += patterns[index].parseMulti(cell.paragraphs).linkTable(ii)
                cells
            }//.apply { println(joinToString("\n")) }
            ii++
            list
        }.collapse().link()
    }

    private var getFaculty = ""
    private var getSpecialization = ""
    private var getEconomist = ""

    fun parseSheet(sheet: XSSFSheet, shift: Pair<Int, Int>): List<ParseData> {
        getFaculty = sheet.getRow(1).getCell(4).stringCellValue
        getSpecialization = sheet.getRow(2).getCell(4).stringCellValue
        getEconomist = sheet.getRow(4).getCell(5).stringCellValue

        var index = 0
        return (shift.first..sheet.lastRowNum).fold(mutableListOf<ParseData>()) { rowList, rowIndex ->
            val row = sheet.getRow(rowIndex) ?: return@fold rowList
            val realRowIndex = rowIndex - shift.first
            if (skips.contains(realRowIndex)) return@fold rowList

            rowList += (shift.second until row.lastCellNum).fold(mutableListOf<ParseData>()) cells@{ cellList, cellIndex ->
                val cell = row.getCell(cellIndex) ?: return@cells cellList
                val i = cellIndex - shift.second
                if (i >= patterns.size) return@cells cellList

                cellList += patterns[i].parseParagraph(cell.toString()).linkTable(index)
                cellList
            }//.apply { println(joinToString("\n")) }

            index++
            rowList
        }.collapse().link()
    }

    private fun nextBlock(pattern: Pattern, temp: String, index: Int) =
            if (pattern.isSpace) null to temp.substringAfter(pattern.text)
            else (if (index < patterns.size - 1) temp.substringBefore(patterns[index + 1].text) else temp) to temp

    private fun List<ParseData>.linkList(index: Int) = apply {
        forEach {
            it.id = "${data.id}$index${it.id}"
            it.links = it.links.mapKeys { "${data.id}$index${it.key}" }.toMutableMap()
        }
    }

    private fun List<ParseData>.linkTable(index: Int) = apply {
        forEach {
            it.id = "$index${it.id}"
            it.links = it.links.mapKeys { "$index${it.key}" }.toMutableMap()
        }
    }

    private inline fun List<ParseData>.connect(map: ParseData.() -> Map<String, String>) =
            fold(mutableMapOf<String, String>()) { params, data ->
                data.map().forEach { key, value ->
                    if (params[key] == null) params[key] = value
                    else params[key] = "${params[key]}/$value"
                }
                params
            }

    private fun List<ParseData>.collapse(): List<ParseData> = groupBy { it.id }.map {
        it.value.run {
            if (size == 1) get(0)
            else ParseData(
                it.key,
                get(0).type,
                getFaculty,
                getSpecialization,
                getEconomist,
                connect { params },
                fold(mutableMapOf()) { init, el -> init += el.links;init },
                connect { tags }
            )
        }
    }

    private fun List<ParseData>.link(): List<ParseData> = toMutableList().apply {
        val links = fold(mutableMapOf<String, String>()) { map, el ->
            map += el.links
            map
        }
        add(
            ParseData(
                data.id,
                "List",
                "getFaculty",
                "getSpecialization",
                "getSpecialization",
                emptyMap(),
                map { it.id }
                    .filter { !links.containsKey(it) }
                    .foldIndexed(mutableMapOf()) { index, map, el ->
                        map[el] = index.toString()
                        map
                    },
                mutableMapOf()
            )
        )
    }
}