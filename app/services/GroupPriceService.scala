package services

import com.google.inject.ImplementedBy
import model.{BestGroupPrice, CabinPrice, Rate}

@ImplementedBy(classOf[GroupPriceServiceImpl])
trait GroupPriceService {
  
  def getBestGroupPrices(rates: Seq[Rate],
                         prices: Seq[CabinPrice]): Seq[BestGroupPrice]
  
}
