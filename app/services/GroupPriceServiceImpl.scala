package services

import com.google.inject.Singleton
import model.{BestGroupPrice, CabinPrice, Rate}

@Singleton
class GroupPriceServiceImpl extends GroupPriceService {

  /**
   * Return the best price for each rate group.
   * @param rates a list of available rates.
   * @param prices a list of cabin prices.
   * @return the best price for each rate group.
   */
  def getBestGroupPrices(rates: Seq[Rate],
                         prices: Seq[CabinPrice]): Seq[BestGroupPrice] =
    prices.groupBy(_.cabinCode).flatMap { case (_, cabinPrices) =>
      cabinPrices
        .map { cp => (rates.find(_.rateCode == cp.rateCode), cp) }
        .filter(_._1.isDefined)
        .groupBy(_._1.get.rateGroup)
        .values
        .map(_.minBy(x => x._2.price))
        .map { e => BestGroupPrice(e._1.get, e._2) }
    }.toSeq
  
}
