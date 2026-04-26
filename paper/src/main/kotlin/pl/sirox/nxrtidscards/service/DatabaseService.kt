package pl.sirox.nxrtidscards.service

import com.google.inject.Inject
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import pl.sirox.common.configuration.DatabaseConfiguration
import pl.sirox.common.logging.LoggerFactory
import pl.sirox.common.logging.logger
import pl.sirox.nxrtidscards.bootstrap.Bootstrap
import pl.sirox.nxrtidscards.data.PlayerData
import java.sql.SQLException

class DatabaseService @Inject constructor(
    private val databaseConfiguration: DatabaseConfiguration,
    private val playerService: PlayerService,
    private val loggerFactory: LoggerFactory,
    private val plugin: Bootstrap
) {

    private val dataSource: HikariDataSource = register()

    private val logger = loggerFactory.logger<DatabaseService>()

    fun register(): HikariDataSource {

        val databaseType = databaseConfiguration.databaseCredential.type
        val databaseHost = databaseConfiguration.databaseCredential.host
        val databasePort = databaseConfiguration.databaseCredential.port
        val databaseName = databaseConfiguration.databaseCredential.database
        val databaseUsername = databaseConfiguration.databaseCredential.username
        val databasePassword = databaseConfiguration.databaseCredential.password

        val config = HikariConfig().apply {
            jdbcUrl = if (databaseType == "h2") {
                "jdbc:h2:./plugins/NxrtIdsCards/database/$databaseName"
            } else {
                "jdbc:$databaseType://$databaseHost:$databasePort/$databaseName"
            }
            username = databaseUsername
            password = databasePassword
            maximumPoolSize = 3
            isAutoCommit = true
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            driverClassName = when (databaseType) {
                "h2" -> "org.h2.Driver"
                "mysql" -> "com.mysql.cj.jdbc.Driver"
                "postgresql" -> "org.postgresql.Driver"
                "mariadb" -> "org.mariadb.jdbc.Driver"
                else -> throw IllegalArgumentException("Unsupported DB type: $databaseType")
            }

            validate()
        }

        return HikariDataSource(config)
    }

    fun createTables() {

        val tableName = databaseConfiguration.table

        val sql = """
            CREATE TABLE IF NOT EXISTS ${tableName}_players (
            uuid VARCHAR(36) PRIMARY KEY,
            name VARCHAR(32),
            surname VARCHAR(32),
            age INT
            );
        """.trimIndent()

        try {
            val connection = dataSource.connection

            connection.createStatement().execute(sql)
            connection.commit()

        } catch (e: SQLException) {
            logger.error("Failed to create tables!", e)
            e.printStackTrace()
        }
    }

    fun addPlayer(uuid: String, name: String, surname: String, age: Int) {
        val tableName = databaseConfiguration.table

        val sql = "MERGE INTO ${tableName}_players (uuid, name, surname, age) KEY(uuid) VALUES (?, ?, ?, ?)"

        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, uuid)
                    stmt.setString(2, name)
                    stmt.setString(3, surname)
                    stmt.setInt(4, age)

                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Failed to add player to database!", e)
            e.printStackTrace()
        }
    }

    fun removePlayer(uuid: String) {
        val tableName = databaseConfiguration.table
        val sql = "DELETE FROM ${tableName}_players WHERE uuid = ?"

        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, uuid)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Failed to remove player from database!", e)
            e.printStackTrace()
        }
    }

    fun getPlayer(uuid: String): PlayerData? {
        val tableName = databaseConfiguration.table
        val sql = "SELECT * FROM ${tableName}_players WHERE uuid = ?"

        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, uuid)

                    val resultSet = statement.executeQuery()

                    if (resultSet.next()) {
                        val name = resultSet.getString("name")
                        val surname = resultSet.getString("surname")
                        val age = resultSet.getInt("age")

                        return PlayerData(name, surname, age)
                    }
                }
            }
        } catch (e: SQLException) {
            logger.error("Failed to get player from database!", e)
            e.printStackTrace()
        }

        return null
    }

    fun autoDataSave() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            val players = Bukkit.getOnlinePlayers()

            players.forEach { player ->
                val uuid = player.uniqueId.toString()
                val name = playerService.getName(player.uniqueId)
                val surname = playerService.getSurname(player.uniqueId)
                val age = playerService.getAge(player.uniqueId)

                if (name != null && surname != null && age != null) {
                    addPlayer(uuid, name, surname, age)
                }
            }
        }, 0L, 12000L)
    }

    fun unregister() {
        dataSource.close()
    }

}