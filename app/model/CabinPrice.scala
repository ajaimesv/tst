package model

/**
 * The price for a specific cabin on a specific cruise. All cabin prices will
 * have a single rate attached.
 * @param cabinCode
 * @param rateCode
 * @param price
 */
case class CabinPrice(
  cabinCode: String,
  rateCode: String,
  price: BigDecimal
)
