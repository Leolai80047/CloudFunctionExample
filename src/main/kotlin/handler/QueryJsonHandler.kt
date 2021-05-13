package handler

import com.github.javafaker.Faker
import com.google.cloud.functions.HttpFunction
import com.google.cloud.functions.HttpRequest
import com.google.cloud.functions.HttpResponse
import com.google.gson.GsonBuilder
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

        val limit = request.queryParameters["limit"]?.first()?.toIntOrNull()?: 10
        val faker = Faker.instance(Locale.ENGLISH)

        val data = (1..limit).map { index ->
            News(
                id = index.toString(),
                title = faker.lorem().sentence(),
                author = faker.name().fullName(),
                content = faker.lorem().paragraph()
            )
        }

        val gson = GsonBuilder().setPrettyPrinting().create()

        with(response) {
            setStatusCode(HttpURLConnection.HTTP_OK)
            writer.write(
                gson.toJson(
                    mapOf("data" to  data)
                )
            )
        }
    }
}