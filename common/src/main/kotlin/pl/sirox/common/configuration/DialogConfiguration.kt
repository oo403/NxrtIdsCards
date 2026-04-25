package pl.sirox.common.configuration

import eu.okaeri.configs.OkaeriConfig

class DialogConfiguration : OkaeriConfig() {

    var title: String = "Create your ID"
    var subtitle: String = "Enter your credentials"

    var nameTitle: String = "Name"
    var namePlaceholder: String = "Enter your name"

    var surnameTitle: String = "Surname"
    var surnamePlaceholder: String = "Enter your surname"

    var ageTitle: String = "Age"

}