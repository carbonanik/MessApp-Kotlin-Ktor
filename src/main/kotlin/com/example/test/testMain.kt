package com.example.test

import com.example.entity.Message
import com.example.entity.User
import com.example.serialization.messageToJson
import com.example.serialization.toJson
import org.bson.types.ObjectId


fun main() {
    val m = Message.TextMessage(
        ObjectId().toString(), 100, "Hi"
    ).apply {
        sender = User(
            name = "Anik",
            phone = "01766"
        )
        receiver = User(
            name = "Banana",
            phone = "014234"
        )
    }

    println(m.messageToJson())
}
//
//    val file = File("upload/sell.json")
//    val jData = file.bufferedReader().readText()
//    val allSells = jData.fromJson<Sells>()
//
//    val sellsByCustomer = separateSellsByCustomer(allSells.sell)
//
//    val customerList = mutableListOf<Customer>()
//
//    sellsByCustomer.forEach { (key, sellList) ->
//        val customer = Customer(key = key, name = sellList[0].customerName)
//
//        val oldestSell = oldestSell(sellList)
//        val newestSell = newestSell(sellList)
//
//        customer.createdAt = oldestSell.date
//        customer.lastEdited = dateStringToMillis(newestSell.date)
//        customer.totalDue = newestSell.afterDue
//        customer.totalTransaction = totalTransaction(sellList).toString()
//
//
//        customerList.add(customer)
//    }
////    customerList.forEach {
////        println("${it.name} ->")
////        println("==== Due: ${it.totalDue}")
////        println("==== Total Transaction: ${it.totalTransaction}")
////    }
//    val customers = Customers(customerList).toJson()
//    writeToFile("upload/customers.json", customers)
//
//    //#########################################
//}
//
//@Serializable
//data class Sells(
//    val sell: List<Sell>
//)
//
//@Serializable
//data class Customers(
//    val customer: List<Customer>
//)
//
//fun writeToFile(path: String, data: String) {
//    File(path).bufferedWriter().use { out ->
//        out.write(data)
//    }
//}
//
//fun newestSell(sellList: List<Sell>): Sell {
//    var newest = Sell()
//    var runningNew = Long.MIN_VALUE
//
//    sellList.forEach { sell ->
//        val timeInMillis = dateStringToMillis(sell.date)
//
//        if (timeInMillis > runningNew) {
//            runningNew = timeInMillis
//            newest = sell
//        }
//    }
//    return newest
//}
//
//fun oldestSell(sellList: List<Sell>): Sell {
//    var oldest = Sell()
//    var runningOld = Long.MAX_VALUE
//
//    sellList.forEach { sell ->
//        val timeInMillis = dateStringToMillis(sell.date)
//
//        if (timeInMillis < runningOld) {
//            runningOld = timeInMillis
//            oldest = sell
//        }
//    }
//    return oldest
//}
//
//fun dateStringToMillis(dateString: String): Long {
//    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    val date: Date = sdf.parse(dateString)
//    return date.time
//}
//
//fun separateSellsByCustomer(sellList: List<Sell>): MutableMap<String, MutableList<Sell>> {
//
//    val sellsByCustomer: MutableMap<String, MutableList<Sell>> = mutableMapOf()
//
//    sellList.forEach {
//        if (sellsByCustomer.contains(it.customerKey)) {
//            sellsByCustomer[it.customerKey]?.add(it)
//        } else {
//            sellsByCustomer[it.customerKey] = mutableListOf(it)
//        }
//    }
//    return sellsByCustomer
//}
//
//fun totalTransaction(sellList: List<Sell>): Int {
//    var total = 0
//    sellList.forEach { total += it.totalPrice.toInt() }
//    return total
//}

//fun findAllCustomerFromSellsList(){
//        val customerList = mutableListOf<Customer>()

//    sellsByCustomer.forEach { (key, value) ->
//        val customer = Customer(key = key, name = value[0].customerName)
//        customerList.add(customer)
//

//        val js = Sells(value).toJson()
//
//        File("upload/sells/$key.json").bufferedWriter().use { out ->
//            out.write(js)
//        }
//    }
//}