package pl.sirox.common.configuration

import eu.okaeri.configs.OkaeriConfig

class DatabaseConfiguration : OkaeriConfig() {

    var table: String = "nxrtidscards"

    var databaseCredential: Database = Database()

    class Database : OkaeriConfig() {
        var type: String = "h2"
        var host: String = "localhost"
        var port: Int = 3306
        var database: String = "nxrtidscards"
        var username: String = "root"
        var password: String = ""
    }
}