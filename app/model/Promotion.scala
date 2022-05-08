package model

/**
 * Cruise bookings can have one or more promotions applied to them. But
 * sometimes a Promotion cannot be combined with another Promotion.
 * @param code
 * @param notCombinableWith
 */
case class Promotion (
  code: String,
  notCombinableWith: Seq[String]
)
