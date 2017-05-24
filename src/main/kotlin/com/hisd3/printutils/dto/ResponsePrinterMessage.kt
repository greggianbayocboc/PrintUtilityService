package com.hisd3.printutils.dto

/**
 * Created by albertoclarit on 5/23/17.
 */
class ResponsePrinterMessage(
        type: MessageType,
        var printers:List<String>) : HISD3Message(type)