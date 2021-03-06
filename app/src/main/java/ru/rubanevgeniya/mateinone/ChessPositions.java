package ru.rubanevgeniya.mateinone;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ChessPositions {
  protected static ArrayList<String> allStartPositions;
  protected static ArrayList<String> allAnswers;

  static {
    allStartPositions = new ArrayList<>();
    allAnswers = new ArrayList<>();

    // ____ivaschenko 91-97
    allStartPositions.add("bp17wR47bp67bK87bp76bp35bp55wp34wp54wK74bB84wp83wQ52bR61b");
    allAnswers.add("bR6164");
    allStartPositions.add("bR18bB38bK58bR88bp17bp27bp37bp47bQ57bp67bp77bp87bN36bN55wp24wp34wB64wN63wp22wN42wp52wp62wp72wp82wR11wQ41wK51wB61wR81b");
    allAnswers.add("bN5543");
    allStartPositions.add("bR58bK78bp27bB47bp67bp77bp87bB26wp46bp15wp14wQ34wp23wp63wB42wp82wK61wR81b");
    allAnswers.add("bB4783");
    allStartPositions.add("bR18bB38bQ48bK58bB68bN78bR88bp17bp27bp47bp57bp67bp87bp76bp35bN44wp54wN33wp43wB53wp73wp12wp22wp32wN52wp62wp82wR11wQ41wK51wB61wR81b");
    allAnswers.add("bN4463");
    allStartPositions.add("bK78bp17bp27bp67bp77bp87bQ26bN66wp45bp55bN44wp54wp43wp22wB32wQ62wp72wp82wR61wK71wN81b");
    allAnswers.add("bN4452");
    allStartPositions.add("bR58bK47bQ16bp36bp76bp86bp45bp65wp44wp64wQ23wK63wp73wp82wR11b");
    allAnswers.add("bQ1652");
    allStartPositions.add("bR48bB58bp17bQ27bR37bK47bp67bp77wB87bp26bp36wp35wp24wp44wN84wp13wR63wp72wp82wR51wK81w");
    allAnswers.add("wB8765");
    // ____ivaschenko 91-97

    // ____ivaschenko 81-90
    allStartPositions.add("bR28bB68bK78bR27bp16bp46wp66bp76bp55wp75bp85bB14bp34wK54wp13wp33wB53wp22wQ82wN41wR71wR81b");
    allAnswers.add("bB1436");
    allStartPositions.add("bK38bp37bp26bp15bp35bN55wp14wp34wB84wR23wp73bp83wQ32wR52wK62bQ81b");
    allAnswers.add("bN5574");
    allStartPositions.add("bR18bB38bK58bR88bp17bp27bp37bp47bQ57bp67bp77bp87bN66bN55wp35wp34wN33wp73wp12wp22wp52wp62wp82wR11wB31wQ41wK51wB61wR81b");
    allAnswers.add("bN5563");
    allStartPositions.add("bR18bB38bK58bR88bp17bp27bp37bQ57bp67bp77bp87wB66wp45bN55wp12wp22wp32wp62wp72wp82wR11wN21wQ41wK51wB61wR81b");
    allAnswers.add("bN5563");
    allStartPositions.add("bR68bK78bp17bp27wR47bp67bp77bQ25wp35wQ45wp24bB64wp13wp63wB22wp62wK72bR81b");
    allAnswers.add("bQ2561");
    allStartPositions.add("bK18bR28bB48bp17bp16bp66bp35bN55wp65bB14bN34bp44wp54wp43wN63wp12wN52wR11wB31wK51wR61b");
    allAnswers.add("bN5543");
    allStartPositions.add("bN58bK78wQ57wN67bp77bp87bp16bQ15wN52wp62wp72wp82wR31bB41wK71b");
    allAnswers.add("bQ1551");
    allStartPositions.add("bK88bp17bp27bp87bp76bp35wQ45bB44wp43bp73wp12wp22bR62wp82wR11wR51wK81b");
    allAnswers.add("bR6282");
    allStartPositions.add("bR38bK78bp17wR27bp77bp87bp56bp45wB55bp65wQ75wp13bN43wp53wp63wp72wp82wK71b");
    allAnswers.add("bR3831");
    allStartPositions.add("bQ68bK37wR26bp15wQ45bp55wp14wp54wK74wp33wR51bR81b");
    allAnswers.add("bQ6864");
    // ____ivaschenko 81-90

    // ____ivaschenko 71-80
    allStartPositions.add("bR38bR68bK78bp17bQ47bB57bp67bp87bp26bB56wN65bp75wB33wp22wp72wp82wR61wK71w");
    allAnswers.add("wN6586");
    allStartPositions.add("bR18bN28bB38bB68bK78bR88bp17bp27bp37bp47bp87wB86wp45bp55bN85wB34wp44wK53wp63wp12wp22wp32bQ72wR11wN21w");
    allAnswers.add("wp4546");
    allStartPositions.add("bR48bR88wR37bp67bp16bK26bp56bp76bp86bB25wp55wN23wB63bN73bQ12wp22wp32wp72wp82wK31w");
    allAnswers.add("wR3727");
    allStartPositions.add("bR38bR58wQ17bp36wp66bp86bp25bN55bK34wN64bQ84wp33wp12wp72wR41wK71w");
    allAnswers.add("wQ1744");
    allStartPositions.add("bR18bN28bB38bQ48bK58bB68bR88bp17bp27bp47bN57bp77bp87bp56bp35bp65wp34wN54wN63wp12wp22wp42wp52wp62wp72wp82wR11wB31wQ41wK51wB61wR81w");
    allAnswers.add("wN5446");
    allStartPositions.add("bR18bB38bQ48bK58bB68bR88bp17bp27bN47bp57bp67bp77bp87bp36bN66wN54wN63wp12wp22wp32wp42wQ52wp62wp72wp82wR11wB31wK51wB61wR81w");
    allAnswers.add("wN5446");
    //allStartPositions.add("bR48bK58bB68bQ47bp67bp87wN56bp66bp15wp14bp24wp83wp22wp32wp62wp72bR41wR51wK71w");
    //allAnswers.add("");
    allStartPositions.add("bR18wR58bp17bp27bp37bK47bp77bp86bN15bR65wB43bQ73wp83wp12wp72wR41wK71w");
    allAnswers.add("wB4325");
    allStartPositions.add("bR18bN28bB38bQ48bK58bB68bN78bR88bp17bp27bp37bp47bp67bp77bp87bp56wp74wp63wp12wp22wp32wp42wp52wp82wR11wN21wB31wQ41wK51wB61wN71wR81b");
    allAnswers.add("bQ4884");
    allStartPositions.add("bR18bB38bK58bB68bN78bR88bp17bp27bp37bp47wN67bp77bp87bN44bQ54wp12wp22wp32wp42wB52wp62wp82wR11wN21wB31wQ41wK51wR61b");
    allAnswers.add("bN4463");
    // ____ivaschenko 71-80

    // ____ivaschenko 61-70
    allStartPositions.add("bR68bK78bp27bp67bp77bp16bB36bQ76wN65wp54wp33wp63wp12wp22wK31wR81w");
    allAnswers.add("wN6557");
    allStartPositions.add("bR78bK88bp27bp37bp67bp87bR16wp66bp76bQ86wp55wN75bp14wp44bN13wp33wp73wR52wp82wR61wK71w");
    allAnswers.add("wN7567");
    allStartPositions.add("bR18bR68bK78bB27bp67bp87bp16bp26bN36wB66bN76bp86wN65bQ34wp54wp33wp12wp32wp72wp82wR11wR61wK81w");
    allAnswers.add("wN6586");
    allStartPositions.add("bR18bB38bQ48bR58bp17bp27bN47bB57bK67bp87bp36bp66bp76wB86wp44wQ63wp12wp22wp32wN52wp62wp72wp82wR11wK51wR81w");
    allAnswers.add("wQ6323");
    allStartPositions.add("bR18bB38bK48bR88bp17bp47bp67wN77bp87bN16wB46bN66bp25wN45wp55wp85wp74wp43wp12wp32wK52bQ11bB71w");
    allAnswers.add("wB4657");
    allStartPositions.add("bR18bQ48bK68bR88bp37bN57wB67bp87bp16bp46bp66bB15bp25wN55wp54wp33wp43wp12wp22wp62wp72wp82wR11wB31bB41wR61wK71w");
    allAnswers.add("wB3186");
    allStartPositions.add("bK38wK57wN16wN25w");
    allAnswers.add("wN2546");
    allStartPositions.add("bB58bQ68bK88bp17bp87bp36bp56wN66bp25wp55bR44wp54wB43wp12wp22wp32wR82wK31w");
    allAnswers.add("wR8287");
    allStartPositions.add("bN88bp17bp67bK87bp86wB55wp44wp23wp33bQ53wp12bR42bR52wp82wR11wR71wK81w");
    allAnswers.add("wR7177");
    allStartPositions.add("bR18bN28bB38bQ48bK58bB68bN78bp17bp27bp37bp47bp57bp75bR85wp44bp64wB43wp53wB73wp12wp22wp32wp62wp72wp82wR11wN21wK51wN71wR81w");
    allAnswers.add("wB4376");
    // ____ivaschenko 61-70

    // ____ivaschenko 51-60
    allStartPositions.add("bN38bK68bp17bp67bp77wp16bp26bR86wB25wQ75bQ24bp44wp73bp83wp22wp62wp82wK71w");
    allAnswers.add("wQ7548");
    allStartPositions.add("bp86wK65bK85bR84wR83wp72w");
    allAnswers.add("wp7274");
    allStartPositions.add("wQ28bp76bp85bK74wp84wp73wK62bQ11w");
    allAnswers.add("wQ2838");
    allStartPositions.add("wQ38wK25bK45bp13wB11bQ71w");
    allAnswers.add("wQ3836");
    allStartPositions.add("bK78bp17wp77bp87bp36wp66bp25bp45bR55wN65bN34bQ54wp33wR83wp12wN52bB62wp72wR51wK81w");
    allAnswers.add("wN6586");
    allStartPositions.add("wN57bp67bp86bB55bp75bR24bp44wp54wp74bK84wp63wR32wK72w");
    allAnswers.add("wN5765");
    allStartPositions.add("bR18bK58bB68bR88bp17bp27wR47bp67bp87bp36bp56bp66bQ34wN54wB53wp12wp22wp32wp62wp72wp82wR11wK71w");
    allAnswers.add("wN5466");
    allStartPositions.add("bB48bK58bR68wR17bp87wN56bN55wp74bQ84wp33wp83wK82w");
    allAnswers.add("wN5677");
    allStartPositions.add("bR18bB38bQ48bR58bK88bp17bp27bp37bB57bp77bN87bp86wp35wN55wp44wB23wp12wp22wp72wp82wR61wK71w");
    allAnswers.add("wN5576");
    allStartPositions.add("bR68bK78bR88bB27bB77bp46bp76bp86bp15wN45bp55wp14bp24wB34wp74bQ84wp23wp32wp82wR41wR61wK71w");
    allAnswers.add("wN4566");
    // ____ivaschenko 51-60

    //allStartPositions.add("bK78wB77wK76wN36w");
    allStartPositions.add("bp57bK78wB77wK76wN36w");
    allAnswers.add("wN3657");
    allStartPositions.add("bB56bp66bp55wp54bK13wp63bp22wR32wK21wN41b");
    allAnswers.add("bB5612");
    allStartPositions.add("bR58bR68bK78bp27bp37bp67bP87bp16bN36bB46bp76wN74wp13wB33wp22wp32wp62wP72wp82wR41wR61wK71w");
    allAnswers.add("wN7486");
    allStartPositions.add("bR18bR78bK88bP77bP87bP16bB36wN86wp83wp12bQ22wp62wp72wR11wR51wK71w");
    allAnswers.add("wN8667");

    // ____ivaschenko 41-50
    allStartPositions.add("bR48bR68bK88bp17bB27bN57bp67bp77bp26bp56" +
            "bp76bQ45wp23wQ33wp73wp12wB22wp52wp62wR31wR61wK71w");
    allAnswers.add("wQ3377");
    allStartPositions.add("bR28bQ48bK68wR17bp67wB87wp46bB56bp86wQ55" +
            "bp75bp24bp33wp73wp83wp22wp62wK71w");
    allAnswers.add("wQ5588");
    allStartPositions.add("bK78bp67bp87bR26wN76wp23wp53bQ83wQ22wp62wp82wK71w");
    allAnswers.add("wQ2288");
    allStartPositions.add("bK28bB38bN48bR37wN26wR23wR12wK41w");
    allAnswers.add("wN2647");
    allStartPositions.add("bR18bQ48bK58bB68bR88bB27bp37bp67bp77bp87bp16bN66bp45bp63" +
            "wp12wp22wp32wB52wp62wp72wp82wR11wN21wB31wQ41wR51wK71w");
    allAnswers.add("wB5225");
    allStartPositions.add("bR18bQ48bB58bB68bR88bp27bp37bK57bp77bp16bN36bp86wp44bp54" +
            "wB23wQ83wp12wp22wp32wp62wp72wp82wR11wN21wB31wK71w");
    allAnswers.add("wQ8356");
    allStartPositions.add("bR18bN28bR78bp27bN57bp67bK87bp36bp56wp66bp76wR55wp23wp83" +
            "wp12bQ22wp72wB21wR71wK81w");
    allAnswers.add("wR5585");
    allStartPositions.add("bR18bN28bQ48bB68bN78bR88bp17bp27bp37bK57wB67bp77bp46bp86" +
            "wN55wp54wN33wp12wp22wp32wp42wp62wp72wp82wR11wB31bB41wK51wR81w");
    allAnswers.add("wN3345");
    allStartPositions.add("bR18bB38bQ48bK58bB68bN78bR88bp27bp37bp47bp67bp77bp87bp16bN36" +
            "bp55wB34wp54wQ63wp12wp22wp32wp42wp62wp72wp82wR11wN21wB31wK51wN71wR81w");
    allAnswers.add("wQ6367");
    allStartPositions.add("bR38wR58bp17bB27bQ37bK67bp87bp26bp76wp86" +
            "bp45bp55wB14wp13wQ33wp22wp62wp72wK71w");
    allAnswers.add("wQ3363");
    // ____ivaschenko 41-50
    // ____ivaschenko 31-40
    allStartPositions.add("wN35bp52wK31bK51bN61w");
    allAnswers.add("wN3543");
    allStartPositions.add("wR48bp27bp57bK77bp87bR36wB56bQ25bN55wp75bp85" +
            "wp54wp12wp22wp32wK31w");
    allAnswers.add("wR4878");
    allStartPositions.add("wN67bp77bp16bQ55bp65wK34bK54bN64bB53w");
    allAnswers.add("wN6775");
    allStartPositions.add("bR88bp27wR57bp87bp16bp56bK66bp76wQ15bR34bQ54" +
            "wp84wp33wp12wp62wp72wK71w");
    allAnswers.add("wQ1575");
    allStartPositions.add("bB56bp66bp55wp54bK13wp63bp22wR32wK21wN41b");
    allAnswers.add("bB5612");
    allStartPositions.add("bR18bN28bR58bK88bp17bp67wR77bp36bB46bp66bp25wp85" +
            "wp44wp84wK23wp33wB43wB53wp22wQ42wN52wp62bQ11wR41b");
    allAnswers.add("bQ1114");
    allStartPositions.add("bR18wB68bR78bp17bQ37bp67bK87bp26wR46bp45" +
            "wp55bp85bp33wp12wp32wN52wp62wp72wp82wK71w");
    allAnswers.add("wR4686");
    allStartPositions.add("bB28bK38bR58bR88bp17bN47bp87bp36bN66wB15wp35bp75wp44" +
            "bp64bB74bQ84wN33wB43wp12wp22wp72wp82wR11wR41wN61wK71w");
    allAnswers.add("wB4316");
    allStartPositions.add("bN17bp27wK47bK26bp15wN22wB61w");
    allAnswers.add("wN2214");
    allStartPositions.add("wR87bR36wp14bN54bN63bK52wp72wp82wR11wK81b");
    allAnswers.add("bN5462");
    //____ivaschenko 31-40
    //____ivaschenko 21-30
    allStartPositions.add("bK18bB28wK38bp17bp16wp26w");
    allAnswers.add("wp2627");
    allStartPositions.add("bB68bR88bK77bp87wK75wp85wB12w");
    allAnswers.add("wp8586");
    allStartPositions.add("bK18bB28wK26wB83w");
    allAnswers.add("wB8372");
    allStartPositions.add("bB78bK88bp17bp87bB66wp23wp12wp32wp82wB11wK81w");
    allAnswers.add("wB1166");
    allStartPositions.add("wK27wB86bK35wp34bp44wp43bQ11w");
    allAnswers.add("wB8668");
    allStartPositions.add("wR38bp67bK77bp87wN66bp76wp75wK72w");
    allAnswers.add("wR3878");
    allStartPositions.add("wK62bp82wN61bK81w");
    allAnswers.add("wN6173");
    allStartPositions.add("bR68bK78wN67bp87bp76wK72wB11w");
    allAnswers.add("wN6786");
    allStartPositions.add("wQ88bQ54bK43wK22bp52w");
    allAnswers.add("wQ8833");
    allStartPositions.add("bN68bK88wR77bp87bp76wB55wK72w");
    allAnswers.add("wR7776");
    //____ivaschenko 21-30
    ///____ivaschenko 11-20
    allStartPositions.add("bK17bp27bp37wN47wp23wK22wp32wR31w");
    allAnswers.add("wR3111");
    allStartPositions.add("bR68bK78bp67wB66bp76wK72wR81w");
    allAnswers.add("wR8188");
    allStartPositions.add("bR38bp37bK16bp26wB36wR34wK23w");
    allAnswers.add("wR3414");
    allStartPositions.add("bK18bB38bp17wR47bp26wN36wK23w");
    allAnswers.add("wR4717");
    allStartPositions.add("bK18bR28wQ87wp26wK34w");
    allAnswers.add("wQ8717");
    allStartPositions.add("wQ18bp66bK76bp86bR54bQ64wp74wK84w");
    allAnswers.add("wQ1878");
    allStartPositions.add("wK87wQ16bQ55bp83bp82bK81w");
    allAnswers.add("wQ1661");
    allStartPositions.add("bK18bR28bR38bp17bp27wN14wK23wR11w");
    allAnswers.add("wN1426");
    allStartPositions.add("bp14bK13bN23wN11wK21w");
    allAnswers.add("wN1132");
    allStartPositions.add("bK58wN27bN47bN57wN56wK86w");
    allAnswers.add("wN2746");
    //____ivaschenko 11-20
    //____ivaschenko 1-10
    allStartPositions.add("bK58wK56wR12w");
    allAnswers.add("wR1218");
    allStartPositions.add("bK58wR87wK43wR11w");
    allAnswers.add("wR1118");
    allStartPositions.add("bK68bp17wK66wQ12w");
    allAnswers.add("wQ1267");
    allStartPositions.add("bK88wK76wB12wB82w");
    allAnswers.add("wB8255");
    allStartPositions.add("bK78bp67bp77bp87wp73wp62wK72wR11w");
    allAnswers.add("wR1118");
    allStartPositions.add("bQ58bR68bK78bp67bp77bp87wp73wQ22wK72wB11w");
    allAnswers.add("wQ2277");
    allStartPositions.add("bK88wK76wN86wB15w");
    allAnswers.add("wB1533");
    allStartPositions.add("bK58wR87wK46wN56bQ11w");
    allAnswers.add("wR8757");
    allStartPositions.add("wR18wK63bp82bK71w");
    allAnswers.add("wR1811");
    allStartPositions.add("bQ68bK78wQ17wK76w");
    allAnswers.add("wQ1787");
    //____ivaschenko 1-10

    allStartPositions.add("bK58wK56wQ64w");
    allAnswers.add("wQ6428");
    allStartPositions.add("bK68wK56wQ11w");
    allAnswers.add("wQ1188");
    allStartPositions.add("bQ48bR68bK78bB27bp37bp67bp77bp87bp16bp25wp33" +
            "wQ43wp12wp22wB32wp62wp72wp82wR61wK71w");
    allAnswers.add("wQ4387");
    allStartPositions.add("bR18bR68bK78bp17bB27bQ37bp67bp87bp26bp76wQ86bp35wp34wp23wp83" +
            "wp12wB22wp62wp72wR11wR61wK71w");
    allAnswers.add("wQ8677");
    allStartPositions.add("bR48bR58bK78bp17bp27bQ37bp67bp87bp76wp86wp14wQ44" +
            "wp22wp62wp72wR31wR61wK71w");
    allAnswers.add("wQ4477");
    allStartPositions.add("bR68bK78wR17wQ47bp87bp76bp65bQ22wp62wp72wp82wK71w");
    allAnswers.add("wQ4787");
    allStartPositions.add("bR18bB38bQ48bK58bN78bR88bp17bp27bp37bp47bB57bp67bp77bp87" +
            "bN36bp55wB34wp54wQ63wp12wp22wp32wp42wp62wp72wp82wR11wN21wB31wK51wN71wR81w");
    allAnswers.add("wQ6367");
    allStartPositions.add("bR18bB38bQ48bR68bK88bp17wN57bp67bp77bp26bN35wQ73wp12wp22wp62" +
            "wp72wp82wR11wR51wK71w");
    allAnswers.add("wQ7384");
    allStartPositions.add("bR18bQ28bK58wR17bp67bp87bN26bp46wN25bp35wp45bp24" +
            "wp34wp23wp83wp62wp72wQ11wK71w");
    allAnswers.add("wQ1188");
    allStartPositions.add("bK68bN57bp87bp16bp25wQ45bQ55wB13wp23bp63wp12" +
            "wp62wp82bR51wR71wK81w");
    allAnswers.add("wQ4578");

    allStartPositions.add("bK78bR47bp67bp77bp87wp62wp72wp82wR51wK71w");
    allAnswers.add("wR5158");
    allStartPositions.add("bB68wR14wR84bK53wK51w");
    allAnswers.add("wR8483");
    allStartPositions.add("bK78bp77bp87wR64bR12bR42wp82wR61wK71w");
    allAnswers.add("wR6468");
    allStartPositions.add("bR18bR38bK78bp27bp57bp67bp76bN15wp53wB22" +
            "wp32wp62wp72wK31wR41wR81w");
    allAnswers.add("wR8188");
    allStartPositions.add("bR38bR68bp67bK77bp16bp56wN66bp76bp25wp55wp73wp" +
            "12wp22bN32wp62wK72wR11wR81w");
    allAnswers.add("wR8187");
    allStartPositions.add("bR68bK78bp17wR47wR87bp84wp83wp72wK82bR31w");
    allAnswers.add("wR4777");
    allStartPositions.add("bK78bp27wp76wp86bR22bR82wR11wR61wK71w");
    allAnswers.add("wR1118");
    allStartPositions.add("bR48bR68bB27bp37wN57bp67bp77bK87bp26bp15wp14" +
            "wp34wp64wR13wp22bQ42wp72wp82wR51wK81w");
    allAnswers.add("wR1383");
    allStartPositions.add("bN28bK58bB68bR88bp17bp67bp77bp87bQ56bp55wB75wp54" +
            "wp12wp22wp32wp62wp72wp82wK31wR41w");
    allAnswers.add("wR4148");
    allStartPositions.add("bQ68bK78bp67bB77bp56wR85wp54wQ74wp73bp22wp62wK72bR11wR81w");
    allAnswers.add("wR8588");

    allStartPositions.add("bK58wB57bN26wK56wB41w");
    allAnswers.add("wB4185");
    allStartPositions.add("bB68bK78bp87bp76bp15bN65bp24wp23wp83wp12wB22wB52wp72wK81w");
    allAnswers.add("wB5234");
    allStartPositions.add("wB18bp17bK87bp26bp86bN24wp23wB22bR42bp52wp82wK21wR71w");
    allAnswers.add("wB1854");
    allStartPositions.add("bR18bN28bB38bQ48bK58bB68bR88bp17bp27bp47bp57bp67bp87bp36bp76wp55" +
            "wB34bN64wQ23wp33wp12wp22wp42wp62wp72wp82wR11wN21wB31wK51wN71wR81w");
    allAnswers.add("wB3467");
    allStartPositions.add("bR18bR68bK88bp27wQ67bp87bQ26bp36bN35wp23wp12wB42wp82wK21wR41w");
    allAnswers.add("wB4233");
    allStartPositions.add("wN47bB46bK45wN44wK43wB41bR51w");
    allAnswers.add("wB4123");
    allStartPositions.add("bB78bK88bp87bp16bp76bp25bN14wp13wN33wB22wp62wp72wp82wK71w");
    allAnswers.add("wN3314");
    allStartPositions.add("bR18bN28bB38bK68bR88bp27bB77bN87bp16bp86bp75wB34bp64" +
            "wp23wN33wp12wp32wp82wR11wB31wR51wK71w");
    allAnswers.add("wB3113");
    allStartPositions.add("bR48bR68bK88bp17bB27bp67bp87bp26bQ36bp46bN66bp76wN86" +
            "bp35wp34wp23wQ73wp12wB22wp62wp72wp82wR11wR41wK71w");
    allAnswers.add("wB2266");
    allStartPositions.add("bR18bK78bB88bB27bp37bN47bp87bp16bN36bp76wp86bp25" +
            "wB35bp65wp34wN64wp13wN33wB12wp22wp62wp72wR51wK71w");
    allAnswers.add("wp3425");

    allStartPositions.add("bK48wp47wK46wp56w");
    allAnswers.add("wp5657");
    allStartPositions.add("bK88wp67bN87wp76wp86wK73w");
    allAnswers.add("wp7677");
    allStartPositions.add("bB68bK78wp66wK76wB44w");
    allAnswers.add("wp6667");
    allStartPositions.add("wR68bR78bK88wR67bp77bp87wp86wp73wK83bR12w");
    allAnswers.add("wp8677");
    allStartPositions.add("bR68bK78bp67wB66bp76wp86bR12wp62wK72wR81w");
    allAnswers.add("wp8687");
    allStartPositions.add("bR48bK58bB68bp27bp67bp36wp56wp66bp76wN55wp75wp82wR11wK71w");
    allAnswers.add("wp5667");
    allStartPositions.add("wQ76bp45bK55bp65bQ33wp43wK53wp63w");
    allAnswers.add("wp6364");
    allStartPositions.add("bK18bR58bp17bp27bB26wp36wB63wp83wp72wB82wK81w");
    allAnswers.add("wp3627");
    allStartPositions.add("bR18bR68bK78bB88wR57bp67bp87wp76wp86bQ15bp35wp75bp24wQ43wp12bp22wp32wK21wR81w");
    allAnswers.add("wp7687");
    allStartPositions.add("bR18bQ58bN68bB78bR88bp17bp27bK77bp87bp36bp76bp45wN55wp65wp85wp44wp74wB43wp12wp22wp32wR11wB31wR61wK71w");
    allAnswers.add("wp6566");

    allStartPositions.add("bK78wB77wK76wN36w");
    allAnswers.add("wN3657");
    allStartPositions.add("bR58bR68bK78bp27bp37bp67bP87bp16bN36bB46bp76wN74wp13wB33wp22wp32wp62wP72wp82wR41wR61wK71w");
    allAnswers.add("wN7486");
    allStartPositions.add("bR18bR78bK88bP77bP87bP16bB36wN86wp83wp12bQ22wp62wp72wR11wR51wK71w");
    allAnswers.add("wN8667");
    allStartPositions.add("bR48bK58bR68wR17wN56bp84bp43wp83wp72wK82w");
    allAnswers.add("wN5677");
    allStartPositions.add("bK78bN77bN67wN76wN45wK71w");
    allAnswers.add("wN4566");
    allStartPositions.add("bB48bp36bp56bK45wp24wN34wN54bp64wK43w");
    allAnswers.add("wN5433");
    allStartPositions.add("bR68bR48bK88bp37bB77bp87bp16bN66bp76bp25wN55bp65wp44bN54wp64wN84wp13wB23wp22wp32wK31wR41wR81w");
    allAnswers.add("wN8476");
    allStartPositions.add("bR18bR68bN78wR57bp67bK77bp76bp25wB75bp14bp34wN44wR84wp33wp12wp22bB72wK21w");
    allAnswers.add("wN4456");
    allStartPositions.add("bR18bR68bB78bK88bp27bQ37bp77bp87bp16bp36bp45wN55bp65bB75wp44wp64wp33wQ83wp12wp22wp72wp82wR51wR61wK71w");
    allAnswers.add("wN5576");
    allStartPositions.add("bR18bB38bQ48bK58bB68bR88bp17bp27bN47bp57bp67bp77bp87bp36bN66wN54wN63wp12wp22wp32wp42wQ52wp62wp72wp82wR11wB31wK51wB61wR81w");
    allAnswers.add("wN5446");
  }

  protected static void saveUnsolvedPosition(int indOfPosition, LockScreenApp lockScreenApp) {
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels(true);
    if (loadUnsolvedLevels == null) {
      loadUnsolvedLevels = new HashSet<>();
    }
    loadUnsolvedLevels.add(Integer.toString(indOfPosition));
    lockScreenApp.saveUnsolvedLevels(loadUnsolvedLevels, true);
  }

  protected static void removeSolvedPositionFromUnsolvedPositions(int indOfPosition, LockScreenApp lockScreenApp) {
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels(true);
    if (loadUnsolvedLevels != null) {
      loadUnsolvedLevels.remove(Integer.toString(indOfPosition));
      lockScreenApp.saveUnsolvedLevels(loadUnsolvedLevels, true);
    }
  }

  protected static void refreshIndOfPositionAndUnsolvedLevels(int oldIndex,
                                                              Set<String> oldLoadUnsolvedLevels,
                                                              boolean oldIsLevelFromUnsolved,
                                                              LockScreenApp lockScreenApp) {
    lockScreenApp.saveInfoInt("indOfPosition", oldIndex);
    lockScreenApp.saveInfoBoolean("isLevelFromUnsolved", oldIsLevelFromUnsolved);
    lockScreenApp.saveUnsolvedLevels(oldLoadUnsolvedLevels, true);
  }

  protected static int findIndexOfNextPositionAfterRightMove(int indOfPosition, LockScreenApp lockScreenApp) {
    int index = -1;
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels(true);
    if (!lockScreenApp.loadInfoBoolean("isLevelFromUnsolved")) {//first "round" of indexes
      if (indOfPosition < allStartPositions.size() - 1) {
        index = indOfPosition + 1;
      } else if (indOfPosition == allStartPositions.size() - 1) {//first "round" finished
        if (loadUnsolvedLevels == null || loadUnsolvedLevels.size() == 0) {//was no unsolved level during first round
          index = 0;   //   start from the beginning!!!!!
        } else {
          lockScreenApp.saveInfoBoolean("isLevelFromUnsolved", true);// start solving position from unsolved positions
          Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
          index = Integer.parseInt(unsolvedLevels[0].toString());
        }
      }
    } else {// indexes from unsolved levels
      Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
      if (unsolvedLevels.length == 0) {
        index = 0; //solved all levels from unsolvedLevels, so start from the beginning
        lockScreenApp.saveInfoBoolean("isLevelFromUnsolved", false);
      } else {
        index = Integer.parseInt(unsolvedLevels[0].toString());
      }
    }
    return index;
  }

  protected static int findIndexOfNextPositionAfterWrongMove(int indOfPosition, LockScreenApp lockScreenApp) {
    Set<String> loadUnsolvedLevels = lockScreenApp.loadUnsolvedLevels(true);
    if (!lockScreenApp.loadInfoBoolean("isLevelFromUnsolved")) {//first "round" of indexes
      if (indOfPosition < allStartPositions.size() - 1) {
        return indOfPosition + 1;
      } else if (indOfPosition == allStartPositions.size() - 1) {//first "round" finished
        if (loadUnsolvedLevels == null) {//was no unsolved level during first round
          return indOfPosition;   //   solving last level again and again
        } else {
          lockScreenApp.saveInfoBoolean("isLevelFromUnsolved", true);// start solving position from unsolved positions
          Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
          return Integer.parseInt(unsolvedLevels[0].toString());
        }
      }
    } else {// indexes from unsolved levels
      Object[] unsolvedLevels = loadUnsolvedLevels.toArray();
      if (unsolvedLevels.length > 1) {
        Arrays.sort(unsolvedLevels);
        for (int i = 0; i < unsolvedLevels.length; i++) {
          int ind = Integer.parseInt(unsolvedLevels[i].toString());
          if (indOfPosition == ind) {
            if (i < unsolvedLevels.length - 1) {
              return Integer.parseInt(unsolvedLevels[i + 1].toString());
            } else {
              return Integer.parseInt(unsolvedLevels[0].toString());
            }
          }
        }
      } else {
        return indOfPosition;
      }
    }
    return -1;
  }
}