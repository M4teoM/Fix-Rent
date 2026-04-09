package edu.javeriana.fixup.data.datasource

import edu.javeriana.fixup.ui.model.PropertyModel
import edu.javeriana.fixup.ui.model.ReviewModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class ReviewRequest(
    val userId: Int,
    val serviceId: Int,
    val rating: Int,
    val comment: String
)

interface FixUpApiService {
    @GET("api/services")
    suspend fun getServices(): List<PropertyModel>

    @GET("api/services/{id}")
    suspend fun getServiceById(@Path("id") id: Int): PropertyModel

    @POST("api/services")
    suspend fun createService(@Body service: PropertyModel): PropertyModel

    @GET("api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): Any

    @GET("api/reviews/service/{serviceId}")
    suspend fun getReviewsByServiceId(@Path("serviceId") serviceId: Int): List<ReviewModel>

    @GET("api/reviews/user/{userId}")
    suspend fun getReviewsByUserId(@Path("userId") userId: String): List<ReviewModel>

    @POST("api/reviews")
    suspend fun createReview(@Body review: ReviewRequest): ReviewModel
}
