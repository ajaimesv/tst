package services

import model.{BestGroupPrice, CabinPrice, Rate}
import org.specs2.mutable._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class GroupPriceServiceSpec extends Specification {

  import GroupPriceServiceSpec._

  val app: Application = new GuiceApplicationBuilder().build()
  val service: GroupPriceService = app.injector.instanceOf[GroupPriceService]
  
  "getBestGroupPrices" should {
    
    "get a valid list of prices" in {
      val results = service.getBestGroupPrices(rates, cabinPrices)
      results must have size 4
      results must contain(BestGroupPrice("CB", "M1", 230, "Military"))
      results must contain(BestGroupPrice("CB", "S1", 245, "Senior"))
      results must contain(BestGroupPrice("CA", "M1", 200, "Military"))
      results must contain(BestGroupPrice("CA", "S1", 225, "Senior"))
    }

    "get an empty list if no rates are available" in {
      val results = service.getBestGroupPrices(Nil, cabinPrices)
      results must have size 0
    }

    "get an empty list if no prices are available" in {
      val results = service.getBestGroupPrices(rates, Nil)
      results must have size 0
    }

    "get an empty list if no rates nor prices are available" in {
      val results = service.getBestGroupPrices(Nil, Nil)
      results must have size 0
    }
    
    "get an empty list if no rates can be matched" in {
      val results = service.getBestGroupPrices(rates, cabinPricesNonMatchingRate)
      results must have size 0
    }

    "get a valid list of prices when there are duplicate prices" in {
      val results = service.getBestGroupPrices(rates, cabinPricesDuplicate)
      results must have size 1
      results must contain(BestGroupPrice("CA", "M1", 200, "Military"))
    }
    
  }
  
}

object GroupPriceServiceSpec {

  val rates: List[Rate] = List(
    Rate("M1", "Military"),
    Rate("M2", "Military"),
    Rate("S1", "Senior"),
    Rate("S2", "Senior")
  )

  val cabinPrices: List[CabinPrice] = List(
    CabinPrice("CA", "M1", 200),
    CabinPrice("CA", "M2", 250),
    CabinPrice("CA", "S1", 225),
    CabinPrice("CA", "S2", 260),
    CabinPrice("CB", "M1", 230),
    CabinPrice("CB", "M2", 260),
    CabinPrice("CB", "S1", 245),
    CabinPrice("CB", "S2", 270)
  )

  val cabinPricesNonMatchingRate: List[CabinPrice] = List(
    CabinPrice("CA", "Other", 200),
  )
  
  val cabinPricesDuplicate: List[CabinPrice] = List(
    CabinPrice("CA", "M1", 200),
    CabinPrice("CA", "M1", 200),
    CabinPrice("CA", "M1", 200),
  )
  
}
