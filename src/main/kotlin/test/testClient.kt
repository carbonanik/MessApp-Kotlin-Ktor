package test


//fun main() {
//    val client = HttpClient(CIO) {
//        install(JsonFeature) {
//            serializer = KotlinxSerializer()
//        }
//    }
//
//    runBlocking {
//        withContext(Dispatchers.Default){
//            try {
//                val file = File("upload/ktor_logo.png").readBytes()
//                val response2: String = client.submitFormWithBinaryData(
//                    url = "http://localhost:8080/upload",
//                    formData = formData {
//                        append("description", "Ktor logo")
//                        append("image", file, Headers.build {
//                            append(HttpHeaders.ContentType, "image/png")
//                            append(HttpHeaders.ContentDisposition, "filename=ktor_logo.png")
//                        })
//                    }
//                ) {
//                    onUpload { bytesSentTotal, contentLength ->
//                        println("Sent $bytesSentTotal bytes from $contentLength")
//                    }
//                }
//
//                println(response2)
//            } catch (e: Exception){
//                println(e.message)
//            }
//        }
//    }
//    client.close()
//}

