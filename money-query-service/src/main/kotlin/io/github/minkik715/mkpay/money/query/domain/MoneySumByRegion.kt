package io.github.minkik715.mkpay.money.query.domain

class MoneySumByRegion private constructor(
    val moneySumByRegionId: String,
    val regionName: String,
    val moneySum: Long,
) {

    companion object {
        fun generateMoneySumByRegion(moneySumByRegionId: MoneySumByRegionId,
                                     regionName: RegionName,
                                     moneySum: MoneySum
                                     ): MoneySumByRegion {
            return MoneySumByRegion(
                moneySumByRegionId = moneySumByRegionId.moneySumByRegionId,
                regionName = regionName.regionName,
                moneySum = moneySum.moneySum
            )
        }
    }
}

@JvmInline
value class MoneySumByRegionId constructor(val moneySumByRegionId: String)
@JvmInline
value class RegionName constructor(val regionName: String)
@JvmInline
value class MoneySum constructor(val moneySum: Long)