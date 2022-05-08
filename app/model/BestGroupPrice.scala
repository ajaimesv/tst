package model

/**
 * Specific rates are grouped into a related rate group. There is a one-to-many
 * relationship between rate groups and rates (a rate group is made up of many
 * rates, but a rate can only belong to a single rate group). Some examples of
 * rate groups are: Standard, Military, Senior and Promotion.
 * @param cabinCode
 * @param rateCode
 * @param price
 * @param rateGroup
 */
case class BestGroupPrice(
  cabinCode: String,
  rateCode: String,
  price: BigDecimal,
  rateGroup: String
)

object BestGroupPrice {
  
  def apply(rate: Rate, cabinPrice: CabinPrice): BestGroupPrice =
    BestGroupPrice(
      cabinCode = cabinPrice.cabinCode,
      rateCode = cabinPrice.rateCode,
      price = cabinPrice.price,
      rateGroup = rate.rateGroup
    )
  
}
