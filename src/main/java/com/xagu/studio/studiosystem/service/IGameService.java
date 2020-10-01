package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.Game;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IGameService {

    boolean addGame(Game game);

    boolean deleteGame(String[] split);

    boolean updateGame(Game game);

    Game getGameByid(String id);

    Page<Game> listGame(Integer page, Integer size, String gameName, String type);

    Game randomGame(String imei);

    boolean setGameOver(String wxId, String gameId);

    boolean setGameScript(String gameId, String scriptId);

    List<Game> listOverGame(String wxId);

    List<Game> listNotOverGame(String wxId);

    boolean setGameNotOver(String wxId, String gameId);

    boolean updateGameInfo(String id, String adid, String sadid, String packageName);

}
