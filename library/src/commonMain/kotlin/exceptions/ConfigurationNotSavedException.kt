package io.github.octestx.basic.multiplatform.common.exceptions

/**
 * Common unsaved configuration exception type.
 * When detecting unsaved configuration changes,
 * should display user alert via sendExceptionToast
 * and log this exception through io.klogging library.
 */
class ConfigurationNotSavedException : Exception("Configuration not saved")