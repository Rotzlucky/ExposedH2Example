package com.example

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

class User(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, User>(Users)

    var score by Users.score
}

object Users : IdTable<String>("users") {
    override var id: Column<EntityID<String>> = varchar("id", 190).entityId()

    var score: Column<Long> = long("score").default(0)

    override val primaryKey = PrimaryKey(id)
}