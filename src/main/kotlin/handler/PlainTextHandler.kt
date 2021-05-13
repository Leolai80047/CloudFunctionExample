package handler

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import java.net.HttpURLConnection

class PlainTextHandler: HttpFunction {
    override fun service(request: HttpRequest, response: HttpResponse) {

        if (request.method != "GET") {
            with(response) {
                setStatusCode(HttpURLConnection.HTTP_BAD_METHOD)
                writer.write("Bad Method")
                return
            }
        }

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("plain/text")
            writer.write("Hello World")
        }
    }

}