package model

/**
 * A rate is a way to group related prices together. A rate is defined by its
 * Rate Code and which Rate Group it belongs to. For example, (MilAB, Military)
 * and (Sen123, Senior).
 * @param rateCode
 * @param rateGroup
 */
case class Rate(
  rateCode: String,
  rateGroup: String
)
