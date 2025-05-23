package com.example.mybaskettrainer.data.local

import com.example.mybaskettrainer.data.model.Event

object EventStorage {
    private val events = mutableListOf()
    private var nextId = 1

    fun getEvents(): List<Event> = events.toList()

    fun addEvent(event: Event) {
        events.add(event)
    }

    fun updateEvent(updatedEvent: Event) {
        val index = events.indexOfFirst { it.id == updatedEvent.id }
        if (index != -1) {
            events[index] = updatedEvent
        }
    }

    fun deleteEvent(eventId: Int) {
        events.removeIf { it.id == eventId }
    }

    fun generateEventId(): Int {
        val id = nextId
        nextId++
        return id
    }
}