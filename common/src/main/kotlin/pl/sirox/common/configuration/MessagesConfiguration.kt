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

    var missingPermissionsMessage: Notice = Notice.builder()
        .chat("<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Missing permissions: {PERMISSIONS}")
        .build()

    var invalidUsageMessage: Notice = Notice.builder()
        .chat("<#AAAAAA>[<#F03C3C>✘<#AAAAAA>] Invalid usage: {ERROR}")
        .build()

    val showIdRequest: Notice = Notice.builder()
        .chat("<#AAAAAA>[<#5DF083>✔<#AAAAAA>] {PLAYER} wants to see your ID, type /showid accept to accept!")
        .build()
}