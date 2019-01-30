
package com.hisd3.printutils.print

import com.hisd3.printutils.dto.PrinterDetail
import javax.print.PrintServiceLookup
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.SimpleDoc
import javax.print.DocFlavor
import javax.print.attribute.standard.PrinterName
import javax.print.attribute.HashPrintServiceAttributeSet
import java.io.ByteArrayInputStream




/**
 * Created by albertoclarit on 5/23/17.
 */
class PrinterService{

    fun rawprint(printerName: String, content: ByteArray): String {
        var res = ""
        val printServiceAttributeSet = HashPrintServiceAttributeSet()
        printServiceAttributeSet.add(PrinterName(printerName, null))
        //val printServices = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet)
        //val printServices = PrintServiceLookup.lookupPrintServices(null, null)

       /* if (printServices.size < 1) {
            println("Can't  select printer :" + printerName)
            return "Can't  select printer :" + printerName
        }*/
        val printdata = content
        val pservice = PrintServiceLookup.lookupDefaultPrintService()   //printServices[5]
        val job = pservice.createPrintJob()
        val flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE

       // val `is` = ByteArrayInputStream(printdata)
        //val flavor = DocFlavor.INPUT_STREAM.AUTOSENSE


        val doc = SimpleDoc(printdata, flavor, null)
        val aset = HashPrintRequestAttributeSet()
        try {
            job.print(doc, null)
        } catch (e: Exception) {
            res = e.message?:""

        }

        return res
    }

    fun getPrinterNames(): List<PrinterDetail>{
       val printers = mutableListOf<PrinterDetail>()


        //https://stackoverflow.com/questions/13453025/correct-way-to-send-escape-codes-raw-data-to-printer
        val pservices = PrintServiceLookup.lookupPrintServices(null,null)

        val defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        for(pservice in pservices){

            val  p =   PrinterDetail( pservice.name, pservice.name,
                    "",
                    defaultPrintService.name == pservice.name,
                    "")
            printers.add(p)
        }



        /*if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {

            val cupsClient = CupsClient("localhost", 631)


            val cupsPrinters = cupsClient.printers

            for (cupPrinter in cupsPrinters) {
                val p = PrinterDetail(cupPrinter.description ?: "", cupPrinter.name ?: "",
                        cupPrinter.location ?: "",
                        cupPrinter.isDefault ?: false,
                        cupPrinter.printerURL.toString())
                printers.add(p)
            }


        } else if (SystemUtils.IS_OS_WINDOWS) {

        }
*/

        return printers;
    }

}
