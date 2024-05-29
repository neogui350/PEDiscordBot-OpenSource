package me.neogui350.command;

import me.neogui350.utilutility.GiveRolesToMember.nationsVerifiedRole;
import me.neogui350.utilutility.GiveRolesToMember.verifiedRole;
import me.neogui350.utilutility.replyEmbeds.ReplyEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.successEmbed;

public class ReloadPlayerDiscordName extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equals("reload_user_data")){

            OptionMapping optionMapping = event.getOption("reload_user_name");
            Member member;
            if (optionMapping == null){
                member = event.getMember();
            } else {
                if (!event.getMember().hasPermission(Permission.MANAGE_ROLES) && (!event.getMember().hasPermission(Permission.ADMINISTRATOR))){
                    EmbedBuilder embedBuilder = ReplyEmbeds.permissionRequired("Permission.MANAGE_ROLES");
                    event.replyEmbeds(embedBuilder.build()).queue();
                    return;
                }
                member = optionMapping.getAsMember();
            }

            try {
                JSONObject jsonObject = Command.getPAPI("discord", "discord", member.getId());
                if (jsonObject.get("status").equals("SUCCESS")){

                    //data에서 json 정보를 가져옴
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                    //맴버 별명 마크닉으로 바꿔줌
                    String memberName = jsonObject1.get("name").toString();
                    member.modifyNickname(memberName).queue();

                    //인증역할
                    boolean verified = verifiedRole.verifyMember(event, member);

                    //국가역할
                    boolean nationsVerified = nationsVerifiedRole.nationsVerifiedMember(event, member, memberName);
                    if (!nationsVerified && !verified){
                        EmbedBuilder embed_uuid = new EmbedBuilder();
                        embed_uuid.setDescription("" +
                                "해당 유저(" + member.getAsMention() + ")는 플래닛어스 디스코드에는 연동이 되어있으나 국가에 가입되어있지 않습니다. " +
                                "\n문제가 계속된다면 서버 관리자로 연락주세요. ");
                        embed_uuid.setFooter("A Discord Bot made by neogui350");
                        embed_uuid.setColor(Color.green);

                        event.replyEmbeds(embed_uuid.build()).queue();
                    } else if (!verified){
                        EmbedBuilder embed_uuid = new EmbedBuilder();
                        embed_uuid.setDescription("정보 찾지 못함\n" +
                                "해당 유저(" + member.getAsMention() + ")는 플래닛어스 디코와 연동이 되어 있지 않습니다. CODE.ERROR.ReloadPlayerDiscordName.java72" +
                                "\n디스코드 봇 관리자로 연락주세요. ");
                        embed_uuid.setFooter("A Discord Bot made by neogui350");
                        embed_uuid.setColor(Color.red);

                        System.out.println("정보 찾지 못함!!" + "해당 유저(" + member.getAsMention() + ")는 플래닛어스 디코와 연동이 되어 있지 않습니다. CODE.ERROR.ReloadPlayerDiscordName.java72");

                        event.replyEmbeds(embed_uuid.build()).queue();
                    }

                } else {
                    EmbedBuilder embed_uuid = new EmbedBuilder();
                    embed_uuid.setDescription("정보 찾지 못함\n" +
                            "해당 유저(" + member.getAsMention() + ")는 플래닛어스 디코와 연동이 되어 있지 않습니다. " +
                            "\n문제가 계속된다면 서버 관리자로 연락주세요. ");
                    embed_uuid.setFooter("A Discord Bot made by neogui350");
                    embed_uuid.setColor(Color.red);

                    System.out.println("정보 찾지 못함!!" + "해당 유저(" + member.getAsMention() + ")는 플래닛어스 디코와 연동이 되어 있지 않습니다. CODE.ERROR.ReloadPlayerDiscordName.java90");

                    event.replyEmbeds(embed_uuid.build()).queue();
                }

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
