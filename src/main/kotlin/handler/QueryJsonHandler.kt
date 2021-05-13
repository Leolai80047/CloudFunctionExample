package handler

import com.github.javafaker.Faker
import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import model.News
import java.net.HttpURLConnection
import java.util.*

class QueryJsonHandler: HttpFunction {
    override fun service(request: HttpRequest, response: HttpResponse) {

        if (request.method != "GET") {
            with(response) {
                setStatusCode(HttpURLConnection.HTTP_BAD_METHOD)
                writer.write("Bad method")
                return
            }
        }

        val limit = request.queryParameters["limit"]
        val faker = Faker.instance(Locale.ENGLISH)

        val data = News(
            id = "",
            title = faker.lorem().sentence(),
            author = faker.name().fullName(),
            content = faker.lorem().paragraph()
        )

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            setContentType("application/json")
            writer.write("")
        }
    }
}