package app.illabo.dadatasuggestions.model

import app.illabo.dadatasuggestions.networking.ApiService
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call

class AddressSuggestionRequest(
    query: String,
    count: Int = 10,
    language: String? = null
) : DadataRequest<DadataAddressResponse> {
    @SerializedName("query")
    @Expose
    private var query: String = query

    @SerializedName("count")
    @Expose
    private var count: Int = count

    @SerializedName("language")
    @Expose
    private var language: String? = language

    @SerializedName("locations")
    @Expose
    private var constraints = mutableListOf<AddressSuggestionConstraint>()

    @SerializedName("locations")
    @Expose
    private var regionPriority = mutableListOf<RegionPriority>()

    @SerializedName("from_bound")
    @Expose
    private var upperBound: BoundConstraint? = null

    @SerializedName("to_bound")
    @Expose
    private var lowerBound: BoundConstraint? = null

    @SerializedName("restrict_value")
    @Expose
    private var trimRegion = false

    fun setQuery(query: String): AddressSuggestionRequest {
        this.query = query
        return this
    }

    fun setCount(count: Int): AddressSuggestionRequest {
        this.count = count
        return this
    }

    fun setLanguage(language: String?): AddressSuggestionRequest {
        if (language == null || listOf("en", "ru").contains(language)) {
            this.language = language
        }
        return this
    }

    fun addConstraint(constraint: AddressSuggestionConstraint?): AddressSuggestionRequest {
        if (constraint != null) {
            this.constraints.add(constraint)
        }
        return this
    }

    fun setConstraints(constraints: List<AddressSuggestionConstraint>): AddressSuggestionRequest {
        this.constraints = constraints as MutableList<AddressSuggestionConstraint>
        return this
    }

    fun addRegionPriority(priority: RegionPriority?): AddressSuggestionRequest {
        if (priority != null) {
            this.regionPriority.add(priority)
        }
        return this
    }

    fun setRegionPriorityList(priority: List<RegionPriority>): AddressSuggestionRequest {
        this.regionPriority = priority as MutableList<RegionPriority>
        return this
    }

    fun setUpperBound(bound: BoundLevel?): AddressSuggestionRequest {
        upperBound = if (bound != null){
            BoundConstraint(bound)
        } else {
            null
        }
        return this
    }

    fun setLowerBound(bound: BoundLevel?): AddressSuggestionRequest {
        lowerBound = if (bound != null){
            BoundConstraint(bound)
        } else {
            null
        }
        return this
    }

    fun shouldTrimRegion(on: Boolean): AddressSuggestionRequest {
        trimRegion = on
        return this
    }

    fun getQuery() = query
    fun getCount() = count
    fun getLanguage() = language
    fun getConstraints() = constraints
    fun getRegionPriority() = regionPriority
    fun getUpperBound() = upperBound
    fun getLowerBound() = lowerBound
    fun shouldTrimRegion() = trimRegion

    override fun buildCall(forClient: ApiService): Call<DadataAddressResponse> =
        forClient.getSuggestions(this)
}