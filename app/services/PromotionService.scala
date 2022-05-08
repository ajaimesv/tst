package services

import com.google.inject.ImplementedBy
import model.{Promotion, PromotionCombo}

@ImplementedBy(classOf[PromotionServiceImpl])
trait PromotionService {
  
  def combinablePromotions(promotionCode: String, 
                           allPromotions: Seq[Promotion]): Seq[PromotionCombo]
  
}
