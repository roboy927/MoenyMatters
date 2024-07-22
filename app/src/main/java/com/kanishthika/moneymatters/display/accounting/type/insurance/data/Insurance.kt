package com.kanishthika.moneymatters.display.accounting.type.insurance.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kanishthika.moneymatters.display.accounting.data.FinancialItem

@Entity(tableName = "insurance_list")
data class Insurance(
    @PrimaryKey (autoGenerate = true)
    override val id: Int = 1,
    val insuranceType: String,
    val paidAmount: Double,
    val providerName: String,
    val policyNumber: Int,
    val premiumAmount: Double,
    val startDate: String,
    val maturityDate: String,
    val sumAssured: Double,
    val premiumMode: String
) : FinancialItem {
    override val name: String
        get() = "$providerName $policyNumber"
    override val amount: Double
        get() = paidAmount
    override val description: String
        get() = "$insuranceType Insurance, Premium: $premiumAmount,"
}
