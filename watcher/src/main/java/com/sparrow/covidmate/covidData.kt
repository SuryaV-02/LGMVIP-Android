package com.sparrow.covidmate

class covidData {
    var districtName : String? = null
    var active : Long? = null
    var confirmed : Long? = null
    var recovered : Long? = null
    var deceased : Long? = null

    constructor(districtName : String="",confirmed: Long = 0 , active : Long = 0, recovered : Long = 0, Deceased : Long = 0){
        this.districtName = districtName
        this.confirmed = confirmed
        this.active = active
        this.recovered = recovered
        this.deceased = deceased
    }
}