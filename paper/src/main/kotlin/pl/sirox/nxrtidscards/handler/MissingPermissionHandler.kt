package pl.sirox.nxrtidscards.handler

import com.google.inject.Inject
import dev.rollczi.litecommands.handler.result.ResultHandlerChain
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.permission.MissingPermissions
import dev.rollczi.litecommands.permission.MissingPermissionsHandler
import org.bukkit.command.CommandSender
import pl.sirox.common.configuration.MessagesConfiguration
import pl.sirox.common.service.MultificationService

class MissingPermissionHandler @Inject constructor(
    private val messagesConfiguration: MessagesConfiguration,
    private val multificationService: MultificationService
) : MissingPermissionsHandler<CommandSender> {

    override fun handle(
        invocation: Invocation<CommandSender?>?,
        missingPermissions: MissingPermissions?,
        chain: ResultHandlerChain<CommandSender?>?
    ) {
        multificationService.create()
            .viewer(invocation!!.sender())
            .notice(messagesConfiguration.missingPermissionsMessage)
            .placeholder("{PERMISSIONS}", missingPermissions!!.permissions.joinToString(", "))
            .send()
    }

}