package pl.sirox.nxrtidscards.handler

import com.google.inject.Inject
import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invalidusage.InvalidUsage
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler
import dev.rollczi.litecommands.invocation.Invocation
import org.bukkit.command.CommandSender
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.service.MultificationService

class InvalidUsageHandler @Inject constructor(
    private val messagesConfiguration: MessagesConfiguration,
    private val multificationService: MultificationService
) : InvalidUsageHandler<CommandSender> {
    override fun handle(
        invocation: Invocation<CommandSender?>?,
        result: InvalidUsage<CommandSender?>?,
        chain: ResultHandlerChain<CommandSender?>?
    ) {
        multificationService.create()
            .viewer(invocation!!.sender())
            .notice(messagesConfiguration.invalidUsageMessage)
            .placeholder("{ERROR}", result?.cause?.name)
            .send()
    }
}