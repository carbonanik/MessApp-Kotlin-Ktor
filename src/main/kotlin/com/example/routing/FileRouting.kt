package com.example.routing

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.ContentDisposition.Companion.File
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.util.*

fun Route.fileRouting(){
    route("/upload") {
        var fileDescription = ""
        var fileName = ""

        post {
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                println(part.name)
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                        println("value"+part.value)
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        println("filename"+fileName)
                        val fileBytes = part.streamProvider().readBytes()
                        println(fileBytes.toString())
                        File("upload/${UUID.randomUUID()}.png").writeBytes(fileBytes)
                    }
                }
            }

            call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
        }
    }

//    route("/uploads"){
////        authenticate {
//            post("/photo") {
//                println("photo==")
//                var fileDescription = ""
//                var fileName = ""
//                val multipartData = call.receiveMultipart()
//
//                multipartData.forEachPart { part ->
//                    when (part) {
//                        is PartData.FormItem -> {
//                            fileDescription = part.value
//                        }
//                        is PartData.FileItem -> {
//                            fileName = part.originalFileName as String
//                            val fileBytes = part.streamProvider().readBytes()
//                            File("uploads/$fileName").writeBytes(fileBytes)
//                        }
//                        is PartData.BinaryItem -> {}
//                    }
//                }
//
//                call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
//            }
//        }
//    }
}