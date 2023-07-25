import com.nbcb.CouponServiceApplication;
import com.nbcb.api.ICouponService;
import com.nbcb.pojo.ShopCoupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouponServiceApplication.class)
public class CouponServiceTest {
    @Autowired
    private ICouponService iCouponService;

    @Test
    public void confirmOrder() {
        Long couponId = 345988230098857984L;
        Long goodsId = 378715381063495688L;
        Long userId = 378715381059301376L;

        ShopCoupon one = iCouponService.findOne(couponId);
        System.out.println(one.getCouponPrice());
        System.out.println(one.getCouponId());


    }
}
