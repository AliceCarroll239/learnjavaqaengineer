package data

import com.google.gson.annotations.SerializedName

class TriangleDataClass(
    @SerializedName("data")
    val `data`: Data
)

data class Data(
    @SerializedName("bugs")
    val bugs: List<Bug>,
    @SerializedName("cases")
    val cases: List<Case>
)

data class Bug(
    @SerializedName("A")
    val a: String,
    @SerializedName("B")
    val b: String,
    @SerializedName("C")
    val c: String,
    @SerializedName("expectedResult")
    val expectedResult: String
)

data class Case(
    @SerializedName("A")
    val a: String,
    @SerializedName("B")
    val b: String,
    @SerializedName("C")
    val c: String,
    @SerializedName("expectedResult")
    val expectedResult: String
)