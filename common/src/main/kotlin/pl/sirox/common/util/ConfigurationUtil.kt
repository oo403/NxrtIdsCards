package pl.sirox.common.util

import com.google.inject.Inject
import com.google.inject.Singleton
import pl.sirox.common.configuration.GeneralConfiguration
import pl.sirox.common.enums.ConfigurationFiles

@Singleton
class ConfigurationUtil @Inject constructor(
    private val generalConfiguration: GeneralConfiguration
) {

    private val configurations = mutableMapOf(
        ConfigurationFiles.GENERAL to generalConfiguration
    )

    fun reloadConfiguration(file: ConfigurationFiles) {
        configurations[file]?.load(true)
    }

    fun reloadConfigurations() {
        configurations.values.forEach { conf ->
            conf.load(true)
        }
    }

}