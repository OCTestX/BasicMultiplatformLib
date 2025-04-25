package io.github.octestx.basic.multiplatform.common.utils

import io.klogging.noCoLogger
import kotlinx.io.RawSink
import kotlinx.io.RawSource
import kotlinx.io.files.SystemFileSystem
import java.io.File
import java.nio.file.Files

private val ologger = noCoLogger("OFileUtils")
fun File.toKPath() = kotlinx.io.files.Path(path = this.absolutePath)
fun File.linkDir(name: String): File = link(name).mustDir(createIfExists = true)
fun File.linkFile(name: String): File = link(name).mustFile(createIfExists = true)
fun File.link(name: String): File = File(this, name)
fun File.ifNotExits(block: (File) -> Unit): File {
    if (exists().not()) {
        block(this)
    }
    return this
}

fun File.listDirectory(): List<File> {
    return listFiles()?.filter { it.isDirectory() } ?: listOf()
}

fun File.listFileNotDir(): List<File> {
    return listFiles()?.filter { it.isFile() } ?: listOf()
}

fun File.mustFile(createIfExists: Boolean = false): File {
    if ((this.parentFile?.exists()?.not()) == true) this.parentFile?.mkdirs()
    if (exists().not() && createIfExists) {
        createNewFile()
        return this
    }
    if (exists() && isFile().not()) throw IllegalStateException("$this 不是文件")
    return this
}

fun File.mustDir(createIfExists: Boolean = false): File {
    if (exists().not() && createIfExists) {
        mkdirs()
        return this
    }
    if (exists() && isDirectory().not()) throw IllegalStateException("$this 不是文件夹")
    return this
}

fun File.rename(newName: String): File {
    val newPath = File(this.parent!!, newName)
    this.renameTo(newPath)
    return newPath
}

fun File.hidden() {
    if (this.isAbsolute.not()) throw UnsupportedOperationException("需要绝对路径")
    val path = this.toString()
    if (System.getProperty("os.name").lowercase().contains("win")) {
//        val attributes = Files.readAttributes(toPath(), DosFileAttributes::class.java)
        Files.setAttribute(File(path).toPath(), "dos:hidden", true)
        ologger.info("隐藏文件创建成功[Dos]")
    } else {
//        val attributes = Files.readAttributes(toPath(), PosixFileAttributes::class.java)
        Files.setAttribute(File(path).toPath(), "unix:hidden", true)
        ologger.info("隐藏文件创建成功[Unix]")
    }
}

fun File.formatFileSize(): String {
    return storage(this.length())
}

fun File.source(): RawSource {
    return SystemFileSystem.source(this.toKPath())
}

fun File.sink(): RawSink {
    return SystemFileSystem.sink(this.toKPath())
}