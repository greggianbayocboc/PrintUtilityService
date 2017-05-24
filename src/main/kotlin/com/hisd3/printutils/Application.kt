package com.hisd3.printutils

/**
 * Created by albertoclarit on 5/23/17.
 */
import com.hisd3.printutils.service.EchoWebSocket
import spark.Filter
import spark.Request
import spark.Response
import spark.Spark.*
import spark.Spark.initExceptionHandler
import com.google.gson.Gson
import com.hisd3.printutils.print.PrinterService


class Application


fun main(args:Array<String>){

    println("===============Starting HISD3 Print Utils===============")

    initExceptionHandler {
        e -> println(e.message)
        e.printStackTrace()
    }

    val gson = Gson()
    val printerService = PrinterService()
   // webSocket("/print", EchoWebSocket::class.java)

   // val filter :(Request,Response)-> Unit = { request, response -> response.header("Content-Encoding", "gzip") }

    after(Filter({ request, response ->
        response.header("Content-Encoding", "gzip")
    }))


    get("/ping"){  req,res -> "OK"}

    get("/printers",{
        req, res ->
        res.type("application/json")
        printerService.getPrinterNames()
    },gson::toJson)

}



