package io.github.kotlin.fibonacci.utils

import io.klogging.noCoLogger
import kotlinx.io.*
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import java.io.File
import java.nio.file.Files

private val ologger = noCoLogger("OFileUtils")
fun Path.linkDir(name: String): Path = link(name).mustDir(createIfExists = true)
fun Path.linkFile(name: String): Path = link(name).mustFile(createIfExists = true)
fun Path.link(name: String): Path = Path(this, name)
fun Path.exists() = SystemFileSystem.exists(this)
fun Path.ifNotExits(block: (Path) -> Unit): Path {
    if (exists().not()) {
        block(this)
    }
    return this
}
fun Path.listFiles(): List<Path>? {
    try {
        return SystemFileSystem.list(this).toList()
    } catch (e: Exception) {
        ologger.warn(e, "listFiles")
        return null
    }
}
fun Path.isDirectory(): Boolean {
    return SystemFileSystem.metadataOrNull(this)?.isDirectory?:throw IOException("metadataOrNull")
}
fun Path.listDirectory(): List<Path> {
    return listFiles()?.filter { it.isDirectory() }?: listOf()
}
fun Path.isFile(): Boolean {
    return SystemFileSystem.metadataOrNull(this)?.isRegularFile?:throw IOException("metadataOrNull")
}
fun Path.listFileNotDir(): List<Path> {
    return listFiles()?.filter { it.isFile() }?: listOf()
}
fun Path.mkdirs() {
    SystemFileSystem.createDirectories(this, mustCreate = false)
}
fun Path.createNewFile() {
    SystemFileSystem.sink(this).close()
}
fun Path.mustFile( createIfExists: Boolean = false): Path {
    if ((this.parent?.exists()?.not()) == true) this.parent?.mkdirs()
    if (exists().not() && createIfExists) {
        createNewFile()
        return this
    }
    if (exists() && isFile().not()) throw  IllegalStateException("$this 不是文件")
    return this
}
fun Path.mustDir( createIfExists: Boolean = false): Path {
    if (exists().not() && createIfExists) {
        mkdirs()
        return this
    }
    if (exists() && isDirectory().not()) throw  IllegalStateException("$this 不是文件夹")
    return this
}
fun Path.rename( newName: String): Path {
    val newPath = Path(this.parent!!, newName)
    SystemFileSystem.atomicMove(this, newPath)
    return newPath
}
fun Path.hidden() {
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
fun Path.formatFileSize(): String {
    return Utils.formatFileSize(SystemFileSystem.metadataOrNull(this)!!.size)
}
fun Path.source(): RawSource {
    return SystemFileSystem.source(this)
}
fun Path.sink(): RawSink {
    return SystemFileSystem.sink(this)
}

fun String.asFilePath() = Path(this)