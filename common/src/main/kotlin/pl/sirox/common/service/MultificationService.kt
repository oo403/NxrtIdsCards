package pl.sirox.common.service

import com.eternalcode.multification.adventure.AudienceConverter
import com.eternalcode.multification.paper.PaperMultification
import com.eternalcode.multification.translation.TranslationProvider
import com.google.inject.Inject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.ComponentSerializer
import org.bukkit.command.CommandSender
import pl.sirox.common.configuration.MessagesConfiguration


class MultificationService @Inject constructor(
    private val messagesConfig: MessagesConfiguration
) : PaperMultification<MessagesConfiguration>() {

    private val miniMessage = MiniMessage.miniMessage()

    protected override fun translationProvider(): TranslationProvider<MessagesConfiguration> {
        return TranslationProvider { locale -> this.messagesConfig }
    }

    protected override fun serializer(): ComponentSerializer<Component?, Component?, String?> {
        return this.miniMessage
    }

    protected override fun audienceConverter(): AudienceConverter<CommandSender?> {
        return AudienceConverter { commandSender: CommandSender? -> commandSender }
    }

}