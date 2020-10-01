package com.xagu.studio.studiosystem.dao;

import com.xagu.studio.studiosystem.bean.Game;
import com.xagu.studio.studiosystem.bean.GameScript;
import com.xagu.studio.studiosystem.bean.WxAccount;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/29
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface GameRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {
    Game findOneById(String id);


    Integer deleteGamesByIdIn(String[] ids);

    @Query(nativeQuery = true, value = "SELECT * FROM tb_game WHERE tb_game.id NOT IN(SELECT DISTINCT tb_game.id FROM tb_game INNER JOIN tb_wx_game on tb_game.id = tb_wx_game.game_id AND tb_wx_game.wx_id  = ?) AND tb_game.type = ? order by rand() limit 1")
    Game randomGame(String wxId, String type);

    @Query(nativeQuery = true, value = "select count(*) from tb_wx_game where wx_id = ? and game_id = ?")
    Integer isGameOver(String wxId,String gameId);

    /**
     * 标记脚本已运行
     *
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "insert into tb_wx_game values (?,?,?)")
    Integer setGameOver(String id, String wxId, String gameId);


    @Query(nativeQuery = true,value = "SELECT tb_game.* FROM tb_wx_game INNER JOIN tb_game ON tb_wx_game.game_id = tb_game.id WHERE wx_id = ?")
    List<Game> listOverGame(String wxId);

    @Query(nativeQuery = true,value = "SELECT * FROM tb_game WHERE id NOT IN (SELECT tb_game.id FROM tb_wx_game INNER JOIN tb_game ON tb_wx_game.game_id = tb_game.id WHERE wx_id = ?)")
    List<Game> listNotOverGame(String wxId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from tb_wx_game where wx_id = ? and game_id = ?")
    Integer setGameNotOver(String wxId, String gameId);
}
