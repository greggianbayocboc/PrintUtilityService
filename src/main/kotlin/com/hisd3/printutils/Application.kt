package com.hisd3.printutils

/**
 * Created by albertoclarit on 5/23/17.
 */
import spark.Filter
import spark.Spark.*
import spark.Spark.initExceptionHandler
import com.google.gson.Gson
import com.hisd3.printutils.print.PrinterService


class Application


fun main(args:Array<String>){

    println("===============Starting HISD3 Print Utils===============")

    System.setProperty("java.awt.headless", "true")

    initExceptionHandler {
        e -> println(e.message)
        e.printStackTrace()
    }

    val gson = Gson()
    val printerService = PrinterService()
   // webSocket("/print", EchoWebSocket::class.java)

   // val filter :(Request,Response)-> Unit = { request, response -> response.header("Content-Encoding", "gzip") }


    enableCORS("*","GET,POST","")
    after(Filter({ request, response ->
        response.header("Content-Encoding", "gzip")
    }))


    get("/ping"){  req,res -> "OK"}

    get("/printers",{
        req, res ->
        res.type("application/json")
        printerService.getPrinterNames()
    },gson::toJson)


    post("/printondefault"){
        req, res ->
        res.type("application/json")
        printerService.rawprint("Zebra Technologies ZTC HC100-300dpi ZPL",req.bodyAsBytes())

        "OK"
    }


}

private fun enableCORS(origin: String, methods: String, headers: String) {

    options("/*") { request, response ->

        val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
        if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }

        val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
        if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }

        "OK"
    }

    before(Filter{ request, response ->
        response.header("Access-Control-Allow-Origin", origin)
        response.header("Access-Control-Request-Method", methods)
        response.header("Access-Control-Allow-Headers", headers)
        // Note: this may or may not be necessary in your particular application
        response.type("application/json")
    })
}

