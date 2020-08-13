package com.xagu.studio.studiosystem;

import com.sun.istack.internal.NotNull;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
class StudioSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMirai(){
        Bot bot = BotFactoryJvm.newBot(Long.parseLong("3358899028"), "990301cq");
        bot.login();
        //输出好友
        Friend xagu = bot.getFriend(2422494482L);
        xagu.sendMessage("test");
        bot.getFriends().forEach(friend -> System.out.println(friend.getId() + ":" + friend.getNick()));
        Events.registerEvents(bot, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus onWxBotMessage(FriendMessageEvent event){
                String msgString = StudioSystemApplicationTests.toString(event.getMessage());
                if (msgString.contains("reply")) {
                    // 引用回复
                    final QuoteReply quote = new QuoteReply(event.getSource());
                    event.getSender().sendMessage(quote.plus("引用回复"));

                }    else if (msgString.contains("recall1")) {
                    event.getSender().sendMessage("你看不到这条消息").recall();
                    // 发送消息马上就撤回. 因速度太快, 客户端将看不到这个消息.

                } else if (msgString.contains("recall2")) {
                    final Job job = event.getSender().sendMessage("3秒后撤回").recallIn(3000);

                    job.cancel(new CancellationException()); // 可取消这个任务

                } else if (msgString.contains("上传图片")) {
                    File file = new File("D:\\桌面\\计算机设计大赛\\QQ图片20200528222311.jpg");
                    if (file.exists()) {
                        final Image image = event.getSender().uploadImage(new File("D:\\桌面\\计算机设计大赛\\QQ图片20200528222311.jpg"));
                        // 上传一个图片并得到 Image 类型的 Message

                        final String imageId = image.getImageId(); // 可以拿到 ID
                        final Image fromId = MessageUtils.newImage(imageId); // ID 转换得到 Image

                        event.getSender().sendMessage(image); // 发送图片
                    }

                } else if (msgString.contains("friend")) {
                    final Future<MessageReceipt<Contact>> future = event.getSender().sendMessageAsync("Async send"); // 异步发送
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    //将图片转换为图片ID
                } else if (msgString.startsWith("convert")) {
                    StringBuilder stringBuilder = new StringBuilder("结果：\n");
                    event.getMessage().forEachContent(msg ->
                            {
                                if (msg instanceof Image) {
                                    stringBuilder.append(((Image) msg).getImageId());
                                    stringBuilder.append("\n");
                                }
                                return Unit.INSTANCE;// kotlin 的所有函数都有返回值. Unit 为最基本的返回值. 请在这里永远返回 Unit
                            }
                    );
                    event.getSender().sendMessage(stringBuilder.toString());
                }
                //保持监听
                return ListeningStatus.LISTENING;
            }

            //处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                throw new RuntimeException("在事件处理中发生异常", exception);
            }


        });

        bot.join(); // 阻塞当前线程直到 bot 离线
    }

    private static String toString(MessageChain chain) {
        return chain.contentToString();
    }
}
