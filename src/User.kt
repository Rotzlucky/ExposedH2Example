package com.example

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object Users : IdTable<String>("users") {
    override var id: Column<EntityID<String>> = varchar("id", 190).entityId()

    override val primaryKey = PrimaryKey(id)
}