package com.lgbtplustech.events

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<EventsApiApplication>().with(TestcontainersConfiguration::class).run(*args)
}
