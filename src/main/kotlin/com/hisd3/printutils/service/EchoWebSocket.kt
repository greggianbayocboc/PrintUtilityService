package com.hisd3.printutils.service

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.hisd3.printutils.dto.HISD3Message
import com.hisd3.printutils.dto.MessageType
import com.hisd3.printutils.print.PrinterService
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue


/**
 * Created by albertoclarit on 5/23/17.
 */
@WebSocket
class EchoWebSocket{
    private val sessions = ConcurrentLinkedQueue<Any>()

    val logger = LoggerFactory.getLogger(EchoWebSocket::class.java)




    @OnWebSocketConnect
    fun connected(session: Session) {
        sessions.add(session)
        logger.info("Client ${session.remoteAddress}")
    }

    @OnWebSocketClose
    fun closed(session: Session, statusCode: Int, reason: String) {
        sessions.remove(session)
    }

    @OnWebSocketMessage
    @Throws(IOException::class)
    fun message(session: Session, message: String) {

        val gson = Gson()
        val message = gson.fromJson<HISD3Message>(message)


        val printerService = PrinterService()


        if(message.type == MessageType.GETPRINTERS){

            printerService.getPrinterNames()



        }

    }

}