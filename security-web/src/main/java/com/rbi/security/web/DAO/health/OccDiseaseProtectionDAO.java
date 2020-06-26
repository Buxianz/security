package com.rbi.security.web.DAO.health;

import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 10:40
 */
@Mapper
public interface OccDiseaseProtectionDAO {

    @Select("select * from occ_disease_protection limit #{pageNo},#{pageSize}")
    List<OccDiseaseProtection> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from occ_disease_protection")
    int findByPageNum();

    @Insert("insert into occ_disease_protection (" +
            "unit,receive_time,general_suit,flame_suit,acid_base_suit," +
            "static_electricity_suit,nuzi_suit,silk_suit,orchid_suit,white_suit," +
            "white_work_suit,winter_suit,rain_suit,a_rain_suit,general_hat," +
            "shawl_hat,white_work_hat,sun_hat,safe_hat,grass_hat," +
            "work_shoes,insulation_shoes,anti_smashing_shoes,wave_shoes,nurse_shoes," +
            "rubber_shoes,water_shoes,slipper_shoes,long_cloth_glove,cloth_glove," +
            "line_glove,acid_base_glove,rubber_glove,hand_glove,oil_hand_glove," +
            "electric_welding_glove,fur_cloth_glove,asbestos_glove,fur_glove,protect_glasses," +
            "color_protect_glasses,anti_impact_glasses,closed_glasses,face_shield,little_face_shield," +
            "welding_lens,towel,sand_mask,dust_mask,poison_mask," +
            "antigas_mask,washing_powder,toilet_soap,soap,skin_care," +
            "shoe_cover,canvas_socks,bib,safety_belt,cuff," +
            "idt,operating_staff) values(" +
            "#{unit},#{receiveTime},#{generalSuit},#{flameSuit},#{acidBaseSuit}," +
            "#{staticElectricitySuit},#{nuziSuit},#{silkSuit},#{orchidSuit},#{whiteSuit}," +
            "#{whiteWorkSuit},#{winterSuit},#{rainSuit},#{aRainSuit},#{generalHat}," +
            "#{shawlHat},#{whitWorkHat},#{sunHat},#{safeHat},#{grassHat}," +
            "#{workShoes},#{insulationShoes},#{antiSmashingShoes},#{waveShoes},#{nurseShoes}," +
            "#{rubberShoes},#{waterShoes},#{slipperShoes},#{longClothGlove},#{clothGlove}," +
            "#{lineGlove},#{acidBaseGlove},#{rubberGlove},#{handGlove},#{oilHandGlove}," +
            "#{electricWeldingGlove},#{furClothGlove},#{asbestosGlove},#{furGlove},#{protectGlasses}," +
            "#{colorProtectGlasses},#{antiImpactGlasses},#{closedGlasses},#{faceShield},#{littleFaceShield}," +
            "#{weldingLens},#{towel},#{sandMask},#{dustMask},#{poisonMask}," +
            "#{antigasMask},#{washingPowder},#{toiletSoap},#{soap},#{skinCare}," +
            "#{shoeCover},#{canvasSocks},#{bib},#{safetyBelt},#{cuff}," +
            "#{idt},#{operatingStaff})")
    void add(OccDiseaseProtection occDiseaseProtection);

    @Update("update occ_disease_protection set " +
            "unit=#{unit},receive_time=#{receiveTime},general_suit=#{generalSuit},flame_suit=#{flameSuit},acid_base_suit=#{acidBaseSuit}," +
            "static_electricity_suit=#{staticElectricitySuit},nuzi_suit=#{nuziSuit},silk_suit=#{silkSuit},orchid_suit=#{orchidSuit},white_suit=#{whiteSuit}," +
            "white_work_suit=#{whiteWorkSuit},winter_suit=#{winterSuit},rain_suit=#{rainSuit},a_rain_suit=#{aRainSuit},general_hat=#{generalHat}," +
            "shawl_hat=#{shawlHat},white_work_hat=#{whitWorkHat},sun_hat=#{sunHat},safe_hat=#{safeHat},grass_hat=#{grassHat}," +
            "work_shoes=#{workShoes},insulation_shoes=#{insulationShoes},anti_smashing_shoes=#{antiSmashingShoes},wave_shoes=#{waveShoes},nurse_shoes=#{nurseShoes}," +
            "rubber_shoes=#{rubberShoes},water_shoes=#{waterShoes},slipper_shoes=#{slipperShoes},long_cloth_glove=#{longClothGlove},cloth_glove=#{clothGlove}," +
            "line_glove=#{lineGlove},acid_base_glove=#{acidBaseGlove},rubber_glove=#{rubberGlove},hand_glove=#{handGlove},oil_hand_glove=#{oilHandGlove}," +
            "electric_welding_glove=#{electricWeldingGlove},fur_cloth_glove=#{furClothGlove},asbestos_glove=#{asbestosGlove},fur_glove=#{furGlove},protect_glasses=#{protectGlasses}," +
            "color_protect_glasses=#{colorProtectGlasses},anti_impact_glasses=#{antiImpactGlasses},closed_glasses=#{closedGlasses},face_shield=#{faceShield},little_face_shield=#{littleFaceShield}," +
            "welding_lens=#{weldingLens},towel=#{towel},sand_mask=#{sandMask},dust_mask=#{dustMask},poison_mask=#{poisonMask}," +
            "antigas_mask=#{antigasMask},washing_powder=#{washingPowder},toilet_soap=#{toiletSoap},soap=#{soap},skin_care=#{skinCare}," +
            "shoe_cover=#{shoeCover},canvas_socks=#{canvasSocks},bib=#{bib},safety_belt=#{safetyBelt},cuff=#{cuff}," +
            "udt=#{udt} where id =#{id}")
    void update(OccDiseaseProtection occDiseaseProtection);

    @Delete("delete from occ_disease_protection where id = #{id}")
    void delete(int id);
}
