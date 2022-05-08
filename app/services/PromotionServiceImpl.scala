package services

import com.google.inject.Singleton
import model.{Promotion, PromotionCombo}

import scala.annotation.tailrec

@Singleton
class PromotionServiceImpl extends PromotionService {

  /**
   * Find all promotion combos for a given promotion and a given list of
   * promotions.
   * @param promotionCode a promotion code to look for.
   * @param allPromotions a list of all available promotions.
   * @return a list of promotion combos.
   */
  def combinablePromotions(promotionCode: String,
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo] =
    Option(promotionCode) match {
      case Some(pc) if pc.isEmpty => combinablePromotions(None, allPromotions)
      case Some(pc) => combinablePromotions(Some(pc), allPromotions)
      case None => combinablePromotions(None, allPromotions)
    }

  /**
   * Find all promotion combos for a given promotion and a given list of
   * promotions. 
   * @param promotionCode an optional promotion code to look for.
   * @param allPromotions a set of available promotions.
   * @return a list of promotion combos.
   */
  def combinablePromotions(promotionCode: Option[String],
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
    val map = toMap(allPromotions)
    val perm = map.keys.toList.permutations.toList
    val filtered = promotionCode.fold(perm)(pc => perm.filter(_.head == pc))
    val results = scala.collection.mutable.Set[List[String]]()
    filtered.foreach { entry =>
      val r = applyNotAllowed(entry, 0, map)
      results += r.sorted
    }
    results.map { PromotionCombo } .toSeq
  }

  /**
   * Convert the promotions structure into Map[String -> Set] to improve
   * search performance.
   * @param promotions a list of promotions to transform.
   * @return the promotions provided in a Map[String -> Set] format.
   */
  def toMap(promotions: Seq[Promotion]): Map[String, Set[String]] = 
    promotions.map { p => p.code -> p.notCombinableWith.toSet } .toMap

  /**
   * Apply the "not combinable with" rules to a list of promotions.
   * @param ss a list of promotion codes.
   * @param index an index to the element in the list to processed.
   * @param map promotions and rules.
   * @return a resulting list after applying the provided rules.
   */
  @tailrec
  final def applyNotAllowed(ss: List[String], index: Int, map: Map[String, Set[String]]): List[String] =
    if (index < ss.length)
      applyNotAllowed(ss.filterNot(map(ss(index)).contains), index + 1, map)
    else ss
  
}
