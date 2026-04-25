package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.DialogBase
import io.papermc.paper.registry.data.dialog.body.DialogBody
import io.papermc.paper.registry.data.dialog.input.DialogInput
import io.papermc.paper.registry.data.dialog.type.DialogType
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import pl.sirox.common.configuration.DialogConfiguration

class DialogService @Inject constructor(
    private val dialogConfiguration: DialogConfiguration
) {

    private val mini = MiniMessage.miniMessage()
    private val title: String get() = dialogConfiguration.title
    private val subtitle: String get() = dialogConfiguration.subtitle

    private val nameTitle: String get() = dialogConfiguration.nameTitle
    private val namePlaceholder: String get() = dialogConfiguration.namePlaceholder

    private val surnameTitle: String get() = dialogConfiguration.surnameTitle
    private val surnamePlaceholder: String get() = dialogConfiguration.surnamePlaceholder
    private val ageTitle: String get() = dialogConfiguration.ageTitle

    fun showDialog(target: Player) {

        val dialog = Dialog.create { builder ->
            builder
                .empty()
                .type(DialogType.notice())
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
                                .labelFormat("%s: %s")
                                .build()
                        ))
                        .build()
                )
        }

        target.showDialog(dialog)
    }

}