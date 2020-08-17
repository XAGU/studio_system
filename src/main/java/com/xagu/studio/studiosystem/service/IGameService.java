package com.xagu.studio.studiosystem.service;

import com.xagu.studio.studiosystem.bean.Game;
import org.springframework.data.domain.Page;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IGameService {

    boolean addGame(Game game);

    boolean deleteGame(String[] split);

    boolean updateGame(Game game) ;

    Game getGameByid(String id);

    Page<Game> listGame(Integer page, Integer size, String gameName, String type);

    Game randomGame(String imei);

    boolean setGameOver(String wxId, String gameId);

    boolean setGameScript(String gameId, String scriptId);
}
