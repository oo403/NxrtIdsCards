package pl.sirox.nxrtidscards.argument

import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import org.bukkit.command.CommandSender
import pl.sirox.common.enums.ConfigurationFiles

class ConfigurationFilesArgument : ArgumentResolver<CommandSender, ConfigurationFiles?>() {

    override fun parse(
        invocation: Invocation<CommandSender?>?,
        context: Argument<ConfigurationFiles?>?,
        argument: String?
    ): ParseResult<ConfigurationFiles?> {
        val configurationFile = ConfigurationFiles.valueOf(argument!!)

        return ParseResult.success(configurationFile)
    }

    override fun suggest(
        invocation: Invocation<CommandSender?>?,
        argument: Argument<ConfigurationFiles?>?,
        context: SuggestionContext?
    ): SuggestionResult? {
        return SuggestionResult.of(ConfigurationFiles.entries.map { it.name })
    }

}