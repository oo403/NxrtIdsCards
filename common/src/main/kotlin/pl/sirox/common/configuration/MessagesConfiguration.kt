package pl.sirox.common.configuration

import com.eternalcode.multification.notice.Notice
import eu.okaeri.configs.OkaeriConfig

class MessagesConfiguration : OkaeriConfig() {

    var reloadAllMessage: Notice = Notice.builder()
        .chat("<#AAAAAA>[<#5DF083>✔<#AAAAAA>] All configs has been reloaded!")
        .build()

    var reloadMessage: Notice = Notice.builder()
        .chat("<#AAAAAA>[<#5DF083>✔<#AAAAAA>] Reloaded {CONFIG} config!")
        .build()
}