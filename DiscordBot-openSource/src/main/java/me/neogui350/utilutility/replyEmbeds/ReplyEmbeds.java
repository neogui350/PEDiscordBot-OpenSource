package me.neogui350.utilutility.replyEmbeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

/**
 * failedEmbed(String type)                 - 정보를 불러오는데 실패했을때 사용하는 함수
 * failedToProcessEmbed(String type)        - 정보를 불러오는데는 성공을 했지만 처리하다가 발생한 예외나 문제가 생겼을때 사용하는 함수
 * successEmbed(String type)                - 정보를 불러오고 처리하는ㄷ 둘다 성공했을때 사용하는 함수
 * permissionRequired(String permission)    - 퍼미션이 명령어를 사용하는데 걸렸을때 사용하는 함수
 * inGamePermissionRequired(String permission, String name, Object jsonObject) - 인겜에서 퍼미션이 필요할 경우 사용하는 함수(예: 국왕, 부왕, 부관, 시장, 부시장, 부관, 트러스트) - 제작 예정
 */

public class ReplyEmbeds {
    public static String footer = "A Discord Bot made by neogui350";

    //정보를 불러오는데 실패했을때 사용하는 함수
    public static EmbedBuilder failedEmbed(String str){
        EmbedBuilder embed_uuid = new EmbedBuilder();
        embed_uuid.setDescription("" +
                "정보를 불러오는데 실패했습니다. " +
                "\n" + str + "는(은) 존재하지 않습니다. ");
        embed_uuid.setFooter(footer);
        embed_uuid.setColor(Color.red);

        return embed_uuid;
    }

    //정보를 불러오는데는 성공을 했지만 처리하다가 발생한 예외나 문제가 생겼을때 사용하는 함수
    public static EmbedBuilder failedToProcessEmbed(String str){
        EmbedBuilder embed_uuid = new EmbedBuilder();
        embed_uuid.setColor(Color.red);
        embed_uuid.setDescription("" +
                "정보 처리에 실패했습니다. " +
                "\n" + str);
        embed_uuid.setFooter(footer);

        return embed_uuid;
    }

    //정보를 불러오고 처리하는ㄷ 둘다 성공했을때 사용하는 함수
    public static EmbedBuilder successEmbed(String str){
        EmbedBuilder embed_uuid = new EmbedBuilder();
        embed_uuid.setDescription("" +
                "정보를 불러오는데 성공했습니다. " +
                "\n" + str + "");
        embed_uuid.setFooter(footer);
        embed_uuid.setColor(Color.GREEN);

        return embed_uuid;
    }

    //퍼미션이 명령어를 사용하는데 걸렸을때 사용하는 함수
    public static EmbedBuilder permissionRequired(String permission){
        EmbedBuilder embed_uuid = new EmbedBuilder();
        embed_uuid.setDescription("" +
                "Permission required : " + permission);
        embed_uuid.setFooter(footer);
        embed_uuid.setColor(Color.red);

        return embed_uuid;
    }

    //유저 서버 입장시 정보를 불러오지 못한 값 출력
    public static EmbedBuilder userJoinServerFailedEmbed(User user){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("" +
                "해당유저( " + user.getAsMention() + " )는 정보를 불러올수 없습니다. " +
                "\n플래닛어스에 등록된 계정이 아니거나 공식 서버가 다운되었을 수 있습니다. ");
        embedBuilder.setFooter(footer);
        embedBuilder.setColor(Color.red);

        return embedBuilder;
    }


}
