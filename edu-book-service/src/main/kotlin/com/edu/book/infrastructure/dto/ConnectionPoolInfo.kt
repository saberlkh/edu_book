package com.edu.book.infrastructure.dto

class ConnectionPoolInfo {

    var totalConnections: Int = 0
    var activeConnections: Int = 0
    var idleConnections: Int = 0
    var threadsAwaitingConnection: Int = 0

    constructor()

    constructor(totalConnections: Int,
                activeConnections: Int,
                idleConnections: Int,
                threadsAwaitingConnection: Int) {
        this.totalConnections = totalConnections
        this.activeConnections = activeConnections
        this.idleConnections = idleConnections
        this.threadsAwaitingConnection = threadsAwaitingConnection
    }
}