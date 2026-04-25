package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.dialog.DialogResponseView
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase
import io.papermc.paper.registry.data.dialog.action.DialogAction
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback
import io.papermc.paper.registry.data.dialog.body.DialogBody
import io.papermc.paper.registry.data.dialog.input.DialogInput
import io.papermc.paper.registry.data.dialog.type.DialogType
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.event.ClickCallback
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import pl.sirox.common.configuration.DialogConfiguration


class DialogService @Inject constructor(
    private val dialogConfiguration: DialogConfiguration,
    private val playerService: PlayerService
) {

    private val mini = MiniMessage.miniMessage()
    private val title: String get() = dialogConfiguration.title
    private val subtitle: String get() = dialogConfiguration.subtitle

    private val nameTitle: String get() = dialogConfiguration.nameTitle
    private val namePlaceholder: String get() = dialogConfiguration.namePlaceholder

    private val surnameTitle: String get() = dialogConfiguration.surnameTitle
    private val surnamePlaceholder: String get() = dialogConfiguration.surnamePlaceholder

    private val ageTitle: String get() = dialogConfiguration.ageTitle
    private val agePlaceholder: String get() = dialogConfiguration.agePlaceholder

    private val button: String get() = dialogConfiguration.button
    private val buttonTooltip: String get() = dialogConfiguration.buttonTooltip

    fun showDialog(target: Player) {

        val dialog = Dialog.create { builder ->
            builder
                .empty()
                .type(DialogType.notice(
                    ActionButton.create(
                        mini.deserialize(button),
                        mini.deserialize(buttonTooltip),
                        100,
                        DialogAction.customClick(
                            DialogActionCallback { view: DialogResponseView?, audience: Audience? ->
                                val name = view!!.getText("name") ?: "Unknown"
                                val surname = view!!.getText("surname") ?: "Unknown"
                                val age = view!!.getFloat("age")?.toInt() ?: 0

                                playerService.setName(target.uniqueId, name)
                                playerService.setSurname(target.uniqueId, surname)
                                playerService.setAge(target.uniqueId, age)
                            },
                            ClickCallback.Options.builder()
                                .uses(100)
                                .lifetime(ClickCallback.DEFAULT_LIFETIME)
                                .build()
                        )
                    )
                ))
                .base(
                    DialogBase.builder(mini.deserialize(title))
                        .body(listOf(
                            DialogBody.plainMessage(mini.deserialize(subtitle))
                        ))
                        .inputs(listOf(
                            DialogInput.text("name", mini.deserialize(nameTitle))
                                .initial(namePlaceholder)
                                .build(),
                            DialogInput.text("surname", mini.deserialize(surnameTitle))
                                .initial(surnamePlaceholder)
                                .build(),
                            DialogInput.numberRange("age", mini.deserialize(ageTitle), 18F, 100F)
                                .step(1F)
                                .initial(18F)
                                .width(300)
                                .labelFormat(agePlaceholder)
                                .build()
                        ))
                        .build()
                )
        }

        if (playerService.getName(target.uniqueId) == null) {
            target.showDialog(dialog)
        } else {
            return
        }
    }

}