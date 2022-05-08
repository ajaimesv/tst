package services

import model.{Promotion, PromotionCombo}
import org.specs2.mutable._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class PromotionServiceSpec extends Specification {

  import PromotionServiceSpec._

  val app: Application = new GuiceApplicationBuilder().build()
  val service: PromotionService = app.injector.instanceOf[PromotionService]
  
  "combinablePromotions" should {
    "get all combinations when an empty code is passed" in {
      val results = service.combinablePromotions("", promotions)
      results must have size 4
      results must contain(PromotionCombo(List("P1", "P2")))
      results must contain(PromotionCombo(List("P3", "P4", "P5")))
      results must contain(PromotionCombo(List("P1", "P4", "P5")))
      results must contain(PromotionCombo(List("P2", "P3")))
    }

    "get all combinations when a null code is passed" in {
      val results = service.combinablePromotions(null, promotions)
      results must have size 4
      results must contain(PromotionCombo(List("P1", "P2")))
      results must contain(PromotionCombo(List("P3", "P4", "P5")))
      results must contain(PromotionCombo(List("P1", "P4", "P5")))
      results must contain(PromotionCombo(List("P2", "P3")))
    }

    "get an empty combo when no promotions are provided" in {
      val results = service.combinablePromotions("", Nil)
      results must have size 1
      results must contain(PromotionCombo(List()))
    }

    "get an empty combo when a code is provided and no promotions are provided " in {
      val results = service.combinablePromotions("", Nil)
      results must have size 1
      results must contain(PromotionCombo(List()))
    }

    "get specific combinations for a specific promotion code" in {
      val results = service.combinablePromotions("P1", promotions)
      results must have size 2
      results must contain(PromotionCombo(List("P1", "P2")))
      results must contain(PromotionCombo(List("P1", "P4", "P5")))
    }

    "get a valid result when the provided promotions have no restrictions" in {
      val results = service.combinablePromotions("", promotionsNoRestrictions)
      results must have size 1
      results must contain(PromotionCombo(List("P1", "P2", "P3", "P4", "P5")))
    }

  }
  
}

object PromotionServiceSpec {
  val promotions: Seq[Promotion] = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2")),
  )

  val promotionsNoRestrictions: Seq[Promotion] = Seq(
    Promotion("P1", Seq()),
    Promotion("P2", Seq()),
    Promotion("P3", Seq()),
    Promotion("P4", Seq()),
    Promotion("P5", Seq()),
  )
}
