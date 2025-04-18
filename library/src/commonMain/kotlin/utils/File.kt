package io.github.kotlin.fibonacci.utils

import java.io.File

fun File.toKPath() = kotlinx.io.files.Path(path = this.absolutePath)