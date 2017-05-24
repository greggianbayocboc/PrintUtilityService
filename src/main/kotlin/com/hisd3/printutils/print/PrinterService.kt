
package com.hisd3.printutils.print

import com.hisd3.printutils.dto.PrinterDetail
import de.spqrinfo.cups4j.CupsClient
import org.apache.commons.lang3.SystemUtils

/**
 * Created by albertoclarit on 5/23/17.
 */
class PrinterService{


    fun getPrinterNames(): List<PrinterDetail>{
       val printers = mutableListOf<PrinterDetail>()


        if(SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC){

            val cupsClient  = CupsClient("localhost",631)


            val cupsPrinters = cupsClient.printers

            for(cupPrinter in cupsPrinters){
             val  p =   PrinterDetail(cupPrinter.description?:"",cupPrinter.name?:"",
                         cupPrinter.location?:"",
                        cupPrinter.isDefault?:false,
                     cupPrinter.printerURL.toString())
                printers.add(p)
            }

        }
        else if(SystemUtils.IS_OS_WINDOWS) {

        }


        return printers;
    }

}
