package controllers

import model.{BestGroupPrice, CabinPrice, Promotion, PromotionCombo, Rate}

import javax.inject._
import play.api.mvc._
import services.{GroupPriceService, PromotionService}

@Singleton
class HomeController @Inject()(
  cc: ControllerComponents,
  groupPriceService: GroupPriceService,
  promotionService: PromotionService
) extends AbstractController(cc) {

  /*
   * A list of sample data provided for this exercise.
   */
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

  val promotions: Seq[Promotion] = Seq(
    Promotion("P1", Seq("P3")),
    Promotion("P2", Seq("P4", "P5")),
    Promotion("P3", Seq("P1")),
    Promotion("P4", Seq("P2")),
    Promotion("P5", Seq("P2")),
  )

  /**
   * 
   * @return
   */
  def index: Action[AnyContent] = Action {
    // Problem 1
    val bgp: Seq[BestGroupPrice] = groupPriceService.getBestGroupPrices(rates, cabinPrices)
    // Problem 2
    val pc1: Seq[PromotionCombo] = promotionService.combinablePromotions("", promotions)
    val pc2: Seq[PromotionCombo] = promotionService.combinablePromotions("P1", promotions)
    val pc3: Seq[PromotionCombo] = promotionService.combinablePromotions("P3", promotions)
    
    Ok(views.html.index(bgp, pc1, pc2, pc3))
  }

}
