package handler

import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import model.News
import java.net.HttpURLConnection

class SimpleJsonHandler: HttpFunction {
    override fun service(request: HttpRequest, response: HttpResponse) {

        if (request.method != "GET") {
            with(response) {
                setStatusCode(HttpURLConnection.HTTP_BAD_METHOD)
                writer.write("Bad method")
                return
            }
        }

        val data = News()

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            writer.write("")
        }
    }
}